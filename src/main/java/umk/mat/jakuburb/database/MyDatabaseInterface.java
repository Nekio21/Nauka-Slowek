package umk.mat.jakuburb.database;

import org.hibernate.Session;
import javafx.scene.input.MouseEvent;

public interface MyDatabaseInterface {
    Object inside(Session session);
    void after(Object wynik);
}
