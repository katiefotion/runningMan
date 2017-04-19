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

// An alternative implementation of Example 3,
//    using the Timeline, KeyFrame, and Duration classes.

// Animation of Earth rotating around the sun. (Hello, world!)
public class GameGUI extends Application 
{
    // Determines where the character is in the model
    static int characterY;
    
    // Array lists for complication location and type (denoted by image filename) 
    static ArrayList<Integer> complicationsX;
    static ArrayList<Integer> complicationsY;
    static ArrayList<Image> complicationsImage;
    
    // Determines how fast the complications are moving 
    private int speedCoeff;
    
    // Button locations
    final int quitX = 15;
    final int quitY = 15;
    final int pauseX = 185;
    final int pauseY = 20;
    final int playX = 260;
    final int playY = 20;
    
    
    public static void initGameGUI(String[] args) 
    {
        launch(args);
    }

    @Override
    public void start(Stage theStage) 
    {
        Controller control = new Controller(new Game(new Player(1)));
        
        control.initialize();
        
        theStage.setTitle( "Running Man" );
        
        speedCoeff = 2;
        
        Group root = new Group();
        Scene theScene = new Scene( root );
        theStage.setScene( theScene );
        
        Canvas canvas = new Canvas(800, 600);
        root.getChildren().add( canvas );
        
        GraphicsContext gc = canvas.getGraphicsContext2D();
        
        // Set background and character images
        Image background = new Image("background.png");
        Image character = new Image("character.png");
        Image quit = new Image("quit.png");
        Image pause = new Image("pause.png");
        Image play = new Image("play.png");
        
        Timeline gameLoop = new Timeline();
        gameLoop.setCycleCount( Timeline.INDEFINITE );
        
        final long timeStart = System.currentTimeMillis();
        
        theScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            
            @Override
            public void handle(KeyEvent e) {
                
                KeyCode key = e.getCode();
                
                if(key == KeyCode.SPACE && gameLoop.getStatus() == Animation.Status.RUNNING) {
                    
                    control.characterJump();
                    
                    // Clear the canvas
                    gc.clearRect(0, 0, 512,512);

                    // background image clears canvas
                    gc.drawImage(background, 0, 0);
                    gc.drawImage(character, 100, characterY);
                    
                    for(int i = 0; i < complicationsX.size(); i++) {
                        gc.drawImage(complicationsImage.get(i), complicationsX.get(i), complicationsY.get(i));
                    }
                }
            }
        });
        
        theScene.setOnMouseClicked(new EventHandler<MouseEvent>()
            {
                @Override
                public void handle(MouseEvent e)
                {
                    if ((e.getX() > quitX) && (e.getX() < (quitX + quit.getWidth())) && (e.getY() > quitY) && (e.getY() < (quitY+quit.getHeight()))) {
                        System.out.println("Quitting game...");
                        System.exit(0);
                    }
                    else if ((e.getX() > pauseX) && (e.getX() < (pauseX + pause.getWidth())) && (e.getY() > pauseY) && (e.getY() < (pauseY+pause.getHeight()))) {
                        System.out.println("Pausing game...");
                        gameLoop.pause();
                    }
                    else if ((e.getX() > playX) && (e.getX() < (playX + play.getWidth())) && (e.getY() > playY) && (e.getY() < (playY+play.getHeight()))) {
                        System.out.println("Unpausing game...");
                        gameLoop.play();
                    }
                }
            });
        
        
        KeyFrame kf = new KeyFrame(
            Duration.seconds(0.017),                // 60 FPS
            new EventHandler<ActionEvent>()
            {
                @Override
                public void handle(ActionEvent ae)
                {            
                    
                    double t = (System.currentTimeMillis() - timeStart) / 1000.0;
                    
                    if((t % 1000) == 0) 
                        speedCoeff += 1;
                    
                    control.tick(speedCoeff);
                    
                    // Clear the canvas
                    gc.clearRect(0, 0, 512,512);
                    
                    // background image clears canvas
                    gc.drawImage(background, 0, 0);
                    gc.drawImage(character, 100, characterY);
                    
                    for(int i = 0; i < complicationsX.size(); i++) {
                        gc.drawImage(complicationsImage.get(i), complicationsX.get(i), complicationsY.get(i));
                    }
                    
                    // Draw score
                    Font theFont = Font.font( "Helvetica", FontWeight.BOLD, 24 );
                    gc.setFont( theFont );
                    NumberFormat formatter = new DecimalFormat("#0.00");     
                    String pointsText = "Time: " + formatter.format(t);
                    gc.fillText( pointsText, 590,  50);
                    gc.strokeText( pointsText, 590, 50);
                    
                    // Draw quit button
                    gc.drawImage(quit, quitX, quitY);
                    
                    // Draw pause button
                    gc.drawImage(pause, pauseX, pauseY);
                    
                    // Draw play button
                    gc.drawImage(play, playX, playY);
                }
            });
        gameLoop.getKeyFrames().add( kf );
        gameLoop.play();
        
        theStage.show();
    }
    
    public static void setCharacterY(int y) {
        characterY = y;
    }
    
    public static void setComplicationsX(ArrayList<Integer> xs) {
        complicationsX = xs;
    }
    
    public static void setComplicationsY(ArrayList<Integer> ys) {
        complicationsY = ys;
    }
    
    public static void setComplicationsImage(ArrayList<Image> images) {
        complicationsImage = images;
    }
}
