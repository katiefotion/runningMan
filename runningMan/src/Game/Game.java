/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game;

import GameObjects.Character;

/**
 *
 * @author katie
 */
public class Game {
    
    private Player player;
    private Character character;
    private GameState currentState;
    
    //Game constructor
    public Game(Player player) {
        
        this.player = player;
        this.character = new Character(100, 280);
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
    //increase score by an amount
    public void incrementScore(int amount) {
        this.currentState.incrementScore(amount);
    }
    //set score to a certain score
    public void setScore(int score) {
        this.currentState.setScore(score);
    }
    
}
