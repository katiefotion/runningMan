/*
 * Sprite class that describes behavior of 
 * characters and complications in GUI 
 *
 */
package GameGUI;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 *
 * @author katie
 */
public class Sprite {
    
    private int x, y;
    private Image image;
    
    public Sprite() {
    }
    
    public Sprite(int x) {
        
        this.x = x;
    }
    
    public Sprite(int x, Image i) {
        
        this.x = x;
        this.image = i;
    }
    
    public void setX(int x) {
        
        this.x = x;
    }
    
    public void setY(int y) {
        
        this.y = y;
    }
    
    public void setImage(Image i) {
        
        this.image = i;
    }
    
    public void drawSprite(GraphicsContext gc) {
        gc.drawImage(this.image, this.x, this.y);
    }
}
