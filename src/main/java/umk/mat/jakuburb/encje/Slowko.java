package umk.mat.jakuburb.encje;

public class Slowko {

    private String stronaA;
    private String stronaB;

    private String cos;


    public Slowko(String stronaA, String stronaB, String cos) {
        this.stronaA = stronaA;
        this.stronaB = stronaB;
        this.cos = cos;
    }

    public String getStronaA() {
        return stronaA;
    }

    public void setStronaA(String stronaA) {
        this.stronaA = stronaA;
    }

    public String getStronaB() {
        return stronaB;
    }

    public void setStronaB(String stronaB) {
        this.stronaB = stronaB;
    }

    public String getCos() {
        return cos;
    }

    public void setCos(String cos) {
        this.cos = cos;
    }
}
