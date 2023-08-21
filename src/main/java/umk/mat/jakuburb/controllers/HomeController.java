package umk.mat.jakuburb.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.CacheHint;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
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
import org.hibernate.Session;
import umk.mat.jakuburb.database.MyDatabase;
import umk.mat.jakuburb.database.MyDatabaseBox;
import umk.mat.jakuburb.database.MyDatabaseInterface;
import umk.mat.jakuburb.encje.User;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class HomeController extends MyController implements MyDatabaseInterface {

    //TODO: HOMECONTROLER BEDZIE DZIEDZICZONY PRZEZ INNE KONTROLERY,
    // przezco bedzie mniej roboty z redundacja kodu,
    // bo nie bedzie trzeba sie babrac z tym meni


    @FXML
    private Circle profilowe;

    @FXML
    private Label nickLabel;

    private MyDatabase myDatabase;
    private DataSender dataSender;

    private final String qImage = "Select i.image FROM User i WHERE i.id = :myid";

    private User user;


    @FXML
    public void initialize(){
        dataSender = DataSender.initDataSender();
        myDatabase = MyDatabase.createDatabase();
        user = (User)dataSender.get(LoginController.PW_KEY_ID);

        setUI();
    }

    private void setUI(){
        ImagePattern imagePattern;

        if(user.getImage() != null){
            Image image = new Image(new ByteArrayInputStream(user.getImage()));
            imagePattern = new ImagePattern(image);
        }else{
            imagePattern = new ImagePattern(new Image("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRuFrzBJjheuN79uOKBC4DlAgqX0yEpy55lmj0glMs9kCdQlK5sHcrcr2SH1s4ixlYSn50&usqp=CAU"));
        }

        profilowe.setFill(imagePattern);

        nickLabel.setText(user.getLogin());
    }


    @FXML
    public void kalendarzMenuAction(MouseEvent mouseEvent){
        change("calendar.fxml", mouseEvent);
    }

    @FXML
    public void zestawyMenuAction(MouseEvent mouseEvent){

        change("zestawy.fxml", mouseEvent);
    }

    @FXML
    public void zestawyEdytujChwilowoMenuAction(MouseEvent mouseEvent){
        change("edytujZestaw.fxml", mouseEvent);
    }

    @Override
    public Object inside(MyDatabaseBox myDatabaseBox, Session session) {
        

        return null;
    }

    @Override
    public void after(MyDatabaseBox myDatabaseBox, Object wynik) {

    }
}
