package umk.mat.jakuburb.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import org.hibernate.Session;
import umk.mat.jakuburb.controllers.helpers.DataSender;
import umk.mat.jakuburb.controllers.helpers.MyController;
import umk.mat.jakuburb.database.MyDatabase;
import umk.mat.jakuburb.database.MyDatabaseBox;
import umk.mat.jakuburb.database.MyDatabaseInterface;
import umk.mat.jakuburb.encje.Slowka;
import umk.mat.jakuburb.encje.ZestawySlowek;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
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
    private ZestawySlowek zestawySlowek;
    private List<Slowka> listaSlowek;

    private boolean checked = false;

    private int poprawne = 0;
    private int negatywne = 0;

    private int index = 0;

    @FXML
    public void initialize(){
        super.initialize();
        dataSender = DataSender.initDataSender();
        myDatabase = MyDatabase.createDatabase();

        zestawySlowek = (ZestawySlowek) dataSender.get(EdytujZestawController.ZESTAW_TO_PLAY_KEY_ID);
        listaSlowek = new ArrayList<>(zestawySlowek.getSlowka());

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
        box.getArray()[0] = null;
        box.getArray()[0] = slowka;
        box.getArray()[1] = poprawnie;

        myDatabase.makeSession(box, this);

        resultLabel.setVisible(true);
        checkGra.setText("Next");

        index++;

        rundaLabel.setText((index + 1) + " Runda");
        procentLabel.setText("Twoja skutecznosc: " + Math.round((poprawne*100)/(double)(poprawne+negatywne)) + "%");
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
        checkGra.setText("Check");

        slowko_gra.setText(listaSlowek.get(index).getTextA());
    }

    private void koniecGry(MouseEvent me){
        checkGra.setVisible(false);

        change("wynik.fxml", me);
    }


    @Override
    public Object inside(MyDatabaseBox myDatabaseBox, Session session) {
        Slowka slowka = (Slowka)myDatabaseBox.getArray()[0];
        boolean poprawnie = (boolean)myDatabaseBox.getArray()[1];

        Integer punkty;

        punkty = slowka.getPunkty();

        if(punkty == null){
            punkty = 0;
        }

        if(poprawnie){
            slowka.setDobreOdpowiedzi(slowka.getDobreOdpowiedzi() + 1);
            //TODO: CHWILOOOOWE TYLKO NARAZIE TAKIE COS
            slowka.setPunkty(3);
        }else{
            slowka.setZleOdpowiedzi(slowka.getZleOdpowiedzi() + 1);
            slowka.setPunkty(1);
        }

        slowka.setOstatniaGra(LocalDateTime.now());
        session.merge(slowka);

        return null;
    }

    @Override
    public void after(MyDatabaseBox myDatabaseBox, Object wynik) {

    }
}
