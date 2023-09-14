package umk.mat.jakuburb.controllers;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import org.hibernate.Session;
import org.hibernate.query.Query;
import umk.mat.jakuburb.controllers.helpers.DataSender;
import umk.mat.jakuburb.controllers.helpers.MyController;
import umk.mat.jakuburb.database.MyDatabase;
import umk.mat.jakuburb.database.MyDatabaseBox;
import umk.mat.jakuburb.database.MyDatabaseInterface;
import umk.mat.jakuburb.encje.User;
import umk.mat.jakuburb.encje.ZestawySlowek;

import java.util.List;

public class ZestawyController extends MyController implements MyDatabaseInterface {

    @FXML
    private ChoiceBox<String> sortujChoiceBox;

    @FXML
    private FlowPane flowPane;

    @FXML
    private Label findLabel;

    @FXML
    private TextField findZestawTF;

    public static String ZESTAW_KEY_ID = "Super ID zestaw ;/";
    public static String ZESTAW_TRYB = "ufksajljrejfisfduio234h9erp8fujfsduq0weujiofadjiolfas";

    private String textFromTF = "";

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
        super.initialize();
        sortujChoiceBox.getItems().addAll(sortujList);
        sortujChoiceBox.setValue(sortujList[0]);

        flowPaneInit();

        ZestawyController zestawyController = this;
        findZestawTF.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                MyDatabaseBox box = new MyDatabaseBox();

                box.getArray()[0] = findZestawTF.getText();
                box.getArray()[1] = sortujChoiceBox.getValue();

                myDatabase.makeSession(box, zestawyController);
            }
        });

        sortujChoiceBox.setOnAction(e->{
            MyDatabaseBox box = new MyDatabaseBox();

            box.getArray()[0] = findZestawTF.getText();
            box.getArray()[1] = sortujChoiceBox.getValue();

            myDatabase.makeSession(box, zestawyController);
        });
    }

    public void flowPaneInit(){
        MyDatabaseBox box = new MyDatabaseBox();
        box.getArray()[1] = sortujChoiceBox.getValue();

        dataSender = DataSender.initDataSender();
        myDatabase = MyDatabase.createDatabase();
        myDatabase.makeSession(box, this);
    }

    @FXML
    public void newMethod(MouseEvent mouseEvent){
        dataSender.add(null,ZESTAW_KEY_ID);

        change("edytujZestaw.fxml", mouseEvent);
    }
    @Override
    public Object inside(MyDatabaseBox myDatabaseBox, Session session) {
        User user = (User)dataSender.get(LoginController.PW_KEY_ID);
        Query<ZestawySlowek> zestawySlowekQuery;

        String textUser = (String)myDatabaseBox.getArray()[0];
        String choiceType = (String)myDatabaseBox.getArray()[1];

        String query = "FROM ZestawySlowek z WHERE z in (SELECT u.zestawySlowek From User u WHERE u.id = :id) AND z.name LIKE :text";

        if(textUser == null){
            textUser = "";
        }

        if(choiceType != null){
            if(choiceType.equals(sortujList[0])){
                query += " ORDER BY z.name ASC";
            }
            else if(choiceType.equals(sortujList[1])){
                query += " ORDER BY z.name DESC";
            }
            else if(choiceType.equals(sortujList[2])){
                query += " ORDER BY z.dataStworzenia ASC";
            }
            else if(choiceType.equals(sortujList[3])){
                query += " ORDER BY z.dataStworzenia DESC";
            }
            else if(choiceType.equals(sortujList[4])){
                query += " ORDER BY z.procentObecnejZnajomosci ASC";
            }
            else if(choiceType.equals(sortujList[5])){
                query += " ORDER BY z.procentObecnejZnajomosci DESC";
            }
        }

        zestawySlowekQuery = session.createQuery(query, ZestawySlowek.class);


        //zestawySlowekQuery = session.createQuery("From ZestawSlowek z WHERE :user IN z.uzytkownicy.id", ZestawySlowek.class);

        zestawySlowekQuery.setParameter("id", user.getId());
        zestawySlowekQuery.setParameter("text", "%" + textUser + "%");


        List<ZestawySlowek> list = zestawySlowekQuery.getResultList();

        return list;
    }

    @Override
    public void after(MyDatabaseBox myDatabaseBox, Object wynik) {
        User user = (User)dataSender.get(LoginController.PW_KEY_ID);
        List<ZestawySlowek> list = (List<ZestawySlowek>) wynik;

        user.setZestawySlowek(list);

        Label l = (Label)flowPane.getChildren().get(0);
        flowPane.getChildren().clear();
        flowPane.getChildren().add(l);

        findLabel.setText("Znaleziono: " + list.size() + " zestawy");

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

            if(z.getProcentObecnejZnajomosci() == null){
                down.setText("Procent znajomosci: " + 0 + "%");
            }else{
                down.setText("Procent znajomosci: " + z.getProcentObecnejZnajomosci() + "%");
            }


            vbox.getChildren().add(title);
            vbox.getChildren().add(down);

            vbox.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    dataSender.add(z,ZESTAW_KEY_ID);

                    change("edytujZestaw.fxml", mouseEvent);
                }
            });

            flowPane.getChildren().add(vbox);
        }
    }
}
