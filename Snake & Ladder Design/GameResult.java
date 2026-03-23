import java.util.List;

public class GameResult {
    private final Player winner;
    private final int turnsPlayed;
    private final List<String> moveLog;

    public GameResult(Player winner, int turnsPlayed, List<String> moveLog) {
        if (turnsPlayed < 0) {
            throw new IllegalArgumentException("turnsPlayed cannot be negative.");
        }
        if (moveLog == null) {
            throw new IllegalArgumentException("moveLog cannot be null.");
        }
        this.winner = winner;
        this.turnsPlayed = turnsPlayed;
        this.moveLog = List.copyOf(moveLog);
    }

    public Player getWinner() {
        return winner;
    }

    public int getTurnsPlayed() {
        return turnsPlayed;
    }

    public List<String> getMoveLog() {
        return moveLog;
    }
}
