package umk.mat.jakuburb.controllers;

import javafx.animation.PauseTransition;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Popup;
import javafx.util.Duration;
import org.hibernate.Session;
import org.hibernate.query.Query;
import umk.mat.jakuburb.controllers.helpers.DataSender;
import umk.mat.jakuburb.controllers.helpers.HomeWaiter;
import umk.mat.jakuburb.controllers.helpers.MyControllerSimple;
import umk.mat.jakuburb.database.MyDatabase;
import umk.mat.jakuburb.database.MyDatabaseBox;
import umk.mat.jakuburb.database.MyDatabaseInterface;
import umk.mat.jakuburb.encje.User;

public class LoginController extends MyControllerSimple implements MyDatabaseInterface {

    @FXML
    private TextField loginTF;

    @FXML
    private PasswordField hasloTF;

    private MyDatabase myDatabase;

    private final String queryUser = "FROM User u WHERE u.login = :ll AND u.password = :lp";
    public static String PW_KEY_ID = "Super tajne ID";

    private Long iloscZestawow = -1L;

    private MouseEvent ms;

    @FXML
    public void initialize(){
        loginTF.setOnAction(e->{
            logIn(e);
        });


        hasloTF.setOnAction(this::logIn);
    }

    @FXML
    public void loginMethod(MouseEvent mouseEvent){
        logIn(mouseEvent);
    }

    private void logIn(Event event){
        myDatabase = MyDatabase.createDatabase();
        MyDatabaseBox myDatabaseBox = new MyDatabaseBox();
        myDatabaseBox.setEvent(event);

        myDatabase.makeSession(myDatabaseBox,this);
    }

    @FXML
    public void registerMethod(MouseEvent mouseEvent){
        change("rejestracja.fxml", mouseEvent);
    }

    @Override
    public Object inside(MyDatabaseBox myDatabaseBox, Session session) {

        Query<User> q = session.createQuery(queryUser, User.class);
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
    public void after(MyDatabaseBox myDatabaseBox, Object wynik) {
        DataSender dataSender = DataSender.initDataSender();
        User result = (User)wynik;

        if(result != null){
            dataSender.add(result, PW_KEY_ID);
            HomeWaiter.checkDishes(result.getId(), myDatabaseBox.getEvent());
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
