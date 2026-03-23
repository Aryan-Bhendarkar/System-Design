public class Player {
    private final String id;
    private final String name;
    private int position;

    public Player(String id, String name) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Player id cannot be blank.");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Player name cannot be blank.");
        }
        this.id = id;
        this.name = name;
        this.position = 0;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        if (position < 0) {
            throw new IllegalArgumentException("Player position cannot be negative.");
        }
        this.position = position;
    }
}
