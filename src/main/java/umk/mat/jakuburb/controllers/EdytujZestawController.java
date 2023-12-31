package umk.mat.jakuburb.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import org.hibernate.Session;
import org.hibernate.query.Query;
import umk.mat.jakuburb.controllers.helpers.DataSender;
import umk.mat.jakuburb.controllers.helpers.MyController;
import umk.mat.jakuburb.database.MyDatabase;
import umk.mat.jakuburb.database.MyDatabaseBox;
import umk.mat.jakuburb.database.MyDatabaseInterface;
import umk.mat.jakuburb.database.StanyDatabase;
import umk.mat.jakuburb.encje.Slowka;
import umk.mat.jakuburb.encje.User;
import umk.mat.jakuburb.encje.ZestawySlowek;
import umk.mat.jakuburb.usefullClass.GameModes;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

public class EdytujZestawController extends MyController implements MyDatabaseInterface {

    @FXML
    private TableView<Slowka> table;

    @FXML
    private TextField slowkoStronaB;

    @FXML
    private TextField slowkoStronaA;

    @FXML
    private TextField edytujZestaw__name;

    @FXML
    private Label edytujZestawDelete;

    @FXML
    private Label edytujZestawPlay;

    @FXML
    private Label licznik;

    @FXML
    private Label labelAktualizuj;

    @FXML
    private Label labelDelete;

    @FXML
    private Label labelFlip;

    @FXML
    private Label labelAdd;

    public static final String ZESTAW_TO_PLAY_KEY_ID = "trooooooba";
    public static final String TRYB_TO_PLAY_KEY_ID = "adsfasdfasdfasdfawefaserwqr23433";

    private List<Slowka> slowkaList = new ArrayList<>();
    private ZestawySlowek zestaw;
    private Slowka selectedSlowko;
    private int selectedSlowkoId = -1;

    private final String QUERY_SLOWKA = "FROM Slowka s Where s.zestawySlowek.id = :iid Order by s.id";

    //TODO: DODANIE MOZLIWOSCI EDYCJI TABELI
    //TODO: DODANIE MOZLIWOSCI UWUWWANIA ZESTAWU
    //TODO: MOZLIWOSCI DODANIA PRZYSCISKU DO USUNIECIA SLOWKA

    //     __
    //    |^^|
    //  <-|<>|->
    //     ||
    //    -  -

