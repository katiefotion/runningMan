
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
import java.util.Random;

/**
 *
 * @author katie
 */
public class GameState {
    
    private int currentScore;
    private List<Complication> complications;
    boolean spawn_flag = true;
    
    public GameState() {
        
               //this.complications = (List<Complication>) new Complication();      
        this.complications = new ArrayList();  //TODO: fix stack overflow error in complications generation
        
        Complication.initComplication(this); //
        // This is for now...
        //this.complications.add(new Obstacle(1200, 329));
        //this.complications.add(new Pit(2000, 450));
        //this.complications.add(new Threat(3000, 300));
        
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
    
    public boolean tick(int speedCoeff, double t, int ch) {
        
        for (Complication c : complications) {
            c.setX(c.getX() - speedCoeff);
        }
        
        //System.out.println("TIme now: S" + t);
        //System.out.println("TIme mod 12: S" + ((int)t)%12);
        if((int)(t*100)%600 == 0 && t > 1)
        {
            System.out.println("******* ADDING NEW COMPLICATION ********" + t);
            boolean shiftedImage = this.update(ch);
            
            return shiftedImage;
        }
        
        return false;
    }
    
        //update every some odd seconds, grabbing location of the current character on the screen
    public boolean update(int x) {
        
        // Add new complication 
        Random rand = new Random();
        ArrayList state = (ArrayList) this.getComplications();
        int compChoice = rand.nextInt(3); // produces a random number to spawn a certain obstacle
        //System.out.println("CompChoice: " + compChoice);
        switch (compChoice)
        {
            //TODO: set y to respective y-coords (image)
            case 0: //produces an obstacle
                Obstacle o = new Obstacle(x+800, 329);//call obstacle class to instantiate an obstacle
                state.add(o);
                break;
            case 1: //produces a pit
                Pit p = new Pit(x+800, 450);//call put class to instatitate a pit
                state.add(p);
                break;
            case 2: //produces a threat
                Threat t = new Threat(x+800,300);//call threat class to instatitate a threat
                state.add(t);
                break;
            default: 
                System.out.println("Error here.");
                //do nothing, continue on.
                break;
        }
        this.setNewComplications(state);//add the new arrayList to gameState
        
        // Then delete first element of Array List if too long to improve runtime 
        if(complications.size() > 3) {
            List<Complication> comp_temp = new ArrayList<>();
            
            for(int i = 1; i < complications.size(); i++) {
                comp_temp.add(complications.get(i));
            }
            
            complications = comp_temp;
            
            return true;
        } 
        
        return false;
    }
}
