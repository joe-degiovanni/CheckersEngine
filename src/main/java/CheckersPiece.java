import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CheckersPiece {

    boolean isKinged;
    TeamColor myTeamColor;
    Optional<BoardSpace> mySpace;

    public CheckersPiece(TeamColor teamColor) {
        myTeamColor = teamColor;
        mySpace = Optional.ofNullable(null);
        isKinged = false;
    }

    public void setSpace(BoardSpace boardSpace){
        mySpace = Optional.ofNullable(boardSpace);
        setKinged(myTeamColor, mySpace);
    }

    private void setKinged(TeamColor myTeamColor, Optional<BoardSpace> mySpace) {
        if(!mySpace.isPresent()) return;
        if(myTeamColor == TeamColor.BLACK && mySpace.get().coordinates.getX()==7){
            isKinged = true;
        } else if (myTeamColor == TeamColor.RED && mySpace.get().coordinates.getX()==0) {
            isKinged = true;
        }
    }

    public String toString(){
        if(isKinged){
            return myTeamColor.toString().toUpperCase();
        } else {
            return myTeamColor.toString().toLowerCase();
        }
    }

    public boolean isOnBoard(){
        return mySpace.isPresent();
    }

}
