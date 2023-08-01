package umk.mat.jakuburb.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import umk.mat.jakuburb.encje.Slowko;

public class EdytujZestawController extends MyController{

    @FXML
    private TableView<Slowko> table;


    @FXML
    public void initialize(){
        table.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("stronaA"));
        table.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("stronaB"));
        table.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("cos"));


        table.getItems().add(new Slowko("Pies","Dog", "cos i cos"));
        table.getItems().add(new Slowko("Kot","Cat", "cos i cos"));
        table.getItems().add(new Slowko("Mysz","Mouse", "cos i cos"));
        table.getItems().add(new Slowko("Komputer","Computer", "cos i cos"));
        table.getItems().add(new Slowko("Rower","Bike", "cos i cos"));
        table.getItems().add(new Slowko("Cebula","Anion", "cos i cos"));

        table.getItems().add(new Slowko("Pies","Dog", "cos i cos"));
        table.getItems().add(new Slowko("Kot","Cat", "cos i cos"));
        table.getItems().add(new Slowko("Mysz","Mouse", "cos i cos"));
        table.getItems().add(new Slowko("Komputer","Computer", "cos i cos"));
        table.getItems().add(new Slowko("Rower","Bike", "cos i cos"));
        table.getItems().add(new Slowko("Cebula","Anion", "cos i cos"));

        table.getItems().add(new Slowko("Pies","Dog", "cos i cos"));
        table.getItems().add(new Slowko("Kot","Cat", "cos i cos"));
        table.getItems().add(new Slowko("Mysz","Mouse", "cos i cos"));
        table.getItems().add(new Slowko("Komputer","Computer", "cos i cos"));
        table.getItems().add(new Slowko("Rower","Bike", "cos i cos"));
        table.getItems().add(new Slowko("Cebula","Anion", "cos i cos"));

        table.getItems().add(new Slowko("Pies","Dog", "cos i cos"));
        table.getItems().add(new Slowko("Kot","Cat", "cos i cos"));
        table.getItems().add(new Slowko("Mysz","Mouse", "cos i cos"));
        table.getItems().add(new Slowko("Komputer","Computer", "cos i cos"));
        table.getItems().add(new Slowko("Rower","Bike", "cos i cos"));
        table.getItems().add(new Slowko("Cebula","Anion", "cos i cos"));

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
