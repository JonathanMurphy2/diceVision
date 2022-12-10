package classes;

import org.encog.ml.data.MLData;
import org.encog.ml.data.MLDataPair;
import org.encog.ml.data.MLDataSet;
import org.encog.neural.networks.BasicNetwork;

public class Reporter {
    BasicNetwork network;
    MLDataSet dataSet;

    record Report(int tried, int hit) {}

    public Reporter(BasicNetwork network, MLDataSet dataSet) {
        this.network = network;
        this.dataSet = dataSet;
    }

    public Report report() {
        int tried = this.dataSet.size();

        // initialize the error for misses
        int error = 0;

        for (MLDataPair pair : this.dataSet) {
            final MLData inputs = pair.getInput();
            final MLData outputs = network.compute(inputs);

            final MLData ideals = pair.getIdeal();
            final double[] ideal = ideals.getData();
            final double[] actual = outputs.getData();

            int cat_ideal = DLoader.eq.decode(ideal);
            int cat_actual = DLoader.eq.decode(actual);

            // Check if the ideal and the actual are different
            if (cat_ideal != cat_actual){
                error += 1;
            }
        }
        System.out.print("success rate = " + (tried-error) + "/" + tried + " (");
        System.out.printf("%.1f", (float)(tried-error)/tried * 100);
        System.out.println("%" + ")");

        return new Report(tried,0);
    }
}
