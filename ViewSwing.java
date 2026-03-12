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
    ArrayList<JButton> actionButtons = new ArrayList<JButton>();
    JLabel turnLabel;
    JFrame frame;
    HashMap<String, int[]> board_spots = new HashMap<String, int[]>();
    HashMap<String, JLabel> playerLabels = new HashMap<String, JLabel>();
    String current_player;
    JLayeredPane pane;

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

    private void buildBoardSpots() {
        board_spots = new HashMap<String, int[]>();
        int[][] positions = {
            {772, 329},
            {914, 91},
            {1085, 349},
            {790, 545},
            {769, 788},
            {1086, 671},
            {400, 570},
            {105, 567},
            {285, 815},
            {98, 246},
            {425, 107},
            {393, 354},
        };

        board_spots.put("Saloon", positions[0]);
        board_spots.put("Main Street", positions[1]);
        board_spots.put("trailer", positions[2]);
        board_spots.put("Bank", positions[3]);
        board_spots.put("Church", positions[4]);
        board_spots.put("Hotel", positions[5]);
        board_spots.put("Ranch", positions[6]);
        board_spots.put("office", positions[7]);
        board_spots.put("Secret Hideout", positions[8]);
        board_spots.put("Train Station", positions[9]);
        board_spots.put("Jail", positions[10]);
        board_spots.put("General Store", positions[11]);
    }

    public int getPlayerNum(){
        buildBoardSpots();
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
    
    private void updateButtons(ArrayList<String> buttonTexts) {
        double boardScale = 1;

        // Remove previous buttons
        for (int i = 0; i < this.actionButtons.size(); i++) {
            frame.remove(this.actionButtons.get(i));
        }

        this.actionButtons = new ArrayList<JButton>();

        // Add action buttons
        this.buttonListeners = new ButtonClickListener[buttonTexts.size()];
        for (int i = 0; i < buttonTexts.size(); i++) {
            JButton button = new JButton(buttonTexts.get(i));
            button.setBounds((int) (1200 * boardScale), i * 50 + 50, 150, 50);
            this.actionButtons.add(button);
            frame.add(button);

            ButtonClickListener buttonListener = new ButtonClickListener(buttonTexts.get(i));
            button.addActionListener(buttonListener);
            this.buttonListeners[i] = buttonListener;
        }

        frame.repaint();
    }

    // this is a comment
    public void startWindow() {
        // Make window
        this.frame = new JFrame("Deadwood");
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setSize(1200, 800);
        this.frame.setLayout(null);

        // Get pane from frame for layering
        this.pane = this.frame.getLayeredPane();

        // Add board image
        ImageIcon boardIcon = new ImageIcon("./images/board.jpg");
        double boardScale = 1; // Used by board and other elements to scale accordingly
        boardIcon = scaleByFactor(boardIcon, boardScale);
        JLabel boardLabel = new JLabel();
        boardLabel.setIcon(boardIcon);
        boardLabel.setBounds(0, 0, boardIcon.getIconWidth(), boardIcon.getIconHeight());
        this.pane.add(boardLabel, new Integer(0));

        // Add Turn Label
        this.turnLabel = new JLabel();
        this.turnLabel.setBounds((int) (1207 * boardScale), 0, 900, 50);
        this.frame.add(this.turnLabel);

        // Add move button
        String[] tests = {"move", "act", "rehearse", "rank up", "take role", "end turn"};

        ArrayList<String> buttonTexts = new ArrayList<String>();
        for (int i = 0; i < tests.length; i++) {
            buttonTexts.add(tests[i]);
        }

        updateButtons(buttonTexts);

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
        JOptionPane.showMessageDialog(null, "Current action failed.");
    }

    //prompt user for action choice
    //choices are act, rehearse, move, rank up, etc.
    public String getPlayerAction(String name, ArrayList<String> actions){
        this.turnLabel.setText(name + "'s turn. \nChoose an action. (act, rehearse, move, take role, rank up)");
        updateButtons(actions);

        // Update who the current player is by name
        this.current_player = name;
        //String action = this.scanner.nextLine();

        // If player name unrecognized, create an icon for it
        if (!playerLabels.containsKey(name)) {
            ImageIcon playerIcon = new ImageIcon("./images/r2.png");
            JLabel playerLabel = new JLabel();
            playerLabel.setIcon(playerIcon);
            playerLabel.setBounds(1180, 100, 100, 100);
            pane.add(playerLabel, new Integer(3));
            playerLabels.put(name, playerLabel);
        }

        pane.repaint();
        
        // Clear button clicks so there is no pre-input
        for (int i = 0; i < this.buttonListeners.length; i++) {
            this.buttonListeners[i].getClicked();
        }

        System.out.println(this.buttonListeners);

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

        JLabel playerLabel = playerLabels.get(this.current_player);

        int[] position = board_spots.get(target);
        int x = position[0];
        int y = position[1];

        playerLabel.setBounds(x, y, playerLabel.getIcon().getIconWidth(), playerLabel.getIcon().getIconHeight());

        frame.repaint();

        return target;
    }


    public String getRole(ArrayList<String> roles){
        // System.out.println("Choose a role to take\n" + roles.keySet());
        // String role = this.scanner.nextLine();
        String sroles = " ";
        String s;
        for (int i =0; i<roles.size();i++){
            s =roles.get(i);
            sroles+=s+=" ";
        }
        System.out.println(sroles);
        System.out.println(roles.size());
        String role = JOptionPane.showInputDialog("Choose a role to take\n" + sroles);

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