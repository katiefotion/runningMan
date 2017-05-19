/*
 * VIEW
 * 
 * Main game application
 */
package GameGUI;

import MenuGUI.Menu;
import Controller.Controller;
import Database.HighScores;
import Database.HighScores.PlayerScore;
import Game.Game;
import Game.Player;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class GameApp extends Application implements
        Menu.MenuEventListener {

    private final int GAME_WIDTH = 800;
    private final int GAME_HEIGHT = 600;
    private static final int MENU_BUTTON_WIDTH = 128;
    private static final int MENU_BUTTON_HEIGHT = 32;

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

    private Game game;
    private static Scene gameScene;
    private static Stage theStage;
    private static Menu menu;
    private static Timeline gameLoop;

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
        showMenu();
    }

    public static void showMenu() {
        menu.showMenu(theStage);
    }
        
    // Set character's y value based on current model
    public static void setCharacterY(int y) {
        character.setY(y);
    }

    // Set character's x value based on current model
    public static void setCharacterX(int x) {
        character.setX(x);
    }

    // sets image for character in still position
    public static void setStillCharacter() {
        character.setImage(new Image("character_1.png"));
    }

    //animates character based on the frame number to set image for character
    public static void animateCharacter(int x) {
        switch (x) {
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

    //set missle x coordinate
    public static void setMissileX(int x) {
        missile.setX(x);
    }

    //set missle y coordinate
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

        if (xs.size() > complications.size()) {
            complications.add(new Sprite(xs.get(xs.size() - 1), ys.get(ys.size() - 1), images.get(images.size() - 1)));
        }

        if (xs.size() < complications.size()) {
            complications.remove(removal);
        }

        for (int i = 0; i < xs.size(); i++) {
            complications.get(i).setX(xs.get(i));
        }
        for (int i = 0; i < ys.size(); i++) {
            complications.get(i).setY(ys.get(i));
        }
        for (int i = 0; i < images.size(); i++) {
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
        game = new Game(new Player(1));
        Controller control = new Controller(game);

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
        gameLoop = new Timeline();
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

    //when player dies
    public static void onEndGame(int score) {
        gameLoop.stop();

        GridPane gridLayout = new GridPane();
        gridLayout.setHgap(10);
        gridLayout.setVgap(12);

        Image backgroundImage = new Image("background_endgame.png");
        gridLayout.setBackground(new Background(
                new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                        BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));

        TextField nameText = new TextField();
        Label nameLabel = new Label();
        Label scoreLabel = new Label();
        Label scoreValueLabel = new Label();
        ImageView submitButton = null;
        ImageView returnMenuButton = null;

        try {
            //buttons for submitting and returning
            submitButton = new ImageView(new Image(new FileInputStream("src/blue_button_submit.png")));
            returnMenuButton = new ImageView(new Image(new FileInputStream("src/blue_button_menu.png")));

            submitButton.setFitWidth(MENU_BUTTON_WIDTH);
            submitButton.setFitHeight(MENU_BUTTON_HEIGHT);

            returnMenuButton.setFitWidth(MENU_BUTTON_WIDTH);
            returnMenuButton.setFitHeight(MENU_BUTTON_HEIGHT);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(GameApp.class.getName()).log(Level.SEVERE, null, ex);
        }

        //name text field
        nameLabel.setText("Name: ");
        nameLabel.setFont(Font.font("Helvetica", FontWeight.BOLD, 24));
        nameLabel.setTextFill(Color.WHITE);

        //score text display
        scoreLabel.setText("Score: ");
        scoreValueLabel.setText(String.valueOf(score));
        scoreLabel.setFont(Font.font("Helvetica", FontWeight.BOLD, 24));
        scoreValueLabel.setFont(Font.font("Helvetica", FontWeight.BOLD, 24));
        scoreValueLabel.setTextFill(Color.WHITE);

        //prompt user to enter name
        nameText.setPromptText("Enter your name");

        gridLayout.add(nameLabel, 0, 0);
        gridLayout.add(nameText, 1, 0);
        gridLayout.add(scoreLabel, 0, 1);
        gridLayout.add(scoreValueLabel, 1, 1);
        gridLayout.add(submitButton, 1, 2);
        gridLayout.add(returnMenuButton, 0, 2);
        gridLayout.setAlignment(Pos.CENTER);

        //when user clicks on submit button, highscore is stored to database
        submitButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                //store highscore
                HighScores highscores = new HighScores();
                String name = nameText.getText();

                if (name.isEmpty()) {
                    name = " Anonymous";
                }

                highscores.insertHighScore(new PlayerScore(name, score));
                showMenu();
            }
        });

        //cursor becomes hand when on submit button
        submitButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                gameScene.setCursor(Cursor.HAND);
            }
        });

        submitButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                gameScene.setCursor(Cursor.DEFAULT);
            }
        });

        //when user clicks on menu button, returns to menu
        returnMenuButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                showMenu();
            }
        });

        returnMenuButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                gameScene.setCursor(Cursor.HAND);
            }
        });

        returnMenuButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                gameScene.setCursor(Cursor.DEFAULT);
            }
        });

        gameScene.setRoot(gridLayout);
        theStage.show();
    }

    @Override
    public void onStartGameSelected() {
        startGame();
    }

    @Override
    //TODO: replace with a new high score scene
    public void onHighScoresSelected() {

        ImageView menuImage = null;
        Image backgroundImage = null;

        try {
            menuImage = new ImageView(new Image(new FileInputStream("src/blue_button_menu.png")));
            backgroundImage = new Image("background_endgame.png");

        } catch (FileNotFoundException ex) {
            Logger.getLogger(GameApp.class.getName()).log(Level.SEVERE, null, ex);
        }

        //set width and height of menuImage
        menuImage.setFitWidth(MENU_BUTTON_WIDTH);
        menuImage.setFitHeight(MENU_BUTTON_HEIGHT);

        GridPane highScoreLayout = new GridPane();
        VBox buttonsLayout = new VBox(4);

        buttonsLayout.getChildren().addAll(menuImage);
        buttonsLayout.setAlignment(Pos.TOP_CENTER);

        //set high score layout 
        highScoreLayout.setAlignment(Pos.CENTER);
        highScoreLayout.setPadding(new Insets(10, 10, 10, 10));

        Text text = new Text("    HIGH SCORES     ");
        text.setFont(Font.font("Helvetica", FontWeight.BOLD, 24));
        text.setFill(Color.rgb(110, 185, 255));

        highScoreLayout.add(text, 0, 0);

        HighScores highscores = new HighScores();
        List<PlayerScore> scores = highscores.getTopScores(10);
        int i = 1; //counter to place scores
            
        //displays top users and their high scores
        if (scores != null) {
            for (PlayerScore score : scores) {
                Text t = new Text();
                t.setFont(Font.font("Helvetica", FontWeight.BOLD, 24));
                t.setFill(Color.WHITE);
                t.setText(i + " : " + score.getName() + " : " + score.getScore());

                //add to highScoreLayout
                highScoreLayout.add(t, 0, i);
                i++;
            }
        }

        //set main menu button
        highScoreLayout.add(buttonsLayout, 1, i + 1);

        i = 0; //bring back to zero in the end to fix any potential errors

        //set background image
        highScoreLayout.setBackground(new Background(
                new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                        BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));

        menuImage.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                showMenu();
            }
        });

        menuImage.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                gameScene.setCursor(Cursor.HAND);
            }
        });

        menuImage.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                gameScene.setCursor(Cursor.DEFAULT);
            }
        });

        gameScene.setRoot(highScoreLayout);
        theStage.show();
    }

    @Override
    public void onQuitSelected() {
        theStage.close();
    }

    @Override
    public void onOptionEnter() {
        gameScene.setCursor(Cursor.HAND);
    }

    @Override
    public void onOptionExit() {
        gameScene.setCursor(Cursor.DEFAULT);
    }
}
