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
public class Game {
    
    private Player player;
    private Character character;
    private GameState currentState;
    
    public Game(Player player, int startingX, int startingY) {
        
        this.player = player;
        this.character = new Character(startingX, startingY);
        this.currentState = new GameState();
    }
    
    public Player getPlayer() {
        return this.player;
    }
    
    public Character getCharacter() {
        return this.character;
    }
    
    public GameState currentState() {
        return this.currentState;
    }
    
    public void incrementScore(int amount) {
        this.currentState.incrementScore(amount);
    }
}
