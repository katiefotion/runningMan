/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameObjects;

import Game.GameState;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author darrylraveche
 */
public class Complication extends GameObject{
    
    static Random rand = new Random();
    private static int compChoice;
    
    public Complication()
    {
        //empty constructor class
    }
    public Complication(GameState g)
    {
        initComplication(g);
    }
    
    public Complication(int x, int y) {
        super(x,y);
    }
    
    @Override
    public void tick() {
        // As of right now, the ticking is happening in GameGUI
    }

    @Override
    public void render(Graphics g) {
        
        switch (compChoice)
        {
            case 0: //draws an obstacle, for now it's a square with len 10
                g.setColor(Color.red);
                g.fillRect(getX(),getY(),10,10);
                break;
            case 1: //draws a pit,,for now, it's a square with len 10, and below X.
                g.setColor(Color.blue);
                g.fillRect(getX()-100,getY(),10,10);
                break;
            case 2: //draws a threat, for now it's a circle with rad 4
                g.setColor(Color.blue);
                g.fillOval(getX()-4, getY()-4, 8, 8);
                break;
            default: 
                //do nothing, continue on.
                break;
        }
    }
    
    public static void initComplication(GameState g)//used to initialize a complication at the start of the game
    {
        ArrayList<Complication> state =  new ArrayList();
        compChoice = rand.nextInt(3); // produces a random number to spawn a certain obstacle
        //System.out.println("CompChoice: " + compChoice);
        switch (compChoice)
        {
            case 0: //produces an obstacle
                Obstacle o = new Obstacle(900,329);//call obstacle class to instantiate an obstacle
                state.add(o);
                break;
            case 1: //produces a pit
                Pit p = new Pit(900,445);//call put class to instatitate a pit
                state.add(p);
                break;
            case 2: //produces a threat
                Threat t = new Threat(900,300);//call threat class to instatitate a threat
                state.add(t);
                break;
            default: 
                System.out.println("Error here.");
                //do nothing, continue on.
                break;
        }
        g.setNewComplications(state);//add the new arrayList to gameState
        
        
    }
    // Overriden by subclasses where appropriate
    public boolean isPit() {return false;}
    public boolean isObstacle() {return false;}
    public boolean isThreat() {return false;}
}
