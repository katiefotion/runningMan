/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameObjects;

/**
 *
 * @author darrylRaveche
 */
public class Threat extends Complication {
        
    public Threat(int x, int y) {
        super(x, y);
    }
    
    // Used in GameState class
    @Override 
    public boolean isThreat() {
        return true;
    }
}
