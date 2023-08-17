package umk.mat.jakuburb.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import umk.mat.jakuburb.encje.Slowko;

public class EdytujZestawController extends MyController{

    @FXML
    private TableView<Slowko> table;

    @FXML
    private TextField slowkoStronaB;

    @FXML
    private TextField slowkoStronaA;


    @FXML
    public void initialize(){
        table.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("stronaA"));
        table.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("stronaB"));
        table.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("cos"));
        table.setEditable(true);
    }


    //TODO: ENTER TEZ MA DZIALAC :>
    @FXML
    public void addMethod(MouseEvent mouseEvent){
        table.getItems().add(new Slowko(slowkoStronaA.getText(),slowkoStronaB.getText(), "cos i cos"));
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
