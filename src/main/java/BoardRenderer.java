public class BoardRenderer {

    private Board board;

    public BoardRenderer(Board board){
        this.board = board;
    }

    public void render(){
        System.out.println();
        System.out.println("   A  B  C  D  E  F  G  H");
        for(short row=0; row<8; row++){
            if(row<9) System.out.print(" ");
            System.out.print(row+1);
            for(short col=0; col<8; col++){
                Coordinates coords = Coordinates.of(row,col);
                System.out.print(board.getSpace(coords));
            }
            System.out.println();
        }
        System.out.println();
    }

    public void render(Move lastMove){
        System.out.println();
        System.out.println("   A  B  C  D  E  F  G  H");
        Coordinates start = lastMove.getStart();
        for(short row=0; row<8; row++){
            if(row<9) System.out.print(" ");
            System.out.print(row+1);
            for(short col=0; col<8; col++){
                Coordinates coords = Coordinates.of(row,col);
                if(coords.equals(start)){
                    if(lastMove.isJump){
                        System.out.print("[!]");
                    } else {
                        System.out.print("[-]");
                    }
                } else if (lastMove.captures.contains(coords)) {
                    System.out.print("[x]");
                } else {
                    System.out.print(board.getSpace(coords));
                }
            }
            System.out.println();
        }
        System.out.println();
    }

}
