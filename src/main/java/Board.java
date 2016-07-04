import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Simple representation of a checkers board
 */

public class Board {

    private Map<Coordinates, BoardSpace> boardMap;
    List<CheckersPiece> blackPieces;
    List<CheckersPiece> redPieces;

    public Board(){
        boardMap = new HashMap<Coordinates, BoardSpace>();
        blackPieces = new ArrayList<CheckersPiece>();
        redPieces = new ArrayList<CheckersPiece>();
        for(short row=0; row < 8; row++){
            for(short column=0; column < 8; column++){
                Coordinates coords = Coordinates.of(row, column);
                BoardSpace boardSpace = new BoardSpace(coords);
                boardMap.put(coords, boardSpace);
            }
        }
        initialize();
    }

    public void addPiece(Coordinates coordinates, CheckersPiece piece){
        piece.setSpace(boardMap.get(coordinates));
        boardMap.get(coordinates).setPiece(piece);
    }

    public void removePiece(Coordinates coordinates, CheckersPiece piece){
        piece.setSpace(null);
        boardMap.get(coordinates).removePiece();
    }

    public BoardSpace getSpace(Coordinates coordinates){
        return boardMap.get(coordinates);
    }

    public List<CheckersPiece> getPlayerPieces(TeamColor teamColor){
        if(teamColor == TeamColor.BLACK){
            return blackPieces;
        } else if (teamColor == TeamColor.RED) {
            return redPieces;
        } else {
            return null;
        }
    }


    public void movePiece(Coordinates current, Coordinates destination){
        CheckersPiece piece = boardMap.get(current).removePiece().orElse(null);
        piece.setSpace(boardMap.get(destination));
        boardMap.get(destination).setPiece(piece);
    }

    public void initialize(){
        // add black pieces
        for(short row=0; row < 3; row++){
            for(short column=0; column < 8; column++){
                if((column+row)%2==0){
                    Coordinates coords = Coordinates.of(row, column);
                    CheckersPiece newPiece = new CheckersPiece(TeamColor.BLACK);
                    blackPieces.add(newPiece);
                    addPiece(coords, newPiece);
                }
            }
        }

        // add red pieces
        for(short row=5; row < 8; row++){
            for(short column=0; column < 8; column++){
                if((column+row)%2==0){
                    Coordinates coords = Coordinates.of(row, column);
                    CheckersPiece newPiece = new CheckersPiece(TeamColor.RED);
                    redPieces.add(newPiece);
                    addPiece(coords, newPiece);
                }
            }
        }
    }

}
