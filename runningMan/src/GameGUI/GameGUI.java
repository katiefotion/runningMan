package GameGUI;


import Controller.Controller;
import Game.Game;
import Game.Player;
import java.util.ArrayList;
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
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

// An alternative implementation of Example 3,
//    using the Timeline, KeyFrame, and Duration classes.

// Animation of Earth rotating around the sun. (Hello, world!)
public class GameGUI extends Application 
{
    static int characterY;
    
    // Array lists for complication x location and type (denoted by image filename) 
    static ArrayList<Integer> complicationsX;
    static ArrayList<Integer> complicationsY;
    static ArrayList<Image> complicationsImage;
    
    private int speedCoeff;
    
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
        
        Timeline gameLoop = new Timeline();
        gameLoop.setCycleCount( Timeline.INDEFINITE );
        
        final long timeStart = System.currentTimeMillis();
        
        theScene.setOnMouseClicked(new EventHandler<MouseEvent>()
            {
                @Override
                public void handle(MouseEvent e)
                {
                    control.mouseClicked();
                    
                    // Clear the canvas
                    gc.clearRect(0, 0, 512,512);

                    // background image clears canvas
                    gc.drawImage(background, 0, 0);
                    gc.drawImage(character, 100, characterY);
                    
                    for(int i = 0; i < complicationsX.size(); i++) {
                        gc.drawImage(complicationsImage.get(i), complicationsX.get(i), complicationsY.get(i));
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
