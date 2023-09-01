package umk.mat.jakuburb.database;

import javafx.event.Event;
import javafx.scene.input.MouseEvent;

public class MyDatabaseBox {

    private StanyDatabase stany;
    private Event event;
    private boolean safe;

    private Object[] array;

    public MyDatabaseBox(){
        array = new Object[10];
        safe = true;
    }

    public MyDatabaseBox(StanyDatabase stany) {
        array = new Object[10];
        this.stany = stany;
        event = null;
        safe = true;
    }

    public MyDatabaseBox(Event mouseEvent) {
        array = new Object[10];
        this.event = mouseEvent;
        stany = StanyDatabase.NULL;
        safe = true;
    }

    public MyDatabaseBox(StanyDatabase stany, Event mouseEvent) {
        this.stany = stany;
        this.event = mouseEvent;
        safe = true;
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

    public boolean isSafe() {
        return safe;
    }

    public void setSafe(boolean safe) {
        this.safe = safe;
    }
}
