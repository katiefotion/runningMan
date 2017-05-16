
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game;

import GameObjects.*;
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
    private Missile m;

    int pastChoice = -1;
    
    public GameState() {
        
        this.complications = new ArrayList();  
        this.currentScore = 0;
    }

    public boolean containsMissile() {
        if (this.m == null) {
            return false;
        }
        return true;
    }

    public void addMissile(Missile m) {
        this.m = m;
    }

    // Sets current score to "amount" more than previous score
    public void incrementScore(int amount) {

        this.currentScore += amount;
    }

    public void setScore(int score) {

        this.currentScore = score;
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
        for (int i = 0; i < this.complications.size(); i++) {

            Complication complication = this.complications.get(i);

            // Check if pit
            if (complication.isPit()) {
                pits.add((Pit) complication);
            }
        }
        return pits;
    }

    // Returns list of all obstacles in current Game State
    public List<Obstacle> getObstacles() {

        List<Obstacle> obstacles = new ArrayList<>();

        // Loop through all complications and add obstacles to list
        for (int i = 0; i < this.complications.size(); i++) {

            Complication complication = this.complications.get(i);

            // Check if obstacle
            if (complication.isObstacle()) {
                obstacles.add((Obstacle) complication);
            }
        }
        return obstacles;
    }

    // Returns list of all threats in current Game State
    public List<Threat> getThreats() {

        List<Threat> threats = new ArrayList<>();

        // Loop through all complications and add threats to list
        for (int i = 0; i < this.complications.size(); i++) {

            Complication complication = this.complications.get(i);

            // Check if pit
            if (complication.isThreat()) {
                threats.add((Threat) complication);
            }
        }
        return threats;
    }

    public boolean tick(int speedCoeff, double t, int ch) {

        for (Complication c : complications) {
            c.setX(c.getX() - speedCoeff);
        }

        if (this.containsMissile()) {
            m.tick();
        }
        
        if((int)(t*100)%200 == 0 && t > 1)
        {
            boolean shiftedImage = this.update(ch);
            return shiftedImage;
        }

        return false;
    }

    public int getMissileX() {
        return m.getX();
    }

    public int getMissileY() {
        return m.getY();
    }

    //update every some odd seconds, grabbing location of the current character on the screen
    public boolean update(int x) {

        // Add new complication 
        Random rand = new Random();
        ArrayList state = (ArrayList) this.getComplications();
        int compChoice = rand.nextInt(3); // produces a random number to spawn a certain obstacle

        if(compChoice == 1 && pastChoice == 1)
            compChoice = 2;
        
        switch (compChoice) {
            //TODO: set y to respective y-coords (image)
            case 0: //produces an obstacle
                Obstacle o = new Obstacle(x + 800, 329);//call obstacle class to instantiate an obstacle
                state.add(o);
                break;
            case 1: //produces a pit
                Pit p = new Pit(x + 800, 450);//call put class to instatitate a pit
                state.add(p);
                break;
            case 2: //produces a threat
                Threat t = new Threat(x + 800, 300);//call threat class to instatitate a threat
                state.add(t);
                break;
            default: 
                break;
        }
        this.setNewComplications(state);//add the new arrayList to gameState
        
        pastChoice = compChoice;
        
        // Then delete first element of Array List if too long to improve runtime 
        if(complications.size() > 5) {
            complications.remove(0);
            return true;
        }

        return false;
    }
    
    public void removeMissile() {
        m = null;
    }
    
    public void removeComplication(int i) {
        
        complications.remove(i);
    }
}
