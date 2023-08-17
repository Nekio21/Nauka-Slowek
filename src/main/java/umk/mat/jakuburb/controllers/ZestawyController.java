package umk.mat.jakuburb.controllers;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.PieChart;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import org.hibernate.Session;
import org.hibernate.query.Query;
import umk.mat.jakuburb.database.MyDatabase;
import umk.mat.jakuburb.database.MyDatabaseInterface;
import umk.mat.jakuburb.encje.User;
import umk.mat.jakuburb.encje.ZestawySlowek;

import java.time.LocalDateTime;
import java.util.List;

public class ZestawyController extends MyController implements MyDatabaseInterface {

    @FXML
    private ChoiceBox<String> sortujChoiceBox;

    @FXML
    private FlowPane flowPane;

    public static String ZESTAW_KEY_ID = "Super ID zestaw ;/";
    private MyDatabase myDatabase;
    private DataSender dataSender;


    private String[] sortujList = new String[]{
            "Alfabetycznie",
            "Przeciwienstwo Alfabetyczne",
            "Data rosnaca",
            "Data malejaca",
            "poziom wiedzy rosnoca",
            "poziom wiedzy malejaco"
    };

    @FXML
    public void initialize(){
        sortujChoiceBox.getItems().addAll(sortujList);

        flowPaneInit();
    }

    public void flowPaneInit(){
        dataSender = DataSender.initDataSender();
        myDatabase = MyDatabase.createDatabase();
        myDatabase.makeSession(this);
    }

    @FXML
    public void newMethod(MouseEvent mouseEvent){
//        myDatabase = MyDatabase.createDatabase();
//        dataSender = DataSender.initDataSender();
//
//        myDatabase.makeSession(this);
            change("edytujZestaw.fxml", mouseEvent);
    }

    @FXML
    public void kalendarzMenuAction(MouseEvent mouseEvent){
        change("calendar.fxml", mouseEvent);
    }

    @FXML
    public void homeMenuAction(MouseEvent mouseEvent){

        change("home.fxml", mouseEvent);
    }

    @FXML
    public void zestawyEdytujChwilowoMenuAction(MouseEvent mouseEvent){
        change("edytujZestaw.fxml", mouseEvent);
    }

    @Override
    public Object inside(Session session) {
        User user = (User)dataSender.get(LoginController.PW_KEY_ID);

        Query<ZestawySlowek> zestawySlowekQuery = session.createQuery("SELECT u.zestawySlowek From User u where u.id = :id", ZestawySlowek.class);
        zestawySlowekQuery.setParameter("id", user.getId());


        List<ZestawySlowek> list = zestawySlowekQuery.getResultList();

        return list;
    }

    @Override
    public void after(Object wynik) {
        User user = (User)dataSender.get(LoginController.PW_KEY_ID);
        List<ZestawySlowek> list = (List<ZestawySlowek>) wynik;

        user.setZestawySlowek(list);

        for(ZestawySlowek z: list){
            VBox vbox = new VBox();
            vbox.getStyleClass().add("zestawBox");
            vbox.setPadding(new Insets(20,20,20,20));
            vbox.setAlignment(Pos.CENTER);
            vbox.setSpacing(50);
            vbox.setFillWidth(true);

            Label title = new Label();
            title.getStyleClass().add("zestawBox_bold");
            title.setText(z.getName());
            title.setWrapText(true);
            title.setAlignment(Pos.CENTER);
            title.setTextAlignment(TextAlignment.CENTER);

            Font font = Font.font("System", FontWeight.NORMAL, FontPosture.REGULAR, 36);

            title.setFont(font);

            title.setPrefSize(150,100);
            title.setMaxSize(150, 100);
            title.setMinSize(150, 100);

            Label down = new Label();
            down.setText(z.getPunkty().toString());

            vbox.getChildren().add(title);
            vbox.getChildren().add(down);

            flowPane.getChildren().add(vbox);
        }
    }
}
