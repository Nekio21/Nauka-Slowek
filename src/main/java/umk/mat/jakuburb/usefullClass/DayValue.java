package umk.mat.jakuburb.usefullClass;

public enum DayValue {
    NONE("eee", "Nie oznaczone"),
    GOOD("FFCA7A", "Dobry dzień"),
    THE_BEST("F7A325", "Bardzo dobry dzień"),
    BAD("F56038", "Nie udało się");

    private String colorValue;
    private String hint;
    DayValue(String colorValue, String hint){
        this.colorValue = colorValue;
        this.hint = hint;
    }

    public String getColorValue(){
        return colorValue;
    }

    public String getHintValue(){
        return hint;
    }
};