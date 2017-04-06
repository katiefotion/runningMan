/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Complication;

import Complication.Complication;

/**
 *
 * @author katie
 */
public class Pit extends Complication{
    
    public Pit(int x, int y) {
        super(x, y);
    }
    
    // Used in GameState class
    @Override 
    public boolean isPit() {
        return true;
    }
}
