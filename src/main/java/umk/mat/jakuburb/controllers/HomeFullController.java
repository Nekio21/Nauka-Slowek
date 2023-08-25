package umk.mat.jakuburb.controllers;

import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.hibernate.Session;
import org.hibernate.query.Query;
import umk.mat.jakuburb.controllers.helpers.MyController;
import umk.mat.jakuburb.database.MyDatabaseBox;
import umk.mat.jakuburb.database.MyDatabaseInterface;
import umk.mat.jakuburb.database.StanyDatabase;
import umk.mat.jakuburb.encje.Slowka;
import umk.mat.jakuburb.encje.User;
import umk.mat.jakuburb.encje.ZestawySlowek;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.*;

import static umk.mat.jakuburb.controllers.ZestawyController.ZESTAW_KEY_ID;

public class HomeFullController extends MyController implements MyDatabaseInterface {

    //TODO: tabela Gry id usera, nazwa gry, data gry, dobre, zle, id_slowkaGra,
    //TODO: slowkaGRA: id_slowkaGra, dobrze czy zle(boolean), idSlowka

    @FXML
    private Label wynikSg;

    @FXML
    private Label apwzz;

    @FXML
    private HBox zestawyHBox;

    @FXML
    private ImageView wykres;

    @FXML
    private Label wybraneSlowko;

    @FXML
    private Label worstLabel3;

    @FXML
    private Label worstLabel2;

    @FXML
    private Label worstLabel1;

    @FXML
    private Label bestLabel3;

    @FXML
    private Label bestLabel2;

    @FXML
    private Label bestLabel1;

    private final String zestawyQuery = "SELECT u.zestawySlowek From User u where u.id = :id";
    private final String losoweSlowkoQuery = "From Slowka s JOIN s.zestawySlowek z JOIN z.uzytkownicy u where u.login = :name order by random() limit 1";
    private final String najtrudniejszeSlowkaQuery = "From Slowka s JOIN s.zestawySlowek z JOIN z.uzytkownicy u where u.id = :id order by zleOdpowiedzi limit 3";
    private final String najlatwiejszeSlowkaQuery = "From Slowka s JOIN s.zestawySlowek z JOIN z.uzytkownicy u where u.id = :id order by dobreOdpowiedzi limit 3";
    private User user;
    private int procent;

    private int[][] stanLabel = {{0,0,0},{0,0,0}};

    @FXML
    public void initialize(){
        super.initialize();
        user = (User)dataSender.get(LoginController.PW_KEY_ID);
        myDatabase.makeSession(new MyDatabaseBox(StanyDatabase.GET_ZESTAWY), this);
        myDatabase.makeSession(new MyDatabaseBox(StanyDatabase.RANDOM_WORD), this);
        myDatabase.makeSession(new MyDatabaseBox(StanyDatabase.THE_BEST), this);
        myDatabase.makeSession(new MyDatabaseBox(StanyDatabase.THE_WORST), this);
    }

    @FXML
    public void zestawyMethod(MouseEvent mouseEvent){
        change("zestawy.fxml", mouseEvent);
    }

    private Object getZestawyInside(MyDatabaseBox myDatabaseBox, Session session){
        Query<ZestawySlowek> query = session.createQuery(zestawyQuery, ZestawySlowek.class);
        query.setParameter("id", user.getId());
        user.setZestawySlowek(query.getResultList());

        return user.getZestawySlowek();
    }

    private void getZestawyAfter(MyDatabaseBox myDatabaseBox, Object wynik){
        procent = ileProcent();
        wynikSg.setText(procent + "%");
        apwzz.setText("Aktualny poziom wiedzy z zestawów to " + procent + "%");

        File file;

        if(procent >= 10) {
            file = new File("src/main/resources/umk/mat/jakuburb/assets/kolko5/kolko" + procent + ".png");
        }else{
            file = new File("src/main/resources/umk/mat/jakuburb/assets/kolko5/kolko0" + procent + ".png");
        }
        try {
            wykres.setImage(new Image(new FileInputStream(file)));
            wykres.setFitWidth(326);
            wykres.setFitHeight(326);
        } catch (FileNotFoundException e) {
            System.out.println("Problem :( " + e);
        }

        setZestawyHBox();
    }

    private Object getRandomWordInside(MyDatabaseBox myDatabaseBox, Session session){
        Query<Slowka> query2 = session.createQuery(losoweSlowkoQuery, Slowka.class);
        query2.setParameter("name", "admin");

        Slowka losoweSlowko;

        try {
            losoweSlowko = query2.getSingleResult();
        }catch(Exception e){
            losoweSlowko = null;
        }

        return losoweSlowko;
    }

    private void getRandomWordAfter(MyDatabaseBox myDatabaseBox, Object wynik){
        setLosoweSlowko((Slowka)wynik);
    }


    private Object getTheBestInside(MyDatabaseBox myDatabaseBox, Session session){
        Query<Slowka> query3 = session.createQuery(najlatwiejszeSlowkaQuery, Slowka.class);
        List<Slowka> list;

        query3.setParameter("id", user.getId());

        try {
            list = query3.getResultList();
        }catch (Exception e){
            list = null;
        }

        return list;
    }

