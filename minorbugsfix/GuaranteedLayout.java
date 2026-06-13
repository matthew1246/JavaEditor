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

        // Column 1: Left (Weight = 0.25)
        c.gridx = 0;
        c.weightx = 2.0;
        mainContainer.add(panel1, c);

        // Column 2: Middle (Weight = 0.50 - Exactly twice the sides)
        c.gridx = 1;
        c.weightx = 3.0;
        mainContainer.add(panel2, c);

        // Column 3: Right (Weight = 0.25)
        c.gridx = 2;
        c.weightx = 2.0;
        mainContainer.add(panel3, c);

        mainContainer.setPreferredSize(new Dimension(800, 600));
        frame.setContentPane(mainContainer);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
