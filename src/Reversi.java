package view;

import javafx.application.Application;

/**
 * The main entry point for the CSC 335 Project 5 Reversi program.
 * <p>
 * This class launches the JavaFX application by invoking
 * {@link ReversiView}, which constructs the GUI and connects
 * the MVC components (Model – View – Controller).
 * </p>
 *
 * @author Your Name
 * @version Fall 2025
 */
public class Reversi {

    /**
     * Launches the Reversi graphical user interface.
     *
     * @param args command-line arguments (unused)
     */
    public static void main(String[] args) {
        Application.launch(ReversiView.class, args);
    }
}
