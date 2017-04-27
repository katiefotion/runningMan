/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameObjects;

import java.awt.Graphics;

/**
 *
 * @author Tony
 */
public class Character extends GameObject{

    
    private boolean goingUp, goingDown;
    private int jumpHeight = 200;

    
    public Character(int x, int y) {
        super(x, y);
    }

    @Override
    public void tick() {
        this.setVelX(this.getX() + this.getVelX());
        this.setVelY(this.getY() + this.getVelY());
    }

    @Override
    public void render(Graphics g) {
        
    }
    
    public void jump(){
        this.setY(this.getY()-jumpHeight);
    }
    
    public void duck() {
        
    }
    
    public void moveRight(){
        
    }
    
    public void moveLeft(){
        
    }
    public boolean isGoingUp() {
        return goingUp;
    }

    public void setGoingUp(boolean goingUp) {
        this.goingUp = goingUp;
    }

    public boolean isGoingDown() {
        return goingDown;
    }

    public void setGoingDown(boolean goingDown) {
        this.goingDown = goingDown;
    }
}
