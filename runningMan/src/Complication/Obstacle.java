/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Complication;

import java.awt.Graphics;
import java.awt.Image;

/**
 *
 * @author darrylraveche
 */
public class Obstacle extends Complication {
    
    Image obstacle;
    
    public Obstacle() {
        
        //empty constructor
    }
    public Obstacle(int x, int y) {
        initObstacle(x,y);
    }
    
    public void initObstacle(int x, int y)
    {
        obstacle = null; //filepath to obstacle image
        initObject(x,y,0,0);
    }
    // Used in GameState class
    @Override 
    public boolean isObstacle() {
        return true;
    }
}
