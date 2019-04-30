package fx.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;

/**
 * @author Arthur Kupriyanov
 */
public class EncodeApplication extends Application {
    public static void main(String[] args){
        launch(args);
    }

    public void run(){
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        URL url = getClass().getClassLoader().getResource("fxml/encode-menu.fxml");
        if (url == null) throw new FileNotFoundException("Файл fxml не был найден!");
        Parent root = FXMLLoader.load(url);
        primaryStage.setTitle("HEX Converter");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
