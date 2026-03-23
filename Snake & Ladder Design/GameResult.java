import java.util.List;

public class GameResult {
    private final Player winner;
    private final int turnsPlayed;
    private final List<String> moveLog;

    public GameResult(Player winner, int turnsPlayed, List<String> moveLog) {
        this.winner = winner;
        this.turnsPlayed = turnsPlayed;
        this.moveLog = moveLog;
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
