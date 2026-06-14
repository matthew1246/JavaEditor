import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GuaranteedLayout {

    public static void main(String[] args) {
        // Completely block OS scaling distortions
        System.setProperty("sun.java2d.uiScale", "1.0");

        JFrame frame = new JFrame("Strict 1:2:1 Layout");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel1 = new JPanel();
        panel1.setBackground(Color.YELLOW);

        JPanel panel2 = new JPanel();
        panel2.setBackground(Color.CYAN);

        JPanel panel3 = new JPanel();
        panel3.setBackground(Color.GREEN);

        // SECRET SAUCE: Force the components' preferred sizes to 0.
        // This tells Java to completely ignore internal content sizes
        // and rely strictly on the mathematical weights.
        panel1.setPreferredSize(new Dimension(0, 0));
        panel2.setPreferredSize(new Dimension(0, 0));
        panel3.setPreferredSize(new Dimension(0, 0));

        // Use GridBagLayout instead of a null layout
        JPanel mainContainer = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        
        // Force components to stretch completely to fill the space
        c.fill = GridBagConstraints.BOTH;
        c.weighty = 1.0; // Fill 100% of vertical height
        
        /*
        1:2 is maths ratio of two columns. How to make bug ratio for two columns?
        1 is first column is whole screen area.
        1*(1/2) is amount of padding that can go to second column to make first column
        1/2x=1/3 or 1/2x=2/3
        x=2/3   or x=4/3
        2:3 is the bug ratio of first and second column
        */
        
        /* Old values
        c.gridx = 0;
        c.weightx = 2.0;
        mainContainer.add(panel1, c);

        c.gridx = 1;
        c.weightx = 3.0;
        mainContainer.add(panel2, c);

        c.gridx = 2;
        c.weightx = 2.0;
        mainContainer.add(panel3, c);
        */
        
          /*
        6x=1/2
        x=1/12
        3-1/12
        3/1-1/12
        36/12-1/12
        35/12
        
        x=1/6
        3-1/6
        3/1-1/6
        18/6-1/6
        17/6
        */
        
        // Column 1: Left (Weight = 0.25)
        c.gridx = 0;
        c.weightx = 2.0;
        mainContainer.add(panel1, c);

        // Column 2: Middle (Weight = 0.50 - Exactly twice the sides)
        c.gridx = 1;
        c.weightx = 3.0;
        mainContainer.add(panel2, c);
        
        // 2/3 / 2 (2/6)x=1/2 x=6*1/(2*2) x = 3/2
        
        /*
        2:3 is bug ratio of second column
        2/3 is second column when full screen is only two columns
        (2/3) / 2 is amount of padding in second column that can go to new third column
        (2/3) / 2 = 2/6
        2/6x=1/2 equals half because third column is half the screen.
        1/3x=1/2
        x = 3/2
        x = 1.5 
        3 + 1.5 = 4.5
        */
        
        // Column 3: Right (Weight = 0.25)
        c.gridx = 2;
        c.weightx = 4.5; // 24.0/3.0; // (2/3)x =1/2 x=4/3 (4/3)*6=24/3
        mainContainer.add(panel3, c);

        mainContainer.setPreferredSize(new Dimension(800, 600));
        frame.setContentPane(mainContainer);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        
        System.out.println(panel1.getWidth());
System.out.println(panel2.getWidth());
System.out.println(panel3.getWidth());
    }
}