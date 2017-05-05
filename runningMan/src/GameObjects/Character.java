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

    
    private boolean goingUp, goingDown, runningRight, runningLeft;
    private int jumpHeight = 300;

   
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
        this.setX(this.getX()+3);
    }
    
    public void moveLeft(){
        this.setX(this.getX()-3);
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
    
    public int getJumpHeight() {
        return jumpHeight;
    }

    public void setJumpHeight(int jumpHeight) {
        this.jumpHeight = jumpHeight;
    }
    
    public boolean isRunningRight() {
        return runningRight;
    }

    public void setRunningRight(boolean runningRight) {
        this.runningRight = runningRight;
    }

    public boolean isRunningLeft() {
        return runningLeft;
    }

    public void setRunningLeft(boolean runningLeft) {
        this.runningLeft = runningLeft;
    }
}
