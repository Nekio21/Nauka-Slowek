package umk.mat.jakuburb.controllers.helpers;

import javafx.animation.PauseTransition;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;

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

    protected void popup(String info, Event event){
        Popup popup = new Popup();
        Label label = new Label(info);

        label.setMinWidth(350);
        label.setMinHeight(200);

        label.setStyle(
                " -fx-background-color: #12492F77;" +
                        " -fx-text-fill: #FFCA7A;" +
                        " -fx-font-weight: 700;" +
                        " -fx-alignment: center;" +
                        " -fx-font-size: 36px;" +
                        " -fx-padding: 10px 30px 10px 30px;"
        );

        popup.getContent().add(label);

        popup.show(((Node) event.getSource()).getScene().getWindow());

        PauseTransition schowaj = new PauseTransition(Duration.seconds(2));
        schowaj.setOnFinished(e -> {
            popup.hide();
        });

        schowaj.play();
    }
}
