import classes.*;
//import neural.labs.lab03_06.Mop;
//import neural.matrix.IMop;
//import neural.mnist.IMLoader;
//import neural.mnist.MDigit;
//import neural.util.EncogHelper;
//import neural.util.IrisHelper;
import org.apache.commons.math3.stat.StatUtils;
import org.encog.Encog;
import org.encog.engine.network.activation.ActivationReLU;
import org.encog.engine.network.activation.ActivationSigmoid;
import org.encog.engine.network.activation.ActivationTANH;
import org.encog.mathutil.Equilateral;
import org.encog.ml.data.MLDataSet;
import org.encog.ml.data.basic.BasicMLDataSet;
import org.encog.ml.train.BasicTraining;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.layers.BasicLayer;
import org.encog.neural.networks.training.propagation.back.Backpropagation;
import org.encog.neural.networks.training.propagation.resilient.ResilientPropagation;
import org.encog.persist.EncogDirectoryPersistence;
import org.encog.util.arrayutil.NormalizedField;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

//import static neural.util.EncogHelper.*;

/*
 * @author Mason Nakamura and Jonathan Murphy
 * @date 01 Oct 2022
 */
public class Training {
    /**
     * These learning parameters generally give good results according to literature,
     * that is, the training algorithm converges with the tolerance below.
     * */
    public final static double LEARNING_RATE = 0.01;
    public final static double LEARNING_MOMENTUM = 0.05;

    public static final int TRAINING_SAMPLES = 2000;

    public static final int TESTING_SAMPLES = 200;
    public static final int MAX_EPOCHS = 1000;
    public static final double TOLERANCE = .05;
    public static final int LOG_FREQUENCY = 1;
    private static  double[][] TESTING_IDEALS;
    private static double[][] TESTING_INPUTS;
    public static double[][] TRAINING_INPUTS;

    public static double[][] TRAINING_IDEALS;

    static String directoryRoot = System.getProperty("user.dir");

    public static void init() throws IOException {
        DLoader dLoader = new DLoader(directoryRoot + "/src/Data/DiceDataset/DiceDataset");

        IDLoader.Normal normal = dLoader.normalize();
        Mop mop = new Mop();

        TRAINING_INPUTS = mop.slice(normal.pixels(), 0, TRAINING_SAMPLES);
        TRAINING_IDEALS = mop.slice(normal.labels(), 0, TRAINING_SAMPLES);

        TESTING_INPUTS = mop.slice(normal.pixels(), TRAINING_SAMPLES, TRAINING_SAMPLES + TESTING_SAMPLES);
        TESTING_IDEALS = mop.slice(normal.labels(), TRAINING_SAMPLES, TRAINING_SAMPLES + TESTING_SAMPLES);
    }

    /**
     * The main method.
     * @param args No arguments are used.
     */
    public static void main(final String args[]) throws IOException {
        // Get the start date and time
        java.util.Date start_date = new java.util.Date();
        System.out.println("started: " + start_date);

        init();

        // Instantiate the network
        BasicNetwork network = new BasicNetwork();

        // Input layer plus bias node
        network.addLayer(new BasicLayer(null, true, 16384));

        // Hidden layer plus bias node
        network.addLayer(new BasicLayer(new ActivationSigmoid(), true, 500));

        // Hidden layer plus bias node
        network.addLayer(new BasicLayer(new ActivationSigmoid(), true, 75));

        // Output layer
        network.addLayer(new BasicLayer(new ActivationSigmoid(), false, 5));

        // No more layers to be added
        network.getStructure().finalizeStructure();

        // Randomize the weights
        network.reset();

        Utils.summarize(network);

        // Create training observations
        MLDataSet trainingSet = new BasicMLDataSet(TRAINING_INPUTS, TRAINING_IDEALS);

        MLDataSet testingSet = new BasicMLDataSet(TESTING_INPUTS, TESTING_IDEALS);

        // Use a training object for the learning algorithm, backpropagation.
        final BasicTraining training = new Backpropagation(network, trainingSet,LEARNING_RATE,LEARNING_MOMENTUM);

        int epoch = 0;

        double minError = Double.MAX_VALUE;

        double error = 0.0;

        int sameCount = 0;
        final int MAX_SAME_COUNT = 5*LOG_FREQUENCY;

        Utils.log(epoch, error,false, false);
        do {
            training.iteration();

            epoch++;

            error = training.getError();

            if(error < minError) {
                minError = error;
                sameCount = 1;
                EncogDirectoryPersistence.saveObject(
                        new File(directoryRoot + "\\src\\encogPersistence"+"\\encog-" + TRAINING_SAMPLES+ ".bin"),network);

            }
            else
                sameCount++;

            if(sameCount > MAX_SAME_COUNT)
                break;

            Utils.log(epoch, error,false,false);

        } while (error > TOLERANCE && epoch < MAX_EPOCHS);

        EncogDirectoryPersistence.saveObject(
                new File(directoryRoot + "\\src\\encogPersistence"+"\\encog-" + TRAINING_SAMPLES+ ".bin"),network);


        training.finishTraining();

        Utils.log(epoch, error,sameCount > MAX_SAME_COUNT, true);

        Reporter mExercise = new Reporter(network, trainingSet);
        System.out.println("Training Results");
        mExercise.report();

        java.util.Date end_date = new java.util.Date();
        System.out.println("finished :" + end_date);

        Reporter mTest = new Reporter(network, testingSet);
        System.out.println("Testing Results");
        mTest.report();

        Encog.getInstance().shutdown();
    }
}