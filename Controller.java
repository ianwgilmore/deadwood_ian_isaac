//potentially completed
import java.util.ArrayList;
import java.util.HashMap;

public class Controller{
    ViewTerminal view = new ViewTerminal();

    public int setup(){

        int playerNum = this.view.getPlayerNum();
        return playerNum;
    }


    public String move(ArrayList<String> neighbors){
        String targetLoc = this.view.getTargetLoc(neighbors);
        return targetLoc;
    }

    public int[] rankUp(int[] balance){
        //array [type, target]
        //where type is 0 or 1 (dollars or credits)
        //and target is int for desired rank
        int[] rankInfo = this.view.getRankInfo(balance);
        return rankInfo;
    }

    public String takeTurn(String name, String location){
        String action = this.view.getPlayerAction(name, location);
        return action;
    }

    //prompt user for whether star or 
    public String typeRole(){
        String action = this.view.getTypeRole();
        return action;
    }

    public String starRole(HashMap<String, Role> roles){
        String action = this.view.getRole(roles);
        return action;
    }

    public String extraRole(HashMap<String, Role> roles){
        String action = this.view.getRole(roles);
        return action;
    }

    //public void endTurn(){}

    public void error(){
        this.view.sendErrorMessage();
    }

    public void newDay(int day){
        this.view.sendNewDay(day);
    }
}