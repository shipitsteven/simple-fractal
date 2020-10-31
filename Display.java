import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.Graphics;

/**
 * Displays the drawing based on data produced by GenerateFractal
 *
 * @author stevenwang
 * @version 2020-08-16
 */
public class Display extends JFrame implements Observer {

    /** subject that produces data about the shapes */
    private Subject subject;
    /** holds the data */
    private ArrayList<Circle> data;
    /** required field to suppress Xlint warning */
    private static final long serialVersionUID = 1;

    /**
     * Constructor for the display class
     * Initialize JFrame, attach itself to the subject, awaiting data
     *
     * @param subject subject that produces data about the shapes to be drawn
     */
    public Display(Subject subject) {
        this.subject = subject;
        this.subject.attach(this);
        setLocation(400, 0);
        setSize(900, 800);
        setTitle("Drawing");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update() {
        data = subject.getData();
        JPanel panel = new GPanel();
        getContentPane().add(panel);
        panel.setLayout(null);
        repaint();
        setVisible(true);
    }

    /**
     * Inner class to draw the shapes from data
     */
    private class GPanel extends JPanel {

        /** required field to suppress Xlint warning */
        private static final long serialVersionUID = 1;

        /**
         * Display all shapes from provided data
         *
         * @param g the Graphics object
         */
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            for (Circle current : data) {
                current.draw(g);
            }
        }
    }
}
