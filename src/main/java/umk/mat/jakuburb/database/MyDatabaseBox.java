package umk.mat.jakuburb.database;

import javafx.event.Event;
import javafx.scene.input.MouseEvent;

public class MyDatabaseBox {

    private StanyDatabase stany;
    private Event event;

    private Object[] array;

    public MyDatabaseBox(){
        array = new Object[10];
    }

    public MyDatabaseBox(StanyDatabase stany) {
        array = new Object[10];
        this.stany = stany;
        event = null;
    }

    public MyDatabaseBox(Event mouseEvent) {
        array = new Object[10];
        this.event = mouseEvent;
        stany = StanyDatabase.NULL;
    }

    public MyDatabaseBox(StanyDatabase stany, Event mouseEvent) {
        this.stany = stany;
        this.event = mouseEvent;
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

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
}
