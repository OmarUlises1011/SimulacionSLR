import java.util.List;

public class CalculoRSquared {

    // Método para calcular R^2 de la regresión lineal
    public static double calcularRSquaredLineal(List<Dataset> data, double beta0, double beta1) {
        double totalSumOfSquares = 0.0;
        double residualSumOfSquares = 0.0;
        double meanSales = 0.0;

        for (Dataset dataset : data) {
            meanSales += dataset.getSales();
        }
        meanSales /= data.size();

        for (Dataset dataset : data) {
            double predictedSales = beta0 + beta1 * dataset.getAdvertising();
            totalSumOfSquares += Math.pow(dataset.getSales() - meanSales, 2);
            residualSumOfSquares += Math.pow(dataset.getSales() - predictedSales, 2);
        }

        return 1 - (residualSumOfSquares / totalSumOfSquares);
    }

    // Método para calcular R^2 de la regresión cuadrática
    public static double calcularRSquaredCuadratico(List<Dataset> data, double beta0, double beta1, double beta2) {
        double totalSumOfSquares = 0.0;
        double residualSumOfSquares = 0.0;
        double meanSales = 0.0;

        for (Dataset dataset : data) {
            meanSales += dataset.getSales();
        }
        meanSales /= data.size();

        for (Dataset dataset : data) {
            double predictedSales = beta0 + beta1 * dataset.getAdvertising() + beta2 * Math.pow(dataset.getAdvertising(), 2);
            totalSumOfSquares += Math.pow(dataset.getSales() - meanSales, 2);
            residualSumOfSquares += Math.pow(dataset.getSales() - predictedSales, 2);
        }

        return 1 - (residualSumOfSquares / totalSumOfSquares);
    }

    // Método para calcular R^2 de la regresión cúbica
    public static double calcularRSquaredCubico(List<Dataset> data, double beta0, double beta1, double beta2, double beta3) {
        double totalSumOfSquares = 0.0;
        double residualSumOfSquares = 0.0;
        double meanSales = 0.0;

        for (Dataset dataset : data) {
            meanSales += dataset.getSales();
        }
        meanSales /= data.size();

        for (Dataset dataset : data) {
            double predictedSales = beta0 + beta1 * dataset.getAdvertising()
                    + beta2 * Math.pow(dataset.getAdvertising(), 2)
                    + beta3 * Math.pow(dataset.getAdvertising(), 3);
            totalSumOfSquares += Math.pow(dataset.getSales() - meanSales, 2);
            residualSumOfSquares += Math.pow(dataset.getSales() - predictedSales, 2);
        }

        return 1 - (residualSumOfSquares / totalSumOfSquares);
    }
}
