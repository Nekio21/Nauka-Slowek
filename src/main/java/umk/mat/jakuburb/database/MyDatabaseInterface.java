package umk.mat.jakuburb.database;

import org.hibernate.Session;

public interface MyDatabaseInterface {
    Object inside(MyDatabaseBox myDatabaseBox, Session session);
    void after(MyDatabaseBox myDatabaseBox, Object wynik);
}
