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

    public Character(int x, int y) {
        super(x, y);
    }

    @Override
    public void tick() {
        
    }

    @Override
    public void render(Graphics g) {
        
    }
    
    public void jump(){
        this.setY(this.getY() - 150);
    }
    
    public void duck() {
        
    }
    
    public void moveRight(){
        
    }
    
    public void moveLeft(){
        
    }
    
}
