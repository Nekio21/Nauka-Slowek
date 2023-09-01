package umk.mat.jakuburb.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import org.hibernate.Session;
import org.hibernate.cache.spi.QueryResultsCache;
import org.hibernate.query.Query;
import umk.mat.jakuburb.controllers.helpers.DataSender;
import umk.mat.jakuburb.controllers.helpers.MyController;
import umk.mat.jakuburb.database.MyDatabase;
import umk.mat.jakuburb.database.MyDatabaseBox;
import umk.mat.jakuburb.database.MyDatabaseInterface;
import umk.mat.jakuburb.encje.User;
import umk.mat.jakuburb.encje.ZestawySlowek;

import java.io.ByteArrayInputStream;
import java.util.List;

import static umk.mat.jakuburb.controllers.ZestawyController.ZESTAW_KEY_ID;

public class HomeController extends MyController{


    private User user;

    @FXML
    public void initialize(){
        super.initialize();

        user = (User)dataSender.get(LoginController.PW_KEY_ID);
    }

    @FXML
    public void createNewPackageMethod(MouseEvent mouseEvent){
        dataSender.add(null, ZESTAW_KEY_ID);
        change("edytujZestaw.fxml", mouseEvent);
    }
}
