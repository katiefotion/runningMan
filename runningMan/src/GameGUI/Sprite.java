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
    
    //empty constructor
    public Sprite() {
    }
    
    //constructor with x coordinate
    public Sprite(int x) {
        
        this.x = x;
    }
    
    //constructor with image
    public Sprite(Image i) {
        
        this.image = i;
    }
    
    //constructor with x coordinate and image
    public Sprite(int x, Image i) {
        
        this.x = x;
        this.image = i;
    }
    
    //constructor with x and y coordinate and image
    public Sprite(int x, int y, Image i) {
        
        this.x = x;
        this.y = y;
        this.image = i;
    }
    
    //set x coordinate
    public void setX(int x) {
        
        this.x = x;
    }
    
    //set y coordinate
    public void setY(int y) {
        
        this.y = y;
    }
    
    //set image
    public void setImage(Image i) {
        
        this.image = i;
    }
    
    //draw image onto graphics with image and x and y coordinates
    public void drawSprite(GraphicsContext gc) {
        
        gc.drawImage(this.image, this.x, this.y);
    }
}
