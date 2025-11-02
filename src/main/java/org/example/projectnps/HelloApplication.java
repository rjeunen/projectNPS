package org.example.projectnps;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("homeScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Image atlasLogo = new Image("atlasLogo.jpg");
        stage.getIcons().add(atlasLogo);
        stage.setTitle("project NPS");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}