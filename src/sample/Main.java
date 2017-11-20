package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.StackPane;

public class Main extends Application {

    @Override
    public void init(){
        //initialise sp_mainlayout and reversi control
        sp_mainlayout = new StackPane();
        go = new GoControl();
        sp_mainlayout.getChildren().add(go);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        // set title scene and display scene
        primaryStage.setTitle("Go");
        primaryStage.setScene(new Scene(sp_mainlayout, 800, 800));
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }

    // private fields for a stack pane and a go control
    private StackPane sp_mainlayout;
    private GoControl go;
}
