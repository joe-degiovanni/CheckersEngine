import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MovesHelper {

    public static List<Move> getValidMoves(CheckersPiece piece, Board board) {
        List<Move> validMoves = new ArrayList<Move>();

        if (piece.isOnBoard()) {
            BoardSpace currentSpace = piece.mySpace.get();
            if(piece.isKinged) {
                validMoves = getValidKingMoves(currentSpace, false);
            } else if (piece.myTeamColor==TeamColor.BLACK) {
                validMoves = getValidBlackMoves(currentSpace, false);
            } else if (piece.myTeamColor==TeamColor.RED) {
                validMoves = getValidRedMoves(currentSpace, false);
            }
        }

        return validMoves;
    }

    private static List<Move> getValidKingMoves(BoardSpace currentSpace, boolean onlyAddJumps) {
        List<Move> moves = new ArrayList<Move>();
        for(Move potentialMove : getAllNorthOrSouthMoves(currentSpace)){
            evaluateMoves(potentialMove, currentSpace, moves, onlyAddJumps);
        }
        return moves;
    }

    private static List<Move> getAllNorthOrSouthMoves(BoardSpace currentSpace) {
        List<Move> allNorthOrSouthMoves = new ArrayList<Move>();
        allNorthOrSouthMoves.addAll(getAllNorthMoves(currentSpace));
        allNorthOrSouthMoves.addAll(getAllSouthMoves(currentSpace));
        return allNorthOrSouthMoves;
    }

    private static List<Move> getValidBlackMoves(BoardSpace currentSpace, boolean onlyAddJumps) {
        List<Move> moves = new ArrayList<Move>();
        for(Move potentialMove : getAllSouthMoves(currentSpace)){
            evaluateMoves(potentialMove, currentSpace, moves, onlyAddJumps);
        }
        return moves;
    }

    private static List<Move> getValidRedMoves(BoardSpace currentSpace, boolean onlyAddJumps) {
        List<Move> moves = new ArrayList<Move>();
        for(Move potentialMove : getAllNorthMoves(currentSpace)){
            evaluateMoves(potentialMove, currentSpace, moves, onlyAddJumps);
        }
        return moves;
    }

    private static List<Move> getAllSouthMoves(BoardSpace currentSpace) {
        List<Move> moves = new ArrayList<Move>();
        Coordinates startCoords = currentSpace.coordinates;
        Move southWest = new Move(startCoords, Coordinates.of(startCoords.getX()+1,startCoords.getY()-1));
        Move southEast = new Move(startCoords, Coordinates.of(startCoords.getX()+1,startCoords.getY()+1));
        moves.add(southWest);
        moves.add(southEast);
        return moves;
    }

    private static List<Move> getAllNorthMoves(BoardSpace currentSpace) {
        List<Move> moves = new ArrayList<Move>();
        Coordinates startCoords = currentSpace.coordinates;
        Move northWest = new Move(startCoords, Coordinates.of(startCoords.getX()-1,startCoords.getY()-1));
        Move northEast = new Move(startCoords, Coordinates.of(startCoords.getX()-1,startCoords.getY()+1));
        moves.add(northWest);
        moves.add(northEast);
        return moves;
    }

    private static void evaluateMoves(Move potentialMove, BoardSpace currentSpace, List<Move> moves, boolean onlyAddJump){
        Coordinates endCoords = potentialMove.getEnd();
        BoardSpace destination = Game.getBoard().getSpace(endCoords);
        if(destination != null){
            if(!destination.getPiece().isPresent() && !onlyAddJump) {
                moves.add(potentialMove);
            } else {
                processJumpPotential(potentialMove, currentSpace, destination, moves);
            }
        }
    }

    private static void processJumpPotential(Move potentialMove, BoardSpace currentSpace, BoardSpace destination, List<Move> moves){
        CheckersPiece myPiece = Game.getBoard().getSpace(potentialMove.getStart()).getPiece().get();;
        CheckersPiece otherPiece = destination.getPiece().get();
        if(myPiece.myTeamColor != otherPiece.myTeamColor){
            // Jump other piece potential, check for jump or double jump.
            int xDirection = potentialMove.getEnd().getX()-potentialMove.getStart().getX();
            int yDirection = potentialMove.getEnd().getY()-potentialMove.getStart().getY();
            Coordinates jumpToCoords = Coordinates.of(currentSpace.coordinates.getX()+2*xDirection, currentSpace.coordinates.getY()+2*yDirection); // try the piece on the other side of the opponent
            BoardSpace jumpToSpace = Game.getBoard().getSpace(jumpToCoords);
            if(jumpToSpace != null){
                if(!jumpToSpace.getPiece().isPresent()) {
                    potentialMove = new Move(potentialMove.getStart(), jumpToCoords);
                    potentialMove.setJump(true);
                    System.out.println("jump potential: "+potentialMove);
                    moves.add(potentialMove);
                    //evaluateMultipleJumpPotential(Game.getBoard().getSpace(jumpToCoords), myPiece.myTeamColor, moves, potentialMove);
                }
            }
        }
    }

    private static void evaluateMultipleJumpPotential(BoardSpace space, TeamColor myTeamColor, List<Move> moves, Move jumpMove) {
        List<Move> additionalMoves = new ArrayList<Move>();
        CheckersPiece piece = Game.getBoard().getSpace(jumpMove.getStart()).getPiece().get();
        if(piece.isKinged) {
            additionalMoves = getValidKingMoves(space, true);
        } else if (myTeamColor==TeamColor.BLACK) {
            additionalMoves = getValidBlackMoves(space, true);
        } else if (myTeamColor==TeamColor.RED) {
            additionalMoves = getValidRedMoves(space, true);
        }
        for (Move additional : additionalMoves){
            Move combined = new Move(jumpMove.getStart(),additional.getEnd(), additional.getEnd(), additional.subMoves.remove(additional.subMoves.size()));
            moves.add(combined);
            evaluateMultipleJumpPotential(space, myTeamColor, moves, combined);
        }
    }

    public static Move chooseRandomMoveFromList(List<Move> moves){
        if (moves.size() > 0) {
            Collections.shuffle(moves);
            moves.sort(new Comparator<Move>() {
                @Override
                public int compare(Move o1, Move o2) {
                    if(o1.isJump && !o2.isJump){
                        return -1;
                    } else if(!o1.isJump && o2.isJump){
                        return 1;
                    } else {
                        return 0;
                    }
                }
            });
            Move result = moves.get(0);
            System.out.println("possible moves: "+moves);
            System.out.println("Chosen: " + result.toString());
            return result;
        } else {
            return null;
        }
    }
}
