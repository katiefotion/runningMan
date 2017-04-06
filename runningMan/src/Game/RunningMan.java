/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game;

/**
 *
 * @author katie
 */
public class RunningMan {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        // TODO: Find a way to make playerId different every time
        int playerId = 1;
        Player player = new Player(playerId);
        
        // TODO: Find a way to calculate center of screen (or wherever player should begin on screen)
        Game game = new Game(player, 400, 400);
        
        // Randomly generate set of complications for first game state
        
        // Take player's input to move character
    }
    
}
