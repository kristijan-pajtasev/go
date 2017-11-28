package sample;

class GameLogic implements GameLogicInterface {
    private GoPiece[][] render;
    private int player1_score, player2_score;

    GameLogic(GoPiece[][] board) {
        this.render = board;
    }

    // private method for determining which pieces surround x,y will update the
    // surrounding array to reflect this
    private void determineSurrounding(final int x, final int y) {
        // todo: implement determing surrounding
    }

    // private method for determining if a reverse can be made will update the can_reverse
    // array to reflect the answers will return true if a single reverse is found
    private boolean determineReverse(final int x, final int y) {
        boolean reverseChain_exist = false;
        // todo: check if has anything to swithc
        return reverseChain_exist;
    }

    // private method that will determine if the end of the game has been reached
    private void determineEndGame() {
        boolean emptyCell = false;

        // check if there is an empty cell
        for(int i = 0; i < render.length ;i++) {
            for(int j = 0; j < render[i].length; j++) {

                if(this.render[i][j].getPiece() == 0 && emptyCell == false) {
                    emptyCell = true;

                }

            }
        }
    }

    // private method to determine if a player has a move available
    private boolean canMove() {
        boolean canMove = false;
        // todo:  check if empty space exist that is not suicide place
        return canMove;

    }

    @Override
    public void placePiece(int x, int y, int player) throws Exception{
        if(getPiece(x, y).isEmpty()) {
            getPiece(x, y).setPiece(player);
        } else {
            throw new Exception("Place is taken");
        }
    }

    public GoPiece getPiece(int x, int y) {
        return render[x][y];
    }

    @Override
    public boolean canPlacePiece(int x, int y) {
        // todo
        return false;
    }

    @Override
    public boolean isEndGame() {
        // todo
        return false;
    }

    // private method that determines who won the game
    public String determineWinner() {
        if(player1_score > player2_score) {
            return "White wins!";
        }else if(player1_score < player2_score) {
            return "Black wins!";
        }else {
            return "Draw";
        }

    }

    // private method for placing a piece and reversing pieces
    private void swapPiece(final int x, final int y, int player) {
        render[x][y].setPiece(player);
    }
}
