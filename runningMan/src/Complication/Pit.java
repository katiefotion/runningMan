/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Complication;

import Complication.Complication;
import java.awt.Image;

/**
 *
 * @author darrylRaveche
 */
public class Pit extends Complication{
    
    Image pit;
    public Pit()
    {
        //empty constructor
    }
    public Pit(int x, int y) {
        initPit(x,y);
    }
    
 public void initPit(int x, int y)
    {
        pit = null; //filepath to obstacle image
        initObject(x,y,0,0);
    }
    // Used in GameState class
    @Override 
    public boolean isPit() {
        return true;
    }
}
