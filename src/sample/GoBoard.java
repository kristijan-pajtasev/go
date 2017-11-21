package sample;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Line;
import javafx.scene.transform.Translate;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Color;



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
    public void placePiece(final double x, final double y) {
        // figure out which cell the current player has clicked on
        final int cellx = (int) (x / cell_width);
        final int celly = (int) (y / cell_height);



        // if the game is not in play then do nothing
//        if(!in_play)
//            return;
//
        // if there is a piece already placed then return and do nothing
        if(render[cellx][celly].getPiece() != 0)
            return;

//        // determine what pieces surround the current piece. if there is no opposing
//        // pieces then a valid move cannot be made.
//        determineSurrounding(cellx, celly);
//        if(!adjacentOpposingPiece())
//            return;
//
//        // see if a reverse can be made in any direction if none can be made then return
//        if(!determineReverse(cellx, celly))
//            return;

        // at this point we have done all the checks and they have passed so now we can place
        // the piece and perform the reversing also check if the game has ended
        placeAndReverse(cellx, celly);

        // if we get to this point then a successful move has been made so swap the
        // players and update the scores
        swapPlayers();
        updateScores();
        determineEndGame();

        // print out some information
        System.out.println("placed at: " + cellx + ", " + celly);
        System.out.println("White: " + player1_score + " Black: " + player2_score);
        if(current_player == 1)
            System.out.println("current player is White");
        else
            System.out.println("current player is Black");
    }

    // public method for resetting the game
    public void resetGame() {

        resetRenders();
        initialiseRender();


        this.current_player = 2;
        this.player1_score = 0;
        this.player2_score = 0;

    }

    // private method that will reset the renders
    private void resetRenders() {

        //set the array of render to 0
        //step 9
        for(int i = 0; i < 7; i++)
            for(int j = 0; j < 7; j++) {
                render[i][j].setPiece(0);
            }

    }

    // private method that will initialise the background and the lines
    private void initialiseLinesBackground() {

        //add background color
        background = new Rectangle();
//        Image image = new Image("/background.jpg");
//        ImagePattern imagepattern = new ImagePattern(image);
//        background.setId("pane");
//        background.setFill(imagepattern);
          background.setFill(Color.CYAN);


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

        //not utilizing the opposing variable

        if(this.current_player == 1) {
            this.current_player = 2;
        }else {
            this.current_player = 1;
        }

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

    // private method for determining which pieces surround x,y will update the
    // surrounding array to reflect this
    private void determineSurrounding(final int x, final int y) {

        // value that I will use to iterate on the surrounding array
        int iteratorX = x;
        int iteratorY = y;

        // check if the value of the piece is not -1 meaning outside the board or already played
        if(this.getPiece(x, y) != -1) {

            //the center piece will have an index of 0,0 ; top left -1,-1 bottom right 1,1
            for(int i = -1; i<=1 ; i++) {
                //calculating the x value of the piece in surrounding
                iteratorX = iteratorX + i;

                for(int j =-1; j<=1; j++) {
                    //calculating the x value of the piece in surrounding and check the type of piece by adding it to surrounding array
                    iteratorY = iteratorY + j;
                    surrounding[i+1][j+1] = this.getPiece(iteratorX, iteratorY);
                    iteratorY = y;

                }

                // initialise iterator x
                iteratorX = x;

            }
        }


    }

    // private method for determining if a reverse can be made will update the can_reverse
    // array to reflect the answers will return true if a single reverse is found
    private boolean determineReverse(final int x, final int y) {

        boolean reverseChain_exist = false;

        //the center piece will have an index of 0,0 ; top left -1,-1 bottom right 1,1
        for(int i = -1; i<=1 ; i++) {
            //set direction dx
            int dx = i;

            for(int j = -1; j<=1; j++) {
                //set direction dy
                int dy= j;

                // set the can_reverse to true or false if the isReverseChain is possible
                if(dx==0 && dy == 0) {
                    can_reverse[i+1][j+1] = false;
                }else {
                    can_reverse[i+1][j+1] = isReverseChain(x, y, dx, dy, this.current_player);
                }
                //System.out.println(can_reverse[i+1][j+1]);

                if(!reverseChain_exist && can_reverse[i+1][j+1]) {
                    reverseChain_exist = true;
                }

            }

        }

        // NOTE: this is to keep the compiler happy until you get to this part
        return reverseChain_exist;
    }

    // private method for determining if a reverse can be made from a position (x,y) for
    // a player piece in the given direction (dx,dy) returns true if possible
    // assumes that the first piece has already been checked
    private boolean isReverseChain(final int x, final int y, final int dx, final int dy, final int player) {


        int adjacentX = x+dx;
        int adjacentY = y+dy;
        boolean validChain = false;

        if(this.getPiece(adjacentX, adjacentY) != player) {



            //very bad but could not find a better way
            while(true) {

                if(this.getPiece(adjacentX, adjacentY) == -1 || this.getPiece(adjacentX, adjacentY) == 0) {
                    //System.out.println("break. adjacentX : " + adjacentX +  " adjacentY : " + adjacentY);
                    break;
                }

                //if in the same direction the adjacent piece is the same
                if(this.getPiece(adjacentX , adjacentY) == player) {

                    validChain = true;
                    //System.out.println("Super. adjacentX : " + adjacentX +  " adjacentY : " + adjacentY);
                    break;
                }


                adjacentX = adjacentX + dx;
                adjacentY = adjacentY + dy;
                //System.out.println(" X: " + adjacentX +  " Y : " + adjacentY);

            }

        }
        // NOTE: this is to keep the compiler happy until you get to this part
        return validChain;


    }

    // private method for determining if any of the surrounding pieces are an opposing
    // piece. if a single one exists then return true otherwise false
    private boolean adjacentOpposingPiece() {

        boolean exist = false;

        for(int i =0; i<=2; i++) {
            for(int j=0; j<=2; j++) {

                if(surrounding[i][j]!= -1) {
                    //check the surrounding piece are the same as the current player
                    if(exist == false && surrounding[i][j] != this.current_player) {
                        exist = true;
                    }
                }

            }
        }

        return exist;
    }

    // private method for placing a piece and reversing pieces
    private void placeAndReverse(final int x, final int y) {

        GoPiece pieceToPlace = render[x][y];
        pieceToPlace.setPiece(this.current_player);

        for(int dx =-1; dx<=1; dx++) {
            for(int dy = -1; dy<=1; dy++) {

                //Reverse in all direction but not 0,0 which the position of the current piece.
                if(!(dx == 0  && dy ==0 )){
                    reverseChain(x, y, dx, dy);
                }
            }
        }
    }

    // private method to reverse a chain
    private void reverseChain(final int x, final int y, final int dx, final int dy) {

        if(can_reverse[dx + 1][dy + 1] == true) {
            //System.out.println("yes");
            //initiate coordinate of the piece that need to be swap
            int currentX = x + dx;
            int currentY = y + dy;

            //check if it is an opposite Piece and swap the color
            while(this.getPiece(currentX, currentY) != this.current_player) {
                GoPiece pieceToReverse = render[currentX][currentY];
                pieceToReverse.swapPiece();

                currentX = currentX + dx;
                currentY = currentY + dy;
            }
        }
    }

    // private method that will determine if the end of the game has been reached
    private void determineEndGame() {


        boolean emptyCell = false;
        boolean moreMoves = false;
        boolean scoreAboveZero = false;

        // check if there is an empty cell
        for(int i = 0; i < render.length ;i++) {
            for(int j = 0; j < render[i].length; j++) {

                if(this.render[i][j].getPiece() == 0 && emptyCell == false) {
                    emptyCell = true;

                }

            }
        }


        //check if player still has piece on the board
        if(player1_score > 0 && player2_score > 0) {
            scoreAboveZero = true;
        }else {
            System.out.println("Game over! you have no more prieces left on the board");
        }

        //check if both can make a move
        if(canMove() == false) {
            swapPlayers();
            if(canMove() == true) {
                moreMoves = true;
            }else {
                System.out.println("Game over no more move possible on both side");
            }
        }else {
            moreMoves = true;
        }


        if(emptyCell == false || moreMoves == false || scoreAboveZero == false) {
            this.in_play = false;
            System.out.println("Game over");
            determineWinner();
        }




    }

    // private method to determine if a player has a move available
    private boolean canMove() {

        boolean canMove = false;

        for(int i = 0; i < render.length ;i++) {
            for(int j = 0; j < render[i].length; j++) {

                if(this.render[i][j].getPiece() == 0 && determineReverse(i, j)) {
                    render[i][j].setPiecegrey();
                }else if (this.render[i][j].getPiece() == 0 && !determineReverse(i, j)) {
                    render[i][j].setPiecewhite();
                }


                if(this.render[i][j].getPiece() == 0 && canMove == false) {

                    canMove = determineReverse(i, j);
                }

            }
        }

        return canMove;

    }

    // private method that determines who won the game
    private void determineWinner() {

        if(player1_score > player2_score) {
            System.out.println("White wins!");
        }else if(player1_score < player2_score) {
            System.out.println("Black wins!");
        }else {
            System.out.println("Draw");
        }

    }

    // private method that will initialise everything in the render array
    private void initialiseRender() {

        //set the array to null object
        for(int i = 0; i < 7; i++)
            for(int j = 0; j < 7; j++) {
                render[i][j] = new GoPiece(0);
                getChildren().add(render[i][j]);
            }

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
    private int opposing;
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

}
