import java.awt.Color;

/**
 * Object responsible for generating an ArrayList of circles to be drawn
 * Act as the subject in the Observer design pattern
 *
 * @author stevenwang
 * @version 2020-08-16
 */
public class GenerateFractal implements Subject {

    /** list of observers */
    private ArrayList<Observer> observers;
    /** recursion depth of the fractal drawing */
    private int recursionDepth;
    /** child to parent size ratio */
    private double childRatio;
    /** color of the pad */
    private Color padColor;
    /** color of the pear */
    private Color pearColor;

    /**
     * Constructor for GenerateFractal, instantiate the list of observers
     */
    public GenerateFractal() {
        observers = new ArrayList<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void attach(Observer observer) {
        observers.add(observer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void detach(Observer observer) {
        observers.remove(observer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void notifyAllObservers() {
        for (Observer observer : observers) {
            observer.update();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setData(int recursionDepth, int c2pRatio, Color padColor, Color pearColor) {
        this.recursionDepth = recursionDepth;
        this.childRatio = c2pRatio / 100.0;
        this.padColor = padColor;
        this.pearColor = pearColor;
        notifyAllObservers();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArrayList<Circle> getData() {
        ArrayList<Circle> circles = new ArrayList<>();
        generateCircle(circles, recursionDepth, 3 * Math.PI / 2, 140.0, 450.0, 600.0);
        return circles;
    }

    /**
     * Recursive method that generate and add circles to the ArrayList
     * @param circles the ArrayList that stores all the data
     * @param recursionCount recursion depth
     * @param angle current angle
     * @param radius radius of the circle
     * @param x starting x coordinate
     * @param y starting y coordinate
     */
    private void generateCircle(ArrayList<Circle> circles, int recursionCount, double angle, double radius, double x, double y) {
        if (recursionCount >= 1 && radius >= 1) {
            if (recursionCount == 1 || radius <= 2) {
                Circle pear = new Circle(x - radius, y - radius, radius * 2, radius * 2, pearColor);
                circles.add(pear);
            } else {
                Circle pad = new Circle(x - radius, y - radius, radius * 2.0, radius * 2.0, padColor);
                circles.add(pad);

                double newX = (radius + radius * childRatio) * Math.cos(angle + Math.PI / 4) + x;
                double newY = (radius + radius * childRatio) * Math.sin(angle + Math.PI / 4) + y;
                generateCircle(circles, recursionCount - 1, angle + Math.PI / 4, radius * childRatio, newX, newY);
                newX = (radius + radius * childRatio) * Math.cos(angle - Math.PI / 4) + x;
                newY = (radius + radius * childRatio) * Math.sin(angle - Math.PI / 4) + y;
                generateCircle(circles, recursionCount - 1, angle - Math.PI / 4, radius * childRatio, newX, newY);
            }
        }
    }

}
