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
    JLabel turnLabel;

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
        int playerNum = Integer.valueOf(JOptionPane.showInputDialog("Enter the Number of Players (2-8)"));
        //System.out.println("Enter the Number of Players (2-8)");
        //int playerNum = this.scanner.nextInt();
        //eat rest of line
        //String badfix = this.scanner.nextLine();
        if (playerNum<2||playerNum>8){
            System.out.println("invalid number of players chosen");
            playerNum = getPlayerNum();
        }
        return playerNum;
    }
    
    private void updateButtons(String[] buttonTexts, double boardScale, JFrame frame) {
        // Add move button
        this.buttonListeners = new ButtonClickListener[buttonTexts.length];
        for (int i = 0; i < buttonTexts.length; i++) {
            JButton button = new JButton(buttonTexts[i]);
            button.setBounds((int) (1200 * boardScale), i * 50 + 50, 150, 50);
            frame.add(button);

            ButtonClickListener buttonListener = new ButtonClickListener(buttonTexts[i]);
            button.addActionListener(buttonListener);
            this.buttonListeners[i] = buttonListener;
        }

        // Set window visible at end
        // (Doing this before adding images causes them to not show up, unsure why)
        frame.setVisible(true);
    }

    // this is a comment
    public void startWindow() {
        // Make window
        JFrame frame = new JFrame("Deadwood");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 800);
        frame.setLayout(null);

        // Add board image
        ImageIcon boardIcon = new ImageIcon("./images/board.jpg");
        double boardScale = 1; // Used by board and other elements to scale accordingly
        boardIcon = scaleByFactor(boardIcon, boardScale);
        JLabel boardLabel = new JLabel();
        boardLabel.setIcon(boardIcon);
        boardLabel.setBounds(0, 0, boardIcon.getIconWidth(), boardIcon.getIconHeight());
        frame.add(boardLabel);

        // Add Turn Label
        this.turnLabel = new JLabel();
        this.turnLabel.setBounds((int) (1207 * boardScale), 0, 900, 50);
        frame.add(this.turnLabel);

        // Add move button
        String[] buttonTexts = {"move", "act", "rehearse", "rank up", "take role", "end turn"};
        updateButtons(buttonTexts, boardScale, frame);

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
    // src: https://mkyong.com/swing/java-swing-how-to-make-a-simple-dialog/
    public void sendErrorMessage(){
        JOptionPane.showMessageDialog(null, "Current action failed.");
    }

    //prompt user for action choice
    //choices are act, rehearse, move, rank up, etc.
    public String getPlayerAction(String name){
        this.turnLabel.setText(name + "'s turn. \nChoose an action. (act, rehearse, move, take role, rank up)");
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
        //System.out.println("Enter your desired rank (2-6)");
        //int rank = this.scanner.nextInt();
        int rank = Integer.valueOf(JOptionPane.showInputDialog("Enter your desired rank (2-6)"));
        //String badsolution = this.scanner.nextLine();
        //System.out.println("Select your used currency\nCurrent Balance: dollars " + balance[0] + " credits " + balance[1]);
        //String type = this.scanner.nextLine();
        String type = JOptionPane.showInputDialog("Select your used currency\nCurrent Balance: dollars " + balance[0] + " credits " + balance[1]);
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
        // System.out.println("Choose a location to move to\n" + neighbors);
        // String target = this.scanner.nextLine();
        String target = JOptionPane.showInputDialog("Choose a location to move to\n" + neighbors);
        return target;
    }


    public String getRole(HashMap<String, Role> roles){
        // System.out.println("Choose a role to take\n" + roles.keySet());
        // String role = this.scanner.nextLine();
        String role = JOptionPane.showInputDialog("Choose a role to take\n" + roles.keySet());
        return role;
    }

    public String getTypeRole(){
        // System.out.println("Choose a type of role(star or extra)");
        // String type = this.scanner.nextLine();
        String type = JOptionPane.showInputDialog("Choose a type of role(star or extra)");
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