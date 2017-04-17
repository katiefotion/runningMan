/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Flex
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
  
  private List<PlayerScore> highScores;
  
  public HighScores() {
    highScores = new ArrayList<>();
    
    //TODO: call netClientGet to fill list of highscores
  }
  
  //TODO: get the first limit number of scores in highScores list
  public List<PlayerScore> getTopScores(int limit) {
    return new ArrayList<PlayerScore>();
  }
  
  public void insertHighScore(PlayerScore playerScore) {
    //TODO: call netClientGet to insert the highscore
  }
}
