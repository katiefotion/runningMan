/*
 * NOTE: Sprite image from https://cdn.codeandweb.com/blog/2016/05/10/how-to-create-a-sprite-sheet
 *
 */
package Game;

import Database.NetClientGet;
import GameGUI.GameApp;
import java.util.logging.Level;
import java.util.logging.Logger;
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
        
      //just testing that rest server works (this should not break.
      //if it does, make sure your database is setup properly
      /*try {
        System.out.println(NetClientGet.getHighScores());
      } catch (ParserConfigurationException ex) {
        Logger.getLogger(RunningMan.class.getName()).log(Level.SEVERE, null, ex);
      } catch (SAXException ex) {
        Logger.getLogger(RunningMan.class.getName()).log(Level.SEVERE, null, ex);
      }        */
      
      GameApp.initGameApp(args);
    }
}
