/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameGUI;

import java.awt.*;           
import java.awt.image.BufferedImage;
import javax.swing.*;

/**
 *
 * @author katie
 */

public class Sprite extends JComponent {
    private Image image;
    private int posX; 
    private int posY;
    
    public Sprite(BufferedImage image, int x, int y) {
        this.image = image;
        this.posX = x;
        this.posY = y;
    }
    
    public void setSpriteX(int x) {
        this.posX = x;
    }
    
    public void setSpriteY(int y) {
        this.posY = y;
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        doDrawing(g);

        Toolkit.getDefaultToolkit().sync();
    }
    
    private void doDrawing(Graphics g) {
        g.drawImage(image, posX, posY, this);
    }
    
}
