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

        System.out.println(
            "========== BCrypt HASHES =========="
        );

        System.out.println(
            "manager -> "
                +
                "$2a$10$N9qo8uLOickgx2ZMRZo5i.ejZAg/P6MqxsVXni4eWh05rq6ArlT2K"
        );

        System.out.println(
            "andrii123 -> "
                +
                "$2a$10$HXL9DyXN6CB5NzkWptJX1eW7vrLRpO11eQ8sC2cv2U5qL6tKDFmsG"
        );

        System.out.println(
            "dmytro123 -> "
                +
                "$2a$10$Um39LW0WRw3ctgMo4/00lux1NqvQZ10CtcVAnBGMee0lyre1Pbq8G"
        );

        System.out.println(
            "ivan123 -> "
                +
                "$2a$10$qbiJZ/zN/wJaxbmE5puq2.brgZvklJ/DGvhJLh5knbQzkWCGLjGiqS"
        );

        System.out.println(
            "olena123 -> "
                +
                "$2a$10$AtlgbIAMssTe0vigLUnJPum1N3v6yDzDXUFPXD.BbgUW4tDn7aqaO"
        );

        System.out.println(
            "=================================="
        );

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
            nameField.getText()
                .trim()
                .toLowerCase();

        String password =
            passwordField.getText()
                .trim();

        if (
            role.equals("Manager")
                &&
                login.equals("manager")
                &&
                password.equals("admin123")
        ) {

            MainSession.username =
                "Manager";

            MainSession.role =
                "Manager";

            openMainView();

            return;
        }

        if (
            role.equals("Master")
        ) {

            if (
                login.equals("andrii")
                    &&
                    password.equals("andrii123")
            ) {

                MainSession.username =
                    "Andrii Kovalenko";

                MainSession.role =
                    "Master";

                openMainView();

                return;
            }

            if (
                login.equals("dmytro")
                    &&
                    password.equals("dmytro123")
            ) {

                MainSession.username =
                    "Dmytro Hrytsenko";

                MainSession.role =
                    "Master";

                openMainView();

                return;
            }

            if (
                login.equals("ivan")
                    &&
                    password.equals("ivan123")
            ) {

                MainSession.username =
                    "Ivan Melnyk";

                MainSession.role =
                    "Master";

                openMainView();

                return;
            }

            if (
                login.equals("olena")
                    &&
                    password.equals("olena123")
            ) {

                MainSession.username =
                    "Olena Bondar";

                MainSession.role =
                    "Master";

                openMainView();

                return;
            }
        }

        showError(
            "Wrong credentials"
        );
    }

    private void openMainView() throws Exception {

        FXMLLoader loader =
            new FXMLLoader(
                getClass().getResource(
                    "/ui/MainView.fxml"
                )
            );

        Scene scene =
            new Scene(
                loader.load(),
                1100,
                600
            );

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