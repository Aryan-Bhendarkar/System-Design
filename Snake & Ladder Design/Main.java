import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Jump> jumps = List.of(
                new Jump(3, 22, JumpType.LADDER),
                new Jump(5, 8, JumpType.LADDER),
                new Jump(27, 1, JumpType.SNAKE),
                new Jump(21, 9, JumpType.SNAKE)
        );

        Board board = new Board(30, jumps);
        Dice dice = new StandardDice(1, 6);

        List<Player> players = List.of(
                new Player("P1", "Aryan"),
                new Player("P2", "Riya")
        );

        boolean exactWinRequired = true;
        int maxTurns = 500;

        SnakeAndLadderGame game = new SnakeAndLadderGame(board, dice, players, exactWinRequired, maxTurns);
        GameResult result = game.play();

        System.out.println("=== Snake and Ladder Result ===");
        for (String log : result.getMoveLog()) {
            System.out.println(log);
        }

        if (result.getWinner() != null) {
            System.out.printf("Winner: %s in %d turns%n", result.getWinner().getName(), result.getTurnsPlayed());
        } else {
            System.out.printf("No winner in %d turns%n", result.getTurnsPlayed());
        }
    }
}
