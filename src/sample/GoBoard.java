package sample;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Line;
import javafx.scene.transform.Translate;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;



public class GoBoard extends Pane {

    GoBoard(){

        // arrays for the internal representation of the board and the pieces that are
        // in place
        render = new GoPiece [7][7];
        // arrays for the lines that makeup the horizontal and vertical grid lines
        horizontal = new Line [7];
        vertical = new Line [7];
        // arrays holding translate objects for the horizontal and vertical grid lines
        horizontal_t = new Translate[7];
        vertical_t = new Translate[7];
        // 3x3 array that holds the pieces that surround a given piece
        surrounding = new int[3][3];
        // 3x3 array that determines if a reverse can be made in any direction
        can_reverse = new boolean[3][3];


        //initialise render
        initialiseRender();
        gameLogic = new GameLogic(render);

        //initialize render
        initialiseLinesBackground();

        //reset game
        resetGame();

    }

    // overridden version of the resize method to give the board the correct size
    @Override
    public void resize(double width, double height) {

        //step 11
        super.resize(width, height);

        // figure out the width and height of a cell
        cell_width = width / 7.0;
        cell_height = height / 7.0;

        // resize the rectangle to take the full size of the widget
        background.setWidth(width); background.setHeight(height);

        //resize and relocate horizontal line
        horizontalResizeRelocate(width);
        verticalResizeRelocate(height);


        // we need to reset the sizes and positions of all XOPieces that were placed
        pieceResizeRelocate();
    }

    // private method for getting a piece on the board. this will return the board
    // value unless we access an index that doesnt exist. this is to make the code
    // for determing reverse chains much easier
    private int getPiece(final int x, final int y) {
        // NOTE: this is to keep the compiler happy until you get to this point

        //step 21
        int pieceSelected;

        if(x >= 0 && y >= 0 && x <7 && y < 7 ) {
            pieceSelected = render[x][y].getPiece();
        }else {
            pieceSelected = -1;
        }

        return pieceSelected;
    }

    // public method that will try to place a piece in the given x,y coordinate
    public void placePiece(final double x, final double y) throws Exception{
        // figure out which cell the current player has clicked on
        final int cellx = (int) (x / cell_width);
        final int celly = (int) (y / cell_height);

        // if the game is not in play then do nothing
        if(!in_play)
            return;


            gameLogic.placePiece(cellx, celly, current_player);
            swapPlayers();
    }

    private boolean isSuicidePlace(int cellx, int celly, int player) {
        // TODO: check if is it suicide place
        return false;
    }

    // public method for resetting the game
    public void resetGame() {

        resetRenders();
        initialiseRender();

        gameLogic = new GameLogic(render);

        this.current_player = 2;
        this.player1_score = 0;
        this.player2_score = 0;
    }

    // private method that will reset the renders
    private void resetRenders() {

        //set the array of render to 0
        //step 9
        for(int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                render[i][j].setPiece(0);
            }
        }


    }


    // private method that will initialise the background and the lines
    private void initialiseLinesBackground() {

        //add background color
        background = new Rectangle();
        // TODO: add background
        Image image = new Image("/sample/assets/wood.jpg");
        ImagePattern imagepattern = new ImagePattern(image);
        background.setId("pane");
        background.setFill(imagepattern);
        //background.setFill(Color.CYAN);


        //Line translate and draw the end line
        for(int i =0; i < 7; i++) {
            horizontal[i] = new Line();
            horizontal[i].setStroke(Color.BLACK);
            horizontal[i].setStartX(0);
            horizontal[i].setStartY(0);
            horizontal[i].setEndY(0);

            vertical[i] = new Line();
            vertical[i].setStroke(Color.BLACK);
            vertical[i].setStartX(0);
            vertical[i].setStartY(0);
            vertical[i].setEndX(0);

            horizontal_t[i] = new Translate(0,0);
            horizontal[i].getTransforms().add(horizontal_t[i]);

            vertical_t[i] = new Translate(0,0);
            vertical[i].getTransforms().add(vertical_t[i]);
        }


        getChildren().add(background);

        // add the rectangles and lines to this group
        for(int i =0; i < 7; i++) {
            getChildren().add(horizontal[i]);
            getChildren().add(vertical[i]);
        }
    }

    // private method for resizing and relocating the horizontal lines
    private void horizontalResizeRelocate(final double width) {
        // set a new y on the horizontal lines and translate them into place
        for(int i=0; i<7 ; i++) {
            horizontal_t[i].setY((i + 0.5)* cell_height);
            horizontal[i].setEndX(width - cell_width/2);
            horizontal[i].setStartX(cell_width/2);
        }
    }

    // private method for resizing and relocating the vertical lines
    private void verticalResizeRelocate(final double height) {
        // set a new x on the vertical lines and translate them into place
        for(int i=0; i<7 ; i++) {
            vertical_t[i].setX((i + 0.5)* cell_width);
            vertical[i].setEndY(height -cell_height/2);
            vertical[i].setStartY(cell_height/2);
        }
    }

    // private method for swapping the players
    private void swapPlayers() {
        this.current_player = current_player == 1 ? 2 : 1;
    }

    // private method for updating the player scores
    private void updateScores() {

        player1_score = 0;
        player2_score = 0;

        for(int i = 0; i < render.length ;i++) {
            for(int j = 0; j < render[i].length; j++) {

                if(this.render[i][j].getPiece() == 1) {
                    player1_score ++;

                }else if (this.render[i][j].getPiece() == 2) {
                    player2_score ++;

                }
            }
        }

        // TODO: update label view

    }

    // private method for resizing and relocating all the pieces
    private void pieceResizeRelocate() {

        for(int i = 0; i < 7; i++) {
            for(int j = 0; j < 7; j++) {
                render[i][j].relocate(i * cell_width, j * cell_height);
                render[i][j].resize(cell_width, cell_height);
            }
        }

    }

    // private method that will initialise everything in the render array
    private void initialiseRender() {
        //set the array to null object
        for(int i = 0; i < 7; i++)
            for(int j = 0; j < 7; j++) {
                render[i][j] = new GoPiece(i, j, 0);
                getChildren().add(render[i][j]);
            }

    }

    public void pass() {
        swapPlayers();
        // todo: implement setting passCount and check end game, then swap
    }

    //get scores from game logic
    public int [] get_score(){
        int scores []  = new int [2];

        scores[0] = gameLogic.playerOneScore();
        scores[1] = gameLogic.playerTwoScore();

        return scores;
    }

    public void endGame() {
        gameLogic.endGame();
    }

    // private fields that make the reversi board work
    // rectangle that makes the background of the board
    private Rectangle background;
    // arrays for the lines that makeup the horizontal and vertical grid lines
    private Line[] horizontal;
    private Line[] vertical;
    // arrays holding translate objects for the horizontal and vertical grid lines
    private Translate[] horizontal_t;
    private Translate[] vertical_t;
    // arrays for the internal representation of the board and the pieces that are
    // in place
    private GoPiece[][] render;
    // the current player who is playing and who is his opposition
    private int current_player = 2;
    // is the game currently in play
    private boolean in_play = true;
    // current scores of player 1 and player 2
    private int player1_score;
    private int player2_score;
    // the width and height of a cell in the board
    private double cell_width;
    private double cell_height;
    // 3x3 array that holds the pieces that surround a given piece
    private int[][] surrounding;
    // 3x3 array that determines if a reverse can be made in any direction
    private boolean[][] can_reverse;
    private int passCount = 0;
    private GameLogicInterface gameLogic;

}
