package umk.mat.jakuburb.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import org.hibernate.Session;
import org.hibernate.query.Query;
import umk.mat.jakuburb.controllers.helpers.DataSender;
import umk.mat.jakuburb.controllers.helpers.MyController;
import umk.mat.jakuburb.database.MyDatabase;
import umk.mat.jakuburb.database.MyDatabaseBox;
import umk.mat.jakuburb.database.MyDatabaseInterface;
import umk.mat.jakuburb.database.StanyDatabase;
import umk.mat.jakuburb.encje.*;
import umk.mat.jakuburb.usefullClass.DayValue;
import umk.mat.jakuburb.usefullClass.GameModes;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PlayController extends MyController implements MyDatabaseInterface {

    @FXML
    private Label slowko_gra;

    @FXML
    private TextField wpiszSlowkoGra;

    @FXML
    private Label resultLabel;

    @FXML
    private Label checkGra;

    @FXML
    private ProgressBar bar;

    @FXML
    private Label rundaLabel;

    @FXML
    private Label procentLabel;

    @FXML
    private Label goodLabel;

    @FXML
    private Label negativeLabel;
    private List<Slowka> zestawySlowek;
    private List<Slowka> listaSlowek;

    private List<SlowkaGra> slowkaGraListToEndGame = new ArrayList<>();

    private Kalendarz kalendarz;
    private User user;
    private Gra gra;

    private boolean checked = false;

    private int poprawne = 0;
    private int negatywne = 0;

    private int index = 0;
    private LocalDateTime localDateTime;

    public static final String GRA = "adfasdf asdfasd fasdf asdf d";

    @FXML
    public void initialize(){
        super.initialize();
        dataSender = DataSender.initDataSender();
        myDatabase = MyDatabase.createDatabase();

        user = (User)dataSender.get(LoginController.PW_KEY_ID);

        myDatabase.makeSession(new MyDatabaseBox(StanyDatabase.CALENDAR_GAME),this);
        myDatabase.makeSession(new MyDatabaseBox(StanyDatabase.CREATE_GAME),this);

        zestawySlowek = (List<Slowka>) dataSender.get(EdytujZestawController.ZESTAW_TO_PLAY_KEY_ID);
        listaSlowek = new ArrayList<>(zestawySlowek);

        Collections.shuffle(listaSlowek);

        slowko_gra.setText(listaSlowek.get(index).getTextA());
        bar.setProgress(index/(double)listaSlowek.size());

        rundaLabel.setText((index + 1) + " Runda");
        procentLabel.setText("Twoja skutecznosc: " + 0 + "%");
        goodLabel.setText(poprawne + " poprawnych odpowiedzi");
        negativeLabel.setText(negatywne + " złych odpowiedzi");
    }

    @FXML
    public void checkMethod(MouseEvent mouseEvent){
        if(checked){
            play(mouseEvent);
        }else{
            checked();
        }

        checked = !checked;
    }

    private void checked(){
        String playerText = wpiszSlowkoGra.getText();
        boolean poprawnie;

        if(playerText.equals(listaSlowek.get(index).getTextB())){
            resultLabel.setText("Dobrze");
            poprawne++;
            poprawnie = true;
        } else{
            negatywne++;
            resultLabel.setText("Zle :( " + listaSlowek.get(index).getTextB());
            poprawnie = false;
        }

        Slowka slowka = listaSlowek.get(index);

        MyDatabaseBox box = new MyDatabaseBox();

        box.setStany(StanyDatabase.SAVE_ROUND);
        box.getArray()[0] = null;
        box.getArray()[0] = slowka;
        box.getArray()[1] = poprawnie;

        myDatabase.makeSession(box,this);

        resultLabel.setVisible(true);
        checkGra.setText("Nastepny");

        index++;

        rundaLabel.setText((index + 1) + " Runda");
        procentLabel.setText("Twoja skuteczność: " + Math.round((poprawne*100)/(double)(poprawne+negatywne)) + "%");
        goodLabel.setText(poprawne + " poprawnych odpowiedzi");
        negativeLabel.setText(negatywne + " złych odpowiedzi");

        bar.setProgress(index/(double)listaSlowek.size());
    }

    private void play(MouseEvent me){
        if(index >= listaSlowek.size()){
            koniecGry(me);
            return;
        }

        wpiszSlowkoGra.setText("");


        resultLabel.setVisible(false);
        checkGra.setText("Sprawdź");

        slowko_gra.setText(listaSlowek.get(index).getTextA());
    }

    private void koniecGry(MouseEvent me){
        checkGra.setVisible(false);

        myDatabase.makeSession(new MyDatabaseBox(StanyDatabase.END_GAME),this);

        dataSender.add(gra, GRA);
        change("wynik.fxml", me);
    }

    private Object calendarGameInside(MyDatabaseBox myDatabaseBox, Session session) {
        Query<Kalendarz> query = session.createQuery("From Kalendarz k WHERE k.localDateTime = :date and k.user.id = :id", Kalendarz.class);
        LocalDateTime localDateTime = LocalDateTime.now();
        LocalDateTime localDateTimeToQuery = LocalDateTime.of(
                                                localDateTime.getYear(),
                                                localDateTime.getMonthValue(),
                                                localDateTime.getDayOfMonth(),
                                                12,
                                                12);

        query.setParameter("date", localDateTimeToQuery);
        query.setParameter("id", user.getId());

        try {
            kalendarz = query.getSingleResult();
        }catch (Exception e) {
            kalendarz = new Kalendarz();
            kalendarz.setUser(user);
            kalendarz.setLocalDateTime(localDateTimeToQuery);
            kalendarz.setDayValue(DayValue.NONE);

            session.persist(kalendarz);
        }

        return kalendarz;
    }

    private void calendarGameAfter(MyDatabaseBox myDatabaseBox, Object wynik) {
    }

    private Object createGameInside(MyDatabaseBox myDatabaseBox, Session session) {
        gra = new Gra();

        localDateTime = LocalDateTime.now();

        gra.setDataGry(localDateTime);
        gra.setGameMode(GameModes.ZWYKLY);
        gra.setKalendarz(kalendarz);

        session.persist(gra);

        return gra;
    }

    private void createGameAfter(MyDatabaseBox myDatabaseBox, Object wynik) {
    }

    private Object saveRoundInside(MyDatabaseBox myDatabaseBox, Session session) {
        Slowka slowka = (Slowka)myDatabaseBox.getArray()[0];

        boolean poprawnie = (boolean)myDatabaseBox.getArray()[1];
        Integer punkty;

        SlowkaGra slowkaGra = new SlowkaGra();

        slowkaGra.setSlowka(slowka);
        slowkaGra.setGra(gra);
        slowkaGra.setDobrzeCzyZle(poprawnie);
        slowkaGraListToEndGame.add(slowkaGra);

        punkty = slowka.getPunkty();

        if(punkty == null){
            punkty = 0;
        }

        if(poprawnie){
            Query<SlowkaGra> querySG = session.createQuery("FROM SlowkaGra sg WHERE sg.slowka = :slowko", SlowkaGra.class);
            querySG.setParameter("slowko", slowka);

            List<SlowkaGra> list = querySG.getResultList();

            for(SlowkaGra s: list){
                Gra g = s.getGra();
                s.setGra(g);
            }

            LocalDateTime ldt = LocalDateTime.now();
            boolean dobrze = false;

            list = list.stream().sorted(new Comparator<SlowkaGra>() {
                @Override
                public int compare(SlowkaGra o1, SlowkaGra o2) {

                    if(o1.getGra().getDataGry().isBefore(o2.getGra().getDataGry())){
                        return -1;
                    }

                    return 1;
                }
            }).toList();

            for(SlowkaGra s: list){
                if(s.getDobrzeCzyZle() == false){
                    ldt = s.getGra().getDataGry();
                    dobrze = false;
                    continue;
                }
                if(dobrze == false){
                    ldt = s.getGra().getDataGry();
                    dobrze = true;
                }
            }

            if(dobrze == false){
                slowka.setPunkty(1);
            }
            else{
                Long diff = ChronoUnit.DAYS.between(ldt, LocalDateTime.now());

                if(diff <= 1){
                    punkty = 1;
                }else if(diff <= 3){
                    punkty = 3;
                }else if(diff <= 7){
                    punkty = 4;
                }else if(diff <= 14){
                    punkty = 7;
                }else if(diff <= 30){
                    punkty = 14;
                }else if(diff <= 45){
                    punkty = 25;
                }else if(diff <= 60){
                    punkty = 31;
                }else if(diff <= 90){
                    punkty = 45;
                }else if(diff <= 120){
                    punkty = 60;
                }else if(diff <= 180){
                    punkty = 90;
                }else if(diff <= 200){
                    punkty = 100;
                }else if(diff <= 400){
                    punkty = 250;
                }else if(diff <= 700){
                    punkty = 300;
                }else{
                    punkty = 600;
                }

                slowka.setPunkty(punkty);
            }

            slowka.setDobreOdpowiedzi(slowka.getDobreOdpowiedzi() + 1);
            slowka.setOstatniaOdpowiedzDobra(true);
        }else{
            slowka.setZleOdpowiedzi(slowka.getZleOdpowiedzi() + 1);
            slowka.setPunkty(1);
            slowka.setOstatniaOdpowiedzDobra(false);
        }

        slowka.setOstatniaGra(LocalDateTime.now());

        session.merge(slowka);
        session.refresh(gra);
        session.persist(slowkaGra);

        return null;
    }

    private void saveRoundAfter(MyDatabaseBox myDatabaseBox, Object wynik) {
    }


    private Object endGameInside(MyDatabaseBox myDatabaseBox, Session session) {
        List<Long> slowkasID = new ArrayList<>();
        List<ZestawySlowek> zestawySlowekList = new ArrayList<>();

        for(SlowkaGra s: slowkaGraListToEndGame){
            slowkasID.add(s.getSlowka().getZestawySlowek().getId());
        }

        String queryZestawySlowekString = "FROM ZestawySlowek z where z.id in :sid";

        Query<ZestawySlowek> queryZestawySlowek = session.createQuery(queryZestawySlowekString, ZestawySlowek.class);
        queryZestawySlowek.setParameter("sid", slowkasID);

        zestawySlowekList = queryZestawySlowek.getResultList();

        for(ZestawySlowek zs: zestawySlowekList){
            HistoriaZestawu hz = new HistoriaZestawu();
            hz.setGra(gra);
            hz.setDataGry(localDateTime);
            hz.setZestawySlowek(zs);

            session.persist(hz);
        }

        return  zestawySlowekList;
    }

    private void endGameAfter(MyDatabaseBox myDatabaseBox, Object wynik) {

    }


    @Override
    public Object inside(MyDatabaseBox myDatabaseBox, Session session) {
        switch(myDatabaseBox.getStany()){
            case NULL->{
                System.out.println("Blad");
                return null;
            }
            case CREATE_GAME -> {return createGameInside(myDatabaseBox, session);}
            case SAVE_ROUND -> {return saveRoundInside(myDatabaseBox, session);}
            case CALENDAR_GAME -> {return calendarGameInside(myDatabaseBox, session);}
            case END_GAME -> {return endGameInside(myDatabaseBox, session);}
        }

       return null;
    }



    @Override
    public void after(MyDatabaseBox myDatabaseBox, Object wynik) {
        switch(myDatabaseBox.getStany()){
            case CREATE_GAME -> {createGameAfter(myDatabaseBox, wynik);}
            case SAVE_ROUND -> {saveRoundAfter(myDatabaseBox, wynik);}
            case CALENDAR_GAME -> {calendarGameAfter(myDatabaseBox, wynik);}
            case END_GAME -> {endGameAfter(myDatabaseBox, wynik);}
        }
    }


}
