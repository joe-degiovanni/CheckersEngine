import java.util.ArrayList;
import java.util.List;

public class Player {

    List<CheckersPiece> myPieces;
    TeamColor teamColor;
    Game game;

    public Player(TeamColor color, Game game){
        this.teamColor = color;
        this.game = game;
        this.myPieces = game.getBoard().getPlayerPieces(color);
    }

    public Move playTurn(){
        Move move = getMove();
        if(move != null) {
            move.execute();
            return move;
        }
        return null;
    }

    public Move getMove(){
        List<Move> allPotentialMoves = new ArrayList<Move>();
        for(CheckersPiece piece : myPieces){
            List<Move> pieceMoves = MovesHelper.getValidMoves(piece, game.getBoard());
            allPotentialMoves.addAll(pieceMoves);
        }
        return MovesHelper.chooseRandomMoveFromList(allPotentialMoves);
    }

}
