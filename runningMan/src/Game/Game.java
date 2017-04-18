/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game;

import java.awt.image.BufferStrategy;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author katie
 */
public class Game extends Canvas implements Runnable{
    
    private Player player;
    private Character character;
    private GameState currentState;
    private Thread thread;
    private boolean running = false;
    
    public Game() {
        new Window(600, 400, "Running Man", this);
    }
    
    public Game(Player player, int startingX, int startingY) {
        
        this.player = player;
        this.character = new Character(startingX, startingY);
        this.currentState = new GameState();
    }
    
    public synchronized void start() {
        thread = new Thread(this);
        thread.start();
        running = true;
    }
    
    public synchronized void stop() {
        try{
            thread.join();
            running = false;
        }
        
        catch(Exception e){
            e.printStackTrace();
        }
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
    
    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        while(running){
            long now = System.nanoTime();
            delta += (now-lastTime)/ns;
            lastTime = now;
            while(delta >= 1 ) {
                tick();
                delta--;
            }
            if(running)
                render();
            frames++;
            if(System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                System.out.println("FPS: " + frames);
                frames = 0;
            }
        }
        stop();
    }
    
    private void tick() {
        
    }
    
    private void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null) {
            this.createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        g.setColor(Color.blue);
        g.fillRect(0, 0, 600, 400);
        g.dispose();
        bs.show();
    }
}
