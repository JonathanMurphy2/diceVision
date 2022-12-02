package classes;

import java.awt.image.BufferedImage;

public class DLoader {
    public static int[][] convertTo2DUsingGetRGB(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        int[][] result = new int[height][width];

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                result[row][col] = image.getRGB(col, row);
            }
        }

        return result;
    }

    public static String toString(int[][] pixels) {
        System.out.println("--- testing");
        System.out.println("  012345678901234567890123456789");
        for (int rowIndex=0; rowIndex < 30; rowIndex++) {
            System.out.print(rowIndex % 10 + " ");
            for (int colIndex=0; colIndex < 30; colIndex++) {
                if (pixels[colIndex][rowIndex] == 0) {
                    System.out.print(".");
                }
                else {
                    // Discretize the ubyte
                    int undiscretizedPixelValue = pixels[colIndex][rowIndex];
                    if (undiscretizedPixelValue <= 256/15) {
                        System.out.printf("%1d", 1);
                    }
                    else if (undiscretizedPixelValue <= (256/15)*2){
                        System.out.printf("%1d", 2);
                    }
                    else if (undiscretizedPixelValue <= (256/15)*3){
                        System.out.printf("%1d", 3);
                    }
                    else if (undiscretizedPixelValue <= (256/15)*4){
                        System.out.printf("%1d", 4);
                    }
                    else if (undiscretizedPixelValue <= (256/15)*5){
                        System.out.printf("%1d", 5);
                    }
                    else if (undiscretizedPixelValue <= (256/15)*6){
                        System.out.printf("%1d", 6);
                    }
                    else if (undiscretizedPixelValue <= (256/15)*7){
                        System.out.printf("%1d", 7);
                    }
                    else if (undiscretizedPixelValue <= (256/15)*8){
                        System.out.printf("%1d", 8);
                    }
                    else if (undiscretizedPixelValue <= (256/15)*9){
                        System.out.printf("%1d", 9);
                    }
                    else if (undiscretizedPixelValue <= (256/15)*10){
                        System.out.printf("%1s", "A");
                    }
                    else if (undiscretizedPixelValue <= (256/15)*11){
                        System.out.printf("%1s", "B");
                    }
                    else if (undiscretizedPixelValue <= (256/15)*12){
                        System.out.printf("%1s", "C");
                    }
                    else if (undiscretizedPixelValue <= (256/15)*13){
                        System.out.printf("%1s", "D");
                    }
                    else if (undiscretizedPixelValue <= (256/15)*14){
                        System.out.printf("%1s", "E");
                    }
                    else if (undiscretizedPixelValue <= 256){
                        System.out.printf("%1.1s", "F");
                    }
                }
            }
            System.out.println();
        }
        return null;
    }
}
