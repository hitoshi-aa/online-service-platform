package com.onlineservise.ui;

import com.onlineservise.dto.MasterDTO;
import com.onlineservise.service.MasterService;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    private ComboBox<String> roleBox;

    @FXML
    private TextField nameField;

    @FXML
    private PasswordField passwordField;

    private MasterService masterService;

    @FXML
    public void initialize() {
        masterService = SpringContext.getContext().getBean(MasterService.class);
        
        roleBox.setItems(
            FXCollections.observableArrayList(
                "Manager",
                "Master"
            )
        );
        roleBox.setValue("Manager");
    }

    @FXML
    public void login() {
        String role = roleBox.getValue();
        String login = nameField.getText().trim();
        String password = passwordField.getText().trim();

        if (login.isEmpty() || password.isEmpty()) {
            showError("Fields cannot be empty");
            return;
        }

        Task<Boolean> loginTask = new Task<>() {
            @Override
            protected Boolean call() throws Exception {
                System.out.println("[DEBUG] Attempting login for role: " + role + ", login: " + login);
                if (role.equals("Manager")) {
                    boolean isManager = login.equals("manager") && password.equals("admin123");
                    System.out.println("[DEBUG] Manager auth result: " + isManager);
                    return isManager;
                } else {
                    boolean isAuthenticated = masterService.authenticate(login, password);
                    System.out.println("[DEBUG] Master auth result for " + login + ": " + isAuthenticated);
                    if (!isAuthenticated) {
                        MasterDTO master = masterService.getMasterByLogin(login);
                        if (master == null) {
                            System.out.println("[DEBUG] Master with login '" + login + "' NOT FOUND in DB");
                        } else {
                            System.out.println("[DEBUG] Master FOUND in DB: " + master.getName() + " (ID: " + master.getId() + ")");
                        }
                    }
                    return isAuthenticated;
                }
            }
        };

        loginTask.setOnSucceeded(event -> {
            if (loginTask.getValue()) {
                if (role.equals("Manager")) {
                    MainSession.username = "Manager";
                    MainSession.role = "Manager";
                } else {
                    MasterDTO master = masterService.getMasterByLogin(login);
                    MainSession.username = master.getName();
                    MainSession.role = "Master";
                }
                try {
                    openMainView();
                } catch (Exception e) {
                    showError("Error opening main view: " + e.getMessage());
                }
            } else {
                showError("Wrong credentials");
            }
        });

        loginTask.setOnFailed(event -> {
            showError("Login failed: " + loginTask.getException().getMessage());
        });

        new Thread(loginTask).start();
    }

    private void openMainView() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/MainView.fxml"));
        Scene scene = new Scene(loader.load(), 1100, 600);
        Stage stage = (Stage) roleBox.getScene().getWindow();
        stage.setScene(scene);
    }

    private void showError(String text) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login Error");
            alert.setHeaderText(null);
            alert.setContentText(text);
            alert.showAndWait();
        });
    }
}
