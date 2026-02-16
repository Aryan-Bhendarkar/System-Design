public class Move{
    private int row;
    private int col;
    private Symbol symbol;

    public Move(Symbol symbol, int col, int row) {
        this.symbol = symbol;
        this.col = col;
        this.row = row;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public Symbol getSymbol() {
        return symbol;
    }
}