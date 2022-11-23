import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import classes.Convert;

public class Main {

    public static void main(String args[]) {
        try {
            // Read the image file
            BufferedImage image = ImageIO.read(new File("./src/Data/img.png"));
            System.out.println("Testing convertTo2DWithoutUsingGetRGB:");
            int[][] result = Convert.convertTo2DUsingGetRGB(image);
            for (int i = 0; i < result.length; i++) {
                System.out.println();
                for (int j = 0; j < result[0].length; j++) {
                    System.out.print(Integer.parseInt(Integer.toHexString(result[i][j]).substring(6, 8), 16) + " ");
                }
            }
//            Convert.toString(result);
        } catch (IOException e) {
            System.out.println("Invalid image path!");
        }
    }
}
