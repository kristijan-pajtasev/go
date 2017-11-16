package go;

//an experiment to see how much JavaFX code is required
//to build a game of reversi

//imports
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

//class defnition for reversi game
public class Go extends Application {
	private StackPane stackPane;
	private GoControl goControl;

	// overridden init method
	public void init() {
		stackPane = new StackPane();
		goControl = new GoControl();
		stackPane.getChildren().add(goControl);
	}
	
	// overridden start method
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Go");
		primaryStage.setScene(new Scene(stackPane, 800, 800));
		primaryStage.show();
	}
	
	// overridden stop method
	public void stop() {
		
	}
	
	// entry point into our program for launching our javafx applicaton
	public static void main(String[] args) {
		launch(args);
	}
	
	// private fields for a stack pane and a reversi control
	private StackPane sp_mainlayout;
	private GoControl rc_reversi;
	
}




