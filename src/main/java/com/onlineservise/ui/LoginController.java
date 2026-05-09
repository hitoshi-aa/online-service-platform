package com.onlineservise.ui;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    private ComboBox<String> roleBox;

    @FXML
    private TextField nameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    public void initialize() {

        roleBox.setItems(

            FXCollections.observableArrayList(
                "Manager",
                "Master"
            )
        );

        roleBox.setValue("Manager");
    }

    @FXML
    public void login() throws Exception {

        String role =
            roleBox.getValue();

        String login =
            nameField.getText();

        String password =
            passwordField.getText();

        if (role.equals("Manager")) {

            if (
                !login.equals("manager")
                    ||
                    !password.equals("admin123")
            ) {

                showError(
                    "Wrong manager credentials"
                );

                return;
            }

            MainSession.username =
                "Manager";
        }

        if (role.equals("Master")) {

            boolean validMaster = false;

            if (
                login.equals("andrii")
                    &&
                    password.equals("andrii123")
            ) {

                MainSession.username =
                    "Andrii Kovalenko";

                validMaster = true;
            }

            if (
                login.equals("dmytro")
                    &&
                    password.equals("dmytro123")
            ) {

                MainSession.username =
                    "Dmytro Hrytsenko";

                validMaster = true;
            }

            if (
                login.equals("ivan")
                    &&
                    password.equals("ivan123")
            ) {

                MainSession.username =
                    "Ivan Melnyk";

                validMaster = true;
            }

            if (
                login.equals("olena")
                    &&
                    password.equals("olena123")
            ) {

                MainSession.username =
                    "Olena Bondar";

                validMaster = true;
            }

            if (!validMaster) {

                showError(
                    "Wrong master credentials"
                );

                return;
            }
        }

        MainSession.role = role;

        FXMLLoader loader =
            new FXMLLoader(
                getClass().getResource(
                    "/ui/MainView.fxml"
                )
            );

        Scene scene =
            new Scene(loader.load(), 1100, 600);

        Stage stage =
            (Stage)
                roleBox
                    .getScene()
                    .getWindow();

        stage.setScene(scene);
    }

    private void showError(String text) {

        Alert alert =
            new Alert(Alert.AlertType.ERROR);

        alert.setTitle("Login Error");

        alert.setHeaderText(null);

        alert.setContentText(text);

        alert.showAndWait();
    }
}