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
    HashMap<String, JLabel> setCard = new HashMap<String, JLabel>(); // setName, cardLabel -
    HashMap<String, String> cardPaths;
    HashMap<String, int[]> cardSpots; // setName, pos -
    HashMap<String, int[]> extraSpots; // partName, pos
    HashMap<String, int[]> shotTokens; //setName+num, pos || when removing need to be passed shottokens for indexing
    HashMap<String, JLabel> shotTokenLabels = new HashMap<>();
    HashMap<String, int[]> boardSpots = new HashMap<String, int[]>(); // setName, pos
    HashMap<String, JLabel> playerLabels = new HashMap<String, JLabel>(); // playerName, pos
    String currentSetName;
    String currentPlayerName;
    JLayeredPane pane;
    String pickedRoleType;
    Parser parser = new Parser();
    JPanel scoreboardPanel;
    HashMap<String, JLabel> scoreLabels = new HashMap<>();

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

    private void clearCards(String[] setNames) {
        for (int i = 0; i < setNames.length; i++) {
            JLabel cardLabel = setCard.get(setNames[i]);

            if (cardLabel != null) {
                pane.remove(cardLabel);
            }
        }
    }

    private void buildSetCard() {
        String[] setNames = {
            "Train Station",
            "Jail",
            "Main Street",
            "General Store",
            "Saloon",
            "Ranch",
            "Bank",
            "Secret Hideout",
            "Church",
            "Hotel"
        };

        clearCards(setNames);

        for (int i = 0; i < setNames.length; i++) {
            String setName = setNames[i];

            JLabel cardLabel = new JLabel();
 
            // Add img to card
            ImageIcon backImg = new ImageIcon("./images/Cardback.png");
            cardLabel.setIcon(backImg);

            // Set cardLabel position
            int[] position = this.cardSpots.get(setName);
            int x = position[0];
            int y = position[1];
            cardLabel.setBounds(x, y, backImg.getIconWidth(), backImg.getIconHeight());

            // Add label to pane
            this.pane.add(cardLabel, Integer.valueOf(2));

            // Put card under setName
            this.setCard.put(setName, cardLabel);
        }
    }

    private void buildCardSpots() {
        this.cardSpots = this.parser.buildCardSpots();
    }

    private void buildExtraSpots() {
        extraSpots = this.parser.buildExtraSpots();
    }

    private void buildShotTokens(){
        shotTokens = this.parser.buildShotTokens();
    }

    private void buildBoardSpots() {
        boardSpots = new HashMap<String, int[]>();
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

        boardSpots.put("Saloon", positions[0]);
        boardSpots.put("Main Street", positions[1]);
        boardSpots.put("trailer", positions[2]);
        boardSpots.put("Bank", positions[3]);
        boardSpots.put("Church", positions[4]);
        boardSpots.put("Hotel", positions[5]);
        boardSpots.put("Ranch", positions[6]);
        boardSpots.put("office", positions[7]);
        boardSpots.put("Secret Hideout", positions[8]);
        boardSpots.put("Train Station", positions[9]);
        boardSpots.put("Jail", positions[10]);
        boardSpots.put("General Store", positions[11]);
    }

    private void buildCardPaths() {
        this.cardPaths = this.parser.buildCardPaths();
    }

    public int getPlayerNum(){
        int playerNum = Integer.valueOf(JOptionPane.showInputDialog("Enter the Number of Players (2-8)"));
        //System.out.println("Enter the Number of Players (2-8)");
        //int playerNum = this.scanner.nextInt();
        //eat rest of line
        //String badfix = this.scanner.nextLine();
        
        if (playerNum<2||playerNum>8){
            JOptionPane.showMessageDialog(null, "invalid number of players chosen");
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

        buildBoardSpots();
        buildExtraSpots();
        buildCardSpots();
        buildShotTokens();
        buildCardPaths();

        // Add board image
        ImageIcon boardIcon = new ImageIcon("./images/board.jpg");
        double boardScale = 1; // Used by board and other elements to scale accordingly
        boardIcon = scaleByFactor(boardIcon, boardScale);
        JLabel boardLabel = new JLabel();
        boardLabel.setIcon(boardIcon);
        boardLabel.setBounds(0, 0, boardIcon.getIconWidth(), boardIcon.getIconHeight());
        this.pane.add(boardLabel, Integer.valueOf(0));

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

        setShots();

        scoreboardPanel = new JPanel();
        scoreboardPanel.setLayout(new BoxLayout(scoreboardPanel, BoxLayout.Y_AXIS));
        scoreboardPanel.setBorder(BorderFactory.createTitledBorder("Scoreboard"));

        int panelWidth = 250;
        int panelHeight = 300;
        scoreboardPanel.setBounds(1200, 400, panelWidth,panelHeight); 
        pane.add(scoreboardPanel, Integer.valueOf(2));
        updateButtons(buttonTexts);

        // Set window visible at end
        // (Doing this before adding images causes them to not show up, unsure why)
        frame.setVisible(true);
    }


    public void addPlayerToScoreboard(Player player) {
        System.out.println("adding " + player.getName()+" to scoreboard");
        String s = player.getName() +"\n"+ "Dol: " + player.getDollars()+ " Cred: " + player.getCredits() + " Rank: " + player.getRank(); 
        JLabel label = new JLabel(s);
        scoreLabels.put(player.getName(), label);
        scoreboardPanel.add(label);
        scoreboardPanel.revalidate();
        scoreboardPanel.repaint();
}

    public void updateScore(String name, int dol, int cred, int rank){
        JLabel label = scoreLabels.get(name);
        if (label != null) {
            label.setText(name +"\n"+ "Dol: " + dol+ " Cred: " + cred + " Rank: " + rank);
        }
    }


    private static ImageIcon scaleByFactor(ImageIcon boardIcon, double factor) {
        if (factor == 1) {
            return boardIcon;
        }

        // Scale image by given factor
        double width = boardIcon.getIconWidth() * factor;
        double height = boardIcon.getIconHeight() * factor;
        System.out.println(boardIcon.getIconWidth());
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
        this.turnLabel.setText(name + "'s turn. \nChoose an action.");
        updateButtons(actions);

        // Update who the current player is by name
        this.currentPlayerName = name;
        //String action = this.scanner.nextLine();

        // If player name unrecognized, create an icon for it
        if (!playerLabels.containsKey(name)) {
            ImageIcon playerIcon = new ImageIcon("./images/r2.png");
            JLabel playerLabel = new JLabel();
            playerLabel.setIcon(playerIcon);
            int[] pos = boardSpots.get("trailer");
            int x = pos[0];
            int y = pos[1];
            playerLabel.setBounds(x, y, 100, 100);
            pane.add(playerLabel, Integer.valueOf(3));
            playerLabels.put(name, playerLabel);
        }

        pane.repaint();

        // Get an action selected via button
        String action = getClickedButton();

        System.out.println(action);
        
        return action;
    }

    //prompt player for desired rank and payment type
    //balance [dol, credit]
    //returns [typepayment, desired rank]
    public int[] getRankInfo(int[] balance){
        //System.out.println("Enter your desired rank (2-6)");
        //int rank = this.scanner.nextInt();
        //int rank = Integer.valueOf(JOptionPane.showInputDialog("Enter your desired rank (2-6)"));
        this.turnLabel.setText("Choose a rank to update to");
        ArrayList<String> options = new ArrayList<>();
        options.add("2");
        options.add("3");
        options.add("4");
        options.add("5");
        options.add("6");
        updateButtons(options);

        pane.repaint();

        // Keep checking the action buttons until one is clicked
        Integer rank = Integer.valueOf(getClickedButton());

        //String badsolution = this.scanner.nextLine();
        //System.out.println("Select your used currency\nCurrent Balance: dollars " + balance[0] + " credits " + balance[1]);
        //String type = this.scanner.nextLine();
        //String type = JOptionPane.showInputDialog("Select your used currency\nCurrent Balance: dollars " + balance[0] + " credits " + balance[1]);
        this.turnLabel.setText("Choose a payment type\nCurrent Balance: dollars " + balance[0] + " credits " + balance[1]);
        ArrayList<String> opts = new ArrayList<>();
        opts.add("dollars");
        opts.add("credits");
        updateButtons(opts);

        pane.repaint();

        // Keep checking the action buttons until one is clicked
        String type = getClickedButton();

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
        //String target = JOptionPane.showInputDialog("Choose a location to move to\n" + neighbors);

        JLabel playerLabel = playerLabels.get(this.currentPlayerName);

        this.turnLabel.setText("Choose a location to move to");
        updateButtons(neighbors);

        pane.repaint();

        // Get which target is selected via buttons
        String target = getClickedButton();

        int[] position = boardSpots.get(target);
        int x = position[0];
        int y = position[1];

        playerLabel.setBounds(x, y, playerLabel.getIcon().getIconWidth(), playerLabel.getIcon().getIconHeight());

        frame.repaint();

        this.currentSetName = target;

        return target;
    }

    private String getClickedButton() {
        // Clear button clicks so there is no pre-input
        for (int i = 0; i < this.buttonListeners.length; i++) {
            this.buttonListeners[i].getClicked();
        }

        // Keep checking the action buttons until one is clicked
        String role = null;
        while (role == null) {
            for (int i = 0; i < this.buttonListeners.length; i++) {
                if (this.buttonListeners[i].getClicked()) {
                    role = this.buttonListeners[i].actionText;
                }
            }
        }

        return role;
    }

    public String getRole(ArrayList<String> roles){
        // System.out.println("Choose a role to take\n" + roles.keySet());
        // String role = this.scanner.nextLine();
        // String sroles = " ";
        // String s;
        // for (int i =0; i<roles.size();i++){
        //     s =roles.get(i);
        //     sroles+=s+=" ";
        // }
        // String role = JOptionPane.showInputDialog("Choose a role to take\n" + sroles);

        this.turnLabel.setText("Choose a role");
        updateButtons(roles);

        pane.repaint();
        
        // Get which button is clicked
        String role = getClickedButton();

        if (this.pickedRoleType.equals("extra")) {
            // Set player position via 'extraSpots' hashmap
            int[] pos = this.extraSpots.get(role);
            int x = pos[0];
            int y = pos[1];
            JLabel playerLabel = playerLabels.get(this.currentPlayerName);
            playerLabel.setBounds(x, y, playerLabel.getIcon().getIconWidth(), playerLabel.getIcon().getIconHeight());
        }

        return role;
    }

    public String getTypeRole(){
        // System.out.println("Choose a type of role(star or extra)");
        // String type = this.scanner.nextLine();
        //String type = JOptionPane.showInputDialog("Choose a type of role(star or extra)");
        this.turnLabel.setText("Choose a type of Role (star or extra)");
        ArrayList<String> options = new ArrayList<>();
        options.add("star");
        options.add("extra");
        updateButtons(options);

        pane.repaint();

        // Get which button is clicked
        String type = getClickedButton();

        this.pickedRoleType = type;

        return type;
    }

    public void updatePlayerLoc(){
        JLabel label;
        for(HashMap.Entry<String,JLabel> entry : this.playerLabels.entrySet()){
            label = entry.getValue();
            int[] position = boardSpots.get("trailer");
            int x = position[0];
            int y = position[1];
            label.setBounds(x, y, label.getIcon().getIconWidth(), label.getIcon().getIconHeight());
            frame.repaint();
        }  
    }

    public void sendNewDay(int day){
        System.out.println("Start of day " + day);
        setShots();
        buildSetCard();
        updatePlayerLoc();

        //need to reset the board and the players but not the scoreboard
    }

    public void displayResults(String[] results){
        System.out.println("The results are: ");
        for (int i=0; i<results.length; i++){
            System.out.println(i + " " +  results[i]);
        }
    }

    public void removeShot(int shots, String actset){
        //index into hashmap and get the location 
        String index = actset + (shots+1);
        JLabel shot = shotTokenLabels.get(index);
        pane.remove(shot);
        shotTokenLabels.remove(index);
        pane.repaint();
    }

    public void removeCard(String cardName){
    //index into hashmap and get the location 
        System.out.println("removed " + cardName + "!");

        ImageIcon image = new ImageIcon("./images/Cardback.png");

        JLabel cardLabel = this.setCard.get(cardName);

        cardLabel.setBounds(cardLabel.getX(),cardLabel.getY(),image.getIconWidth(),image.getIconHeight()
        );

        System.out.println("removing card");
        cardLabel.setIcon(image);

        pane.repaint();
    }

    public void setShots(){
        //ensure tokens are empty for next day
        for (JLabel shot : shotTokenLabels.values()) {
            pane.remove(shot);
        }
        shotTokenLabels.clear();

        ImageIcon image = new ImageIcon("./images/shot.png");
        int[] loc;
        for (HashMap.Entry<String, int[]> token : this.shotTokens.entrySet()) {
            loc = token.getValue();
            JLabel shotLabel = new JLabel();
            shotLabel.setIcon(image);
            shotLabel.setBounds(loc[0], loc[1], image.getIconWidth(), image.getIconHeight());
            pane.add(shotLabel, Integer.valueOf(2));
            shotTokenLabels.put(token.getKey(), shotLabel);
        }
        pane.repaint();
    }

    public void flipCard(String cardName) {
        System.out.println("Flipped card to reveal " + cardName + "!");

        String cardPath = this.cardPaths.get(cardName);

        System.out.println(this.cardPaths.get(cardName));

        ImageIcon image = new ImageIcon(cardPath);
        //image = scaleByFactor(image, 0.6);

        System.out.println("flipping card");
        this.setCard.get(this.currentSetName).setIcon(image);
    }

    public void showRoll(int roll){
        this.turnLabel.setText("Rolled a " + roll);
        System.out.println("Rolled a " + roll);
        pane.repaint();
    }

    public void showTokens(int tokens, String actset){
        System.out.println(tokens + " Shot Tokens Left");
        removeShot(tokens, actset);
        //remove tokens from display, also need to initially add them
    }

    public void displayWrap(){
        System.out.println("Scene has wrapped");
    }

    public String getName(){
        return JOptionPane.showInputDialog("Enter Player Name");
    }
}
