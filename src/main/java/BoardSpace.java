import java.util.Optional;

public class BoardSpace {

    private Optional<CheckersPiece> occupyingPiece;
    final Coordinates coordinates;

    public BoardSpace(Coordinates coordinates){
        this.occupyingPiece = Optional.ofNullable(null);
        this.coordinates = coordinates;
    }

    public void setPiece(CheckersPiece piece) {
        occupyingPiece = Optional.of(piece);
    }

    public Optional<CheckersPiece> getPiece() {
        return occupyingPiece;
    }

    public Optional<CheckersPiece> removePiece() {
        Optional<CheckersPiece> piece = occupyingPiece;
        occupyingPiece = Optional.ofNullable(null);
        return piece;
    }

    public String toString(){
        if(occupyingPiece.isPresent()) {
            return "[" + occupyingPiece.get() + "]";
        } else {
            return "[ ]";
        }
    }
}
