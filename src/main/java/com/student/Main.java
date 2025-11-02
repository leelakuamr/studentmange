package com.student;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        java.net.URL url = getClass().getResource("/com/student/view/MainView.fxml");
        if (url == null) {
            url = getClass().getClassLoader().getResource("com/student/view/MainView.fxml");
        }
        if (url == null) {
            throw new java.io.FileNotFoundException("FXML file not found: MainView.fxml");
        }
        FXMLLoader loader = new FXMLLoader(url);
        Parent root = loader.load();
        primaryStage.setTitle("Student Management System");
        primaryStage.setScene(new Scene(root, 700, 550));
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
    