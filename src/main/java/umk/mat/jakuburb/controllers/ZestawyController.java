package umk.mat.jakuburb.controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.MouseEvent;

import java.util.List;

public class ZestawyController extends MyController{

    @FXML
    private ChoiceBox<String> sortujChoiceBox;

    private String[] sortujList = new String[]{
            "Alfabetycznie",
            "Przeciwienstwo Alfabetyczne",
            "Data rosnaca",
            "Data malejaca",
            "poziom wiedzy rosnoca",
            "poziom wiedzy malejaco"
    };

    @FXML
    public void initialize(){

        sortujChoiceBox.getItems().addAll(sortujList);

    }


    @FXML
    public void kalendarzMenuAction(MouseEvent mouseEvent){
        change("calendar.fxml", mouseEvent);
    }

    @FXML
    public void homeMenuAction(MouseEvent mouseEvent){

        change("home.fxml", mouseEvent);
    }

    @FXML
    public void zestawyEdytujChwilowoMenuAction(MouseEvent mouseEvent){
        change("edytujZestaw.fxml", mouseEvent);
    }
}
