import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {
    private final int size;
    private final Map<Integer, Jump> jumpsByStart;

    public Board(int size, List<Jump> jumps) {
        if (size <= 1) {
            throw new IllegalArgumentException("Board size must be greater than 1.");
        }
        this.size = size;
        this.jumpsByStart = buildJumpMap(size, jumps);
    }

    private Map<Integer, Jump> buildJumpMap(int size, List<Jump> jumps) {
        Map<Integer, Jump> jumpMap = new HashMap<>();
        if (jumps == null) {
            return jumpMap;
        }

        for (Jump jump : jumps) {
            if (jump.getStart() >= size || jump.getEnd() >= size) {
                throw new IllegalArgumentException("Jump positions must be within board size.");
            }
            if (jumpMap.containsKey(jump.getStart())) {
                throw new IllegalArgumentException("Only one jump can start from a given cell.");
            }
            jumpMap.put(jump.getStart(), jump);
        }
        return jumpMap;
    }

    public int getSize() {
        return size;
    }

    public Jump getJumpAt(int cell) {
        return jumpsByStart.get(cell);
    }

    public Map<Integer, Jump> getJumpsByStart() {
        return Collections.unmodifiableMap(jumpsByStart);
    }
}
