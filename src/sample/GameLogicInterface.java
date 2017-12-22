package sample;

/**
 * Created by Kristijan Pajtasev
 * 28/11/2017.
 */
public interface GameLogicInterface {

    public void placePiece(int x, int y, int player) throws Exception;

    public GoPiece getPiece(int x, int y);

    public boolean canPlacePiece(int x, int y);

    public boolean isEndGame();

    public String determineWinner();

    public int playerOneScore();

    public int playerTwoScore();

    public void endGame()throws Exception ;

}
