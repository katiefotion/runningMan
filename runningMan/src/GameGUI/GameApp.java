/*
 * VIEW
 * 
 * Main game application
 */
package GameGUI;

import MenuGUI.Menu;
import Controller.Controller;
import Game.Game;
import Game.Player;
import java.util.ArrayList;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Group;
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

  private Scene gameScene;
  private Stage theStage;
  private Menu menu;

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
  public static void setStillCharacter(){
        character.setImage(new Image("character_1.png"));
    }
  public static void animateCharacter(int x){
    switch(x){
        case 0:
            character.setImage(new Image("character_1.png"));
            break;
        case 1:
            character.setImage(new Image("character_2.png"));
            break;
        case 2:
            character.setImage(new Image("character_3.png"));
            break;
        case 3:
            character.setImage(new Image("character_4.png"));
            break;
        case 4:
            character.setImage(new Image("character_5.png"));
            break;
        case 5:
            character.setImage(new Image("character_6.png"));
            break;
        default:
            break;
    }
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

  // Update complications' values based on current model
  public static void updateComplications(ArrayList<Integer> xs, ArrayList<Integer> ys, ArrayList<Image> images, int removal) {
    
    if(xs.size() > complications.size()) {
        complications.add(new Sprite(xs.get(xs.size()-1), ys.get(ys.size()-1), images.get(images.size()-1)));
    }
    
    if(xs.size() < complications.size()) {
        complications.remove(removal);
    }
    
    for (int i = 0; i < xs.size(); i++) {
        complications.get(i).setX(xs.get(i));
    }
    for(int i = 0; i < ys.size(); i++) {
        complications.get(i).setY(ys.get(i));
    }
    for(int i = 0; i < images.size(); i++) {
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
    Timeline gameLoop = new Timeline();
    gameLoop.setCycleCount(Timeline.INDEFINITE);
    final long timeStart = System.currentTimeMillis();

    // Listen for user input continuously 
    ActionHandler ah = new ActionHandler(theStage, menu, gameLoop, control, gc, character, missile, complications, timeStart);
    control.setActionHandler(ah);
    KeyFrame kf = ah.listen();
    gameLoop.getKeyFrames().add(kf);
    gameLoop.play();

    // Display the scene
    theStage.show();
  }

  @Override
  public void onStartGameSelected() {
    startGame();
  }

  @Override
  public void onHighScoresSelected() {
    //TODO: fix rest server and make a call to display a new scene with the high scores
  }

  @Override
  public void onQuitSelected() {
    theStage.close();
  }
}
