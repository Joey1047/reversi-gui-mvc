package model;

import java.io.*;
import java.util.Observable;

/**
 * The Reversi model manages game state and communicates with the view.
 * It handles both player and AI turns and notifies observers.
 * 
 * @author Zhaoyi
 */
@SuppressWarnings("deprecation")
public class ReversiModel extends Observable {

    private ReversiBoard board;
    private boolean playerTurn = true; // white = player, black = AI

    public ReversiModel() {
        board = new ReversiBoard();
    }

    public ReversiBoard getBoard() {
        return board;
    }

    public boolean makeMove(int row, int col) {
        boolean moved = board.makeMove(row, col, 1);
        if (moved) {
            playerTurn = false;
            notifyChange();
        }
        return moved;
    }

    public void makeComputerMove() {
        int[] move = ReversiAI.chooseMove(board);
        if (move != null) {
            board.makeMove(move[0], move[1], -1);
        }
        playerTurn = true;
        notifyChange();
    }

    /** Called when board updates */
    public void notifyChange() {
        setChanged();
        notifyObservers(board);
    }

    public void newGame() {
        board = new ReversiBoard();
        playerTurn = true;
        notifyChange();
    }

    public boolean isGameOver() {
        return board.isGameOver();
    }

    public String getWinnerMessage() {
        int white = board.getWhiteCount();
        int black = board.getBlackCount();
        if (white > black) return "White wins!";
        else if (black > white) return "Black wins!";
        else return "It's a tie!";
    }

    public int getWhiteScore() { return board.getWhiteCount(); }
    public int getBlackScore() { return board.getBlackCount(); }

    public void saveGame(File file) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))) {
            out.writeObject(board);
        }
    }

    /** Load saved game from file */
    public void loadGame(File file) throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            ReversiBoard loaded = (ReversiBoard) in.readObject();
            this.board = loaded;
            this.playerTurn = true;   // reset to player's turn
            notifyChange();           // notify observers to redraw
        }
}
}
