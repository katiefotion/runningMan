/*
 * VIEW
 * 
 * Main game application
 */
package GameGUI;

import Controller.Controller;
import Game.Game;
import Game.Player;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;

public class GameApp extends Application {

  private final int MENU_PADDING = 4;
  private final int MENU_BUTTON_WIDTH = 256;
  private final int MENU_BUTTON_HEIGHT = 128;

  private final int GAME_WIDTH = 800;
  private final int GAME_HEIGHT = 600;

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

  private Scene gameScene;
  private Stage theStage;

  //menu stuff
  ImageView startImage;
  ImageView highscoresImage;
  ImageView quitImage;
  Image backgroundImage;

  // Launch application
  public static void initGameApp(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage theStage) {
    this.theStage = theStage;
    gameScene = new Scene(new Group(), GAME_WIDTH, GAME_HEIGHT);
    this.theStage.setScene(gameScene);

    showMenu();
    initMenuButtonListeners();
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

  private void showMenu() {
    try {
      BorderPane menuLayout = new BorderPane();
      VBox buttonLayout = new VBox(MENU_PADDING);

      startImage = new ImageView(new Image(new FileInputStream("src/start_button.png")));
      highscoresImage = new ImageView(new Image(new FileInputStream("src/highscores_button.png")));
      quitImage = new ImageView(new Image(new FileInputStream("src/quit_button.png")));
      backgroundImage = new Image("background.png");

      startImage.setFitWidth(MENU_BUTTON_WIDTH);
      startImage.setFitHeight(MENU_BUTTON_HEIGHT);

      highscoresImage.setFitWidth(MENU_BUTTON_WIDTH);
      highscoresImage.setFitHeight(MENU_BUTTON_HEIGHT);

      quitImage.setFitWidth(MENU_BUTTON_WIDTH);
      quitImage.setFitHeight(MENU_BUTTON_HEIGHT);

      buttonLayout.getChildren().addAll(startImage, highscoresImage, quitImage);
      buttonLayout.setAlignment(Pos.CENTER);
      menuLayout.setCenter(buttonLayout);

      menuLayout.setBackground(new Background(
              new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                      BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));

      gameScene.setRoot(menuLayout); 
      theStage.show();
    } catch (FileNotFoundException ex) {
      Logger.getLogger(GameApp.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  private void initMenuButtonListeners() {
    if (startImage != null) {
      startImage.setOnMouseClicked(new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
          startGame();
        }
      });
    }
  }

  public void startGame() {
    // Start controller to mediate between model and view
    Controller control = new Controller(new Game(new Player(1)));

    theStage.setTitle("Running Man");    // Set screen title

    // Initialize empty character and complication structures
    character = new Sprite(100, new Image("character.png"));
    complications = new ArrayList<>();

    // Fill character and complication structures with relevant info
    // based on model
    control.initializeVariables();

    Group root = new Group();
    //gameScene = new Scene(root);
    gameScene.setRoot(root);
    //theStage.setScene(gameScene);

    Canvas canvas = new Canvas(GAME_WIDTH, GAME_HEIGHT);
    root.getChildren().add(canvas);

    GraphicsContext gc = canvas.getGraphicsContext2D();

    // Set background and button images
    Image background = new Image("background.png");
    Image quit = new Image("quit.png");
    Image pause = new Image("pause.png");
    Image play = new Image("play.png");

    // Start timer
    Timeline gameLoop = new Timeline();
    gameLoop.setCycleCount(Timeline.INDEFINITE);
    final long timeStart = System.currentTimeMillis();

    // Listen for user input continuously 
    ActionHandler ah = new ActionHandler(gameScene, gameLoop, control, gc, character, complications, timeStart);
    KeyFrame kf = ah.listen();
    gameLoop.getKeyFrames().add(kf);
    gameLoop.play();

    // Display the scene
    theStage.show();
  }
}
