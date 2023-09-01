package umk.mat.jakuburb.controllers.helpers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import umk.mat.jakuburb.controllers.LoginController;
import umk.mat.jakuburb.controllers.helpers.MyControllerSimple;
import umk.mat.jakuburb.database.MyDatabase;
import umk.mat.jakuburb.encje.User;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;

public abstract class MyController extends MyControllerSimple {

    @FXML
    private Circle profilowe;

    @FXML
    private Label nickLabel;

    protected MyDatabase myDatabase;
    protected DataSender dataSender;
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
            profilowe.setFill(imagePattern);
        }else{
            try {
                File file = new File("src/main/resources/umk/mat/jakuburb/assets/ttt.png");
                imagePattern = new ImagePattern(new Image(new FileInputStream(file)));
                profilowe.setFill(imagePattern);
            } catch (Exception e) {
                System.out.println("Nie udalo sie. e: " + e);
            }
        }



        nickLabel.setText(user.getLogin());
    }

    @FXML
    public void homeMenuAction(MouseEvent mouseEvent){
        DataSender dataSender = DataSender.initDataSender();

        HomeWaiter.checkDishes(((User)dataSender.get(LoginController.PW_KEY_ID)).getId(), mouseEvent);
    }

    @FXML
    public void zestawyMenuAction(MouseEvent mouseEvent){

        change("zestawy.fxml", mouseEvent);
    }

    @FXML
    public void kalendarzMenuAction(MouseEvent mouseEvent){
        change("calendar.fxml", mouseEvent);
    }

    @FXML
    public void TrenerMenuAction(MouseEvent mouseEvent){
        change("trener.fxml", mouseEvent);
    }


    @FXML
    public void logoutMenuAction(MouseEvent mouseEvent){
        dataSender.clear();
        change("login.fxml", mouseEvent);
    }
}
