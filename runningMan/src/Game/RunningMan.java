/*
 * NOTE: Sprite image from https://cdn.codeandweb.com/blog/2016/05/10/how-to-create-a-sprite-sheet
 *
 */
package Game;

import Controller.Controller;
import Database.NetClientGet;
import GameGUI.GameGUI;
import GameGUI.ImagePanel;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

/**
 *
 * @author katie
 */
public class RunningMan {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        
        GameGUI.initGameGUI(args);
        
        
        
        //Code below generates 404
      /*try {
        System.out.println(NetClientGet.getHighScores());
      } catch (ParserConfigurationException ex) {
        Logger.getLogger(RunningMan.class.getName()).log(Level.SEVERE, null, ex);
      } catch (SAXException ex) {
        Logger.getLogger(RunningMan.class.getName()).log(Level.SEVERE, null, ex);
      }*/
    }
    
}
