package umk.mat.jakuburb.controllers;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;
import org.hibernate.Session;
import org.hibernate.query.Query;
import umk.mat.jakuburb.database.MyDatabase;
import umk.mat.jakuburb.database.MyDatabaseInterface;
import umk.mat.jakuburb.encje.User;

import java.util.Map;

public class LoginController extends MyController implements MyDatabaseInterface {

    @FXML
    private TextField loginTF;

    @FXML
    private PasswordField hasloTF;

    private MyDatabase myDatabase;

    private final String queryString = "FROM User u WHERE u.login = :ll AND u.password = :lp";
    public static String PW_KEY_ID = "Super tajne ID";

    private MouseEvent ms;

    @FXML
    public void initialize(){
        loginTF.setOnAction(e->{
            loginMethod();
        });

        hasloTF.setOnAction(e->{
            loginMethod();
        });
    }

    @FXML
    public void loginMethod(MouseEvent mouseEvent){
        ms = mouseEvent;
        loginMethod();
    }

    private void loginMethod(){
        myDatabase = MyDatabase.createDatabase();
        myDatabase.makeSession(this);
    }

    @FXML
    public void registerMethod(MouseEvent mouseEvent){
        change("rejestracja.fxml", mouseEvent);
    }

    @Override
    public Object inside(Session session) {

        Query<User> q = session.createQuery(queryString, User.class);
        User user;

        q.setParameter("ll", loginTF.getText());
        q.setParameter("lp", hasloTF.getText());

        long wynik = q.getResultStream().count();

        if(wynik == 1){
            user = q.getResultStream().toList().get(0);
        }else{
            user = null;
        }

        return user;
    }

    @Override
    public void after(Object wynik) {
        DataSender dataSender = DataSender.initDataSender();
        User result = (User)wynik;

        if(result != null){
            dataSender.add(result, PW_KEY_ID);
            change("home.fxml", ms);
        }else{
            Popup popup = new Popup();
            Label label = new Label("Zle dane logowania :/");

            label.setMinWidth(350);
            label.setMinHeight(200);

            label.setStyle(
                    " -fx-background-color: #12492F77;" +
                    " -fx-text-fill: #FFCA7A;" +
                    " -fx-font-weight: 700;" +
                    " -fx-alignment: center;" +
                    " -fx-font-size: 36px;" +
                    " -fx-padding: 10px 30px 10px 30px;"
            );

            popup.getContent().add(label);

            popup.show(((Node)ms.getSource()).getScene().getWindow());

            PauseTransition schowaj = new PauseTransition(Duration.seconds(2));
            schowaj.setOnFinished(e->{
                popup.hide();
            });

            schowaj.play();
        }
    }
}
