package sample;

/**
 * Created by Aurelien Delaguillaumie
 * 03/12/2017.
 */

import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.VBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.GridPane;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.animation.RotateTransition;
import javafx.util.Duration;
import javafx.animation.ScaleTransition;
import javafx.scene.effect.InnerShadow;


public class SideControl extends VBox {

    //Side control is made of 3 pane

    //Current player hold the name the current player
    private StackPane current_player;
    private Label lb_current_player;

    //control for the current player
    private GridPane player_control;
    private Label lb_message;
    private Button bt_pass;
    private Button bt_offer_draw;
    private Button bt_undo;
    private Button bt_end_game;

    //display players name and scores
    private VBox player_display;
    private StackPane title_box;
    private Label title;
    private GridPane scores;
    private Label lb_player1_name;
    private Label lb_player2_name;
    private Label lb_player1_score;
    private Label lb_player2_score;
    private Ellipse piece_1;
    private Ellipse piece_2;

    private int score_1 = 0;
    private int score_2 = 0;
    private String current_Player = "Player 1";
    private String player1_name = "Player 1";
    private String player2_name = "Player 2";
    private String message =  "";

    private GoControl goControl;

    SideControl(GoControl goControl){

        this.goControl = goControl;
        initGUI();
        goControl.setSideControl(this);


    }

    //initialise GUI for sidecontrol
    public void initGUI(){

        this.setPrefSize(300, 600);

        //initialise the current player display
        init_current_player();

        //initialise current player control
        init_player_control();

        //initialise current player Scores and name
        init_player_display();

        //init event
        init_event();

    }

    //initialise the current player display
    public void init_current_player(){

        //initialise stack pane and label
        current_player = new StackPane();
        current_player.setId("current_player");
        lb_current_player = new Label();
        lb_current_player.textProperty().bind(new SimpleStringProperty(current_Player));
        lb_current_player.setId("lb_current_player");



        //add the main layout
        current_player.getChildren().add(lb_current_player);
        current_player.setPrefSize(300, 200);
        this.getChildren().add(current_player);

    }


    //initialise current player control
    public void init_player_control(){

        //initialise Grid pane button and label
        player_control = new GridPane();
        lb_message = new Label("");
        bt_pass = new Button("Pass");
        bt_offer_draw = new Button("Reset");
        bt_undo = new Button("Undo");
        bt_end_game = new Button("End Game");

        //style message label
        lb_message.setId("message");

        //styling the button with css
        bt_pass.setId("action_button");
        bt_undo.setId("action_button");
        bt_offer_draw.setId("action_button");
        bt_end_game.setId("action_button");

        // adding btn to grid
        player_control.add(lb_message, 0, 0, 4, 1);
        player_control.add(bt_pass,0,1,1,1);
        player_control.add(bt_undo,1,1,1,1);
        player_control.add(bt_offer_draw,2, 1, 1, 1);
        player_control.add(bt_end_game,3,1,1,1);

        player_control.setHgap(10);
        player_control.setVgap(40);
        player_control.setPrefSize(300, 200);
        player_control.setAlignment(Pos.CENTER);

        this.getChildren().add(player_control);

    }


