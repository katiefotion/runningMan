/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Complication;

import Game.GameObject;
import java.awt.Graphics;

/**
 *
 * @author katie
 */
public class Complication extends GameObject{
    
    public Complication(int x, int y) {
        super(x, y);
    }
    
    @Override
    public void tick() {
        
    }

    @Override
    public void render(Graphics g) {
        
    }
    
    // Overriden by subclasses where appropriate
    public boolean isPit() {return false;}
    public boolean isObstacle() {return false;}
    public boolean isThreat() {return false;}
}
