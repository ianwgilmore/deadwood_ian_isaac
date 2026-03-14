//potentially completed
import java.util.ArrayList;
import java.util.HashMap;

public interface Controller{
    public int setup();
    public String move(ArrayList<String> neighbors);
    public int[] rankUp(int[] balance);
    public String takeTurn(ArrayList<String> actions,String name);

    //prompt user for whether star or 
    public String typeRole();
    public String starRole(ArrayList<String> roles);
    public String extraRole(ArrayList<String> roles);
    public String getName(int i);
    
    public void error();
    public void newDay(int day);
    public void displayResult(String[] results);
    public void flipCard(String cardName);
    public void showRoll(int roll);
    public void showTokens(int tokens, String actset);
    public void displayWrap();
    public void displayResults(Player[] players);
    public void addScoreBoard(Player player);
    public void updateScore(String name, int dol, int cred, int rank);
    public void removeCard(String cardName);
}