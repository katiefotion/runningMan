/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game;

import Database.NetClientGet;
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
        
        // TODO: Find a way to make playerId different every time
        int playerId = 1;
        Player player = new Player(playerId);
        
        // TODO: Find a way to calculate center of screen (or wherever player should begin on screen)
        //Game game = new Game(player, 400, 400);
        //Window w = new Window(600,400,"Running Man", game);
        // Randomly generate set of complications for first game state
        
        // Take player's input to move character
        
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
