/*
 * CONTROL
 * 
 * Mediates between the model and the view
 * Ensures view accurately reflects model
 */
package Controller;

import Game.Game;
import GameGUI.ActionHandler;
import GameGUI.GameApp;
import GameObjects.Character;
import GameObjects.*;
import java.util.ArrayList;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author katie
 */
public class Controller {
    
    private ActionHandler ah;

    private final Game game;
    private double timer1 = 4;
    private double timer2 = 0;
    private double timer3 = 0;
    private Character c;

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
        c = this.game.getCharacter();
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

        // Make character in model jump after checking if character is in the air

        if (c.getY() >= 275) {
            c.setGoingUp(true);
        }

        // Make GUI reflect that change
        GameApp.setCharacterY(c.getY());
    }

    public void characterShoot() {
        Missile m = new Missile(this.game.getCharacter().getX() + 10, this.game.getCharacter().getY());
        this.game.currentState().addMissile(m);
    }

    public void characterMoveRight(boolean b) {

        c.setRunningRight(b);

        // Make GUI reflect that change
        GameApp.setCharacterX(c.getX());
    }

    public void characterMoveLeft(boolean b) {

        c.setRunningLeft(b);

        // Make GUI reflect that change
        GameApp.setCharacterX(c.getX());
    }

    public void setComplicationsX() {

        // Access all complications in game state
        List<Complication> comps = game.currentState().getComplications();

        // Make a list of all x coordinates of those complications
        complicationsX = new ArrayList<>();
        for (Complication comp : comps) {
            complicationsX.add((Integer) comp.getX());
        }
    }

    public void setComplicationsY() {

        // Access all complications in game state
        List<Complication> comps = game.currentState().getComplications();

        // Make a list of all y coordinates of those complications
        complicationsY = new ArrayList<>();
        for (Complication comp : comps) {
            complicationsY.add((Integer) comp.getY());
        }
    }

    public void setComplicationsImage() {

        // Access all complications in game state
        List<Complication> comps = game.currentState().getComplications();

        // Make a list of all images per complication type
        complicationsImage = new ArrayList<>();

        for (Complication comp : comps) {

            // Check which type of complication it is an assign filename accordingly 
            if (comp.isObstacle()) {
                complicationsImage.add(new Image("obstacle.png"));
            } else if (comp.isPit()) {
                complicationsImage.add(new Image("pit_blue_flat.png"));
            } else if (comp.isThreat()) {
                complicationsImage.add(new Image("threat.png"));
            }
        }
    }

    public void tick(int speedCoeff, double t) {

        timer3 = timer3 + .2;

        //redraws character sprite each frame
        GameApp.setCharacterY(c.getY());
        GameApp.setCharacterX(c.getX());

        if (c.isGoingUp() && c.getY() >= 275 - c.getJumpHeight()) {
            timer1 = timer1 - .2;
            c.setY((int) (c.getY() - timer1));
        } else {
            timer1 = 12;
        }

        if(c.getY()<280-c.getJumpHeight()){
            c.setGoingUp(false);
            c.setGoingDown(true);
        }
        if (c.isGoingDown() && c.getY() < 275) {
            timer2 = timer2 + .2;
            c.setY((int) (c.getY() + timer2));
        } else {
            timer2 = 0;
        }
        if (c.getY() > 275) {
            c.setGoingDown(false);
        }
        if (c.isRunningRight()) {
            c.moveRight();
        }

        if (c.isRunningLeft()) {
            c.moveLeft();
        }

        if (!c.isGoingUp() && !c.isGoingDown()) {
            GameApp.animateCharacter((int) (timer3 % 6));
        } else {
            GameApp.setStillCharacter();
        
        int missileCollision = -1;
        
        if(this.checkMissile()) {
          
            GameApp.setMissileX(game.currentState().getMissileX());
            GameApp.setMissileY(game.currentState().getMissileY());
            
            missileCollision = checkCollision(game.currentState().getMissileX(), game.currentState().getMissileY(), complicationsX, complicationsY, complicationsImage);
            
            if(missileCollision >= 0) {
                                
                game.currentState().removeMissile();
                game.currentState().removeComplication(missileCollision);
                ah.removeComplication(missileCollision);
            }
        }

        
        boolean shiftedImage = game.currentState().tick(speedCoeff, t,c.getX());
        
        if(shiftedImage) {
            ArrayList<Image> compImageTemp = new ArrayList<>();
            for(int i = 1; i < complicationsImage.size(); i++) {
                compImageTemp.add(complicationsImage.get(i));
            }
            
            complicationsImage = compImageTemp;
        }
        
        // Increment complications' x coordinates with time  
        // (and y coordinates in case new complication is created)
        this.setComplicationsX();
        this.setComplicationsY();
        this.setComplicationsImage();

        
        GameApp.updateComplications(complicationsX, complicationsY, complicationsImage, missileCollision);
       
        // Check if character and complications collide
        boolean collision = checkCollision(c, complicationsX, complicationsY, complicationsImage);

        if (collision) {
            int score = game.currentState().getCurrentScore();

            GameApp.onEndGame(score);
        }
    }
    
    public void setActionHandler(ActionHandler ah) {
        this.ah = ah;
    }
    
    public static int checkCollision(int missileX, int missileY, ArrayList<Integer> complicationsX, ArrayList<Integer> complicationsY, ArrayList<Image> complicationsImage) {
        
        for(int i = 0; i < complicationsX.size(); i++) {
            
            int compX = complicationsX.get(i);
            int compY = complicationsY.get(i);
            int compWidth = complicationsImage.get(i).widthProperty().intValue();
            int compHeight = complicationsImage.get(i).heightProperty().intValue();
            
            
            if(compY == 445)
            {
                if((Math.abs(missileX - compX) < (compWidth-280)) && (Math.abs((missileY+445) - compY) > 280))
                    return i;
            }
            if((Math.abs(missileX - (compX)) < (compWidth-15)) && (Math.abs(missileY - compY) < compHeight)) 
                return i;
        }
        
        
        return -1;
    }


    public static boolean checkCollision(Character c, ArrayList<Integer> complicationsX, ArrayList<Integer> complicationsY, ArrayList<Image> complicationsImage) {

        for (int i = 0; i < complicationsX.size(); i++) {

            int compX = complicationsX.get(i);
            int compY = complicationsY.get(i);
            int compWidth = complicationsImage.get(i).widthProperty().intValue();
            int compHeight = complicationsImage.get(i).heightProperty().intValue();

            if (compY == 450) {
                if ((Math.abs(c.getX() - compX) < (compWidth - 250)) && (Math.abs((c.getY() + 450) - compY) > 270)) {
                    return true;
                }
            }
        
            if ((Math.abs(c.getX() - compX) < (compWidth)) && (Math.abs(c.getY() - compY) < compHeight)) {
                return true;
            }
        }

        return false;
    }

    public boolean checkMissile() {
        return this.game.currentState().containsMissile();
    }
    
    public Game getGame() { return this.game; }
}
