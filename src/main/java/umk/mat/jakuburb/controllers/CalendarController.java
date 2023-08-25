package umk.mat.jakuburb.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import org.hibernate.Session;
import org.hibernate.query.Query;
import umk.mat.jakuburb.controllers.helpers.DataSender;
import umk.mat.jakuburb.controllers.helpers.DayValue;
import umk.mat.jakuburb.controllers.helpers.MyController;
import umk.mat.jakuburb.database.MyDatabaseBox;
import umk.mat.jakuburb.database.MyDatabaseInterface;
import umk.mat.jakuburb.database.StanyDatabase;
import umk.mat.jakuburb.encje.Kalendarz;
import umk.mat.jakuburb.encje.User;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class CalendarController extends MyController implements MyDatabaseInterface {

    @FXML
    private Label titleKalendar;

    @FXML
    private GridPane grdsd;

    private LocalDateTime localDateTime;

    private User user;

    private Map<String, Kalendarz> wartoscDnia = new HashMap<>();

//    private enum DayValue {
//        NONE("eee", "Nie oznaczone"),
//        GOOD("FFCA7A", "Dobry dzień"),
//        THE_BEST("F7A325", "Bardzo dobry dzień"),
//        BAD("F56038", "Nie udało się");
//
//        private String colorValue;
//        private String hint;
//        DayValue(String colorValue, String hint){
//            this.colorValue = colorValue;
//            this.hint = hint;
//        }
//
//        public String getColorValue(){
//            return colorValue;
//        }
//
//        public String getHintValue(){
//            return hint;
//        }
//    };

    @FXML
    public void initialize(){
        super.initialize();

        localDateTime = LocalDateTime.now();
        user = (User)dataSender.get(LoginController.PW_KEY_ID);

        myDatabase.makeSession(new MyDatabaseBox(StanyDatabase.READ_CALENDAR),this);

        initKalendarz();
    }


    @Override
    public Object inside(MyDatabaseBox myDatabaseBox, Session session) {
        switch (myDatabaseBox.getStany()){
            case READ_CALENDAR -> {return readCalendarInside(myDatabaseBox, session);}
            case UPDATE_CALENDAR -> {return updateCalendarInside(myDatabaseBox, session);}
            case CREATE_CALENDAR -> {return createCalendarInside(myDatabaseBox, session);}
            case NULL -> {
                System.out.println("Null Calendar");
                return null;
            }
        }

        return null;
    }


    @Override
    public void after(MyDatabaseBox myDatabaseBox, Object wynik) {
        switch (myDatabaseBox.getStany()){
            case READ_CALENDAR -> readCalendarAfter(myDatabaseBox, wynik);
            case UPDATE_CALENDAR -> updateCalendarAfter(myDatabaseBox, wynik);
            case CREATE_CALENDAR -> createCalendarAfter(myDatabaseBox, wynik);
        }
    }

    private Object readCalendarInside(MyDatabaseBox myDatabaseBox, Session session){
        Query<Kalendarz> queryKalendarz = session.createQuery("FROM Kalendarz k WHERE k.user.id = :id", Kalendarz.class);
        queryKalendarz.setParameter("id", user.getId());

        List<Kalendarz> kalendarzList = queryKalendarz.getResultList();

        user.setKalendarzList(kalendarzList);

        return user.getKalendarzList();
    }

    private void readCalendarAfter(MyDatabaseBox myDatabaseBox, Object wynik){
        List<Kalendarz> kalendarzList = (List<Kalendarz>) wynik;

        for(Kalendarz kalendarz: kalendarzList){

            String key = kalendarz.getLocalDateTime().getYear() +
                    "/" + kalendarz.getLocalDateTime().getMonthValue() +
                    "/" + kalendarz.getLocalDateTime().getDayOfMonth();

            wartoscDnia.put(key, kalendarz);
        }
    }

    private Object createCalendarInside(MyDatabaseBox myDatabaseBox, Session session) {
        Kalendarz kalendarz = new Kalendarz();
        int year = (int)myDatabaseBox.getArray()[0];
        int month = (int)myDatabaseBox.getArray()[1];
        int day = (int)myDatabaseBox.getArray()[2];


        kalendarz.setUser(user);
        kalendarz.setDayValue(DayValue.GOOD);
        kalendarz.setLocalDateTime(LocalDateTime.of(year, month, day, 12, 12));

        session.persist(kalendarz);

        return kalendarz;
    }

    private void createCalendarAfter(MyDatabaseBox myDatabaseBox, Object wynik) {
        Kalendarz kalendarz = (Kalendarz) wynik;
        Pane pane = (Pane) myDatabaseBox.getArray()[4];

        String key = kalendarz.getLocalDateTime().getYear() +
                "/" + kalendarz.getLocalDateTime().getMonthValue() +
                "/" + kalendarz.getLocalDateTime().getDayOfMonth();

        wartoscDnia.put(key, kalendarz);
        pane.setStyle("-fx-background-color: #" + wartoscDnia.get(key).getDayValue().getColorValue());
        Tooltip.install(pane,new Tooltip(wartoscDnia.get(key).getDayValue().getHintValue()));
    }

    private Object updateCalendarInside(MyDatabaseBox myDatabaseBox, Session session){
        Kalendarz kalendarz = (Kalendarz) myDatabaseBox.getArray()[3];

        switch (kalendarz.getDayValue()){
            case NONE -> kalendarz.setDayValue(DayValue.GOOD);
            case GOOD -> kalendarz.setDayValue(DayValue.THE_BEST);
            case THE_BEST -> kalendarz.setDayValue(DayValue.BAD);
            case BAD -> {
                session.remove(kalendarz);
                return null;
            }
        }

        session.merge(kalendarz);

        return kalendarz;
    }

    private void updateCalendarAfter(MyDatabaseBox myDatabaseBox, Object wynik){
        Kalendarz kalendarz = (Kalendarz) wynik;
        Pane pane = (Pane) myDatabaseBox.getArray()[4];
        DayValue dayValue;

        String key = myDatabaseBox.getArray()[0] +
                "/" + myDatabaseBox.getArray()[1] +
                "/" + myDatabaseBox.getArray()[2];




        if(kalendarz == null){
            dayValue = DayValue.NONE;
        } else{
            dayValue = kalendarz.getDayValue();
        }

        wartoscDnia.put(key, kalendarz);
        pane.setStyle("-fx-background-color: #" + dayValue.getColorValue());
        Tooltip.install(pane,new Tooltip(dayValue.getHintValue()));
    }



    private void initKalendarz(){
        int monthValue = localDateTime.getMonthValue();
        int yearValue = localDateTime.getYear();

        titleKalendar.setText(localDateTime.getMonth().name() + " " + localDateTime.getYear());

        LocalDateTime dzienPierwszCzas = LocalDateTime.of(yearValue, monthValue, 1, 12, 12);
        DayOfWeek pierwszyDzien =  dzienPierwszCzas.getDayOfWeek();

        int pierwszyDzienWartosc = pierwszyDzien.getValue();
        int iloscDni = dzienPierwszCzas.getMonth().length(dzienPierwszCzas.toLocalDate().isLeapYear());

        grdsd.getChildren().forEach(e->{
            if(e instanceof Pane) {
                e.setVisible(false);
            }
        });

        for(int i=1;i<=iloscDni; i++) {
            Pane pane = ((Pane) grdsd.getChildren().get(5 + pierwszyDzienWartosc + i));
            pane.setVisible(true);

            String key = yearValue + "/" + monthValue + "/" + i;

            wartoscDnia.putIfAbsent(key, null);

            int day = i;

            pane.setOnMouseClicked(e->{
                MyDatabaseBox myDatabaseBox = new MyDatabaseBox();

                if(wartoscDnia.get(key) == null){
                    myDatabaseBox.setStany(StanyDatabase.CREATE_CALENDAR);
                }else{
                    myDatabaseBox.setStany(StanyDatabase.UPDATE_CALENDAR);
                }

                myDatabaseBox.getArray()[0] = yearValue; //Rok
                myDatabaseBox.getArray()[1] = monthValue; //Miesiac
                myDatabaseBox.getArray()[2] = day; //day
                myDatabaseBox.getArray()[3] = wartoscDnia.get(key);
                myDatabaseBox.getArray()[4] = pane;

                myDatabase.makeSession(myDatabaseBox,this);
            });

            if(wartoscDnia.get(key) != null) {
                pane.setStyle("-fx-background-color: #" + wartoscDnia.get(key).getDayValue().getColorValue());
                Tooltip.install(pane, new Tooltip(wartoscDnia.get(key).getDayValue().getHintValue()));
            }else{
                DayValue dayValue = DayValue.NONE;
                pane.setStyle("-fx-background-color: #" + dayValue.getColorValue());
                Tooltip.install(pane, new Tooltip(dayValue.getHintValue()));
            }

            ((Label) pane.getChildren().get(0)).setText(String.valueOf(i));
        }
    }

    @FXML
    private void nextMounthMethod(MouseEvent mouseEvent){
        int year = localDateTime.getYear();
        int month = localDateTime.getMonthValue();//1-12

        if(month == 12){
            month = 1;
            year++;
        }else{
            month++;
        }

        localDateTime = LocalDateTime.of(year, month,1, 12, 12);
        initKalendarz();
    }

    @FXML
    private void backMounthMethod(MouseEvent mouseEvent){
        int year = localDateTime.getYear();
        int month = localDateTime.getMonthValue();//1-12

        if(month == 1){
            month = 12;
            year--;
        }else{
            month--;
        }

        localDateTime = LocalDateTime.of(year, month,1, 12, 12);
        initKalendarz();
    }
}
