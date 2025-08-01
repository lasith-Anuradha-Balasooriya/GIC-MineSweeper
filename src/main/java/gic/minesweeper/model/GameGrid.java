package gic.minesweeper.model;

import java.util.Random;

public class GameGrid {
    private final int size;
    private final int mineCount;
    private final GridCell[][] grid;
    private boolean gameLost;

    public GameGrid(int size, int mineCount) {
        this.size = size;
        this.mineCount = mineCount;
        this.grid = new GridCell[size][size];
    }

    public void initialize() {
        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                grid[r][c] = new GridCell();
            }
        }

        placeMines();
        calculateAdjacency();
    }

    private void placeMines() {
        // assign mines to cells using random
        try{
            Random random = new Random();
            int placed = 0;
            while (placed < mineCount) {
                int r = random.nextInt(size);
                int c = random.nextInt(size);
                if (!grid[r][c].hasMine()) {
                    grid[r][c].setMine(true);
                    placed++;
                }
            }
        }catch (Exception e){
            System.out.println("Exception in PlaceMine "+e);
        }
    }

    private void calculateAdjacency() {
        try{
            for (int r = 0; r < size; r++) {
                for (int c = 0; c < size; c++) {
                    if (!grid[r][c].hasMine()) {
                        int count = 0;
                        for (int dr = -1; dr <= 1; dr++) {
                            for (int dc = -1; dc <= 1; dc++) {
                                int nr = r + dr;
                                int nc = c + dc;
                                if (inBounds(nr, nc) && grid[nr][nc].hasMine()){
                                    count++;
                                }
                            }
                        }
                        grid[r][c].setAdjacentMines(count);
                    }
                }
            }
        }catch (Exception e){
            System.out.println("Exception in calculation "+e);
        }
    }

    public void reveal(CellPosition cellPosition) {
        try{
            if (!inBounds(cellPosition.row(), cellPosition.col()) || grid[cellPosition.row()][cellPosition.col()].isRevealed()){
                return;
            }
            GridCell cell = grid[cellPosition.row()][cellPosition.col()];
            cell.setRevealed(true);
            if (cell.hasMine()) {
                gameLost = true;
                return;
            }
            if (cell.getAdjacentMines() == 0) {
                for (int dr = -1; dr <= 1; dr++) {
                    for (int dc = -1; dc <= 1; dc++) {
                        if (dr == 0 && dc == 0){
                            continue;
                        }
                        reveal(new CellPosition(cellPosition.row() + dr, cellPosition.col() + dc));
                    }
                }
            }
        }catch (Exception e){
            System.out.println("Exception in reveal "+e);
        }
    }

    public void print() {
        try{
            System.out.print("  ");
            for (int c = 1; c <= size; c++){
                System.out.print(c + " ");
            }
            System.out.println();
            for (int r = 0; r < size; r++) {
                System.out.print((char) ('A' + r) + " ");
                for (int c = 0; c < size; c++) {
                    System.out.print(grid[r][c] + " ");
                }
                System.out.println();
            }
        }catch (Exception e){
            System.out.println("Exception in print "+e);
        }
    }

    public void printWithMines() {
        try{
            System.out.print("  ");
            for (int c = 1; c <= size; c++){
                System.out.print(c + " ");
            }
            System.out.println();
            for (int r = 0; r < size; r++) {
                System.out.print((char) ('A' + r) + " ");
                for (int c = 0; c < size; c++) {
                    System.out.print(grid[r][c].displayWithMine() + " ");
                }
                System.out.println();
            }
        }catch (Exception e){
            System.out.println("Exception in printwithMines "+e);
        }
    }

    public boolean isGameOver() {
        return gameLost || isGameWon();
    }

    public boolean isGameWon() {
        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                if (!grid[r][c].hasMine() && !grid[r][c].isRevealed()) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isGameLost() {
        return gameLost;
    }

    public boolean isValid(CellPosition cellPosition) {
        return inBounds(cellPosition.row(), cellPosition.col());
    }

    private boolean inBounds(int r, int c) {
        return r >= 0 && r < size && c >= 0 && c < size;
    }
}
