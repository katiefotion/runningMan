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

public class ImagePanel extends JComponent {
    private Image image;
    
    public ImagePanel(Image image) {
        this.image = image;
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, this);
    }
}
