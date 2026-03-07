import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import javax.swing.*;

// Change to use swing and not terminal
public class ViewSwing{
    Scanner scanner = new Scanner(System.in);
    ButtonClickListener[] buttonListeners;

    private static class ButtonClickListener implements ActionListener {
        boolean wasClicked = false;
        String actionText;

        public ButtonClickListener(String text) {
            this.actionText = text;
        }

        public boolean getClicked() {
            if (this.wasClicked) {
                this.wasClicked = false;
                return true;
            }

            return false;
        }

        // Every listener uses 'actionPerformed' method. 
        // And yes, '@Override' is necessary and the code refuses
        // to compile without it.
        @Override
        public void actionPerformed(ActionEvent event) { // Argument name 'event' can be safely changed, for example, to 'e'
            this.wasClicked = true;
        }
    }

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
    
    public void startWindow() {
        // Make window
        JFrame frame = new JFrame("Deadwood");
        frame.setSize(1200, 800);
        frame.setLayout(null);

        // Add board image
        ImageIcon boardIcon = new ImageIcon("./images/board.jpg");
        boardIcon = scaleByFactor(boardIcon, 0.75);
        JLabel boardLabel = new JLabel();
        boardLabel.setIcon(boardIcon);
        boardLabel.setBounds(0, 0, boardIcon.getIconWidth(), boardIcon.getIconHeight());
        frame.add(boardLabel);

        // Add move button
        String[] buttonTexts = {"move", "act", "rehearse", "rank up", "take role"};
        this.buttonListeners = new ButtonClickListener[buttonTexts.length];
        for (int i = 0; i < buttonTexts.length; i++) {
            JButton button = new JButton(buttonTexts[i]);
            button.setBounds(900, i * 50, 150, 50);
            frame.add(button);

            ButtonClickListener buttonListener = new ButtonClickListener(buttonTexts[i]);
            button.addActionListener(buttonListener);
            this.buttonListeners[i] = buttonListener;
        }

        // Set window visible at end
        // (Doing this before adding images causes them to not show up, unsure why)
        frame.setVisible(true);
    }

    private static ImageIcon scaleByFactor(ImageIcon boardIcon, double factor) {
        if (factor == 1) {
            return boardIcon;
        }

        // Scale image by given factor
        double width = boardIcon.getIconWidth() * factor;
        double height = boardIcon.getIconHeight() * factor;
        Image scaledImage = boardIcon.getImage().getScaledInstance((int) width, (int) height, Image.SCALE_SMOOTH);
        boardIcon = new ImageIcon(scaledImage);
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
        //String action = this.scanner.nextLine();
        
        // Clear button clicks so there is no pre-input
        for (int i = 0; i < this.buttonListeners.length; i++) {
            this.buttonListeners[i].getClicked();
        }

        // Keep checking the action buttons until one is clicked
        String action = null;
        while (action == null) {
            for (int i = 0; i < this.buttonListeners.length; i++) {
                if (this.buttonListeners[i].getClicked()) {
                    action = this.buttonListeners[i].actionText;
                }
            }
        }

        System.out.println(action);
        
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