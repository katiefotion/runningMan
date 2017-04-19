/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameGUI;

import Controller.ActionListeners;
import Game.Game;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
/**
 *
 * @author Tony
 */
public class Window extends Canvas{
    
    private JFrame frame;
    
    public Window(int w, int h, String title){
        frame = new JFrame(title);
        frame.setPreferredSize(new Dimension(w,h));
        frame.setMaximumSize(new Dimension(w,h));
        frame.setMinimumSize(new Dimension(w,h));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    public void setBackgroundImage(BufferedImage image) {
        frame.setContentPane(new ImagePanel(image));
        frame.setVisible(true);
    }
    
    public void updateSprite(Sprite sprite) {
        
        Graphics g = frame.getGraphics();
        sprite.paintComponent(g);
        this.setVisible(true);
    }
    
    public JFrame getFrame() {
        return frame;
    }
}
