
import java.util.Scanner;

public class Game{
    private Board board;
    private Player player1;
    private Player player2;
    private Player currentPlayer;

    public Game() {
        this.board = new Board(3);
        this.player1 = new Player("Player 1", Symbol.X);
        this.player2 = new Player("Player 2", Symbol.O);
        this.currentPlayer = player1;
    }

    public void switchPlayer(){
        currentPlayer = (currentPlayer == player1) ? player2 : player1;
    }

    public void start(){
        Scanner scn = new Scanner(System.in);
        while (true) { 
            board.printBoard();
            System.out.println(currentPlayer.getName()+"'s turn");
            int row = scn.nextInt();
            int col = scn.nextInt();

            Move move = new Move(currentPlayer.getSymbol(), col, row);
           
            if(board.makeMove(move)){
                switchPlayer();
            }
            else{
                System.out.println("Invalid move. Try again.");
            }
        }
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.start();
    }
}