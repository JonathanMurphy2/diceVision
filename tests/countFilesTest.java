
import classes.DImage;
import classes.DLoader;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;

import static org.junit.Assert.assertEquals;

public class countFilesTest {
    DLoader Loader = new DLoader(Training.directoryRoot + "\\src\\Data\\DiceDataset\\DiceDataset");

    final int EXPECTED_NUMBER = 2400;

    @Test
    public void test_0() throws IOException {

       Loader.loadImages();

       assertEquals(Loader.imageCounter, EXPECTED_NUMBER);
    }
}

