package control;

import java.io.File;
import java.io.IOException;
import model.ReversiModel;

/**
 * Controller handles the communication between the view and the model.
 * It receives user input from the View and updates the Model accordingly.
 * 
 * Project 5 Reversi – Controller
 * Ensures MVC separation and manages save/load/new-game logic.
 */
public class ReversiController {

    private final ReversiModel model;
    private static final String SAVE_FILE = "save_game.dat";

    public ReversiController(ReversiModel model) {
        this.model = model;
    }

    /**
     * Handles player's move.
     * If the move is valid, updates the model and triggers the computer's turn.
     *
     * @param row The row of the attempted move
     * @param col The column of the attempted move
     * @return true if the move was made successfully, false otherwise
     */
    public boolean playerMove(int row, int col) {
        boolean moved = model.makeMove(row, col);
        if (moved) {
            // After player move, let computer respond
            model.makeComputerMove();
        }
        return moved;
    }

    /** Starts a completely new game and deletes any saved state. */
    public void newGame() {
        model.newGame();
        File file = new File(SAVE_FILE);
        if (file.exists()) {
            file.delete();
        }
    }

    /** Saves the current game to a file. */
    public void saveGame() {
        try {
            model.saveGame(new File(SAVE_FILE));
        } catch (IOException e) {
            System.err.println("Error saving game: " + e.getMessage());
        }
    }

    /** Loads a saved game if present; otherwise starts a new game. */
    public void loadGame() {
        try {
            File file = new File(SAVE_FILE);
            if (file.exists()) {
                model.loadGame(file);
            } else {
                model.newGame();
            }
        } catch (Exception e) {
            System.err.println("Error loading game: " + e.getMessage());
            model.newGame();
        }
    }

    /** Gives the View controlled access to the model if needed for observer setup. */
    public ReversiModel getModel() {
        return model;
    }
}
