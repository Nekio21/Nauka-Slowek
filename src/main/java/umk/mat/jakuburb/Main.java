package umk.mat.jakuburb;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import umk.mat.jakuburb.controllers.MyController;
import umk.mat.jakuburb.database.MyDatabase;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

public class Main extends Application {

    private Parent pane;
    private MyDatabase database;

    @Override
    public void start(Stage stage) throws Exception {
        try {
            startUI(stage);

            database = MyDatabase.createDatabase();
        }catch (Exception e){
            System.out.println("blad: " + e);
        }
    }

    @Override
    public void stop(){
        System.out.println("zegnam :(");
        database.stopDatabase();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

    public void startUI(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        URL url = new File("src/main/resources/umk/mat/jakuburb/login.fxml").toURI().toURL();
        URL url2 = new File("src/main/resources/umk/mat/jakuburb/main.css").toURI().toURL();

        loader.setClassLoader(getClass().getClassLoader());

        pane = loader.load(url);

        Scene scene = new Scene(pane, 1040,630);

//        scene.getStylesheets().add(getClass().getResource("main.css").toExternalForm());
        scene.getStylesheets().add(url2.toString());

        stage.setMinWidth(1040);
        stage.setMinHeight(630);
        stage.setTitle("Nauka słowek :>");
        stage.setScene(scene);
        stage.show();
    }
}