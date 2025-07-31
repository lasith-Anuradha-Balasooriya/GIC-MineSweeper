package gic.minesweeper.model;

public class GridCell {

    private boolean mine;
    private boolean revealed;
    private int adjacentMines;

    public boolean hasMine() {
        return mine;
    }

    public void setMine(boolean mine) {
        this.mine = mine;
    }

    public boolean isRevealed() {
        return revealed;
    }

    public void setRevealed(boolean revealed) {
        this.revealed = revealed;
    }

    public int getAdjacentMines() {
        return adjacentMines;
    }

    public void setAdjacentMines(int count) {
        this.adjacentMines = count;
    }

    @Override
    public String toString() {
        if (!revealed) {
            return "_";
        }
        if (mine){
            return "*";
        }
        return String.valueOf(adjacentMines);
    }

    public String displayWithMine() {
        if (mine) {
            return "*";
        }
        return toString();
    }
}
