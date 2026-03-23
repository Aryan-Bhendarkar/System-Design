import java.util.ArrayList;
import java.util.List;

public class SnakeAndLadderGame {
    private final Board board;
    private final Dice dice;
    private final List<Player> players;
    private final boolean exactWinRequired;
    private final int maxTurns;

    public SnakeAndLadderGame(Board board, Dice dice, List<Player> players, boolean exactWinRequired, int maxTurns) {
        if (board == null || dice == null) {
            throw new IllegalArgumentException("Board and dice are required.");
        }

        if (players == null || players.size() < 2) {
            throw new IllegalArgumentException("At least 2 players are required.");
        }
        if (maxTurns <= 0) {
            throw new IllegalArgumentException("maxTurns must be greater than 0.");
        }

        this.board = board;
        this.dice = dice;
        this.players = players;
        this.exactWinRequired = exactWinRequired;
        this.maxTurns = maxTurns;
    }

    public GameResult play() {
        List<String> moveLog = new ArrayList<>();
        int turnsPlayed = 0;
        Player winner = null;
        int playerTurn = 0;

        while (turnsPlayed < maxTurns && winner == null) {
            Player currentPlayer = players.get(playerTurn);
            turnsPlayed++;

            int diceValue = dice.roll();
            int currentPosition = currentPlayer.getPosition();
            int candidatePosition = currentPosition + diceValue;

            int finalPosition = resolvePosition(candidatePosition, currentPosition);

            currentPlayer.setPosition(finalPosition);
            moveLog.add(currentPlayer.getName() + " rolled " + diceValue
                    + " and moved from " + currentPosition + " to " + finalPosition);

            if (finalPosition == board.getSize() - 1) {
                winner = currentPlayer;
                break;
            }

            playerTurn = (playerTurn + 1) % players.size();
        }

        return new GameResult(winner, turnsPlayed, moveLog);
    }

    private int resolvePosition(int candidatePosition, int currentPosition) {
        int lastCell = board.getSize() - 1;

        if (candidatePosition > lastCell) {
            return exactWinRequired ? currentPosition : lastCell;
        }

        Jump jump = board.getJumpAt(candidatePosition);
        return jump == null ? candidatePosition : jump.getEnd();
    }
}
