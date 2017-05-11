/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

/**
 *
 * @author Felix
 */
public class HighScores {

    public static class PlayerScore {

        private int hid;
        private String name;
        private double score;

        public PlayerScore(int hid, String name, double score) {
            this.hid = hid;
            this.name = name;
            this.score = score;
        }

        public PlayerScore(String name, double score) {
            this.hid = lastScoreId;
            this.name = name;
            this.score = score;
        }

        public int getHid() {
            return this.hid;
        }

        public double getScore() {
            return this.score;
        }

        public String getName() {
            return this.name;
        }
    }

    public static int lastScoreId = 0;
    private List<PlayerScore> highScores;

    public HighScores() {
        highScores = new ArrayList<>();

        //TODO: call netClientGet to fill list of highscores
    }

    //TODO: get the first limit number of scores in highScores list
    public List<PlayerScore> getTopScores(int limit) {
        List<PlayerScore> scores = null;
        
        try {
            scores = NetClientGet.getHighScores();
            
            if(limit > 0) {
                scores = scores.subList(0, limit);
            }
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(HighScores.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(HighScores.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return scores;
    }

    public void insertHighScore(PlayerScore playerScore) {
        try {
            //TODO: call netClientGet to insert the highscore
            NetClientGet.insertHighScore(playerScore);
            lastScoreId++;
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(HighScores.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(HighScores.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
