/**
 * Main application utilizing all components and showcase the functionality of the program
 * Draws a fractal base on the inputted parameters
 *
 * @author stevenwang
 * @version 2020-08-08
 */
public class Main {
    /**
     * Application main method
     * @param args the command line argument
     */
    public static void main(String[] args) {
        GenerateFractal generator = new GenerateFractal();
        Display window = new Display(generator);
        GUI gui = new GUI(generator);
    }
}