/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game;

import GameGUI.ImagePanel;
import GameGUI.Window;
import GameObjects.Character;
import java.awt.image.BufferStrategy;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javafx.scene.canvas.GraphicsContext;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

/**
 *
 * @author katie
 */
public class Game {
    
    private Player player;
    private Character character;
    private GameState currentState;
    
    public Game(Player player) {
        
        this.player = player;
        this.character = new Character(100, 280);
        this.currentState = new GameState();
    }
    
    public Player getPlayer() {
        return this.player;
    }
    
    public Character getCharacter() {
        return this.character;
    }
    
    public GameState currentState() {
        return this.currentState;
    }
    
    public void incrementScore(int amount) {
        this.currentState.incrementScore(amount);
    }
    
}
