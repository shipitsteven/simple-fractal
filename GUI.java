import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JSlider;
import javax.swing.SpringLayout;
import javax.swing.SpinnerNumberModel;
import javax.swing.JColorChooser;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * GUI class provides the Graphical User Interface for the user to set the parameter of the fractal drawing
 *
 * @author stevenwang
 * @version 2020-08-16
 */
public class GUI extends JFrame {

    /** the subject to send the data to */
    private Subject subject;
    /** the color of the pad */
    private Color padColorValue;
    /** the color of the pear */
    private Color pearColorValue;
    /** button to change pad color */
    private final JButton padColorButton;
    /** button to change pear color */
    private final JButton pearColorButton;
    /** JPanel displaying current pad color */
    private final JPanel padColorWindow;
    /** JPanel displaying current pear color */
    private final JPanel pearColorWindow;
    /** Spinner to change recursion depth */
    private final JSpinner recursionSpinner;
    /** Slider to change child to parent ratio */
    private final JSlider ratioSlider;
    /** Boolean to decide whether widgets should trigger live update */
    private boolean updateLive;
    /** required field to suppress Xlint warning */
    private static final long serialVersionUID = 1;

    /**
     * Constructor for the GUI, initialize all components of GUI
     * and attach subject that generate data about fractal drawing
     *
     * @param subject class that generates data for the fractal drawing
     */
    public GUI(Subject subject) {

        this.subject = subject;
        setTitle("Fractal Drawing Program");
        setSize(380, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        padColorValue = new Color(53,165,0);
        pearColorValue = new Color(216,56,212);
        updateLive = false;

        JPanel panel = new JPanel();
        SpringLayout layout = new SpringLayout();
        panel.setLayout(layout);

        // Recursion depth
        SpinnerNumberModel recursion = new SpinnerNumberModel(4, 2, 10, 1);
        recursionSpinner = new JSpinner(recursion);
        recursionSpinner.setSize(50, 50);
        recursionSpinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (updateLive) {
                    subject.setData((int) recursionSpinner.getValue(), ratioSlider.getValue(), padColorValue, pearColorValue);
                }
            }
        });
        panel.add(recursionSpinner);

        JLabel recursionLabel = new JLabel("Recursion Depth");
        recursionLabel.setSize(120, 50);
        recursionLabel.setLabelFor(recursionSpinner);
        panel.add(recursionLabel);

        // Child to Parent Ratio
        ratioSlider = new JSlider(JSlider.HORIZONTAL, 40, 70, 50);
        ratioSlider.setMajorTickSpacing(10);
        ratioSlider.setMinorTickSpacing(2);
        ratioSlider.setPaintTicks(true);
        ratioSlider.setPaintLabels(true);
        ratioSlider.setPreferredSize(new Dimension(130, 50));
        ratioSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (updateLive) {
                    subject.setData((int) recursionSpinner.getValue(), ratioSlider.getValue(), padColorValue, pearColorValue);
                }
            }
        });
        panel.add(ratioSlider);

        JLabel ratioLabel = new JLabel("Child to parent ratio");
        ratioLabel.setSize(150, 50);
        ratioLabel.setLabelFor(ratioSlider);
        panel.add(ratioLabel);

        // Pad Color
        padColorWindow = new JPanel();
        padColorWindow.setPreferredSize(new Dimension(30, 15));
        padColorWindow.setBackground(padColorValue);
        panel.add(padColorWindow);

        padColorButton = new JButton("Select Color");
        padColorButton.setSize(100, 50);
        padColorButton.addActionListener(new ColorListener());
        panel.add(padColorButton);

        JLabel padColorLabel = new JLabel("Pad Color");
        padColorLabel.setSize(150, 50);
        padColorLabel.setLabelFor(padColorButton);
        panel.add(padColorLabel);

        // Pear Color
        pearColorWindow = new JPanel();
        pearColorWindow.setPreferredSize(new Dimension(30, 15));
        pearColorWindow.setBackground(pearColorValue);
        panel.add(pearColorWindow);

        pearColorButton = new JButton("Select Color");
        pearColorButton.setSize(100, 50);
        pearColorButton.addActionListener(new ColorListener());
        panel.add(pearColorButton);

        JLabel pearColorLabel = new JLabel("Pear Color");
        padColorLabel.setSize(150, 50);
        padColorLabel.setLabelFor(padColorButton);
        panel.add(pearColorLabel);

        // Generate Button
        JButton generateBtn = new JButton("Generate Fractal Drawing");
        generateBtn.setPreferredSize(new Dimension(200, 75));
        panel.add(generateBtn);
        generateBtn.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        updateLive = true;
                        int recursionDepth = (int) recursionSpinner.getValue();
                        int c2pRatio = ratioSlider.getValue();
                        subject.setData(recursionDepth, c2pRatio, padColorValue, pearColorValue);
                    }
                }
        );

        // Close button
        JButton close = new JButton("Close");
        close.setSize(80, 30);
        close.setHorizontalAlignment(JButton.CENTER);
        close.addActionListener(
                e -> System.exit(0)
        );
        panel.add(close);

        //------ Layout Manager -----
        // Recursion Depth
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, recursionLabel, -recursionLabel.getWidth() / 2, SpringLayout.HORIZONTAL_CENTER, panel);
        layout.putConstraint(SpringLayout.NORTH, recursionLabel, 30, SpringLayout.NORTH, panel);
        layout.putConstraint(SpringLayout.WEST, recursionSpinner, 10, SpringLayout.EAST, recursionLabel);
        layout.putConstraint(SpringLayout.NORTH, recursionSpinner, 25, SpringLayout.NORTH, panel);
        // Child to parent ratio
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, ratioLabel, -recursionLabel.getWidth() / 2, SpringLayout.HORIZONTAL_CENTER, panel);
        layout.putConstraint(SpringLayout.NORTH, ratioLabel, 30, SpringLayout.SOUTH, recursionLabel);
        layout.putConstraint(SpringLayout.WEST, ratioSlider, 10, SpringLayout.EAST, ratioLabel);
        layout.putConstraint(SpringLayout.NORTH, ratioSlider, 20, SpringLayout.SOUTH, recursionLabel);
        // Pad Color
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, padColorLabel, -recursionLabel.getWidth() / 2, SpringLayout.HORIZONTAL_CENTER, panel);
        layout.putConstraint(SpringLayout.NORTH, padColorLabel, 30, SpringLayout.SOUTH, ratioLabel);
        layout.putConstraint(SpringLayout.WEST, padColorWindow, 10, SpringLayout.EAST, padColorLabel);
        layout.putConstraint(SpringLayout.NORTH, padColorWindow, 31, SpringLayout.SOUTH, ratioLabel);
        layout.putConstraint(SpringLayout.WEST, padColorButton, 10, SpringLayout.EAST, padColorWindow);
        layout.putConstraint(SpringLayout.NORTH, padColorButton, 25, SpringLayout.SOUTH, ratioLabel);
        // Pear Color
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, pearColorLabel, -recursionLabel.getWidth() / 2, SpringLayout.HORIZONTAL_CENTER, panel);
        layout.putConstraint(SpringLayout.NORTH, pearColorLabel, 30, SpringLayout.SOUTH, padColorLabel);
        layout.putConstraint(SpringLayout.WEST, pearColorWindow, 8, SpringLayout.EAST, pearColorLabel);
        layout.putConstraint(SpringLayout.NORTH, pearColorWindow, 31, SpringLayout.SOUTH, padColorLabel);
        layout.putConstraint(SpringLayout.WEST, pearColorButton, 10, SpringLayout.EAST, pearColorWindow);
        layout.putConstraint(SpringLayout.NORTH, pearColorButton, 25, SpringLayout.SOUTH, padColorLabel);
        // Generate Button
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, generateBtn, 0, SpringLayout.HORIZONTAL_CENTER, panel);
        layout.putConstraint(SpringLayout.NORTH, generateBtn, 40, SpringLayout.SOUTH, pearColorLabel);
        // Close Button
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, close, 0, SpringLayout.HORIZONTAL_CENTER, panel);
        layout.putConstraint(SpringLayout.SOUTH, close, -10, SpringLayout.SOUTH, panel);

        // add main panel to frame and display
        getContentPane().add(panel);
        setVisible(true);
    }

    /**
     * Inner class for Color Chooser
     */
    private class ColorListener implements ActionListener {

        /**
         * Implementation for the color choosers, sets the picked color and display in color window
         *
         * @param e mouse click of the button
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton clicked = (JButton) e.getSource();
            if (clicked.equals(pearColorButton)) {
                pearColorValue = JColorChooser.showDialog(pearColorButton, "Pick a color", pearColorValue);
                if (pearColorValue == null) {
                    pearColorValue = Color.RED;
                    pearColorWindow.setBackground(pearColorValue);
                }
                pearColorWindow.setBackground(pearColorValue);
            } else if (clicked.equals(padColorButton)) {
                padColorValue = JColorChooser.showDialog(padColorButton, "Pick a color", padColorValue);
                if (padColorValue == null) {
                    padColorValue = Color.GREEN;
                    padColorWindow.setBackground(padColorValue);
                }
                padColorWindow.setBackground(padColorValue);
            }
            if (updateLive) {
                subject.setData((int) recursionSpinner.getValue(), ratioSlider.getValue(), padColorValue, pearColorValue);
            }
        }
    }
}
