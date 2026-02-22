import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;

//need to implement the view
public class ViewTerminal{
    Scanner scanner = new Scanner(System.in);

    public int getPlayerNum(){
        System.out.println("Enter the Number of Players (2-8)");
        int playerNum = this.scanner.nextInt();
        //eat rest of line
        String badfix = this.scanner.nextLine();
        if (playerNum<2||playerNum>8){
            System.out.println("invalid number of players chosen");
            playerNum = getPlayerNum();
        }
        return playerNum;
    }

    //print error message to user to signify some failure
    public void sendErrorMessage(){
        System.out.println("Current action failed.");
    }

    //prompt user for action choice
    //choices are act, rehearse, move, rank up, etc.
    public String getPlayerAction(String name, String location){
        System.out.println(name + "'s turn. You are at the " + location);
        System.out.println("Choose an action. (act, rehearse, move, take role, rank up)");
        String action = this.scanner.nextLine();
        return action;
    }

    //prompt player for desired rank and payment type
    //balance [dol, credit]
    //returns [typepayment, desired rank]
    public int[] getRankInfo(int[] balance){
        System.out.println("Enter your desired rank (2-6)");
        int rank = this.scanner.nextInt();
        String badsolution = this.scanner.nextLine();
        System.out.println("Select your used currency");
        //print 
        System.out.println("Current Balance: Dollars " + balance[0] + " Credits " + balance[1]);
        String type = this.scanner.nextLine();
        int intType;
        int[] rankInfo;
        if (type.equals("dollars")){
            intType = 0;
            rankInfo = new int[]{intType,rank};
        }
        else if (type.equals("credits")){
            intType = 1;
            rankInfo = new int[]{intType,rank};
        }
        else{
            sendErrorMessage();
            rankInfo = getRankInfo(balance);
        }
 
        return rankInfo;
    }

    //prompt player for valid move locations 
    public String getTargetLoc(ArrayList<String> neighbors){
        System.out.println("Choose a location to move to");
        System.out.println(neighbors);
        String target = this.scanner.nextLine();
        return target;
    }


    public String getRole(HashMap<String, Role> roles){
        System.out.println("Choose a role to take");
        System.out.println(roles.keySet());
        String role = this.scanner.nextLine();
        return role;
    }

    public String getTypeRole(){
        System.out.println("Choose a type of role(star or extra)");
        String type = this.scanner.nextLine();
        return type;
    }

    public void displayResults(String[] results){
        System.out.println("The placements are as follows: ");
        for (int i=0; i<results.length; i++){
            System.out.println(i+ ": " + results[i]);
        }
    }
}