    public void init_player_display(){


        player_display = new VBox();  // container for scores and title box
        title_box = new StackPane();  // sub container to hold the title of the box
        title = new Label("Players"); // box title
        scores = new GridPane();  // sub container to display the scores
        lb_player1_name = new Label(player1_name); // contained in scores - name of player 1
        lb_player2_name = new Label(player2_name); // contained in scores - name of player 2
        lb_player1_score = new Label();  // contained in scores - score of player 1
        lb_player2_score = new Label();  // contained in scores - score of player 2
        lb_player1_score.textProperty().bind(new SimpleIntegerProperty(score_1).asString());
        lb_player2_score.textProperty().bind(new SimpleIntegerProperty(score_2).asString());
        //lb_player2_score = new Label("0");  // contained in scores -  score of player 2
        piece_1 = new Ellipse();
        piece_2 = new Ellipse();

        //Styling player display
        player_display.setId("player_display");
        player_display.setSpacing(10);

        //Styling the title
        title.setId("title");

        //Styling the title box and adding title to layout
        title_box.getChildren().add(title);
        title_box.setId("title_box");
        title_box.setPrefSize(300,40);

        // Piece styling
        piece_1.setRadiusX(20);
        piece_1.setRadiusY(20);
        piece_2.setRadiusX(20);
        piece_2.setRadiusY(20);
        DropShadow ds = new DropShadow(5.0, 3.0, 3.0, Color.color(0.4, 0.4, 0.4));
        piece_1.setEffect(ds);
        piece_2.setEffect(ds);
        piece_2.setFill(Color.WHITE);

        //generating the grid with players label and scores
        scores.add(piece_1,0,0);
        scores.add(lb_player1_name,1,0);
        scores.add(lb_player1_score,2,0);
        scores.add(piece_2,0,1);
        scores.add(lb_player2_name,1,1);
        scores.add(lb_player2_score,2,1);

        InnerShadow innerShadow = new InnerShadow(BlurType.GAUSSIAN ,Color.color(0.4, 0.4, 0.4), 10,0,2,2);
//        innerShadow.setOffsetX(2);
//        innerShadow.setOffsetY(2);
        //innerShadow.setColor(0,0,2,2Color.color(0.4, 0.4, 0.4));

        scores.setEffect(innerShadow);

        scores.setPrefSize(300,160);

        //styling scores labels
        lb_player1_name.setId("label_score");
        lb_player1_score.setId("label_score");
        lb_player2_name.setId("label_score");
        lb_player2_score.setId("label_score");
        scores.setHgap(40);
        scores.setVgap(30);
        scores.setAlignment(Pos.CENTER);
        scores.setId("scores");

        //adding sub container to player_display
        player_display.getChildren().add(title_box);
        player_display.getChildren().add(scores);
        player_display.setPrefSize(300,200);

        this.getChildren().add(player_display);

    }

    //init event
    public void init_event(){
        // Pass button event
        bt_pass.setOnAction(new EventHandler <ActionEvent>(){
            //call the compute result function calculate the result
            @Override
            public void handle(ActionEvent event) {
                update_display("");
                goControl.pass();
            }
        });


        bt_offer_draw.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                goControl.reset();
                update_score_label(goControl.update_score());

                //reset player label to player 1
                if(lb_current_player.getText().equals(player2_name)){
                    update_current_player();
                }

            }
        });
    }




    //update display
    public void update_display(String message){

        lb_message.textProperty().bind(new SimpleStringProperty(message));

        if(message.equals("")){

            //update score label
            update_score_label(goControl.update_score());

            //update current player label
            update_current_player();

        }

    }

    //public voi

    // update display is no message is passed
    public void update_display(){
        update_display("");
    }

    //update score label using the binding
    public void update_score_label(int [] scores){

        score_1 = scores[0];
        score_2 = scores[1];
        //update score display
        lb_player1_score.textProperty().bind(new SimpleIntegerProperty(score_1).asString());
        lb_player2_score.textProperty().bind(new SimpleIntegerProperty(score_2).asString());
    }

    // update the current player pane
    public void update_current_player(){

        //update current player label
        if(lb_current_player.getText().equals(player1_name)){
            current_Player = player2_name;
        }else{
            current_Player = player1_name;
        }

        //animation while changing player
        ScaleTransition st = new ScaleTransition(Duration.millis(100), lb_current_player);
        st.setByX(1f);
        st.setByY(1f);
        st.setCycleCount(4);
        st.setAutoReverse(true);

        st.play();
        lb_current_player.textProperty().bind(new SimpleStringProperty(current_Player));

    }








    // overridden version of the resize method to give the board the correct size
    @Override
    public void resize(double width, double height) {

        //step 11
        super.resize(width, height);
    }



}
