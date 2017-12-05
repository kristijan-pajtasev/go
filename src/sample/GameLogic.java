package sample;

import java.util.HashSet;
import java.util.Set;

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
        if(!getPiece(x, y).isEmpty()) throw new Exception("Place is taken");

        GoPiece selectedPiece = getPiece(x, y);
        Set<GoPiece> patch = buildPatch(selectedPiece, player);


        if(isSuicideMove(selectedPiece, patch, player))  throw new Exception("This is suicide move");

        if(hasEscapeRoute(selectedPiece, patch)) {
            selectedPiece.setPiece(player);
        }

        takeOpponentPieces(selectedPiece, player);


    }

    private void takeOpponentPieces(GoPiece selectedPiece, int player) {
        final int other = player == 1 ? 2 : 1;
        final int x = selectedPiece.getX();
        final int y = selectedPiece.getY();
        if(isValidIndex(x - 1, y) && getPiece(x - 1, y).getPiece() == other){ takeOverIfSurrounded(getPiece(x - 1, y)); }
        if(isValidIndex(x + 1, y) && getPiece(x + 1, y).getPiece() == other){ takeOverIfSurrounded(getPiece(x + 1, y)); }
        if(isValidIndex(x, y - 1) && getPiece(x, y - 1).getPiece() == other){ takeOverIfSurrounded(getPiece(x, y - 1)); }
        if(isValidIndex(x, y + 1) && getPiece(x, y + 1).getPiece() == other){ takeOverIfSurrounded(getPiece(x, y + 1)); }
    }

    private void takeOverIfSurrounded(GoPiece startPiece) {
        Set<GoPiece> patch = buildPatch(startPiece, startPiece.getPiece());
        if(isPatchSurrounded(patch)){
            for(GoPiece piece: patch) piece.setPiece(0);
        }
    }

    private boolean isPatchSurrounded(Set<GoPiece> patch) {
        boolean isSurrounded = true;
        for(GoPiece piece: patch) {
            final int x = piece.getX();
            final int y = piece.getY();
            if(isValidIndex(x - 1, y) && getPiece(x - 1, y).getPiece() == 0) { isSurrounded = false; break; }
            if(isValidIndex(x + 1, y) && getPiece(x + 1, y).getPiece() == 0) { isSurrounded = false; break; }
            if(isValidIndex(x, y - 1) && getPiece(x, y - 1).getPiece() == 0) { isSurrounded = false; break; }
            if(isValidIndex(x, y + 1) && getPiece(x, y + 1).getPiece() == 0) { isSurrounded = false; break; }
        }
        return isSurrounded;
    }

    private boolean hasEscapeRoute(GoPiece selectedPiece, Set<GoPiece> patch){
        boolean hasEscapeRoute = false;
        for(GoPiece piece: patch) {
            hasEscapeRoute = hasAvailableAdjacent(piece, selectedPiece) || hasEscapeRoute;
        }
        return hasEscapeRoute;
    }

    private boolean hasAvailableAdjacent(GoPiece piece, GoPiece origin) {
        int x = piece.getX();
        int y = piece.getY();
        boolean hasAvailableAdjacent = false;
        if(isValidIndex(x - 1, y) && isAvailablePlace(getPiece(x - 1, y), origin)) { hasAvailableAdjacent = true; }
        if(isValidIndex(x + 1, y) && isAvailablePlace(getPiece(x + 1, y), origin)) { hasAvailableAdjacent = true; }
        if(isValidIndex(x, y - 1) && isAvailablePlace(getPiece(x, y - 1), origin)) { hasAvailableAdjacent = true; }
        if(isValidIndex(x, y + 1) && isAvailablePlace(getPiece(x, y + 1), origin)) { hasAvailableAdjacent = true; }
        return hasAvailableAdjacent;
    }

    private boolean isAvailablePlace(GoPiece piece, GoPiece origin) {
        return piece.getPiece() == 0 && piece != origin;
    }

    private boolean isSuicideMove(GoPiece selectedPiece, Set<GoPiece> patch, int player){
        boolean hasEscape = false;
        for(GoPiece piece: patch) {
            hasEscape = hasEscapeRoute(piece, patch) || hasEscape;
        }
        return !hasEscape;
    }

    private Set<GoPiece> buildPatch(GoPiece origin, int player) {
        Set<GoPiece> patch = new HashSet<>();
        buildPatch(origin, patch, player);
        return patch;
    }

    private void buildPatch(GoPiece origin, Set<GoPiece> patch, int player) {
        if(patch.contains(origin)) return;
        patch.add(origin);
        int x = origin.getX();
        int y = origin.getY();

        if(isValidIndex(x - 1, y) && getPiece(x - 1, y).getPlayer() == player) {
            buildPatch(getPiece(x - 1, y), patch, player);
        }

        if(isValidIndex(x + 1, y) && getPiece(x + 1, y).getPlayer() == player) {
            buildPatch(getPiece(x + 1, y), patch, player);
        }

        if(isValidIndex(x, y - 1) && getPiece(x, y - 1).getPlayer() == player) {
            buildPatch(getPiece(x, y - 1), patch, player);
        }

        if(isValidIndex(x, y + 1) && getPiece(x, y + 1).getPlayer() == player) {
            buildPatch(getPiece(x, y + 1), patch, player);
        }
    }

    private boolean isValidIndex(int x, int y) {
        return x >= 0 && y >= 0 && x < render.length && y < render[0].length;
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
