/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game;

/**
 * Class that describes the human player
 * PlayerId is used for recording high score
 * 
 * @author katie
 */
public class Player {
    
    private final int playerId;
    
    // Constor 
    public Player(int id) {
        
        this.playerId = id;
    }
   
    // Public getter method 
    public int getPlayerId() {
        
        return this.playerId;
    }
    
    // Note: no setter because playerId is permanent
}
