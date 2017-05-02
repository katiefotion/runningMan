/*
 * CONTROL
 * 
 * Mediates between the model and the view
 * Ensures view accurately reflects model
 */

package Controller;

import Game.Game;
import GameGUI.GameApp;
import GameObjects.Character;
import GameObjects.Complication;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.image.Image;

/**
 *
 * @author katie
 */
public class Controller {

    private final Game game;
    
    ArrayList<Image> complicationsImage; 
    static int charHeight;
    static int charWidth;
    
    ArrayList<Integer> complicationsX;
    ArrayList<Integer> complicationsY;
    
    public Controller(Game game) {
        this.game = game;
    }
    
    public void initializeVariables() {
        
        // Access model's character position
        Character c = this.game.getCharacter();
        int charY = c.getY();
        
        // Access model's complications' positions and types
        this.setComplicationsX();
        this.setComplicationsY();
        this.setComplicationsImage();
        
        // Set locations and images in GUI
        GameApp.setCharacterY(charY);
        GameApp.setComplicationsX(complicationsX);
        GameApp.setComplicationsY(complicationsY);
        GameApp.setComplicationsImage(complicationsImage);
        
        // Calculate character's height and width for collisions
        Image characterImage = new Image("character.png");
        charHeight = characterImage.heightProperty().intValue();
        charWidth = characterImage.widthProperty().intValue();
    }
   
    public void characterJump() {
        
        // Access character in model
        Character c = this.game.getCharacter();
        
        // Make character in model jump
        c.jump();
        
        // Make GUI reflect that change
        GameApp.setCharacterY(c.getY());
    }

    public void setComplicationsX() {
        
        // Access all complications in game state
        List<Complication> comps = game.currentState().getComplications();
        
        // Make a list of all x coordinates of those complications
        complicationsX = new ArrayList<>();
        for(Complication comp : comps) {
            complicationsX.add((Integer)comp.getX());
        }
    } 
    
    public void setComplicationsY() {
        
        // Access all complications in game state
        List<Complication> comps = game.currentState().getComplications();
        
        // Make a list of all y coordinates of those complications
        complicationsY = new ArrayList<>();
        for(Complication comp : comps) {
            complicationsY.add((Integer)comp.getY());
        }
    } 
    
    public void setComplicationsImage() {
        
        // Access all complications in game state
        List<Complication> comps = game.currentState().getComplications();
        
        // Make a list of all images per complication type
        complicationsImage = new ArrayList<>();
        
        for(Complication comp : comps) {
            
            // Check which type of complication it is an assign filename accordingly 
            if(comp.isObstacle())
                complicationsImage.add(new Image("obstacle.png"));
            else if(comp.isPit())
                complicationsImage.add(new Image("pit.png"));
            else if(comp.isThreat())
                complicationsImage.add(new Image("threat.png"));
        }
    } 
    
    public void tick(int speedCoeff, double t) {
        
        game.currentState().tick(speedCoeff, t, game.getCharacter());
        //redraws character sprite each frame
        GameApp.setCharacterY(game.getCharacter().getY()); 
        //System.out.println(game.getCharacter().getY());
        //chararctr keeps going down if not on floor level
        if(game.getCharacter().getY()<275){
            game.getCharacter().setY(game.getCharacter().getY()+1);
        }
        // Increment complications' x coordinates with time 
        this.setComplicationsX();
        GameApp.updateComplicationsX(complicationsX);
        
        // Check if character and complications collid
        boolean collision = checkCollision(game.getCharacter(), complicationsX, complicationsY, complicationsImage);
        
        if(collision) {
            System.out.println("Game Over!");
            System.exit(0);
        }
    }
    
    public static boolean checkCollision(Character c, ArrayList<Integer> complicationsX, ArrayList<Integer> complicationsY, ArrayList<Image> complicationsImage) {

        for(int i = 0; i < complicationsX.size(); i++) {
            
            int compX = complicationsX.get(i);
            int compY = complicationsY.get(i);
            int compWidth = complicationsImage.get(i).widthProperty().intValue();
            int compHeight = complicationsImage.get(i).heightProperty().intValue();
            
            String name = (complicationsImage.get(i).impl_getUrl());
            
            if(name.equals("file:/Users/darrylraveche/NetBeansProjects/CSC668Project/runningMan5/runningMan/build/classes/pit.png"))
            {
                c.setVelY(-5);
                if((Math.abs(c.getX() - compX) < (compWidth-300)) && (Math.abs(c.getY() - compY) < 50))
                    return true;
            }
            if((Math.abs(c.getX() - compX) < (compWidth)) && (Math.abs(c.getY() - compY) < compHeight)) 
                return true;
        }
        
        return false;
    } 
}


