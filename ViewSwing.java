import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import javax.swing.*;

// Change to use swing and not terminal
public class ViewSwing{
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
    
    public static void startWindow() {
        // Make window
        JFrame frame = new JFrame("Deadwood");
        frame.setSize(800, 600);
        frame.setLayout(null);

        // Add board image
        ImageIcon boardIcon = new ImageIcon("./images/board.jpg");
        JLabel boardLabel = new JLabel();
        boardIcon = scaleByFactor(boardIcon, 0.75);
        boardLabel.setIcon(boardIcon);
        boardLabel.setBounds(0, 0, boardIcon.getIconWidth(), boardIcon.getIconHeight());
        frame.add(boardLabel);

        // Add button (to be done)

        // Set window visible at end
        // (Doing this before adding images causes them to not show up, unsure why)
        frame.setVisible(true);
    }

    private static ImageIcon scaleByFactor(ImageIcon boardIcon, double factor) {
        if (factor != 1) {
            // Scale image to given size
            double width = boardIcon.getIconWidth() * factor;
            double height = boardIcon.getIconHeight() * factor;
            Image b = boardIcon.getImage();
            Image f = b.getScaledInstance((int) width, (int) height, Image.SCALE_SMOOTH);
            boardIcon = new ImageIcon(f);
            return boardIcon;
        }

        return boardIcon;
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
        System.out.println("Current Balance: dollars " + balance[0] + " credits " + balance[1]);
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

    public void sendNewDay(int day){
        System.out.println("Start of day " + day);
    }

    public void displayResults(String[] results){
        System.out.println("The results are: ");
        for (int i=0; i<results.length; i++){
            System.out.println(i + " " +  results[i]);
        }
    }

    public void showRoll(int roll){
        System.out.println("Rolled a " + roll);
    }

    public void showTokens(int tokens){
        System.out.println(tokens + " Shot Tokens Left");
    }
    public void displayWrap(){
        System.out.println("Scene has wrapped");
    }
}