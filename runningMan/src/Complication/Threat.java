/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Complication;
import java.awt.Image;
/**
 *
 * @author darrylRaveche
 */
public class Threat extends Complication {
    
    Image threat;
    
    public Threat()
    {
        //empty constructor
    }
    public Threat(int x, int y) {
        initThreat(x, y);
    }
    
    public void initThreat(int x, int y)
    {
        threat = null; //filepath to obstacle image
        initObject(x,y,-1,0);
    }
     
    // Used in GameState class
    @Override 
    public boolean isThreat() {
        return true;
    }
}
