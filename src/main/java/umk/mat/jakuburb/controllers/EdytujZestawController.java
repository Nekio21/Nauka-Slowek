package umk.mat.jakuburb.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;

public class EdytujZestawController extends MyController{

    @FXML
    private TableView table;

    @FXML
    public void initialize(){

    }

    @FXML
    public void homeMenuAction(MouseEvent mouseEvent){
        change("home.fxml", mouseEvent);
    }

    @FXML
    public void kalendarzMenuAction(MouseEvent mouseEvent){
        change("calendar.fxml", mouseEvent);
    }

    @FXML
    public void zestawyMenuAction(MouseEvent mouseEvent){

        change("zestawy.fxml", mouseEvent);
    }
}
