package umk.mat.jakuburb.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.hibernate.Session;
import umk.mat.jakuburb.database.MyDatabase;
import umk.mat.jakuburb.database.MyDatabaseBox;
import umk.mat.jakuburb.database.MyDatabaseInterface;
import umk.mat.jakuburb.encje.User;

import java.io.*;
import java.nio.file.Files;

public class RegisterController extends MyController implements MyDatabaseInterface {

    @FXML
    private TextField loginTF;

    @FXML
    private PasswordField hasloTF;

    @FXML
    private PasswordField hasloTwiceTF;

    @FXML
    private Circle kolko;

    private MyDatabase myDatabase;
    private File image;

    private User user;
    private String login;
    private String haslo;
    private byte[] byteImage;




    @FXML
    public void initialize(){

    }

    @FXML
    public void returnToLogin(MouseEvent mouseEvent){
        change("login.fxml", mouseEvent);
    }


    @FXML
    public void registerMethod(MouseEvent mouseEvent){
        myDatabase = MyDatabase.createDatabase();

        if(!(loginTF.getText().equals("") || hasloTF.getText().equals(""))) {
            myDatabase.makeSession(this);
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
            } catch (FileNotFoundException e) {
                System.out.println("Blad :( FNFE: " + e);
            } catch (Exception e){
                System.out.println("Blad :( E: " + e);
            }


        }
    }

    @Override
    public Object inside(MyDatabaseBox myDatabaseBox, Session session) {
        user = new User();

        user.setLogin(loginTF.getText());
        user.setPassword(hasloTF.getText());

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

    }
}
