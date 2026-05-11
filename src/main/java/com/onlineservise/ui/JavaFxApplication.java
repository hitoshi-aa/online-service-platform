package com.onlineservise.ui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class JavaFxApplication extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader loader =
            new FXMLLoader(
                getClass().getResource(
                    "/ui/LoginView.fxml"
                )
            );

        Scene scene =
            new Scene(loader.load(), 500, 350);

        stage.setTitle(
            "Платформа онлайн-замовлення сервісних послуг"
        );

        stage.setScene(scene);

        stage.show();

        stage.setOnCloseRequest(event -> {

            Platform.exit();

            System.exit(0);
        });
    }

    public static void main(String[] args) {

        launch(args);
    }
}