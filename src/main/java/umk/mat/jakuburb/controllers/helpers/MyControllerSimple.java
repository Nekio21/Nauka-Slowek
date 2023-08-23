package umk.mat.jakuburb.controllers.helpers;

import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;

public class MyControllerSimple {

    protected void change(String nazwa, Event event){
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
