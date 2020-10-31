import java.awt.Color;

/**
 * Interface for the subject in observer model
 *
 * @author stevenwang
 * @version 2020-08-08
 */
public interface Subject {

    /**
     * Attach observer to the subject
     *
     * @param observer class watching the subject
     */
    void attach(Observer observer);

    /**
     * Remove observer from the subject
     *
     * @param observer class no longer wish to watch the subject
     */
    void detach(Observer observer);

    /**
     * Notify all observers that there's changes in the subject class
     */
    void notifyAllObservers();

    /**
     * Sets data in the subject class
     *
     * @param recursionDepth how many times the recursive function is called
     * @param c2pRatio       child to parent ratio
     * @param padColor       color of the pads
     * @param pearColor      color of the pear
     */
    void setData(int recursionDepth, int c2pRatio, Color padColor, Color pearColor);

    /**
     * Retrieves the data from the subject
     *
     * @return an array list of circle objects
     */
    ArrayList<Circle> getData();
}
