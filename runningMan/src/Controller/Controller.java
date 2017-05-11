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
import GameObjects.*;
import java.util.ArrayList;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author katie
 */
public class Controller {

    private final Game game;
    private double timer1 = 4;
    private double timer2 = 0;
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
            //System.out.println("going up");
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
                complicationsImage.add(new Image("pit.png"));
            } else if (comp.isThreat()) {
                complicationsImage.add(new Image("threat.png"));
            }
        }
    }

    public void tick(int speedCoeff, double t) {

        boolean shiftedImage = game.currentState().tick(speedCoeff, t, c.getX());

        if (shiftedImage) {
            ArrayList<Image> compImageTemp = new ArrayList<>();
            for (int i = 1; i < complicationsImage.size(); i++) {
                compImageTemp.add(complicationsImage.get(i));
            }

            complicationsImage = compImageTemp;
        }

        //redraws character sprite each frame
        GameApp.setCharacterY(c.getY());
        GameApp.setCharacterX(c.getX());
        //System.out.println(game.getCharacter().getY());
        //chararctr keeps going down if not on floor level
        //if(game.getCharacter().getY()<=275){
        //    timer = timer + .2;
        //    game.getCharacter().setY((int) (game.getCharacter().getY()+timer));    
        //}
        //else
        //    timer = 0;
        if (c.isGoingUp() && c.getY() >= 275 - c.getJumpHeight()) {
            //System.out.println("going up initiation");
            timer1 = timer1 - .2;
            //System.out.println(timer);
            c.setY((int) (c.getY() - timer1));
        } else {
            timer1 = 12;
        }
        if (c.getY() < 280 - c.getJumpHeight()) {
            //System.out.println("not going up");
            c.setGoingUp(false);
            //System.out.println("going down");
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

        if (this.game.currentState().containsMissile()) {
            GameApp.setMissileX(game.currentState().getMissileX());
            GameApp.setMissileY(game.currentState().getMissileY());
        }

        // Increment complications' x coordinates with time  
        // (and y coordinates in case new complication is created)
        this.setComplicationsX();
        this.setComplicationsY();
        this.setComplicationsImage();
        GameApp.updateComplicationsX(complicationsX, complicationsY, complicationsImage);

        // Check if character and complications collid
        boolean collision = checkCollision(c, complicationsX, complicationsY, complicationsImage);

        if (collision) {
            BorderPane borderLayout = new BorderPane();
            VBox verticalLayout = new VBox();
            
            TextField nameText = new TextField();
            Label scoreLabel = new Label();
            Button submitScoreButton = new Button();

            submitScoreButton.setText("Submit");
            scoreLabel.setText(String.valueOf(game.currentState().getCurrentScore()));
            nameText.setPromptText("Enter your name");

            verticalLayout.getChildren().addAll(nameText, scoreLabel, submitScoreButton);
            borderLayout.setCenter(verticalLayout);

            submitScoreButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent t) {
                    //call database
                    
                }
            });

            GameApp.onEndGame(borderLayout);
        }
    }

    public static boolean checkCollision(Character c, ArrayList<Integer> complicationsX, ArrayList<Integer> complicationsY, ArrayList<Image> complicationsImage) {

        for (int i = 0; i < complicationsX.size(); i++) {

            int compX = complicationsX.get(i);
            int compY = complicationsY.get(i);
            int compWidth = complicationsImage.get(i).widthProperty().intValue();
            int compHeight = complicationsImage.get(i).heightProperty().intValue();

            // System.out.println(compY);
            // System.out.println("Char: " + c.getY());
            if (compY == 450) {
                //c.setVelY(15);
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
}
