package classes;

import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
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
    CRC32 crc;
        public DLoader(String imagePath) {
            this.imagePath = imagePath;

            this.parentFolder = imagePath;
            this.crc = new CRC32();
        }

        //public DImage[] load() throws IOException {


//////////////////////////////////////////

    public static void main(String... args) throws Exception {
        Path dir = Paths.get("C:\\Users\\jmurp\\Documents\\GitHub\\diceVision\\src\\Data\\DiceDataset\\DiceDataset");
        Files.walk(dir).forEach(path -> showFile(path.toFile()));
    }

    public static void showFile(File file) {
        if (file.isDirectory()) {

            System.out.println("Directory: " + file.getAbsolutePath());
        } else {
            int length = file.getAbsolutePath().length();
            String label =  file.getAbsolutePath().substring(length - 11, length - 10);
            System.out.println("File: " + file.getAbsolutePath() + " Label: " + label);
        }
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

