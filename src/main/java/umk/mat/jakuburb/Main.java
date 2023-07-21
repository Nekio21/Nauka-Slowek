package umk.mat.jakuburb;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

import java.io.File;
import java.net.URL;

public class Main extends Application {

    private Parent pane;
    @Override
    public void start(Stage stage) throws Exception {

        URL url = new File("src/main/resources/umk/mat/jakuburb/home.fxml").toURI().toURL();
        pane = FXMLLoader.load(url);

        Scene scene = new Scene(pane);

        stage.setTitle("Nauka słowek :>");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}