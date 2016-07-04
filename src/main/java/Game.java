public class Game {

    private static Board board;
    BoardRenderer renderer;
    Player redPlayer;
    Player blackPlayer;

    public Game(){
        this.board = new Board();
        this.renderer = new BoardRenderer(board);
        this.redPlayer = new Player(TeamColor.RED, this);
        this.blackPlayer = new Player(TeamColor.BLACK, this);
    }

    public static final Board getBoard(){
        return board;
    }

    public static void main(String[] args){
        Game game = new Game();
        game.renderer.render();
        int jumpCount = 0;
        int movesCount = 0;
        while (true) {
            // black turn
            System.out.println("Black turn start");
            Move move = game.blackPlayer.playTurn();
            if(move == null) {
                System.out.println("black's turn and has no valid move: game over");
                break;
            }
            game.renderer.render(move);
            movesCount++;
            if(move.isJump) jumpCount++;

            // red turn
            System.out.println("Red turn start");
            move = game.redPlayer.playTurn();
            if(move == null) {
                System.out.println("red's turn and has no valid move: game over");
                break;
            }
            game.renderer.render(move);
            movesCount++;
            if(move.isJump) jumpCount++;
        }
        System.out.println("total moves: "+movesCount);
        System.out.println("total jumps: "+jumpCount);
    }
}
