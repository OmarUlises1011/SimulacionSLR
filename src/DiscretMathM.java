import java.util.List;

public class DiscretMathM {

    private double beta0;
    private double beta1;
    private double beta2;

    // Método para ajustar el modelo de regresión múltiple
    public void fit(List<Dataset> data) {
        double sumAdvertising = 0, sumAnotherVariable = 0, sumSales = 0;
        double sumAdvertisingSales = 0, sumAnotherVariableSales = 0, sumAdvertisingAnotherVariable = 0;
        double sumAdvertisingSquared = 0, sumAnotherVariableSquared = 0;

        for (Dataset record : data) {
            double advertising = record.getAdvertising();
            double anotherVariable = record.getAnotherVariable();
            double sales = record.getSales();

            sumAdvertising += advertising;
            sumAnotherVariable += anotherVariable;
            sumSales += sales;
            sumAdvertisingSales += advertising * sales;
            sumAnotherVariableSales += anotherVariable * sales;
            sumAdvertisingAnotherVariable += advertising * anotherVariable;
            sumAdvertisingSquared += advertising * advertising;
            sumAnotherVariableSquared += anotherVariable * anotherVariable;
        }

        double[][] A = {
                {data.size(), sumAdvertising, sumAnotherVariable},
                {sumAdvertising, sumAdvertisingSquared, sumAdvertisingAnotherVariable},
                {sumAnotherVariable, sumAdvertisingAnotherVariable, sumAnotherVariableSquared}
        };
        double[] C = {sumSales, sumAdvertisingSales, sumAnotherVariableSales};

        RegresionMultiple math = new RegresionMultiple();
        double[][] inverseA = math.invertMatrix(A);
        double[] B = math.multiplyMatrix(inverseA, C);

        this.beta0 = B[0];
        this.beta1 = B[1];
        this.beta2 = B[2];
    }

    public double getBeta0() { return beta0; }
    public double getBeta1() { return beta1; }
    public double getBeta2() { return beta2; }

    // Método para hacer predicciones
    public double predict(double advertising, double anotherVariable) {
        return beta0 + beta1 * advertising + beta2 * anotherVariable;
    }
}
