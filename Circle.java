import java.awt.Color;
import java.awt.Graphics;

/**
 * Class encapsulate the relevant attributes about a circle
 *
 * @author stevenwang
 * @version 2020-08-16
 */
public class Circle {

    /** x coordinate of the drawing window */
    private final double x;
    /** y coordinate of the drawing window */
    private final double y;
    /** width of the circle, same as diameter */
    private final double width;
    /** height of the circle, same as diameter */
    private final double height;
    /** color of the circle */
    private final Color color;

    /**
     * Constructor for the circle
     * @param x x coordinate of the drawing window
     * @param y y coordinate of the drawing window
     * @param width width of the circle, same as diameter
     * @param height height of the circle, same as diameter
     * @param color color of the circle
     */
    public Circle(double x, double y, double width, double height, Color color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
    }

    /**
     * Retrieves the X coordinate of the drawing window
     * @return X coordinate
     */
    public double getX() {
        return x;
    }

    /**
     * Retrieves the Y coordinate of the drawing window
     * @return Y coordinate
     */
    public double getY() {
        return y;
    }

    /**
     * Retrieves the width of the circle, same as diameter
     * @return width of the circle
     */
    public double getWidth() {
        return width;
    }

    /**
     * Retrieves the height of the circle, same as diameter
     * @return height of the circle
     */
    public double getHeight() {
        return height;
    }

    /**
     * Retrieves the color of the circle
     * @return the color of the circle
     */
    public Color getColor() {
        return color;
    }

    /**
     * Draws the circles
     *
     * @param g the graphics element
     */
    public void draw(Graphics g){
        g.setColor(color);
        g.fillOval((int)x, (int)y, (int)width, (int)height);
    }
}
