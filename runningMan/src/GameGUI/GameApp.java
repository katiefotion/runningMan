/*
 * VIEW
 * 
 * Main game application
 */

package GameGUI;


import Controller.Controller;
import Game.Game;
import Game.Player;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;


public class GameApp extends Application 
{   
    // Tracks known characters and complications
    static Sprite character;
    static ArrayList<Sprite> complications;
    
    // Button locations
    final int quitX = 15;
    final int quitY = 15;
    final int pauseX = 185;
    final int pauseY = 20;
    final int playX = 260;
    final int playY = 20;
    
    // Launch application
    public static void initGameApp(String[] args) 
    {
        launch(args);
    }

    @Override
    public void start(Stage theStage) 
    {
        // Start controller to mediate between model and view
        Controller control = new Controller(new Game(new Player(1)));
        
        // Initialize empty character and complication structures
        character = new Sprite(100, new Image("character.png"));
        complications = new ArrayList<>();
                
        // Fill character and complication structures with relevant info
        // based on model
        control.initializeVariables();
        
        // Set screen title
        theStage.setTitle( "Running Man" );
        
        Group root = new Group();
        Scene theScene = new Scene( root );
        theStage.setScene( theScene );
        
        Canvas canvas = new Canvas(800, 600);
        root.getChildren().add( canvas );
        
        GraphicsContext gc = canvas.getGraphicsContext2D();
        
        // Set background and button images
        Image background = new Image("background.png");
        Image quit = new Image("quit.png");
        Image pause = new Image("pause.png");
        Image play = new Image("play.png");
        
        // Start timer
        Timeline gameLoop = new Timeline();
        gameLoop.setCycleCount( Timeline.INDEFINITE );
        final long timeStart = System.currentTimeMillis();
        
        // Listen for user input continuously 
        ActionHandler ah = new ActionHandler(theScene, gameLoop, control, gc, character, complications, timeStart);
        KeyFrame kf = ah.listen();
        gameLoop.getKeyFrames().add( kf );
        gameLoop.play();
        
        // Display the scene
        theStage.show();
    }
    
    // Set character's y value based on current model
    public static void setCharacterY(int y) {
        character.setY(y);
    }
    
    public static void setCharacterX(int x) {
        character.setX(x);
    }
    
    // Set complications' x values the first time 
    public static void setComplicationsX(ArrayList<Integer> xs) {
        for (int i = 0; i < xs.size(); i++) {
            complications.add(new Sprite(xs.get(i)));
        }
    }
    
    // Update complications' x values based on current model
    public static void updateComplicationsX(ArrayList<Integer> xs) {
        for (int i = 0; i < xs.size(); i++) {
            complications.get(i).setX(xs.get(i));
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
}
