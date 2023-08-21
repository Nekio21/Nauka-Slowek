package umk.mat.jakuburb.database;

import javafx.scene.input.MouseEvent;
import org.hibernate.Session;

public class MyDatabaseBox {

    private StanyDatabase stany;
    private MouseEvent mouseEvent;

    private Object[] array;

    public MyDatabaseBox(){
        array = new Object[10];
    }

    public MyDatabaseBox(StanyDatabase stany) {
        array = new Object[10];
        this.stany = stany;
        mouseEvent = null;
    }

    public MyDatabaseBox(MouseEvent mouseEvent) {
        array = new Object[10];
        this.mouseEvent = mouseEvent;
        stany = StanyDatabase.NULL;
    }

    public MyDatabaseBox(StanyDatabase stany, MouseEvent mouseEvent) {
        this.stany = stany;
        this.mouseEvent = mouseEvent;
    }

    public Object[] getArray() {
        return array;
    }

    public void setArray(Object[] array) {
        this.array = array;
    }

    public StanyDatabase getStany() {
        return stany;
    }

    public void setStany(StanyDatabase stany) {
        this.stany = stany;
    }

    public MouseEvent getMouseEvent() {
        return mouseEvent;
    }

    public void setMouseEvent(MouseEvent mouseEvent) {
        this.mouseEvent = mouseEvent;
    }
}
