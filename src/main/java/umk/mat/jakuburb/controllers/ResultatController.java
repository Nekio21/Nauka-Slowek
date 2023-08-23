package umk.mat.jakuburb.controllers;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import umk.mat.jakuburb.controllers.helpers.MyController;

public class ResultatController extends MyController {


    private int procent;
    private int dobre;
    private int zle;

    //TODO: STowrzyc klase z trybami gry :>
    //private TrybGry trybGry;


    @FXML
    public void returnMethod(MouseEvent mouseEvent){
        change("zestawy.fxml", mouseEvent);
    }

}
