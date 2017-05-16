/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MenuGUI;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author Felix
 */
public class Menu {

    public interface MenuEventListener {

        public void onStartGameSelected();

        public void onHighScoresSelected();

        public void onQuitSelected();

        public void onOptionEnter();

        public void onOptionExit();

    }

    private final int MENU_PADDING = 16;
    private final int MENU_BUTTON_WIDTH = 256;
    private final int MENU_BUTTON_HEIGHT = 64;

    private ImageView startImage;
    private ImageView highscoresImage;
    private ImageView quitImage;
    private Image backgroundImage;

    private BorderPane menuLayout;
    private VBox buttonsLayout;

    public Menu(MenuEventListener listener) {
        try {
            startImage = new ImageView(new Image(new FileInputStream("src/blue_button_startgame.png")));
            highscoresImage = new ImageView(new Image(new FileInputStream("src/blue_button_highscores.png")));
            quitImage = new ImageView(new Image(new FileInputStream("src/blue_button_quit.png")));
            backgroundImage = new Image("background_game.png");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        }

        startImage.setFitWidth(MENU_BUTTON_WIDTH);
        startImage.setFitHeight(MENU_BUTTON_HEIGHT);

        highscoresImage.setFitWidth(MENU_BUTTON_WIDTH);
        highscoresImage.setFitHeight(MENU_BUTTON_HEIGHT);

        quitImage.setFitWidth(MENU_BUTTON_WIDTH);
        quitImage.setFitHeight(MENU_BUTTON_HEIGHT);

        menuLayout = new BorderPane();
        buttonsLayout = new VBox(MENU_PADDING);

        buttonsLayout.getChildren().addAll(startImage, highscoresImage, quitImage);
        buttonsLayout.setAlignment(Pos.CENTER);
        menuLayout.setCenter(buttonsLayout);

        menuLayout.setBackground(new Background(
                new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                        BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));

        initMenuListeners(listener);
    }

    public void showMenu(Stage stage) {
        stage.getScene().setRoot(menuLayout);
        stage.show();
    }

    private void initMenuListeners(MenuEventListener listener) {
        if (startImage != null) {
            startImage.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    listener.onStartGameSelected();
                }
            });

            startImage.setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent t) {
                    listener.onOptionEnter();
                }
            });

            startImage.setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent t) {
                    listener.onOptionExit();
                }
            });
        }

        if (quitImage != null) {
            quitImage.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    listener.onQuitSelected();
                }
            });

            quitImage.setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent t) {
                    listener.onOptionEnter();
                }
            });

            quitImage.setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent t) {
                    listener.onOptionExit();
                }
            });
        }

        if (highscoresImage != null) {
            highscoresImage.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    listener.onHighScoresSelected();
                }
            });

            highscoresImage.setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent t) {
                    listener.onOptionEnter();
                }
            });

            highscoresImage.setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent t) {
                    listener.onOptionExit();
                }
            });
        }
    }
}
