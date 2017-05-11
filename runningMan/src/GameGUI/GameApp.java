/*
 * VIEW
 * 
 * Main game application
 */
package GameGUI;

import MenuGUI.Menu;
import Controller.Controller;
import Database.HighScores;
import Database.HighScores.PlayerScore;
import Game.Game;
import Game.Player;
import java.util.ArrayList;
import java.util.List;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class GameApp extends Application implements
        Menu.MenuEventListener {

    private final int GAME_WIDTH = 800;
    private final int GAME_HEIGHT = 600;

    // Tracks known characters and complications
    static Sprite character, missile;
    static ArrayList<Sprite> complications;

    // Button locations
    final int quitX = 15;
    final int quitY = 15;
    final int pauseX = 185;
    final int pauseY = 20;
    final int playX = 260;
    final int playY = 20;

    private static Scene gameScene;
    private static Stage theStage;
    private Menu menu;
    private static Timeline gameLoop;

    // Launch application
    public static void initGameApp(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage theStage) {
        this.theStage = theStage;
        this.theStage.setTitle("Running Man");

        this.gameScene = new Scene(new Group(), GAME_WIDTH, GAME_HEIGHT);
        this.theStage.setScene(gameScene);

        menu = new Menu(this);
        menu.showMenu(theStage);
    }

    // Set character's y value based on current model
    public static void setCharacterY(int y) {
        character.setY(y);
    }

    public static void setCharacterX(int x) {
        character.setX(x);
    }

    public static void setMissileX(int x) {
        missile.setX(x);
    }

    public static void setMissileY(int y) {
        missile.setY(y);
    }

    // Set complications' x values the first time 
    public static void setComplicationsX(ArrayList<Integer> xs) {
        for (int i = 0; i < xs.size(); i++) {
            complications.add(new Sprite(xs.get(i)));
        }
    }

    // Update complications' x values based on current model
    public static void updateComplicationsX(ArrayList<Integer> xs, ArrayList<Integer> ys, ArrayList<Image> images) {
        if (xs.size() > complications.size()) {
            complications.add(new Sprite(xs.get(xs.size() - 1), ys.get(ys.size() - 1), images.get(images.size() - 1)));
        }
        for (int i = 0; i < xs.size(); i++) {
            complications.get(i).setX(xs.get(i));
        }
        for (int i = 0; i < ys.size(); i++) {
            complications.get(i).setY(ys.get(i));
        }
        for (int i = 0; i < images.size(); i++) {
            complications.get(i).setImage(images.get(i));
        }
    }

    // Set / update complications' y values based on current model
    public static void setComplicationsY(ArrayList<Integer> ys) {
        for (int i = 0; i < ys.size(); i++) {
            complications.get(i).setY(ys.get(i));
        }
    }

    // Set complications' images 
    public static void setComplicationsImage(ArrayList<Image> images) {
        for (int i = 0; i < images.size(); i++) {
            complications.get(i).setImage(images.get(i));
        }
    }

    public void startGame() {
        // Start controller to mediate between model and view
        Controller control = new Controller(new Game(new Player(1)));

        // Initialize empty character and complication structures
        character = new Sprite(100, new Image("character.png"));
        missile = new Sprite(new Image("missile.png"));
        complications = new ArrayList<>();

        // Fill character and complication structures with relevant info
        // based on model
        control.initializeVariables();

        Group root = new Group();
        gameScene.setRoot(root);

        Canvas canvas = new Canvas(GAME_WIDTH, GAME_HEIGHT);
        root.getChildren().add(canvas);

        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Start timer
        gameLoop = new Timeline();
        gameLoop.setCycleCount(Timeline.INDEFINITE);
        final long timeStart = System.currentTimeMillis();

        // Listen for user input continuously 
        ActionHandler ah = new ActionHandler(theStage, menu, gameLoop, control, gc, character, missile, complications, timeStart);
        KeyFrame kf = ah.listen();
        gameLoop.getKeyFrames().add(kf);
        gameLoop.play();

        // Display the scene
        theStage.show();
    }

    public static void onEndGame(Parent root) {
        gameLoop.stop();
        
        gameScene.setRoot(root);
        theStage.show();
    }

    @Override
    public void onStartGameSelected() {
        startGame();
    }

    @Override
    //TODO: replace with a new high score scene
    public void onHighScoresSelected() {
        HighScores highscores = new HighScores();
        List<PlayerScore> scores = highscores.getTopScores(5);

        if (scores != null) {
            for (PlayerScore score : scores) {
                System.out.println(score.getName() + " : " + score.getScore());
            }
        }
    }

    @Override
    public void onQuitSelected() {
        theStage.close();
    }
}
