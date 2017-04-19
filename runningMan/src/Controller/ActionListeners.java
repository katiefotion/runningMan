/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Game.Game;
import GameGUI.GameGUI;
import GameObjects.Character;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 *
 * @author katie
 */
public class ActionListeners implements MouseListener {

    private Character character;
    private GameGUI gui;
    
    public ActionListeners(Game g, GameGUI gameGui) {
        character = g.getCharacter();
        gui = gameGui;
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        character.jump();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        
    }
    
    
}