    private void getTheBestAfter(MyDatabaseBox myDatabaseBox, Object wynik){
        List<Slowka> list = (List<Slowka>)wynik;
        setTheBestTheWorst(list, Arrays.asList(bestLabel1, bestLabel2, bestLabel3), false);
    }

    private Object getTheWorstInside(MyDatabaseBox myDatabaseBox, Session session){
        Query<Slowka> query4 = session.createQuery(najtrudniejszeSlowkaQuery, Slowka.class);
        List<Slowka> list;

        query4.setParameter("id", user.getId());

        try {
            list = query4.getResultList();
        }catch (Exception e){
            list = null;
        }

        return list;
    }

    private void getTheWorstAfter(MyDatabaseBox myDatabaseBox, Object wynik){
        List<Slowka> list = (List<Slowka>)wynik;
        setTheBestTheWorst(list, Arrays.asList(worstLabel1, worstLabel2, worstLabel3), true);
    }

    @Override
    public Object inside(MyDatabaseBox myDatabaseBox, Session session) {
        switch (myDatabaseBox.getStany()){
            case NULL -> {
                System.out.println("Zle żadanie :<");
                return null;
            }
            case GET_ZESTAWY -> { return getZestawyInside(myDatabaseBox, session);}
            case RANDOM_WORD -> { return getRandomWordInside(myDatabaseBox, session);}
            case THE_BEST -> { return getTheBestInside(myDatabaseBox, session);}
            case THE_WORST -> { return getTheWorstInside(myDatabaseBox, session);}
        }

        return null;
    }

    @Override
    public void after(MyDatabaseBox myDatabaseBox, Object wynik) {
        switch (myDatabaseBox.getStany()){
            case GET_ZESTAWY -> getZestawyAfter(myDatabaseBox, wynik);
            case RANDOM_WORD -> getRandomWordAfter(myDatabaseBox, wynik);
            case THE_BEST -> getTheBestAfter(myDatabaseBox, wynik);
            case THE_WORST -> getTheWorstAfter(myDatabaseBox, wynik);
        }
    }

    private void setLosoweSlowko(Slowka slowka){
        if(slowka != null) {
            wybraneSlowko.setText(slowka.getTextA() + " - " + slowka.getTextB());
        }
    }


    private void setZestawyHBox(){
        List<ZestawySlowek> list = user.getZestawySlowek();
        Collections.shuffle(list);
        int index = 0;


        for(Node vBox: zestawyHBox.getChildren()){
            if(index >= list.size()){
                ((VBox)vBox).setVisible(false);
                continue;
            }

            ((Label)((VBox)vBox).getChildren().get(0)).setText(list.get(index).getName());
            ((Label)((VBox)vBox).getChildren().get(1)).setText(list.get(index).getProcentObecnejZnajomosci() + "% zrozumienia");

            int indexToMethod = index;
            ((VBox)vBox).addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    dataSender.add(list.get(indexToMethod),ZESTAW_KEY_ID);

                    change("edytujZestaw.fxml", mouseEvent);
                }
            });

            index++;
        }
    }

    private void setTheBestTheWorst(List<Slowka> slowka, List<Label> labels, boolean negative){

        labels.get(0).setText("Brak danych");
        labels.get(1).setText("Brak danych");
        labels.get(2).setText("Brak danych");
        int indexBefore;

        if(negative){
            indexBefore = 0;
        }
        else{
            indexBefore = 1;
        }

        if(slowka == null){
            return;
        }

        for(int i=0;i<slowka.size();i++){
            labels.get(i).setText("Miejsce " + (i+1));
            int index = i;
            labels.get(i).setOnMouseClicked(e->{
                stanLabel[indexBefore][index] = (stanLabel[indexBefore][index]+1)%5;

                switch (stanLabel[indexBefore][index]){
                    case 0 -> labels.get(index).setText("Miejsce " + (index+1));
                    case 1 -> labels.get(index).setText(slowka.get(index).getTextA());
                    case 2 -> labels.get(index).setText(slowka.get(index).getTextB());
                    case 3 -> labels.get(index).setText("Dobre odpowiedzi: " + slowka.get(index).getDobreOdpowiedzi());
                    case 4 -> labels.get(index).setText("Zle odpowiedzi: " + slowka.get(index).getZleOdpowiedzi());
                }
            });
        }
    }

    private int ileProcent(){
        long ilosc = 0;

        for(ZestawySlowek zs: user.getZestawySlowek()){
            if(zs.getProcentObecnejZnajomosci() == null) {
                continue;
            }
            ilosc += zs.getProcentObecnejZnajomosci();
        }

        ilosc = ilosc / user.getZestawySlowek().size();

        return (int)ilosc;
    }

    @FXML
    public void goToTrenerMethod(MouseEvent mouseEvent){
        change("trener.fxml", mouseEvent);
    }
}
