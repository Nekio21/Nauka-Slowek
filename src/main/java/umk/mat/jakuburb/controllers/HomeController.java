package umk.mat.jakuburb.controllers;

import javafx.fxml.FXML;
import javafx.scene.CacheHint;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.List;

public class HomeController {

    @FXML
    private VBox cos;

    @FXML
    public void initialize(){
        List<String> list = Font.getFamilies();



        for(String s: list){
            Label label = new Label();

            label.setText(s);
            label.setFont(new Font(s, 36));
            label.setTextFill(Color.rgb(0,30,100));

            cos.spacingProperty().set(20);
            cos.getChildren().add(label);

            label.setCache(true);
            label.setCacheShape(true);
            label.setCacheHint(CacheHint.SPEED);
        }
    }
}
