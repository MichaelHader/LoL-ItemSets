package itemsets;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("GUI.fxml"));
        primaryStage.setTitle("LoL Item Sets Update");
        primaryStage.setScene(new Scene(root, 600, 460));
        primaryStage.getScene().getStylesheets().setAll(getClass().getResource("style.css").toString());
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
