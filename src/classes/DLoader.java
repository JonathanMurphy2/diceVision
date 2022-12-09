package classes;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.zip.CRC32;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;


public class DLoader {

    public String imagePath;
    public String parentFolder;
    public int imageMagicNumber;
    public int imageNumberOfItems;
    public int nRows;
    public int nCols;
    public int labelMagicNumber;
    int labelNumberOfLabels;
    public int imageCounter = 0;
    public int[] DLabels = new int[this.numberOfFiles];
    public DImage[] DImageArray = new DImage[this.numberOfFiles];
    int numberOfFiles;
    CRC32 crc;
//        public DLoader(String imagePath) {
//            this.imagePath = imagePath;
//            this.parentFolder = imagePath;
//            this.crc = new CRC32();
//        }




//////////////////////////////////////////

    public DImage[] loadImages() throws IOException {
        Path dir = Paths.get("C:\\Users\\jmurp\\Documents\\GitHub\\diceVision\\src\\Data\\DiceDataset\\DiceDataset");
        // Counts number of files
        Files.walk(dir).forEach(path -> countFile(path.toFile()));

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

    public int[] loadLabels() throws IOException {
        return this.DLabels;
    }

    public void countFile (File file) {
        if (file.isFile()) {
            this.numberOfFiles += 1;
        }
    }

    public void showFile (File file) throws IOException {
        if (file.isDirectory()) {
            System.out.println("Directory: " + file.getAbsolutePath());
        } else {
            BufferedImage image = ImageIO.read(file);
            int[][] matrixImagePixels = Convert.convertTo2DUsingGetRGB(image);
            int[] imagePixles = Arrays.stream(matrixImagePixels)
                    .flatMapToInt(Arrays::stream)
                    .toArray();
            // normalize the pixels
            for(int index = 0; index < imagePixles.length; index++){
                imagePixles[index] = imagePixles[index]/255;
            }
            int length = file.getAbsolutePath().length();
            String label = file.getAbsolutePath().substring(length - 11, length - 10);
            System.out.println("File: " + file.getAbsolutePath() + " Label: " + label);
            // Add the label
            this.DLabels[this.imageCounter] = Integer.parseInt(label);
            // Add the image
            this.DImageArray[this.imageCounter] = new DImage(this.imageCounter, imagePixles, Integer.parseInt(label));
        }
        this.imageCounter += 1;
    }
///////////////////////////////////////////




//            // Create a new array of written numbers.
//            DImage[] MArray = new DImage[imageNumberOfItems];
//            // Loop through that list of written numbers.
//            for (int itemNumber = 0; itemNumber < this.imageNumberOfItems; itemNumber++) {
//                // Create a new array of pixels for each written number.
//                int [] pixelArray = new int [nRows * nCols];
//
//                // Loop through and read all the individual pixels for each written number.
//                for ( int pixel = 0; pixel < nRows * nCols; pixel++) {
//                    pixelArray[pixel] = imageInputStream.readUnsignedByte();
//                    this.crc.update(pixelArray[pixel]);
//                }
//
//                int label = labelsInputStream.readUnsignedByte();
//                MArray[itemNumber] = new DImage(itemNumber, pixelArray, label);
//            }
//            return MArray;
//        }

//        @Override
//        public int getPixelsMagic() {
//            return imageMagicNumber;
//        }
//
//        @Override
//        public int getLabelsMagic() {
//            return labelMagicNumber;
//        }
//
//        @Override
//        public long getChecksum() {return this.crc.getValue(); }
//
//        @Override
//        public Normal normalize() {
//            return null;
//        }
    }

