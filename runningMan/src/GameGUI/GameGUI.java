/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameGUI;

import Controller.ActionListeners;
import Game.Game;
import GameObjects.Complication;
import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

/**
 *
 * @author katie
 */
public class GameGUI extends Canvas implements Runnable{
    
    private Window window;
    private Game game;
    private Sprite character;
    private Sprite obstacle;
    private Sprite pit;
    
    private Thread thread;
    private boolean running = false;
    
    public GameGUI(Game g) {
        
        // Create window
        window = new Window(800, 600, "Running Man");
        game = g;
        
        try {
            
            // Create background of game screen
            BufferedImage backgroundImage = ImageIO.read(new File("background.png"));
            window.setBackgroundImage(backgroundImage);
            
            // Create character sprite
            BufferedImage characterImage = ImageIO.read(new File("character.png"));
            character = new Sprite(characterImage, 100, 300);
            window.updateSprite(character);
            
            // Create obstacle sprite
            BufferedImage obstacleImage = ImageIO.read(new File("obstacle.png"));
            obstacle = new Sprite(obstacleImage, 400, 350);
            window.updateSprite(obstacle);
            
            // Create pit sprite
            BufferedImage pitImage = ImageIO.read(new File("pit.png"));
            pit = new Sprite(pitImage, 600, 470);
            window.updateSprite(pit);
        }
        catch(IOException e) {
            System.out.println(e);
        }
        
        // Add mouse event listener
        JFrame frame = window.getFrame();
        frame.addMouseListener(new ActionListeners(game, this));
        
        // Start running
        this.start();
    }
    
    public synchronized void start() {
        thread = new Thread(this);
        thread.start();
        running = true;
    }
    
    public synchronized void stop() {
        try {
            thread.join();
            running = false;
        }
        
        catch(InterruptedException e){
            System.out.println(e);
        }
    }
    
    @Override
    public void run() {
        
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        
        while(running){
            long now = System.nanoTime();
            delta += (now-lastTime)/ns;
            lastTime = now;
            while(delta >= 1 ) {
                tick();
                delta--;
            }
            if(running) {
                render();
            }
        }
        stop();
    }
    
    private void tick() {
        
        List<Complication> comps = game.currentState().getComplications();
        for(Complication comp : comps ) {
            comp.setX(comp.getX() - 1);
        }
    }
    
    private void render() {
        
        // TODO: RIGHT NOW ONLY MAKES COPIES OF SPRITES AND BASED ON 
        // SYNTHETIC INITIALIZATION OF COMPLICATIONS
        
        // Repaint character's location based on model
        int x = game.getCharacter().getX();
        int y = game.getCharacter().getY();
        character.setSpriteX(x);
        character.setSpriteY(y);
        window.updateSprite(character);
        
        // Repaint obstacle's location based on model
        Complication comp = game.currentState().getComplications().get(0);
        x = comp.getX();
        y = comp.getY();
        obstacle.setSpriteX(x);
        obstacle.setSpriteY(y);
        window.updateSprite(obstacle);
        
        // Repaint pit's location based on model
        comp = game.currentState().getComplications().get(1);
        x = comp.getX(); 
        y = comp.getY();
        pit.setSpriteX(x);
        pit.setSpriteY(y);
        window.updateSprite(pit);
    }

}
