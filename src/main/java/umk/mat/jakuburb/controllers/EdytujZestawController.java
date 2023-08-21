package umk.mat.jakuburb.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import org.hibernate.Session;
import org.hibernate.query.Query;
import umk.mat.jakuburb.database.MyDatabase;
import umk.mat.jakuburb.database.MyDatabaseBox;
import umk.mat.jakuburb.database.MyDatabaseInterface;
import umk.mat.jakuburb.database.StanyDatabase;
import umk.mat.jakuburb.encje.Slowka;
import umk.mat.jakuburb.encje.User;
import umk.mat.jakuburb.encje.ZestawySlowek;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
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

    private MyDatabase myDatabase;
    private DataSender dataSender;
    private List<Slowka> slowkaList = new ArrayList<>();
    private ZestawySlowek zestaw;
    private Slowka selectedSlowko;

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
        table.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("textA"));
        table.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("textB"));
        table.setEditable(true);

        dataSender = DataSender.initDataSender();
        zestaw = (ZestawySlowek)dataSender.get(ZestawyController.ZESTAW_KEY_ID);

        labelAdd.managedProperty().bind(labelAdd.visibleProperty());
        labelAktualizuj.managedProperty().bind(labelAktualizuj.visibleProperty());
        labelDelete.managedProperty().bind(labelDelete.visibleProperty());
        labelFlip.managedProperty().bind(labelFlip.visibleProperty());

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
        selectedSlowko = table.getSelectionModel().getSelectedItem();

        slowkoStronaB.setText(selectedSlowko.getTextB());
        slowkoStronaA.setText(selectedSlowko.getTextA());

        labelAdd.setVisible(false);
        labelAktualizuj.setVisible(true);
        labelDelete.setVisible(true);


        System.out.println(selectedSlowko);
    }

    private void clearSelect(){
        table.getSelectionModel().clearSelection();

        slowkoStronaB.setText("");
        slowkoStronaA.setText("");

        labelAdd.setVisible(true);
        labelAktualizuj.setVisible(false);
        labelDelete.setVisible(false);

        selectedSlowko = null;
    }

    //TODO: ENTER TEZ MA DZIALAC

    @FXML
    public void usunMethod(MouseEvent mouseEvent){
        MyDatabaseBox myDatabaseBox = new MyDatabaseBox();
        myDatabaseBox.setStany(StanyDatabase.USUN_ZESTAW);
        myDatabaseBox.setMouseEvent(mouseEvent);

        myDatabase = MyDatabase.createDatabase();
        myDatabase.makeSession(myDatabaseBox, this);
    }

    @FXML
    public void addMethod(MouseEvent mouseEvent){
        myDatabase = MyDatabase.createDatabase();

        MyDatabaseBox myDatabaseBox = new MyDatabaseBox();
        myDatabaseBox.setStany(StanyDatabase.ZAPIS_SLOWEK);

        myDatabase.makeSession(myDatabaseBox, this);
    }

    @FXML
    public void homeMenuAction(MouseEvent mouseEvent){
        change("home.fxml", mouseEvent);
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
    public void saveMethod(MouseEvent mouseEvent){
        if(zestaw != null){
            change("zestawy.fxml", mouseEvent);
            return;
        }

        myDatabase = MyDatabase.createDatabase();
        MyDatabaseBox myDatabaseBox = new MyDatabaseBox();
        myDatabaseBox.setStany(StanyDatabase.ZAPIS_ZESTAWU);
        myDatabaseBox.setMouseEvent(mouseEvent);

        myDatabase.makeSession(myDatabaseBox, this);
    }


    //TODO: OGARNIJ TE KASKADOWOSC I JAK CO DO KOGO I beDZIE Z GORKI UWIERZ MI :> BEDZIE DOBRZE

    private Object zapisZestawuInside(MyDatabaseBox myDatabaseBox, Session session){

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
        change("zestawy.fxml", myDatabaseBox.getMouseEvent());
    }

    private Object odczytSlowkaInside(MyDatabaseBox myDatabaseBox, Session session){
        Query<Slowka> q = session.createQuery("FROM Slowka s Where s.zestawySlowek.id = :iid", Slowka.class);
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

        newSlowko.setDobreOdpowiedzi(300);

        newSlowko.setDataStworzenia(LocalDateTime.now());
        newSlowko.setOstatniaGra(LocalDateTime.now());

        newSlowko.setDobreOdpowiedzi(12);
        newSlowko.setZleOdpowiedzi(123);

        if(zestaw != null) {
            newSlowko.setIdZestawu(zestaw);
        }

        session.persist(newSlowko);

        return newSlowko;
    }

    private void zapisSlowkaAfter(Object wynik){
        Slowka slowka = (Slowka) wynik;

        table.getItems().add(slowka);
        slowkaList.add(slowka);
        licznik.setText("Ilość słowek: " + slowkaList.size());
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
        change("zestawy.fxml", myDatabaseBox.getMouseEvent());
    }

    private Object updateSlowkaInside(MyDatabaseBox myDatabaseBox, Session session) {

        selectedSlowko.setTextA(slowkoStronaA.getText());
        selectedSlowko.setTextB(slowkoStronaB.getText());

        session.merge(selectedSlowko);

        return null;
    }

    private void updateSlowkaAfter(MyDatabaseBox myDatabaseBox, Object wynik){
        table.getItems().setAll(zestaw.getSlowka());
        slowkaList = new ArrayList<>(zestaw.getSlowka());
        clearSelect();
    }

    private Object deleteSlowkaInside(MyDatabaseBox myDatabaseBox, Session session) {

        session.remove(selectedSlowko);
        zestaw.getSlowka().remove(selectedSlowko);

        return selectedSlowko;
    }

    private void deleteSlowkaAfter(MyDatabaseBox myDatabaseBox, Object wynik){
        Slowka usunieteSlowko = (Slowka) wynik;

        table.getItems().remove(usunieteSlowko);
        slowkaList = new ArrayList<>(zestaw.getSlowka());
        clearSelect();
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