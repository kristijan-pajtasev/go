package sample;
import javafx.scene.Group;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.transform.Translate;

import java.util.ArrayList;


//class definition for a Go piece
class GoPiece extends Group {
    // default constructor for the class
    private int x, y;
    private ArrayList<Integer> history;

    public GoPiece(int x, int y, int player) {
        this.x = x;
        this.y = y;
        history = new ArrayList<Integer>();
        t = new Translate();
        this.player = player;
        piece = new Ellipse();

        setPiece(player);

        piece.getTransforms().add(t);
        getChildren().add(piece);

        DropShadow ds = new DropShadow(5.0, 3.0, 3.0, Color.color(0.4, 0.4, 0.4));
        piece.setEffect(ds);

    }

    public boolean isReptableState() {
        int size = history.size();
        if(size < 5) return false;
        return history.get(size - 1) == history.get(size - 3);
    }

    public void undoLastMove() {
        player = history.get(history.size() - 2);
        setColor(player);
        history.remove(history.size() - 1);
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
        super.relocate(x, y);
        t.setX(x); t.setY(y);

    }

    public void setForMoveLevel(int level) {
        if(history.size() < level) history.add(player);
    }

    // method that will set the piece type
    public void setPiece(final int type) {

        player = type;
        history.add(type);
        setColor(player);
    }

    private void setColor(int player) {
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


    public boolean isEmpty() {
        return this.player == 0;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getPlayer() {
        return player;
    }

    // private fields
    private int player;		// the player that this piece belongs to
    private Ellipse piece;	// ellipse representing the player's piece
    private Translate t;	// translation for the player piece
}