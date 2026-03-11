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
    
    public void error();
    public void newDay(int day);
    public void displayResult(String[] results);
    public void showRoll(int roll);
    public void showTokens(int tokens);
    public void displayWrap();
    public void displayResults(String[] results);
}