    @FXML
    public void initialize(){
        super.initialize();

        table.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("textA"));
        table.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("textB"));
        table.setEditable(true);

        dataSender = DataSender.initDataSender();
        zestaw = (ZestawySlowek)dataSender.get(ZestawyController.ZESTAW_KEY_ID);

        labelAdd.managedProperty().bind(labelAdd.visibleProperty());
        labelAktualizuj.managedProperty().bind(labelAktualizuj.visibleProperty());
        labelDelete.managedProperty().bind(labelDelete.visibleProperty());
        labelFlip.managedProperty().bind(labelFlip.visibleProperty());

        slowkoStronaB.setOnAction(e->{
            if(selectedSlowkoId == -1){
                dodaj();
            }
        });

        slowkoStronaA.setOnAction(e->{
            if(selectedSlowkoId == -1){
                dodaj();
            }
        });

        if(zestaw == null){
            edytujZestawDelete.setVisible(false);
            edytujZestawPlay.setVisible(false);
        }else{
            MyDatabaseBox myDatabaseBox = new MyDatabaseBox();
            myDatabaseBox.setStany(StanyDatabase.ODCZYT_SLOWEK);

            myDatabase = MyDatabase.createDatabase();
            myDatabase.makeSession(myDatabaseBox, this);
        }
    }

    @FXML
    public void updateMethod(MouseEvent mouseEvent){
        myDatabase = MyDatabase.createDatabase();
        MyDatabaseBox myDatabaseBox = new MyDatabaseBox();
        myDatabaseBox.setStany(StanyDatabase.UPDATE_SLOWKA);

        myDatabase.makeSession(myDatabaseBox,this);
    }

    @FXML
    public void deleteMethod(MouseEvent mouseEvent){
        myDatabase = MyDatabase.createDatabase();
        MyDatabaseBox myDatabaseBox = new MyDatabaseBox();
        myDatabaseBox.setStany(StanyDatabase.DELETE_SLOWKO);
        myDatabase.makeSession(myDatabaseBox,this);
    }

    @FXML
    public void flipMethod(MouseEvent mouseEvent){
        String textA;
        String textB;

        textA = slowkoStronaA.getText();
        textB = slowkoStronaB.getText();

        slowkoStronaA.setText(textB);
        slowkoStronaB.setText(textA);
    }

    @FXML
    public void tableClickMethod(MouseEvent mouseEvent){
        try {
            selectedSlowko = table.getSelectionModel().getSelectedItem();
            selectedSlowkoId = table.getSelectionModel().getSelectedIndex();

            slowkoStronaB.setText(selectedSlowko.getTextB());
            slowkoStronaA.setText(selectedSlowko.getTextA());

            labelAdd.setVisible(false);
            labelAktualizuj.setVisible(true);
            labelDelete.setVisible(true);

            System.out.println(selectedSlowko);
        }catch (Exception e){

        }
    }

    private void clearSelect(){
        table.getSelectionModel().clearSelection();

        slowkoStronaB.setText("");
        slowkoStronaA.setText("");

        labelAdd.setVisible(true);
        labelAktualizuj.setVisible(false);
        labelDelete.setVisible(false);

        selectedSlowko = null;
        selectedSlowkoId = -1;
    }

    //TODO: ENTER TEZ MA DZIALAC

    @FXML
    public void playMethod(MouseEvent mouseEvent){
        dataSender.add(null, ZestawyController.ZESTAW_KEY_ID);
        dataSender.add(null, TRYB_TO_PLAY_KEY_ID);

        if(zestaw.getSlowka().size() > 0) {
            dataSender.add(zestaw.getSlowka(), ZESTAW_TO_PLAY_KEY_ID);
            dataSender.add(GameModes.ZWYKLY, TRYB_TO_PLAY_KEY_ID);

            change("gra.fxml", mouseEvent);
        }else{
            popup("Nie ma słowek do gry");
        }
    }

    @FXML
    public void usunMethod(MouseEvent mouseEvent){
        MyDatabaseBox myDatabaseBox = new MyDatabaseBox();
        myDatabaseBox.setStany(StanyDatabase.USUN_ZESTAW);
        myDatabaseBox.setEvent(mouseEvent);

        myDatabase = MyDatabase.createDatabase();
        myDatabase.makeSession(myDatabaseBox, this);
    }

    @FXML
    public void addMethod(MouseEvent mouseEvent){
        dodaj();
    }

    private void dodaj(){
        myDatabase = MyDatabase.createDatabase();

        MyDatabaseBox myDatabaseBox = new MyDatabaseBox();
        myDatabaseBox.setStany(StanyDatabase.ZAPIS_SLOWEK);

        myDatabase.makeSession(myDatabaseBox, this);
    }


    @FXML
    public void saveMethod(MouseEvent mouseEvent){
        myDatabase = MyDatabase.createDatabase();
        MyDatabaseBox myDatabaseBox = new MyDatabaseBox();
        myDatabaseBox.setStany(StanyDatabase.ZAPIS_ZESTAWU);
        myDatabaseBox.setEvent(mouseEvent);

        myDatabase.makeSession(myDatabaseBox, this);
    }


    private Object zapisZestawuInside(MyDatabaseBox myDatabaseBox, Session session){

        if(zestaw != null){
            zestaw.setName(edytujZestaw__name.getText());
            session.merge(zestaw);
            return zestaw;
        }

        ZestawySlowek zestawySlowek = new ZestawySlowek();
        User user = (User)dataSender.get(LoginController.PW_KEY_ID);

        zestawySlowek.getUzytkownicy().add(user);
        user.getZestawySlowek().add(zestawySlowek);

        zestawySlowek.setName(edytujZestaw__name.getText());
        zestawySlowek.setSlowka(slowkaList);

        session.persist(zestawySlowek);

        for(Slowka s: slowkaList){
            s.setIdZestawu(zestawySlowek);
            session.merge(s);
        }

        session.merge(user);

        return zestawySlowek;
    }

    private void zapisZestawuAfter(MyDatabaseBox myDatabaseBox, Object wynik){
        change("zestawy.fxml", myDatabaseBox.getEvent());
    }

    private Object odczytSlowkaInside(MyDatabaseBox myDatabaseBox, Session session){
        Query<Slowka> q = session.createQuery(QUERY_SLOWKA, Slowka.class);
        q.setParameter("iid", zestaw.getId());

        List<Slowka> slowka = q.getResultList();

        zestaw.setSlowka(slowka);

        return slowka;
    }

    private void odczytSlowkaAfter(MyDatabaseBox myDatabaseBox,Object wynik){
        edytujZestaw__name.setText(zestaw.getName());

        for(Slowka s: zestaw.getSlowka()){
            table.getItems().add(s);
            slowkaList.add(s);
        }

        licznik.setText("Ilość słowek: " + slowkaList.size());
    }

    private Object zapisSlowkaInside(Session session){

        Slowka newSlowko = new Slowka();

        newSlowko.setTextA(slowkoStronaA.getText());
        newSlowko.setTextB(slowkoStronaB.getText());

        newSlowko.setDataStworzenia(LocalDateTime.now());
        newSlowko.setOstatniaGra(null);

        newSlowko.setDobreOdpowiedzi(0);
        newSlowko.setZleOdpowiedzi(0);

        newSlowko.setPunkty(0);

        if(zestaw != null) {
            newSlowko.setIdZestawu(zestaw);
        }

        session.persist(newSlowko);

        if(zestaw != null) {
            Query<Slowka> q = session.createQuery(QUERY_SLOWKA, Slowka.class);
            q.setParameter("iid", zestaw.getId());

            List<Slowka> slowka = q.getResultList();
            zestaw.setSlowka(slowka);
        }

        return newSlowko;
    }

    private void zapisSlowkaAfter(Object wynik){
        Slowka slowka = (Slowka) wynik;

        table.getItems().add(slowka);
        slowkaList.add(slowka);
        licznik.setText("Ilość słowek: " + slowkaList.size());
        slowkoStronaA.setText("");
        slowkoStronaB.setText("");
        slowkoStronaA.requestFocus();
    }

    private Object usunZestawInside(MyDatabaseBox myDatabaseBox, Session session) {
        //ZOSTAWIEM TO DLA HISTORII

      //  Query<User> qUsers = session.createQuery("SELECT z.uzytkownicy FROM ZestawySlowek z where z.id = :id", User.class);
//        qUsers.setParameter("id", zestaw.getId());
//
//        List<User> users = qUsers.getResultList();
//
//        zestaw.setUzytkownicy(users);
//
//        for(User user: zestaw.getUzytkownicy()){
//            user.getZestawySlowek().remove(zestaw);
//            session.merge(user);
//        }
//
//        session.remove(zestaw);

//        MutationQuery zestawySlowekQuery = session.createQuery("UPDATE ZestawySlowek z SET z.uzytkownicy = null WHERE z.id = :id");
//        zestawySlowekQuery.setParameter("id", zestaw.getId());
//        zestawySlowekQuery.executeUpdate();
//
//        MutationQuery userQuery = session.createQuery("UPDATE User u SET u.uzytkownicy = null WHERE z.id = :id");
//        zestawySlowekQuery.setParameter("id", zestaw.getId());
//        zestawySlowekQuery.executeUpdate();


        session.remove(zestaw);

        for(User user: zestaw.getUzytkownicy()){
            user.getZestawySlowek().removeIf(new Predicate<ZestawySlowek>() {
                @Override
                public boolean test(ZestawySlowek zestawySlowek) {
                    return Objects.equals(zestawySlowek.getId(), zestaw.getId());
                }
            });
            session.merge(user);
        }

        return null;
    }

    private void usunZestawAfter(MyDatabaseBox myDatabaseBox, Object wynik){
        change("zestawy.fxml", myDatabaseBox.getEvent());
    }

    private Object updateSlowkaInside(MyDatabaseBox myDatabaseBox, Session session) {

        selectedSlowko.setTextA(slowkoStronaA.getText());
        selectedSlowko.setTextB(slowkoStronaB.getText());

        session.merge(selectedSlowko);

        if(zestaw != null) {
            Query<Slowka> q = session.createQuery(QUERY_SLOWKA, Slowka.class);
            q.setParameter("iid", zestaw.getId());

            List<Slowka> slowka = q.getResultList();
            zestaw.setSlowka(slowka);
        }

        return selectedSlowko;
    }

    //TODO: UPDATE I DELETE BEZ ZESTAWU :0

    private void updateSlowkaAfter(MyDatabaseBox myDatabaseBox, Object wynik){
        if(zestaw == null){
            Slowka slowka = (Slowka) wynik;

            table.getItems().set(selectedSlowkoId, slowka);
            slowkaList.set(selectedSlowkoId, slowka);
        }else{
            table.getItems().setAll(zestaw.getSlowka());
            slowkaList = new ArrayList<>(zestaw.getSlowka());
        }

        clearSelect();
        licznik.setText("Ilość słowek: " + slowkaList.size());
    }

    private Object deleteSlowkaInside(MyDatabaseBox myDatabaseBox, Session session) {

        session.remove(selectedSlowko);

        if(zestaw != null){
            zestaw.getSlowka().remove(selectedSlowko);

            Query<Slowka> q = session.createQuery(QUERY_SLOWKA, Slowka.class);
            q.setParameter("iid", zestaw.getId());

            List<Slowka> slowka = q.getResultList();
            zestaw.setSlowka(slowka);
        }

        return selectedSlowko;
    }

    private void deleteSlowkaAfter(MyDatabaseBox myDatabaseBox, Object wynik){
        Slowka usunieteSlowko = (Slowka) wynik;

        table.getItems().remove(usunieteSlowko);

        if(zestaw != null) {
            slowkaList = new ArrayList<>(zestaw.getSlowka());
        } else{
            slowkaList.remove(selectedSlowkoId);
        }

        clearSelect();
        licznik.setText("Ilość słowek: " + slowkaList.size());
    }

    @Override
    public Object inside(MyDatabaseBox myDatabaseBox, Session session) {
        switch (myDatabaseBox.getStany()){
            case ODCZYT_SLOWEK -> {
                return odczytSlowkaInside(myDatabaseBox, session);
            }
            case ZAPIS_SLOWEK -> {
                return zapisSlowkaInside(session);
            }
            case ZAPIS_ZESTAWU -> {
                return zapisZestawuInside(myDatabaseBox, session);
            }
            case USUN_ZESTAW -> {
                return usunZestawInside(myDatabaseBox, session);
            }
            case UPDATE_SLOWKA -> {
                return updateSlowkaInside(myDatabaseBox, session);
            }
            case DELETE_SLOWKO -> {
                return deleteSlowkaInside(myDatabaseBox, session);
            }
            case NULL ->{
                System.out.println("Nie została wybrana zadna akcja");
            }
        }

        return null;
    }



    @Override
    public void after(MyDatabaseBox myDatabaseBox, Object wynik) {
        switch (myDatabaseBox.getStany()){
            case ODCZYT_SLOWEK -> odczytSlowkaAfter(myDatabaseBox, wynik);
            case ZAPIS_SLOWEK -> zapisSlowkaAfter(wynik);
            case ZAPIS_ZESTAWU -> zapisZestawuAfter(myDatabaseBox, wynik);
            case USUN_ZESTAW ->  usunZestawAfter(myDatabaseBox, wynik);
            case UPDATE_SLOWKA -> updateSlowkaAfter(myDatabaseBox, wynik);
            case DELETE_SLOWKO -> deleteSlowkaAfter(myDatabaseBox, wynik);
        }
    }
}