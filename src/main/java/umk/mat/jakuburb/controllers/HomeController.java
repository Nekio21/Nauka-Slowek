package umk.mat.jakuburb.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.CacheHint;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class HomeController {

    @FXML
    private Circle profilowe;

    @FXML
    public void initialize(){
        ImagePattern imagePattern = new ImagePattern(new Image("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRuFrzBJjheuN79uOKBC4DlAgqX0yEpy55lmj0glMs9kCdQlK5sHcrcr2SH1s4ixlYSn50&usqp=CAU"));

        profilowe.setFill(imagePattern);
    }

    @FXML
    public void kalendarzMenuAction(MouseEvent mouseEvent){

        try {
            Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();

            FXMLLoader loader = new FXMLLoader();
            URL url = new File("src/main/resources/umk/mat/jakuburb/calendar.fxml").toURI().toURL();
            URL url2 = new File("src/main/resources/umk/mat/jakuburb/main.css").toURI().toURL();

            loader.setClassLoader(getClass().getClassLoader());
            Pane pane = loader.load(url);

            Scene scene = new Scene(pane, 1040, 630);
            scene.getStylesheets().add(url2.toString());

            stage.setScene(scene);
            stage.show();
        }catch (Exception e){
            System.out.println(e);
        }
    }
}
