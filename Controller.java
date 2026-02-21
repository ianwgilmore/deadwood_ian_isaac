
import java.util.ArrayList;

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

    public String takeTurn(){
        String action = this.view.getPlayerAction();
        return action;
    }

    //public void endTurn(){}

    public void error(){
        this.view.sendErrorMessage();
    }
}