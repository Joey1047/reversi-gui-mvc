package model;

import java.util.*;

/**
 * Simple AI that selects the move maximizing its score.
 * 
 * @author Zhaoyi
 */
public class ReversiAI {

    private static final int SIZE = 8;

    public static int[] chooseMove(ReversiBoard board) {
        int bestScore = Integer.MIN_VALUE;
        int[] bestMove = null;

        for (int r = 0; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {
                ReversiBoard copy = board.copy();
                if (copy.makeMove(r, c, -1)) { // AI plays as black
                    int score = copy.getBlackCount() - copy.getWhiteCount();
                    if (score > bestScore) {
                        bestScore = score;
                        bestMove = new int[]{r, c};
                    }
                }
            }
        }
        return bestMove;
    }
}
