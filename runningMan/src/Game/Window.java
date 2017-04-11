/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game;

import java.awt.*;
import javax.swing.*;
/**
 *
 * @author Tony
 */
public class Window extends Canvas{
    
    public Window(int w, int h, String title, Game g){
        JFrame frame = new JFrame(title);
        frame.setPreferredSize(new Dimension(w,h));
        frame.setMaximumSize(new Dimension(w,h));
        frame.setMinimumSize(new Dimension(w,h));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        
    }
}
