/*
 * CONTROL
 * 
 * Listens for user input and updates control and view accordingly 
 * 
 */
package GameGUI;

import Controller.Controller;
import Game.Game;
import Game.GameState;
import MenuGUI.Menu;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author katie
 */
public class ActionHandler {

    private final Scene theScene;
    private final Timeline gameLoop;
    private final Controller control;
    private final GraphicsContext gc;
    private final Sprite character, missile;
    private final ArrayList<Sprite> complications;
    private final long timeStart;
    private Menu menu;
    private Stage theStage;
    private Game game;
    private GameState gameState;

    // Set background and button images and locations
    final Image background = new Image("background_game.png");
    final Image quit = new Image("quit.png");
    final Image pause = new Image("pause.png");
    final Image play = new Image("play.png");
    final int quitX = 15;
    final int quitY = 15;
    final int pauseX = 185;
    final int pauseY = 20;
    final int playX = 260;
    final int playY = 20;

    // Determines how fast the complications are moving 
    private int speedCoeff = 2;

    public ActionHandler(Stage theStage, Menu menu, Timeline gameLoop, Controller control, GraphicsContext gc, Sprite character, Sprite missile, ArrayList<Sprite> complications, long timeStart) {
        this.theStage = theStage;
        this.theScene = theStage.getScene();
        this.menu = menu;
        this.game = control.getGame();
        this.gameState = game.currentState();
        this.gameLoop = gameLoop;
        this.control = control;
        this.gc = gc;
        this.character = character;
        this.complications = complications;
        this.timeStart = timeStart;
        this.missile = missile;
    }

    public KeyFrame listen() {

        theScene.setOnKeyPressed(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent e) {

                KeyCode key = e.getCode();

                if (key == KeyCode.SPACE && gameLoop.getStatus() == Animation.Status.RUNNING) {

                    control.characterJump();

                    // Clear the canvas
                    gc.clearRect(0, 0, 512, 512);

                    // background image clears canvas
                    gc.drawImage(background, 0, 0);

                    // Draw sprites
                    character.drawSprite(gc);

                }

                if (key == KeyCode.RIGHT && gameLoop.getStatus() == Animation.Status.RUNNING) {
                    control.characterMoveRight(true);

                    // Clear the canvas
                    gc.clearRect(0, 0, 512, 512);

                    // background image clears canvas
                    gc.drawImage(background, 0, 0);

                    // Draw sprites
                    character.drawSprite(gc);
                }

                if (key == KeyCode.LEFT && gameLoop.getStatus() == Animation.Status.RUNNING) {
                    control.characterMoveLeft(true);

                    // Clear the canvas
                    gc.clearRect(0, 0, 512, 512);

                    // background image clears canvas
                    gc.drawImage(background, 0, 0);

                    // Draw sprites
                    character.drawSprite(gc);
                }

                if (key == KeyCode.K && gameLoop.getStatus() == Animation.Status.RUNNING) {
                    control.characterShoot();
                }
            }
        });

        theScene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent e) {

                KeyCode key = e.getCode();

                if (key == KeyCode.RIGHT && gameLoop.getStatus() == Animation.Status.RUNNING) {

                    control.characterMoveRight(false);
                    // Clear the canvas
                    gc.clearRect(0, 0, 512, 512);

                    // background image clears canvas
                    gc.drawImage(background, 0, 0);

                    // Draw sprites
                    character.drawSprite(gc);
                }
                if (key == KeyCode.LEFT && gameLoop.getStatus() == Animation.Status.RUNNING) {

                    control.characterMoveLeft(false);
                    // Clear the canvas
                    gc.clearRect(0, 0, 512, 512);

                    // background image clears canvas
                    gc.drawImage(background, 0, 0);

                    // Draw sprites
                    character.drawSprite(gc);
                }
            }

        });

        theScene.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                if ((e.getX() > quitX) && (e.getX() < (quitX + quit.getWidth())) && (e.getY() > quitY) && (e.getY() < (quitY + quit.getHeight()))) {
                    System.out.println("Quitting game...");
                    gameLoop.stop();
                    menu.showMenu(theStage);
                } else if ((e.getX() > pauseX) && (e.getX() < (pauseX + pause.getWidth())) && (e.getY() > pauseY) && (e.getY() < (pauseY + pause.getHeight()))) {
                    System.out.println("Pausing game...");
                    gameLoop.pause();
                } else if ((e.getX() > playX) && (e.getX() < (playX + play.getWidth())) && (e.getY() > playY) && (e.getY() < (playY + play.getHeight()))) {
                    System.out.println("Unpausing game...");
                    gameLoop.play();
                }
            }
        });

        KeyFrame kf = new KeyFrame(
                Duration.seconds(0.017), // 60 FPS
                new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ae) {

                double t = (System.currentTimeMillis() - timeStart) / 1000.0;

                if ((t % 1000) == 0) {
                    speedCoeff += 1;
                }

                control.tick(speedCoeff, t);

                // Clear the canvas
                gc.clearRect(0, 0, 512, 512);

                // background image clears canvas
                gc.drawImage(background, 0, 0);

                // Draw sprites
                character.drawSprite(gc);
                for (int i = 0; i < complications.size(); i++) {
                    complications.get(i).drawSprite(gc);
                }

                // Draw score
                Font theFont = Font.font("Helvetica", FontWeight.BOLD, 24);
                gc.setFont(theFont);
                NumberFormat formatter = new DecimalFormat("#0.00");
                String score = formatter.format(t);
                String pointsText = "Time: " + score;
                gc.fillText(pointsText, 590, 50);
                gc.strokeText(pointsText, 590, 50);

                game.setScore((int) Double.parseDouble(score));

                // Draw quit button
                gc.drawImage(quit, quitX, quitY);

                // Draw pause button
                gc.drawImage(pause, pauseX, pauseY);

                // Draw play button
                gc.drawImage(play, playX, playY);

                if (control.checkMissile()) {
                    missile.drawSprite(gc);
                }
            }
        });

        return kf;
    }
}
