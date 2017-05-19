/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameObjects;

import java.awt.Graphics;

/**
 *
 * @author jeffreyhu
 */

//missle speed is set to 15
public class Missile extends GameObject {
    public Missile(int x, int y) {
        super(x, y);
        this.setVelX(15);
    }
//missle goes to the right by 15 pixels
    @Override
    public void tick() {
        this.setX(this.getX() + this.getVelX());
    }

    @Override
    public void render(Graphics g) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
