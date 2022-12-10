package classes;

import org.encog.mathutil.Equilateral;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.zip.CRC32;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;


public class DLoader implements IDLoader {

    public String imagePath;
    public String parentFolder;
    public int imageCounter = 0;
    int numberOfFiles;
    public int[] DLabels;
    public DImage[] DImageArray;
    public String path;

    static final Equilateral eq =
            new Equilateral(7,
                    0,
                    1);
    CRC32 crc;

    public DLoader(String path) {
        this.path = path;
    }
    @Override
    public DImage[] loadImages() throws IOException {
        Path dir = Paths.get(this.path);
        // Counts number of files
        Files.walk(dir).forEach(path -> countFiles(path.toFile()));

        initializeArrays();

        Files.walk(dir).forEach(path -> {
            try {
                showFile(path.toFile());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        // May need to reset pointers here
        return this.DImageArray;
    }

    public void initializeArrays() {
        this.DLabels = new int[this.numberOfFiles];
        this.DImageArray = new DImage[this.numberOfFiles];
    }

    @Override
    public int[] loadLabels() throws IOException {
        return this.DLabels;
    }

    @Override
    public void countFiles (File file) {
        if (file.isFile()) {
            this.numberOfFiles += 1;
        }
    }

    @Override
    public void showFile (File file) throws IOException {
        if (file.isFile()) {
            BufferedImage image = ImageIO.read(file);
            int[][] matrixImagePixels = Convert.convertTo2DUsingGetRGB(image);
            int[] imagePixles = Arrays.stream(matrixImagePixels)
                    .flatMapToInt(Arrays::stream)
                    .toArray();

            int length = file.getAbsolutePath().length();
            String label = file.getAbsolutePath().substring(length - 11, length - 10);
            //System.out.println("File: " + file.getAbsolutePath() + " Label: " + label);
            // Add the label
            //System.out.println("COUNTER:" + this.imageCounter);
            //System.out.println("ARRAY lENGTH: " + this.DImageArray.length);
            this.DLabels[this.imageCounter] = Integer.parseInt(label);
            // Add the image
            this.DImageArray[this.imageCounter] = new DImage(this.imageCounter, imagePixles, Integer.parseInt(label));
            this.imageCounter += 1;
        }
    }

    @Override
    public Normal normalize() throws IOException {
        loadImages();
//        System.out.println(DImageArray[2].pixels.length);
        double[][] pixels = new double[this.numberOfFiles][this.DImageArray[0].pixels.length];
        double[][] labels = new double[this.numberOfFiles][6];

        for (int itemNumber = 0; itemNumber < this.numberOfFiles; itemNumber++) {
            int[] arrayPixels = this.DImageArray[itemNumber].pixels;
            double[] normalizedPixels = new double[this.DImageArray[itemNumber].pixels.length];

            for (int pixelIndex = 0; pixelIndex < this.DImageArray[itemNumber].pixels.length; pixelIndex++) {
                normalizedPixels[pixelIndex] = ((double)arrayPixels[pixelIndex])/255.0;
            }

            // Store the normalized pixels
            pixels[itemNumber] = normalizedPixels;
            // Store the encoding
            labels[itemNumber] = eq.encode(this.DImageArray[itemNumber].label);
        }

        return new Normal(pixels, labels);
    }
}

