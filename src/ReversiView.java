package view;

import control.ReversiController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import model.ReversiBoard;
import model.ReversiModel;

import java.util.Observable;
import java.util.Observer;

/**
 * ReversiView is the JavaFX GUI for Project 5.
 * <p>
 * It observes the {@link ReversiModel} and updates the display when the
 * model changes. All user interactions are delegated to the
 * {@link ReversiController}.
 * </p>
 *
 * @author Your Name
 * @version Fall 2025
 */
@SuppressWarnings("deprecation")
public class ReversiView extends Application implements Observer {

    private static final int SIZE = 8;
    private static final int CELL_SIZE = 60;

    private ReversiController controller;
    private GridPane grid;
    private Label scoreLabel;

    @Override
    public void start(Stage stage) {
        // ==== Initialize MVC ====
        ReversiModel model = new ReversiModel();
        controller = new ReversiController(model);
        model.addObserver(this);

        // ==== Layout ====
        grid = new GridPane();
        grid.setHgap(2);
        grid.setVgap(2);
        grid.setStyle("-fx-background-color: green;");

        scoreLabel = new Label("White: 2   Black: 2");
        scoreLabel.setAlignment(Pos.CENTER);

        BorderPane root = new BorderPane();
        root.setCenter(grid);
        root.setBottom(scoreLabel);
        BorderPane.setAlignment(scoreLabel, Pos.CENTER);

        // ==== Menu Bar ====
        MenuBar menuBar = new MenuBar();
        Menu fileMenu = new Menu("File");

        MenuItem newGameItem = new MenuItem("New Game");
        newGameItem.setOnAction(e -> controller.newGame());

        MenuItem saveItem = new MenuItem("Save Game");
        saveItem.setOnAction(e -> controller.saveGame());

        MenuItem loadItem = new MenuItem("Load Game");
        loadItem.setOnAction(e -> controller.loadGame());

        MenuItem exitItem = new MenuItem("Exit");
        exitItem.setOnAction(e -> {
            controller.saveGame();
            Platform.exit();
        });

        fileMenu.getItems().addAll(newGameItem, saveItem, loadItem, new SeparatorMenuItem(), exitItem);
        menuBar.getMenus().add(fileMenu);
        root.setTop(menuBar);

        Scene scene = new Scene(root, SIZE * CELL_SIZE + 40, SIZE * CELL_SIZE + 100);
        stage.setScene(scene);
        stage.setTitle("Reversi");
        stage.show();

        // ==== Load saved game if any ====
        controller.loadGame();

        // ==== Save game on window close ====
        stage.setOnCloseRequest(e -> controller.saveGame());
    }

    /**
     * Draws the current board state using circles.
     *
     * @param board the ReversiBoard to display
     */
    private void drawBoard(ReversiBoard board) {
        grid.getChildren().clear();
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                StackPane cell = new StackPane();
                cell.setPrefSize(CELL_SIZE, CELL_SIZE);
                cell.setStyle("-fx-border-color: black;");
                Circle circle = new Circle(CELL_SIZE / 2.0 - 5);

                int val = board.getCell(row, col);
                if (val == 1) {
                    circle.setFill(Color.WHITE);
                } else if (val == -1) {
                    circle.setFill(Color.BLACK);
                } else {
                    circle.setFill(Color.TRANSPARENT);
                }
                cell.getChildren().add(circle);

                final int r = row;
                final int c = col;
                cell.setOnMouseClicked(e -> controller.playerMove(r, c));

                grid.add(cell, col, row);
            }
        }

        scoreLabel.setText(
                "White: " + board.getWhiteCount() + "   Black: " + board.getBlackCount()
        );
    }

    /**
     * Called when the model notifies observers.
     *
     * @param o   the Observable model
     * @param arg the updated ReversiBoard
     */
    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof ReversiBoard) {
            ReversiBoard board = (ReversiBoard) arg;
            Platform.runLater(() -> drawBoard(board));
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
