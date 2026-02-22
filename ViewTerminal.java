import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;

//need to implement the view
public class ViewTerminal{
    public int getPlayerNum(){
        //Scanner scanner = new Scanner();
        //System.out.println("Enter the Number of Players");
        return 2;
    }

    //print error message to user to signify some failure
    public void sendErrorMessage(){

    }

    //prompt user for action choice
    //choices are act, rehearse, move, rank up, etc.
    public String getPlayerAction(){
        return "act";
    }

    //prompt player for desired rank and payment type
    //balance [dol, credit]
    //returns [typepayment, desired rank]
    public int[] getRankInfo(int[] balance){
        int[] rankinfo = {1,0};
        return rankinfo;
    }

    //prompt player for valid move locations 
    public String getTargetLoc(ArrayList<String> neighbors){
        String location = "set";
        return location;
    }


    public String getRole(HashMap<String, Role> roles){
        String location = "baker";
        return location;
    }

    public String getTypeRole(){
        String location = "credit";
        return location;
    }
}