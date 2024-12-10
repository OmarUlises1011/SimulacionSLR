import java.util.ArrayList;
import java.util.List;

public class Dataset {
    private double advertising;
    private double anotherVariable;
    private double sales;

    public Dataset(double advertising, double anotherVariable, double sales) {
        this.advertising = advertising;
        this.anotherVariable = anotherVariable;
        this.sales = sales;
    }

    public double getAdvertising() {
        return advertising;
    }

    public double getAnotherVariable() {
        return anotherVariable;
    }

    public double getSales() {
        return sales;
    }

    public static List<Dataset> getExampleData() {
        double[] X = {23, 26, 30, 34, 43, 48, 52, 57, 58};

        double[] Y = {651, 762, 856, 1063, 1190, 1298, 1421, 1440, 1518};
        double[] X2 = {529, 676, 900, 1156, 1849, 2304, 2704, 3249, 3364};
        List<Dataset> data = new ArrayList<>();
        for (int i = 0; i < X.length; i++) {
            data.add(new Dataset(X[i], X2[i], Y[i]));
        }
        return data;
    }
}
