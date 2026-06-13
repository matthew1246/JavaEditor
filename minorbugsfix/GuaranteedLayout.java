import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GuaranteedLayout {

    public static void main(String[] args) {
        // Completely block OS scaling distortions
        System.setProperty("sun.java2d.uiScale", "1.0");

        JFrame frame = new JFrame("Strict 1:2:1 Window Tracking");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel1 = new JPanel();
        panel1.setBackground(Color.YELLOW);

        JPanel panel2 = new JPanel();
        panel2.setBackground(Color.CYAN);

        JPanel panel3 = new JPanel();
        panel3.setBackground(Color.GREEN);

        // Use a null layout container so we have absolute control over pixels
        JPanel mainContainer = new JPanel(null);
        mainContainer.add(panel1);
        mainContainer.add(panel2);
        mainContainer.add(panel3);

        // =====================================================================
        // THE FIX: Direct Window Resize Listener
        // This forces Java to actively detect the frame's true, bigger size
        // =====================================================================
        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                // Read the freshly updated frame dimensions directly
                int frameWidth = frame.getContentPane().getWidth();
                int frameHeight = frame.getContentPane().getHeight();

                if (frameWidth <= 0 || frameHeight <= 0) return;

                // Divide the detected window size into strict 25% / 50% / 25% pieces
                int unit = frameWidth / 4;
                
                int w1 = unit;
                int w2 = unit * 2; // Mathematically guaranteed to be exactly double w1
                int w3 = frameWidth - (w1 + w2); // Safely absorbs any rounding pixels

                // Forcefully pin the panels down to these exact pixel dimensions
                panel1.setBounds(0, 0, w1, frameHeight);
                panel2.setBounds(w1, 0, w2, frameHeight);
                panel3.setBounds(w1 + w2, 0, w3, frameHeight);

                // Force a structural refresh of the UI components
                mainContainer.revalidate();
                mainContainer.repaint();
            }
        });

        mainContainer.setPreferredSize(new Dimension(800, 600));
        frame.setContentPane(mainContainer);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
