/*
 * NOTE: Sprite image from https://cdn.codeandweb.com/blog/2016/05/10/how-to-create-a-sprite-sheet
 *
 */
package Game;

import Database.HighScores;
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
        //starts game
        GameApp.initGameApp(args);
    }
}
