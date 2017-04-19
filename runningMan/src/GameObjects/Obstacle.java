/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameObjects;

import java.awt.Graphics;

/**
 *
 * @author darrylraveche
 */
public class Obstacle extends Complication {
     
    public Obstacle(int x, int y) {
        super(x,y);
    }
    
    // Used in GameState class
    @Override 
    public boolean isObstacle() {
        return true;
    }
}
