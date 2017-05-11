package Database;

import Database.HighScores.PlayerScore;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Felix
 */
public class NetClientGet {
  
  public static int lastHighScoreId = 0;

    public static List<PlayerScore> getHighScores()
            throws ParserConfigurationException, SAXException {
        List<PlayerScore> highScores = new ArrayList<>();

        try {
            /*
             GET
             */
            URL urlHighScores = new URL("http://localhost:8080/RunningManServer/webresources/com.entity.highscoresentity.highscores");
            HttpURLConnection getConnHighScores = (HttpURLConnection) urlHighScores.openConnection();
            getConnHighScores.setRequestMethod("GET");
            getConnHighScores.setRequestProperty("Accept", "application/xml");

            if (getConnHighScores.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + getConnHighScores.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (getConnHighScores.getInputStream())));

            String output, msg = "";
            while ((output = br.readLine()) != null) {
                msg += output;
            }

            /*
             Following are 2 alternatives for accessing values in XML tree nodes
             */
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.parse(new ByteArrayInputStream(msg.getBytes()));

            int hid;
            double score;
            String playerName;

            /*
             getElementsByTagName is guaranteed to retrieve nodes in the XML tree order
             so the following 3 NodeLists are in sync
             */
            NodeList hids = doc.getElementsByTagName("hid");
            NodeList playerNames = doc.getElementsByTagName("playername");
            NodeList scores = doc.getElementsByTagName("score");
            for (int i = 0; i < hids.getLength(); i++) {
                hid = Integer.parseInt(hids.item(i).getTextContent());
                playerName = playerNames.item(i).getTextContent();
                score = Double.parseDouble(scores.item(i).getTextContent());
                highScores.add(new PlayerScore(hid, playerName, score));
            }

            getConnHighScores.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return highScores;
    }

    public static void insertHighScore(PlayerScore playerScore)
            throws ParserConfigurationException, SAXException {
        
        try {
            /*
             POST
             */
            URL urlSale = new URL("http://localhost:8080/RunningManServer/webresources/com.entity.highscoresentity.highscores");

            HttpURLConnection postConnHighScore = (HttpURLConnection) urlSale.openConnection();

            postConnHighScore.setDoOutput(true);
            postConnHighScore.setRequestMethod("POST");
            postConnHighScore.setRequestProperty("Content-Type", "application/xml");

            String playerName = playerScore.getName();
            double score = playerScore.getScore();
            int highScoreId = playerScore.getHid();
            
            String newHighScoreString
                    = "     <highscores> \n"
                    + "          <hid>" + highScoreId + "</hid> \n"
                    + "          <playername>" + playerName + "</playername> \n"
                    + "          <score>" + score + "</score> \n"
                    + "     </highscores> ";
            
            OutputStream postOutputStream = postConnHighScore.getOutputStream();
            postOutputStream.write(newHighScoreString.getBytes());
            postOutputStream.flush();

            if (postConnHighScore.getResponseCode() >= 400) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + postConnHighScore.getResponseCode());
            }

            postConnHighScore.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
