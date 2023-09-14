package umk.mat.jakuburb.controllers;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.hibernate.Session;
import org.hibernate.query.Query;
import umk.mat.jakuburb.controllers.helpers.DataSender;
import umk.mat.jakuburb.controllers.helpers.MyController;
import umk.mat.jakuburb.database.MyDatabase;
import umk.mat.jakuburb.database.MyDatabaseBox;
import umk.mat.jakuburb.database.MyDatabaseInterface;
import umk.mat.jakuburb.encje.HistoriaZestawu;
import umk.mat.jakuburb.encje.Slowka;
import umk.mat.jakuburb.encje.User;
import umk.mat.jakuburb.encje.ZestawySlowek;
import umk.mat.jakuburb.usefullClass.GameModes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class TrenerController extends MyController implements MyDatabaseInterface {

    @FXML
    private Label titleTrener;

    @FXML
    private Label zestawTrener;

    @FXML
    private HBox hboxTrener;

    private List<Slowka> slowkaList = new ArrayList<>();

    private User user;

    private boolean moznaGra = true;

    @FXML
    public void initialize(){
        super.initialize();

        user = (User)dataSender.get(LoginController.PW_KEY_ID);

        titleTrener.setText("Witaj " + user.getLogin());

        myDatabase = MyDatabase.createDatabase();
        myDatabase.makeSession(this);
    }

    @FXML
    public void zagraj(MouseEvent mouseEvent){
        if(moznaGra) {
            dataSender.add(null, ZestawyController.ZESTAW_KEY_ID);
            dataSender.add(slowkaList, EdytujZestawController.ZESTAW_TO_PLAY_KEY_ID);
            dataSender.add(GameModes.ROZWINIETY, EdytujZestawController.TRYB_TO_PLAY_KEY_ID);
            change("gra.fxml", mouseEvent);
        }else{
            popup("Brak zestawów dla trenera :<");
        }
    }

    @Override
    public Object inside(MyDatabaseBox myDatabaseBox, Session session) {
        Query<ZestawySlowek> queryZestaw = session.createQuery("SELECT u.zestawySlowek From User u WHERE u.id = :id", ZestawySlowek.class);
        queryZestaw.setParameter("id", user.getId());
        List<ZestawySlowek> z = queryZestaw.getResultList();

        if(z.isEmpty()){
            user.setZestawySlowek(z);
            return null;
        }else{
            user.setZestawySlowek(z);
        }

        Query<Slowka> querySlowka = session.createQuery("From Slowka s WHERE s.zestawySlowek IN :zez ORDER BY s.punkty ASC LIMIT 30", Slowka.class);
        querySlowka.setParameter("zez", z);
        slowkaList = querySlowka.getResultList();

        for(ZestawySlowek zestawySlowek: z){
            Query<HistoriaZestawu> query = session.createQuery("From HistoriaZestawu h WHERE h.zestawySlowek = :zes  ORDER BY h.dataGry", HistoriaZestawu.class);
            query.setParameter("zes", zestawySlowek);

            zestawySlowek.setHistoriaZestawuList(query.getResultList());
        }

        return z;
    }

    @Override
    public void after(MyDatabaseBox myDatabaseBox, Object wynik) {

        if(wynik == null){
            popup("Brak zestawów dla trenera :<");
            nieRysujWykres();
            return;
        }

        List<ZestawySlowek> zestawySlowekList = (List<ZestawySlowek>) wynik;

        Collections.shuffle(zestawySlowekList);

        for(ZestawySlowek z: zestawySlowekList){
            ArrayList<HistoriaZestawu> theBestHistory;


            long ilosc = z.getHistoriaZestawuList().stream().count();
            long iloscPotencjalnejHistori;

            if(ilosc > 2){
                theBestHistory = new ArrayList<>();
                theBestHistory.add(z.getHistoriaZestawuList().get(0));

                for(HistoriaZestawu history : z.getHistoriaZestawuList()){
                    if(theBestHistory.get(theBestHistory.size()-1).getProcentZnajmosci() != null) {
                        if (theBestHistory.get(theBestHistory.size() - 1).getProcentZnajmosci() <= history.getProcentZnajmosci()) {
                            theBestHistory.add(history);
                        }
                    }
                }

                iloscPotencjalnejHistori = theBestHistory.size();

                if(iloscPotencjalnejHistori > 3){
                    rysujWykres(theBestHistory);
                    return;
                }
            }
        }

        nieRysujWykres();
    }

    private void rysujWykres(ArrayList<HistoriaZestawu> theBestHistory){
        zestawTrener.setText("Twój proges - zestaw: \"" + theBestHistory.get(0).getZestawySlowek().getName() + "\"");
        int index = 1;
        int i=0;
        int size = theBestHistory.size();

        Random random = new Random();

        int[] indexTab = {index, (random.nextInt(size-1))%(size-3) + 2,size-1};

        if(theBestHistory.size() < 4){
            nieRysujWykres();
            return;
        }

        for(Node v: hboxTrener.getChildren()){

            if(v instanceof VBox){

                int dnien = theBestHistory.get(indexTab[i]).getDataGry().getDayOfMonth();
                int miesiac = theBestHistory.get(indexTab[i]).getDataGry().getMonthValue();
                int year = theBestHistory.get(indexTab[i]).getDataGry().getYear();

                ((Label)((VBox) v).getChildren().get(0)).setText(theBestHistory.get(indexTab[i]).getProcentZnajmosci() + "%");
                ((Pane)((VBox) v).getChildren().get(1)).setMinHeight(2 * theBestHistory.get(indexTab[i]).getProcentZnajmosci());
                ((Label)((VBox) v).getChildren().get(2)).setText("Poziom wiedzy z " +  dnien + "." + miesiac + "." + year);

                i++;
            }
        }
    }

    private void nieRysujWykres(){
        hboxTrener.setVisible(false);
        zestawTrener.setVisible(false);
    }
}
