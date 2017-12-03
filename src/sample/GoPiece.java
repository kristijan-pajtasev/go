package sample;
import javafx.scene.Group;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.transform.Translate;


//class definition for a Go piece
class GoPiece extends Group {
    // default constructor for the class
    public GoPiece(int player) {

        // create a new translate object and take a copy of the player
        //step 15
        t = new Translate();
        this.player = player;
        piece = new Ellipse();

        setPiece(player);

        piece.getTransforms().add(t);
        getChildren().add(piece);

        DropShadow ds = new DropShadow(5.0, 3.0, 3.0, Color.color(0.4, 0.4, 0.4));
        piece.setEffect(ds);

    }

    // overridden version of the resize method to give the piece the correct size
    @Override
    public void resize(double width, double height) {

        super.resize(width, height);

        // recenter the ellipse// and update the radii
        piece.setCenterX(width / 2); piece.setCenterY(height / 2);
        piece.setRadiusX(width / 2.2); piece.setRadiusY(height / 2.2);

    }

    // overridden version of the relocate method to position the piece correctly
    @Override
    public void relocate(double x, double y) {

        //step 17
        super.relocate(x, y);
        t.setX(x); t.setY(y);

    }

    // public method that will swap the colour and type of this piece
    public void swapPiece() {

        if(piece.getFill() == Color.WHITE) {
            piece.setFill(Color.BLACK);
        }else {
            piece.setFill(Color.WHITE);
        }

    }

    // method that will set the piece type
    public void setPiece(final int type) {

        player = type;

        //choose which piece type we have
        if(player == 1) {

            piece.setFill(Color.WHITE);


        }else if(player == 2) {

            piece.setFill(Color.BLACK);

        }else if(player == 0) {
            piece.setFill(Color.TRANSPARENT);

        }


    }

    // method that will set the piece type
    public void setPiecegrey() {

        piece.setFill(Color.rgb(0,0,0,0.5));
    }

    // method that will set the piece type
    public void setPiecewhite() {

        piece.setFill(Color.TRANSPARENT);
    }


    // returns the type of this piece
    public int getPiece() {
        // NOTE: this is to keep the compiler happy until you get to this point
        int player_value = 0;

        if(piece.getFill() == Color.WHITE) {

            player_value = 1;

        }else if(piece.getFill() == Color.BLACK) {

            player_value = 2;

        }

        return player_value;
    }

    // private fields
    private int player;		// the player that this piece belongs to
    private Ellipse piece;	// ellipse representing the player's piece
    private Translate t;	// translation for the player piece
}