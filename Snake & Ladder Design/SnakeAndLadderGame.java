import java.util.ArrayList;
import java.util.List;

public class SnakeAndLadderGame {
    private static final int FIRST_CELL = 0;

    private final Board board;
    private final Dice dice;
    private final List<Player> players;
    private final boolean exactWinRequired;
    private final int maxTurns;

    public SnakeAndLadderGame(Board board, Dice dice, List<Player> players, boolean exactWinRequired, int maxTurns) {
        if (board == null || dice == null) {
            throw new IllegalArgumentException("Board and dice are required.");
        }

        validatePlayers(players);
        if (maxTurns <= 0) {
            throw new IllegalArgumentException("maxTurns must be greater than 0.");
        }

        this.board = board;
        this.dice = dice;
        this.players = List.copyOf(players);
        this.exactWinRequired = exactWinRequired;
        this.maxTurns = maxTurns;
    }

    public GameResult play() {
        List<String> moveLog = new ArrayList<>();
        int turnsPlayed = 0;
        Player winner = null;
        int currentTurnIndex = 0;

        while (turnsPlayed < maxTurns && winner == null) {
            turnsPlayed++;
            TurnResult turnResult = playTurn(currentTurnIndex);
            moveLog.add(turnResult.moveLogEntry());

            if (turnResult.isWinningMove()) {
                winner = turnResult.player();
            } else {
                currentTurnIndex = getNextTurnIndex(currentTurnIndex);
            }
        }

        return new GameResult(winner, turnsPlayed, moveLog);
    }

    private void validatePlayers(List<Player> players) {
        if (players == null || players.size() < 2) {
            throw new IllegalArgumentException("At least 2 players are required.");
        }
    }

    private TurnResult playTurn(int currentTurnIndex) {
        Player currentPlayer = players.get(currentTurnIndex);
        int diceValue = dice.roll();
        int currentPosition = currentPlayer.getPosition();
        int candidatePosition = currentPosition + diceValue;
        int finalPosition = resolvePosition(candidatePosition, currentPosition);

        currentPlayer.setPosition(finalPosition);

        String moveLogEntry = currentPlayer.getName() + " rolled " + diceValue
                + " and moved from " + currentPosition + " to " + finalPosition;
        boolean isWinningMove = finalPosition == board.getSize() - 1;

        return new TurnResult(currentPlayer, moveLogEntry, isWinningMove);
    }

    private int getNextTurnIndex(int currentTurnIndex) {
        return (currentTurnIndex + 1) % players.size();
    }

    private int resolvePosition(int candidatePosition, int currentPosition) {
        int lastCell = board.getSize() - 1;

        if (candidatePosition > lastCell) {
            return exactWinRequired ? currentPosition : lastCell;
        }

        if (candidatePosition < FIRST_CELL) {
            return FIRST_CELL;
        }

        Jump jump = board.getJumpAt(candidatePosition);
        return jump == null ? candidatePosition : jump.getEnd();
    }

    private record TurnResult(Player player, String moveLogEntry, boolean isWinningMove) {
    }
}
