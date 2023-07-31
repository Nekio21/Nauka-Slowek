package umk.mat.jakuburb.controllers;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

public class CalendarController extends MyController{

    @FXML
    public void homeMenuAction(MouseEvent mouseEvent){
        change("home.fxml", mouseEvent);
    }

    @FXML
    public void zestawyMenuAction(MouseEvent mouseEvent){

        change("zestawy.fxml", mouseEvent);
    }

    @FXML
    public void zestawyEdytujChwilowoMenuAction(MouseEvent mouseEvent){
        change("edytujZestaw.fxml", mouseEvent);
    }
}
