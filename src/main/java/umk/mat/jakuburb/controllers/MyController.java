package umk.mat.jakuburb.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public abstract class MyController {


    protected void change(String nazwa, MouseEvent event){
        try {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            FXMLLoader loader = new FXMLLoader();
            URL url = new File("src/main/resources/umk/mat/jakuburb/" + nazwa).toURI().toURL();
            URL url2 = new File("src/main/resources/umk/mat/jakuburb/main.css").toURI().toURL();

            //loader.setClassLoader(getClass().getClassLoader());

            Pane pane = loader.load(url);

            Scene scene = new Scene(pane, stage.getScene().getWidth(), stage.getScene().getHeight());
            scene.getStylesheets().add(url2.toString());

            stage.setMinWidth(1040);
            stage.setMinHeight(630);

            stage.setScene(scene);
            stage.show();
        }catch (Exception e){
            System.out.println(e);
        }
    }

}
