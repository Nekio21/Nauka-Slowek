package umk.mat.jakuburb.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import umk.mat.jakuburb.controllers.helpers.MyController;
import umk.mat.jakuburb.encje.Gra;

import static umk.mat.jakuburb.controllers.PlayController.GRA;

public class ResultatController extends MyController {

    @FXML
    private Label gratulacjeOpis;

    private int procent;
    private int dobre;
    private int zle;

    //TODO: STowrzyc klase z trybami gry :>
    //private TrybGry trybGry;

    private Gra gra;

    private String text;


    private String[] beggin = new String[]{
            "Super, że kolejny raz ćwiczysz z naszym programem :) ",
        "Nawet nie wiesz jakie przysługe robisz swojemu mózgowi na przyszłość !!! ",
        "Kolejny krok za tobą, jesteś jak Amstron na księżycu, stawiasz krok za krokiem ku lepszej przyszłości BRAWO  ",
        "Dobrze, że kolejny raz możemy się spotkać przy podsumowaniu. ",
        "Nawet jesli Ci się nie wydaje to naprawde robisz olbrzymie postepy!!!!"
    };



    private String[] end = new String[]{
            "Trzymaj tak dalej i pamietaj, że wielki sukces to zasługa małych kroków :)",
            "Każdą próbe udana, mniej lub bardziej, zliżasz się do zywyciestwa",
            "Widzimy się na kolejny tescie ;)",
            "Tak trzymaj, widzimy się niedlugo...",
            "Robisz duże postepy, trzymaj tak dalej :)"
    };

    @FXML
    public void initialize(){
        super.initialize();

        gra = (Gra)dataSender.get(GRA);

        text = "Bardzo cieszymy się";

        gratulacjeOpis.setText(beggin[1] + end[3]);
    }

    @FXML
    public void returnMethod(MouseEvent mouseEvent){
        dataSender.add(null, GRA);
        change("zestawy.fxml", mouseEvent);
    }

}
