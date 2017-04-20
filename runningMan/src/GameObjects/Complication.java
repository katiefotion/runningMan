/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameObjects;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

/**
 *
 * @author darrylraveche
 */
public class Complication extends GameObject{
    
    Random rand = new Random();
    private int compChoice;
    
    public Complication()
    {
        initComplication();
    }
    
    public Complication(int x, int y) {
        super(x,y);
    }
    
    @Override
    public void tick() {
        // As of right now, the ticking is happening in GameGUI
    }
    //update every some odd seconds, grabbing location of the current character on the screen
    public void update(int x, int y) {
        
        compChoice = rand.nextInt(3); // produces a random number to spawn a certain obstacle
        
        switch (compChoice)
        {
            case 0: //produces an obstacle
                new Obstacle(x,y);//call obstacle class to instantiate an obstacle
                break;
            case 1: //produces a pit
                new Pit(x,y);//call put class to instatitate a pit
                break;
            case 2: //produces a threat
                new Threat(x,y);//call threat class to instatitate a threat
                break;
            default: 
                //do nothing, continue on.
                break;
        }
        
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
    
    private void initComplication()//used to initialize a complication at the start of the game
    {
        
        compChoice = rand.nextInt(3); // produces a random number to spawn a certain obstacle
        
        switch (compChoice)
        {
            case 0: //produces an obstacle
                new Obstacle(400,0);//call obstacle class to instantiate an obstacle
                break;
            case 1: //produces a pit
                new Pit(400,0);//call put class to instatitate a pit
                break;
            case 2: //produces a threat
                new Threat(400,0);//call threat class to instatitate a threat
                break;
            default: 
                //do nothing, continue on.
                break;
        }
        
    }
    // Overriden by subclasses where appropriate
    public boolean isPit() {return false;}
    public boolean isObstacle() {return false;}
    public boolean isThreat() {return false;}
}
