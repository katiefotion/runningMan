/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game;

import GameObjects.Pit;
import GameObjects.Complication;
import GameObjects.Threat;
import GameObjects.Obstacle;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author katie
 */
public class GameState {
    
    private int currentScore;
    private List<Complication> complications;
    
    public GameState() {
        
        //this.complications = (List<Complication>) new Complication();      
        this.complications = new ArrayList();  //TODO: fix stack overflow error in complications generation
        
        // This is for now...
        this.complications.add(new Obstacle(1200, 329));
        this.complications.add(new Pit(2000, 450));
        this.complications.add(new Threat(3000, 290));
        
        this.currentScore = 0;
    }
    
    // Sets current score to "amount" more than previous score
    public void incrementScore(int amount) {
        
        this.currentScore += amount;
    }
    
    // Set Game State to contain a new set of complications (from random generator every 30 seconds)
    public void setNewComplications(List<Complication> newComplications) {
        
        this.complications = newComplications;
    }
    
    // Returns the current score of game 
    public int getCurrentScore() {
        return this.currentScore;
    }
    
    // Returns list of all complications in current GameState
    public List<Complication> getComplications() {
        
        return this.complications;
    }
    
    // Returns list of all pits in current Game State
    public List<Pit> getPits() {
        
        List<Pit> pits = new ArrayList<>();
        
        // Loop through all complications and add pits to list
        for(int i = 0; i < this.complications.size(); i++) {
            
            Complication complication = this.complications.get(i);
            
            // Check if pit
            if (complication.isPit()) {
                pits.add((Pit)complication);
            }
        }
        return pits;
    }
    
    // Returns list of all obstacles in current Game State
    public List<Obstacle> getObstacles() {
        
        List<Obstacle> obstacles = new ArrayList<>();
        
        // Loop through all complications and add obstacles to list
        for(int i = 0; i < this.complications.size(); i++) {
            
            Complication complication = this.complications.get(i);
            
            // Check if obstacle
            if (complication.isObstacle()) {
                obstacles.add((Obstacle)complication);
            }
        }
        return obstacles;
    }
    
    // Returns list of all threats in current Game State
    public List<Threat> getThreats() {
        
        List<Threat> threats = new ArrayList<>();
        
        // Loop through all complications and add threats to list
        for(int i = 0; i < this.complications.size(); i++) {
            
            Complication complication = this.complications.get(i);
            
            // Check if pit
            if (complication.isThreat()) {
                threats.add((Threat)complication);
            }
        }
        return threats;
    }
    
    public void tick(int speedCoeff) {
        
        for (Complication c : complications) {
            c.setX(c.getX() - speedCoeff);
        }
    }
}
