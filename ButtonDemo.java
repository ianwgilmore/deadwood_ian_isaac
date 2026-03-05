// Written by Isaac Raven on 3/4/2026

import java.awt.event.*; // For the ActionListener
import javax.swing.*; // For everything else

// Sources:
// https://www.geeksforgeeks.org/java/introduction-to-java-swing/
// https://www.tutorialspoint.com/swing/swing_event_handling.htm

// This is a small program which gives an idea
// on how basic JSwing implementation works.
public class ButtonDemo {
    // This is an ActionListener. The ActionListener parent is abstract, so
    // we need to make this concrete child with code to run upon the button
    // being clicked.
    private static class ButtonClickListener implements ActionListener {
        // Every listener uses 'actionPerformed' method. 
        // And yes, '@Override' is necessary and the code refuses
        // to compile without it.
        @Override
        public void actionPerformed(ActionEvent event) { // Argument name 'event' can be safely changed, for example, to 'e'
            System.out.println("The button was clicked!");
        }
    }

    public static void main(String args[]) {
        // Create frame/window for GUI stuff
        JFrame frame = new JFrame();

        // Create button with text on it
        JButton button = new JButton("This is a button.");

        // Add our custom ActionListener to the button
        button.addActionListener(new ButtonClickListener());

        // Set button's x, y, width, height
        // (Fun fact: You can do this part after
        // the button is already added to the window)
        int x = 100;
        int y = 100;
        int width = 100;
        int height = 100;
        button.setBounds(x, y, width, height);

        // Add button to frame/window
        frame.add(button);

        // Set frame/window size
        frame.setSize(800, 600);

        // Have to set the frame/window's layout to null,
        // otherwise the button stretches to 
        // takes up the entire window space.
        frame.setLayout(null);

        // Show frame/window
        // (otherwise no GUI window will pop open
        // and you'll be very confused)
        frame.setVisible(true);
    }
}