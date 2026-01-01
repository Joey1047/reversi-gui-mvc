package model;

import java.io.Serializable;
import java.util.*;

/**
 * Represents the 8x8 Reversi game board.
 * Handles placing pieces and flipping captured tiles.
 * 
 * @author Zhaoyi
 */
public class ReversiBoard implements Serializable {
    private static final int SIZE = 8;
    private int[][] board = new int[SIZE][SIZE]; // 1 = white, -1 = black, 0 = empty

    public ReversiBoard() {
        // initialize starting 4 pieces
        board[3][3] = 1; board[4][4] = 1;
        board[3][4] = -1; board[4][3] = -1;
    }

    public int getCell(int r, int c) {
        return board[r][c];
    }

    public void setCell(int r, int c, int val) {
        board[r][c] = val;
    }

    public boolean inBounds(int r, int c) {
        return r >= 0 && r < SIZE && c >= 0 && c < SIZE;
    }

    /** Try to make a move, flipping captured pieces if valid */
    public boolean makeMove(int r, int c, int color) {
        if (!inBounds(r, c) || board[r][c] != 0) return false;
        boolean valid = false;
        int[][] dirs = { {-1,0},{1,0},{0,-1},{0,1},{-1,-1},{-1,1},{1,-1},{1,1} };
        for (int[] d : dirs) {
            int nr = r + d[0], nc = c + d[1];
            boolean hasOpp = false;
            while (inBounds(nr, nc) && board[nr][nc] == -color) {
                nr += d[0]; nc += d[1];
                hasOpp = true;
            }
            if (hasOpp && inBounds(nr, nc) && board[nr][nc] == color) {
                valid = true;
                nr = r + d[0]; nc = c + d[1];
                while (inBounds(nr, nc) && board[nr][nc] == -color) {
                    board[nr][nc] = color;
                    nr += d[0]; nc += d[1];
                }
            }
        }
        if (valid) board[r][c] = color;
        return valid;
    }

    /** Creates a deep copy of this board */
    public ReversiBoard copy() {
        ReversiBoard copy = new ReversiBoard();
        for (int i = 0; i < SIZE; i++)
            copy.board[i] = Arrays.copyOf(this.board[i], SIZE);
        return copy;
    }

    public int getWhiteCount() {
        return count(1);
    }

    public int getBlackCount() {
        return count(-1);
    }

    private int count(int color) {
        int sum = 0;
        for (int[] row : board)
            for (int cell : row)
                if (cell == color) sum++;
        return sum;
    }

    public boolean isGameOver() {
        for (int[] row : board)
            for (int cell : row)
                if (cell == 0) return false;
        return true;
    }

    public int[][] getGrid() {
        return board;
    }
}
