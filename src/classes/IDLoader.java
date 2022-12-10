package classes;

import java.io.File;
import java.io.IOException;

/**
 * Interface to load the MNIST data.
 * <p>There is one instance for each sample, ie, one for the training and one for the testing database.</p>
 * @author Ron.Coleman
 */
public interface IDLoader {
    record Normal(double[][] pixels, double[][] labels) {
        public Normal(double[][] pixels, double[][] labels){
            this.pixels = pixels;
            this.labels = labels;
        }
    }
    ////////////////
    // TODO: Add a constructor which takes the pixel and label paths.
    ////////////////

    /**
     * Gets the pixel and label data in row-major order from their respective files.
     */
    public void loadImages() throws IOException;

    /**
     * Gets the pixel magic number.
     * @return Magic number
     */
    public int[] loadLabels() throws IOException ;

    public void countFiles (File file);

    /**
     * Gets the checksum over the pixels <i>only</i>.
     * @return Checksum
     */
    public void showFile (File file) throws IOException ;

    /**
     * Normalizes the data.
     * @return Normalized data
     */
    public Normal normalize() throws IOException;
}
