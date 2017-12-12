package sample;

import java.util.HashSet;
import java.util.Set;

class GameLogic implements GameLogicInterface {
    private GoPiece[][] render;
    private int player1_score, player2_score;
    private int move = 1;
    private boolean gameOver = false;

    GameLogic(GoPiece[][] board) {
        this.render = board;
        player1_score = 0;
        player2_score = 0;
    }

    @Override
    public void placePiece(int x, int y, int player) throws Exception{
        if(gameOver) throw new Exception("Game is over");

        if(!getPiece(x, y).isEmpty()) throw new Exception("Place is taken");

        GoPiece selectedPiece = getPiece(x, y);
        Set<GoPiece> patch = buildPatch(selectedPiece, player);


        if(isSuicideMove(selectedPiece, patch, player)){
            if(isKOMove(selectedPiece, patch, player)){
                selectedPiece.setPiece(player);
            } else throw new Exception("This is suicide move");
        } else {
            selectedPiece.setPiece(player);
        }

        takeOpponentPieces(selectedPiece, player);
        move++;
        for(GoPiece[] row: render)
            for(GoPiece piece: row)
                piece.setForMoveLevel(move);

        if(isRepeatableState()) {
            undo();
            throw new Exception("Repeatable state");
        }
    }

    private void undo() {
        move--;
        for(GoPiece[] row: render)
            for(GoPiece piece: row) piece.undoLastMove();
    }

    private boolean isRepeatableState() {
        boolean isRepeatableState = true;
        for(GoPiece[] row: render)
            for(GoPiece piece: row) isRepeatableState = piece.isReptableState() != false && isRepeatableState;
        return isRepeatableState;
    }

    private boolean isKOMove(GoPiece selectedPiece, Set<GoPiece> patch, int player) {
        boolean isKOMove = false;
        final int other = player == 1 ? 2 : 1;
        final int x = selectedPiece.getX();
        final int y = selectedPiece.getY();

        if(isValidIndex(x - 1, y) && getPiece(x - 1, y).getPiece() == other){
            isKOMove = canTakeoverArea(getPiece(x - 1, y), selectedPiece) || isKOMove; }

        if(isValidIndex(x + 1, y) && getPiece(x + 1, y).getPiece() == other){
            isKOMove = canTakeoverArea(getPiece(x + 1, y), selectedPiece) || isKOMove; }

        if(isValidIndex(x, y - 1) && getPiece(x, y - 1).getPiece() == other){
            isKOMove = canTakeoverArea(getPiece(x, y - 1), selectedPiece) || isKOMove; }

        if(isValidIndex(x, y + 1) && getPiece(x, y + 1).getPiece() == other){
            isKOMove = canTakeoverArea(getPiece(x, y + 1), selectedPiece) || isKOMove; }

        return isKOMove;
    }

    private boolean canTakeoverArea(GoPiece piece, GoPiece selectedPiece) {
        return isPatchSurrounded(buildPatch(piece, piece.getPlayer()), selectedPiece);
    }

    private boolean isPatchSurrounded(Set<GoPiece> goPieces, GoPiece selectedPiece) {
        boolean isSurrounded = true;
        for(GoPiece piece: goPieces) {
            final int x = piece.getX();
            final int y = piece.getY();
            if(isValidIndex(x - 1, y) && getPiece(x - 1, y).getPiece() == 0 && getPiece(x - 1, y) != selectedPiece){ isSurrounded = false; }
            if(isValidIndex(x + 1, y) && getPiece(x + 1, y).getPiece() == 0 && getPiece(x + 1, y) != selectedPiece){ isSurrounded = false; }
            if(isValidIndex(x, y - 1) && getPiece(x, y - 1).getPiece() == 0 && getPiece(x, y - 1) != selectedPiece){ isSurrounded = false; }
            if(isValidIndex(x, y + 1) && getPiece(x, y + 1).getPiece() == 0 && getPiece(x, y + 1) != selectedPiece){ isSurrounded = false; }
        }
        return isSurrounded;
    }

    private void takeOpponentPieces(GoPiece selectedPiece, int player) {
        final int other = player == 1 ? 2 : 1;
        final int x = selectedPiece.getX();
        final int y = selectedPiece.getY();
        if(isValidIndex(x - 1, y) && getPiece(x - 1, y).getPiece() == other){ takeOverIfSurrounded(getPiece(x - 1, y), player); }
        if(isValidIndex(x + 1, y) && getPiece(x + 1, y).getPiece() == other){ takeOverIfSurrounded(getPiece(x + 1, y), player); }
        if(isValidIndex(x, y - 1) && getPiece(x, y - 1).getPiece() == other){ takeOverIfSurrounded(getPiece(x, y - 1), player); }
        if(isValidIndex(x, y + 1) && getPiece(x, y + 1).getPiece() == other){ takeOverIfSurrounded(getPiece(x, y + 1), player); }
    }

    private void takeOverIfSurrounded(GoPiece startPiece, int player) {
        Set<GoPiece> patch = buildPatch(startPiece, startPiece.getPiece());
        if(isPatchSurrounded(patch)){
            updateScore(player == 1 ? 2 : 1, patch.size());
            for(GoPiece piece: patch) piece.setPiece(0);
        }
    }

    private void updateScore(int player, int increment) {
        if(player == 1) player1_score += increment;
        else player2_score += increment;
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

    private boolean hasEscapeRoute(GoPiece currentPiece, Set<GoPiece> patch, GoPiece selectedPiece){
        boolean hasEscapeRoute = false;
        for(GoPiece piece: patch) {
            hasEscapeRoute = hasAvailableAdjacent(piece, currentPiece, selectedPiece) || hasEscapeRoute;
        }
        return hasEscapeRoute;
    }

    private boolean hasAvailableAdjacent(GoPiece piece, GoPiece origin, GoPiece selectedPiece) {
        int x = piece.getX();
        int y = piece.getY();
        boolean hasAvailableAdjacent = false;
        if(isValidIndex(x - 1, y) && isAvailablePlace(getPiece(x - 1, y), selectedPiece)) { hasAvailableAdjacent = true; }
        if(isValidIndex(x + 1, y) && isAvailablePlace(getPiece(x + 1, y), selectedPiece)) { hasAvailableAdjacent = true; }
        if(isValidIndex(x, y - 1) && isAvailablePlace(getPiece(x, y - 1), selectedPiece)) { hasAvailableAdjacent = true; }
        if(isValidIndex(x, y + 1) && isAvailablePlace(getPiece(x, y + 1), selectedPiece)) { hasAvailableAdjacent = true; }
        return hasAvailableAdjacent;
    }

    private boolean isAvailablePlace(GoPiece piece, GoPiece origin) {
        return piece.getPiece() == 0 && piece != origin;
    }

    private boolean isSuicideMove(GoPiece selectedPiece, Set<GoPiece> patch, int player){
        boolean hasEscape = false;
        for(GoPiece piece: patch) {
            hasEscape = hasEscapeRoute(piece, patch, selectedPiece) || hasEscape;
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

    public void endGame() {
        // todo add areas to total score
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

    @Override
    public int playerOneScore() {
        return player1_score;
    }

    @Override
    public int playerTwoScore() {
        return player2_score;
    }
}
