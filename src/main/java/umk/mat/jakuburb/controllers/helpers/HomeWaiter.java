package umk.mat.jakuburb.controllers.helpers;

import javafx.event.Event;
import org.hibernate.Session;
import org.hibernate.query.Query;
import umk.mat.jakuburb.database.MyDatabase;
import umk.mat.jakuburb.database.MyDatabaseBox;
import umk.mat.jakuburb.database.MyDatabaseInterface;

public class HomeWaiter extends MyController implements MyDatabaseInterface {

    private static final String queryCountZestawy = "SELECT COUNT(*) FROM ZestawySlowek z JOIN z.uzytkownicy u WHERE u.id = :userid";

    private HomeWaiter(){

    }

    public static void checkDishes(Long userId, Event event){
        MyDatabase myDatabase = MyDatabase.createDatabase();
        MyDatabaseBox box = new MyDatabaseBox();

        box.setEvent(event);
        box.getArray()[0] = userId;

        myDatabase.makeSession(box, new HomeWaiter());
    }

    @Override
    public Object inside(MyDatabaseBox myDatabaseBox, Session session) {
        Query<Long> q2 = session.createQuery(queryCountZestawy, Long.class);
        Long idUser = (Long)myDatabaseBox.getArray()[0];

        q2.setParameter("userid", idUser);

        Long iloscZestawow = q2.getSingleResult();

        return iloscZestawow;
    }

    @Override
    public void after(MyDatabaseBox myDatabaseBox, Object wynik) {
        Long iloscZestawow = (Long)wynik;

        if(iloscZestawow > 0){
            change("home2.fxml", myDatabaseBox.getEvent());
        }else{
            change("home.fxml", myDatabaseBox.getEvent());
        }
    }
}
