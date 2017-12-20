package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.HBox;

public class Main extends Application {

    @Override
    public void init(){
        //initialise sp_mainlayout and reversi control
        sp_mainlayout = new HBox();
        go = new GoControl();
        sp_mainlayout.getChildren().add(go);

        side_control = new SideControl(go);
        sp_mainlayout.getChildren().add(side_control);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        // set title scene and display scene
        side_control.setPrimaryStage(primaryStage);
        primaryStage.setTitle("Go");
        Scene scene = new Scene(sp_mainlayout, 900, 600);
        scene.getStylesheets().addAll(this.getClass().getResource("go.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }

    // private fields for a stack pane and a go control
    private HBox sp_mainlayout;
    private GoControl go;
    private SideControl side_control;
}
