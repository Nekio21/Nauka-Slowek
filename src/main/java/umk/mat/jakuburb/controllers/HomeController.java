package umk.mat.jakuburb.controllers;

import javafx.fxml.FXML;
import javafx.scene.CacheHint;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;

import java.io.File;
import java.net.MalformedURLException;
import java.util.List;

public class HomeController {

    @FXML
    private Circle profilowe;

    @FXML
    public void initialize(){
        ImagePattern imagePattern = new ImagePattern(new Image("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRuFrzBJjheuN79uOKBC4DlAgqX0yEpy55lmj0glMs9kCdQlK5sHcrcr2SH1s4ixlYSn50&usqp=CAU"));

        profilowe.setFill(imagePattern);
    }
}
