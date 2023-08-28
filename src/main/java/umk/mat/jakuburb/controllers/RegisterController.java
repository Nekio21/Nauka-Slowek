package umk.mat.jakuburb.controllers;

import javafx.animation.PauseTransition;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.hibernate.Session;
import org.hibernate.query.Query;
import umk.mat.jakuburb.controllers.helpers.MyControllerSimple;
import umk.mat.jakuburb.database.MyDatabase;
import umk.mat.jakuburb.database.MyDatabaseBox;
import umk.mat.jakuburb.database.MyDatabaseInterface;
import umk.mat.jakuburb.encje.User;

import java.io.*;
import java.nio.file.Files;
import java.time.LocalDateTime;

public class RegisterController extends MyControllerSimple implements MyDatabaseInterface {

    @FXML
    private TextField loginTF;

    @FXML
    private PasswordField hasloTF;

    @FXML
    private PasswordField hasloTwiceTF;

    @FXML
    private Circle kolko;

    @FXML
    private Label wybierzObraz;

    private MyDatabase myDatabase;
    private File image;

    private User user;
    private String login;
    private String haslo;
    private byte[] byteImage;

    @FXML
    public void returnToLogin(MouseEvent mouseEvent){
        change("login.fxml", mouseEvent);
    }


    @FXML
    public void registerMethod(MouseEvent mouseEvent){
        myDatabase = MyDatabase.createDatabase();

        if(!(loginTF.getText().equals("") || hasloTF.getText().equals("")) && (hasloTF.getText().equals(hasloTwiceTF.getText()))) {

            myDatabase.makeSession(new MyDatabaseBox(mouseEvent),this);
        }else{
            popup("Zle wpisane Dane :<",mouseEvent);
        }
    }

    @FXML
    public void chooseImage(MouseEvent mouseEvent){
        FileChooser fileChooser = new FileChooser();

        fileChooser.setTitle("Wybierz swoja ulubiona fotke :)");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Assets", "*.jpg", "*.jpeg", "*.png", "*.gif"));

        image = fileChooser.showOpenDialog(new Stage());

        System.out.println(image.getAbsolutePath());

        if(image.isFile()) {

            try {
                ImagePattern imagePattern = new ImagePattern(new Image(new FileInputStream(image)));
                kolko.setFill(imagePattern);
                wybierzObraz.setVisible(false);
            } catch (FileNotFoundException e) {
                System.out.println("Blad :( FNFE: " + e);
            } catch (Exception e){
                System.out.println("Blad :( E: " + e);
            }


        }
    }

    @Override
    public Object inside(MyDatabaseBox myDatabaseBox, Session session) {
        Query<Long> iloscQuery = session.createQuery("SELECT COUNT(*) From User where login = :l", Long.class);
        iloscQuery.setParameter("l", loginTF.getText());

        long wynik = iloscQuery.getSingleResult();

        if(wynik > 0){
            return false;
        }

        user = new User();

        user.setLogin(loginTF.getText());
        user.setPassword(hasloTF.getText());
        user.setLogowanieDate(LocalDateTime.now());

        try {
            byteImage = Files.readAllBytes(image.toPath());
        } catch (Exception e){
            byteImage = null;
            System.out.println("blad: " + e);
        }

        user.setImage(byteImage);

        session.persist(user);

        return true;
    }

    @Override
    public void after(MyDatabaseBox myDatabaseBox, Object wynik) {
        Boolean result = (Boolean) wynik;

        if(result == false) {
            popup("Taki User juz istnieje :<", myDatabaseBox.getEvent());
        }else{
            popup("Konto założone", myDatabaseBox.getEvent());
        }
    }


}
