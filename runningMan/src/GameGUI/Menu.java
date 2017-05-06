/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameGUI;

import Controller.Controller;
import Game.*;
import static GameGUI.GameApp.character;
import java.util.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author Jia Hu
 */
public class Menu extends Application{
    static Sprite startB, bg, quitB, hsB, square;
    static ArrayList<Sprite> complications;
    final int playBX = 260;
    final int playBY = 20;
    public static void initMenu(String[] args) 
    {
        launch(args);
    }
    
    public void start(Stage theStage){
        // Start controller to mediate between model and view
        Controller control = new Controller(new Game(new Player(1)));
        
        // Initialize complication structures
        
        startB = new Sprite(135, new Image("Start_Button.png"));
        quitB = new Sprite(135, new Image("Quit_Button.png"));
        hsB = new Sprite(135, new Image("HighScoresButton.png"));
        square = new Sprite(135, new Image("square.jpg"));
        square.setY(50);
        startB.setY(-50);
        hsB.setY(100);
        quitB.setY(250);
        complications = new ArrayList<>();
                
        //control.initializeVariables();
        
        // Set screen title
        theStage.setTitle( "Running Man Menu" );
        
        Group root = new Group();
        Scene theScene = new Scene( root );
        theStage.setScene(theScene);
        
        Canvas canvas = new Canvas(800, 600);
        root.getChildren().add( canvas );
        
        GraphicsContext gc = canvas.getGraphicsContext2D();
        
        // Set background and button images
        Image background = new Image("background.png");
        Image play = new Image("Start_Button.png");
        
        bg = new Sprite(0, background);
        
        bg.drawSprite(gc);
        startB.drawSprite(gc);
        hsB.drawSprite(gc);
        quitB.drawSprite(gc);
        // Display the scene
        theStage.show();
    }
    
    // Set character's y value based on current model
    public static void setCharacterY(int y) {
        character.setY(y);
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
