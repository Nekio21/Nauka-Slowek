package umk.mat.jakuburb.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import org.hibernate.Session;
import org.hibernate.query.Query;
import umk.mat.jakuburb.controllers.helpers.MyController;
import umk.mat.jakuburb.database.MyDatabase;
import umk.mat.jakuburb.database.MyDatabaseBox;
import umk.mat.jakuburb.database.MyDatabaseInterface;
import umk.mat.jakuburb.encje.Gra;
import umk.mat.jakuburb.encje.SlowkaGra;
import umk.mat.jakuburb.encje.User;

import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.function.BinaryOperator;

import static umk.mat.jakuburb.controllers.PlayController.GRA;

public class ResultatController extends MyController implements MyDatabaseInterface {

    @FXML
    private Label gratulacjeOpis;

    @FXML
    private Label gratulacje;

    private int procent;
    private int dobre;
    private int zle;

    //TODO: STowrzyc klase z trybami gry :>
    //private TrybGry trybGry;

    private Gra gra;

    private String text;

    private Random random;

    private User user;


    private String[] beggin = new String[]{
            "Super, że kolejny raz ćwiczysz z naszym programem :) ",
            "Nawet nie wiesz, jakie przysługi robisz swojemu mózgowi na przyszłość!!! ",
            "Kolejny krok za tobą, jesteś jak Armstrong na Księżycu, stawiasz krok za krokiem ku lepszej przyszłości. BRAWO!  ",
            "Dobrze, że kolejny raz możemy się spotkać przy podsumowaniu. ",
            "Nawet jeśli Ci się nie wydaje, to naprawdę robisz olbrzymie postępy!!!! "
    };



    private String[] end = new String[]{
            "Trzymaj tak dalej i pamiętaj, że wielki sukces to zasługa małych kroków :) ",
            "Każdą próbę, udaną mniej lub bardziej, zbliżasz się do zwycięstwa. ",
            "Widzimy się na kolejnym teście ;) ",
            "Tak trzymaj, widzimy się niedługo... ",
            "Robisz duże postępy, trzymaj tak dalej :) "
    };

    private String[] startTalkingAboutData = new String[]{
            "Pozwól mi powiedzieć troszeczkę o danych... ",
            "A teraz przejdźmy do szczegółów Twojej rozgrywki. ",
            "Jeśli chodzi o twarde dane, ",
            "Warto powiedzieć, że ",
            "A teraz przyjrzyjmy się bliżej danym... "
    };

    @FXML
    public void initialize(){
        super.initialize();

        gra = (Gra)dataSender.get(GRA);
        user = (User)dataSender.get(LoginController.PW_KEY_ID);
        random = new Random();

        myDatabase = MyDatabase.createDatabase();
        myDatabase.makeSession(this);
    }


    private String information(Gra gra){
        int ilosc = gra.getSlowkaGraList().size();
        int dobrych = 0;
        int zlych = 0;
        int punkty = 0;

        String data = gra.getDataGry().toString();
        String info = gra.getGameMode().toString();


        for(SlowkaGra sg: gra.getSlowkaGraList()){
            if(sg.getDobrzeCzyZle()){
                dobrych++;
            } else{
                zlych++;
            }
            punkty += sg.getSlowka().getPunkty();
        }

        String text = "";

        text += startTalkingAboutData[random.nextInt(5)];

        text += "Ilość słówek, które brały udział w teście, to " + ilosc + ". ";
        text += "Dobrych odpowiedzi było " + dobrych + ", natomiast złych " + zlych + ". ";
        text += "Grałeś w trybie " + info + ". ";
        text += "Grę zacząłeś " + data + ". ";
        text += "Dzisiejszy wysiłek sprawił, że ze słówek, które przerabiałeś dzisiaj, masz łącznie " + punkty + ". BRAWO! ";

        return text;
    }

    @FXML
    public void returnMethod(MouseEvent mouseEvent){
        dataSender.add(null, GRA);
        change("zestawy.fxml", mouseEvent);
    }


    @Override
    public Object inside(MyDatabaseBox myDatabaseBox, Session session) {
        Query<SlowkaGra> query = session.createQuery("SELECT g.slowkaGraList FROM Gra g WHERE g.idGra = :id", SlowkaGra.class);
        query.setParameter("id", gra.getIdGra());

        List<SlowkaGra> sg = query.getResultList();

        if(sg != null) {
            gra.setSlowkaGraList(sg);
        }

        return sg;
    }

    @Override
    public void after(MyDatabaseBox myDatabaseBox, Object wynik) {
        text = beggin[random.nextInt(5)] + information(gra) + end[random.nextInt(5)];

        gratulacjeOpis.setText(text);

        gratulacje.setText("Gratulacje " + user.getLogin());
    }
}
