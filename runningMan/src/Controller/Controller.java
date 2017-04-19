/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Game.Game;
import GameGUI.GameGUI;
import GameObjects.Character;
import GameObjects.Complication;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.input.MouseEvent;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author katie
 */
public class Controller {

    private final Game game;
    
    public Controller(Game game) {
        this.game = game;
    }
    
    public void initialize() {
        
        // Access model's character position
        Character c = this.game.getCharacter();
        int charY = c.getY();
        
        // Access model's complications' positions and types
        ArrayList<Integer> complicationsX = this.getComplicationsX();
        ArrayList<Integer> complicationsY = this.getComplicationsY();
        ArrayList<Image> complicationsImage = this.getComplicationsImage();
        
        // Set locations and images in GUI
        GameGUI.setCharacterY(charY);
        GameGUI.setComplicationsX(complicationsX);
        GameGUI.setComplicationsY(complicationsY);
        GameGUI.setComplicationsImage(complicationsImage);
    }
   
    public void mouseClicked() {
        
        // Access character in model
        Character c = this.game.getCharacter();
        
        // Make character in model jump
        c.jump();
        
        // Make GUI reflect that change
        GameGUI.setCharacterY(c.getY());
    }

    public ArrayList<Integer> getComplicationsX() {
        
        // Access all complications in game state
        List<Complication> comps = game.currentState().getComplications();
        
        // Make a list of all x coordinates of those complications
        ArrayList<Integer> complicationsX = new ArrayList<>();
        for(Complication comp : comps) {
            complicationsX.add((Integer)comp.getX());
        }
        
        return complicationsX;
    } 
    
    public ArrayList<Integer> getComplicationsY() {
        
        // Access all complications in game state
        List<Complication> comps = game.currentState().getComplications();
        
        // Make a list of all y coordinates of those complications
        ArrayList<Integer> complicationsY = new ArrayList<>();
        for(Complication comp : comps) {
            complicationsY.add((Integer)comp.getY());
        }
        
        return complicationsY;
    } 
    
    public ArrayList<Image> getComplicationsImage() {
        
        // Access all complications in game state
        List<Complication> comps = game.currentState().getComplications();
        
        // Make a list of all images per complication type
        ArrayList<Image> complicationsImage = new ArrayList<>();
        
        for(Complication comp : comps) {
            
            // Check which type of complication it is an assign filename accordingly 
            if(comp.isObstacle())
                complicationsImage.add(new Image("obstacle.png"));
            else if(comp.isPit())
                complicationsImage.add(new Image("pit.png"));
            else if(comp.isThreat())
                complicationsImage.add(new Image("threat.png"));
        }
        
        return complicationsImage;
    } 
    
    public void tick(int speedCoeff) {
        
        game.currentState().tick(speedCoeff);
        
        // Increment complications' x coordinates with time 
        ArrayList<Integer> complicationsX = this.getComplicationsX();
        GameGUI.setComplicationsX(complicationsX);
    }
    
}
