
import java.util.ArrayList;
import java.util.List;

public class Move {

    Board board;
    List<Coordinates> subMoves;
    List<Coordinates> captures;
    boolean isJump;

    public Move(Coordinates start, Coordinates end, Coordinates... intermediates){
        captures = new ArrayList<Coordinates>();
        board = Game.getBoard();
        isJump = false;
        subMoves = new ArrayList<Coordinates>();
        subMoves.add(start);
        if(intermediates != null) {
            for (Coordinates middle : intermediates) {
                subMoves.add(middle);
            }
        }
        subMoves.add(end);
    }

    public void setJump(boolean isJump){
        this.isJump = isJump;
    }

    public boolean isJump(){
        return isJump;
    }

    public Coordinates getStart(){
        return subMoves.get(0);
    }

    public Coordinates getEnd(){
        return subMoves.get(subMoves.size()-1);
    }

    public String toString(){
        return subMoves.toString();
    }

    public void execute(){
        Game.getBoard().movePiece(getStart(), getEnd());
        if(isJump){
            int deadPieceX = (getStart().getX()+getEnd().getX())/2;
            int deadPieceY = (getStart().getY()+getEnd().getY())/2;
            Coordinates deadCoords = Coordinates.of(deadPieceX, deadPieceY);
            captures.add(deadCoords);
            System.out.println("Captured peice at: " +deadCoords);
            CheckersPiece deadPiece = Game.getBoard().getSpace(deadCoords).getPiece().get();
            Game.getBoard().removePiece(deadCoords, deadPiece);
        }
    }

}
