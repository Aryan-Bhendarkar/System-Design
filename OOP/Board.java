public class Board{
    private int size;
    private Symbol[][] grid;

    public Board(int size){
        this.size = size;
        grid = new Symbol[size][size];
    }

    public boolean makeMove(Move move){
        int row = move.getRow();
        int col = move.getCol();

        if(grid[row][col] == null){
            grid[row][col] = move.getSymbol();
            return true;
        }
        return false;
    }

    public void printBoard(){
        for(int i=0; i<size; i++){
            for(int j=0; j<size; j++){
                System.out.print(grid[i][j]==null ? "-" : grid[i][j]);
                System.out.print(" ");
            }
            System.out.println();
        }
    }
}