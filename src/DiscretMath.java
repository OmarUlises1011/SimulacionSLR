import java.util.List;

public class DiscretMath {
    private double beta0;
    private double beta1;
    private double beta2;
    private double beta3;

    // Método para entrenar el modelo de regresión lineal
    public void fitLinear(List<Dataset> data) {
        double sumX = 0, sumY = 0, sumXY = 0, sumX2 = 0;
        int n = data.size();

        for (Dataset point : data) {
            double x = point.getAdvertising();
            double y = point.getSales();
            sumX += x;
            sumY += y;
            sumXY += x * y;
            sumX2 += x * x;
        }

        // Calcular beta1 y beta0
        beta1 = (n * sumXY - sumX * sumY) / (n * sumX2 - sumX * sumX);
        beta0 = (sumY - beta1 * sumX) / n;
    }

    // Método para entrenar el modelo cuadrático
    public void fitQuadratic(List<Dataset> data) {
        double sumX = 0, sumY = 0, sumX2 = 0, sumX3 = 0, sumX4 = 0;
        double sumXY = 0, sumX2Y = 0;
        int n = data.size();

        for (Dataset point : data) {
            double x = point.getAdvertising();
            double y = point.getSales();
            double x2 = x * x;
            sumX += x;
            sumY += y;
            sumX2 += x2;
            sumX3 += x2 * x;
            sumX4 += x2 * x2;
            sumXY += x * y;
            sumX2Y += x2 * y;
        }

        // Resolución del sistema de ecuaciones para beta0, beta1, y beta2
        double[][] matrix = {
                {n, sumX, sumX2, sumY},
                {sumX, sumX2, sumX3, sumXY},
                {sumX2, sumX3, sumX4, sumX2Y}
        };
        double[] betas = solveSystem(matrix);
        beta0 = betas[0];
        beta1 = betas[1];
        beta2 = betas[2];
    }

    // Método para entrenar el modelo cúbico
    public void fitCubic(List<Dataset> data) {
        double sumX = 0, sumY = 0, sumX2 = 0, sumX3 = 0, sumX4 = 0, sumX5 = 0, sumX6 = 0;
        double sumXY = 0, sumX2Y = 0, sumX3Y = 0;
        int n = data.size();

        for (Dataset point : data) {
            double x = point.getAdvertising();
            double y = point.getSales();
            double x2 = x * x;
            double x3 = x2 * x;
            sumX += x;
            sumY += y;
            sumX2 += x2;
            sumX3 += x3;
            sumX4 += x2 * x2;
            sumX5 += x3 * x2;
            sumX6 += x3 * x3;
            sumXY += x * y;
            sumX2Y += x2 * y;
            sumX3Y += x3 * y;
        }

        // Resolución del sistema de ecuaciones para beta0, beta1, beta2 y beta3
        double[][] matrix = {
                {n, sumX, sumX2, sumX3, sumY},
                {sumX, sumX2, sumX3, sumX4, sumXY},
                {sumX2, sumX3, sumX4, sumX5, sumX2Y},
                {sumX3, sumX4, sumX5, sumX6, sumX3Y}
        };
        double[] betas = solveSystem(matrix);
        beta0 = betas[0];
        beta1 = betas[1];
        beta2 = betas[2];
        beta3 = betas[3];
    }

    // Método para predecir valores de Y (Ventas) dado X (Publicidad)
    public double predict(double advertising, String modelType) {
        switch (modelType) {
            case "linear":
                return beta0 + beta1 * advertising;
            case "quadratic":
                return beta0 + beta1 * advertising + beta2 * advertising * advertising;
            case "cubic":
                return beta0 + beta1 * advertising + beta2 * advertising * advertising + beta3 * advertising * advertising * advertising;
            default:
                throw new IllegalArgumentException("Modelo no soportado");
        }
    }

    // Resolver sistema de ecuaciones mediante eliminación Gaussiana
    private double[] solveSystem(double[][] matrix) {
        int n = matrix.length;

        for (int i = 0; i < n; i++) {
            // Normalizar la fila i
            double factor = matrix[i][i];
            for (int j = 0; j <= n; j++) {
                matrix[i][j] /= factor;
            }

            // Hacer ceros en las demás filas
            for (int k = 0; k < n; k++) {
                if (k != i) {
                    factor = matrix[k][i];
                    for (int j = 0; j <= n; j++) {
                        matrix[k][j] -= factor * matrix[i][j];
                    }
                }
            }
        }

        // Extraer las soluciones
        double[] solution = new double[n];
        for (int i = 0; i < n; i++) {
            solution[i] = matrix[i][n];
        }
        return solution;
    }


    public double getBeta0() {
        return beta0;
    }

    public double getBeta1() {
        return beta1;
    }

    public double getBeta2() {
        return beta2;
    }

    public double getBeta3() {
        return beta3;
    }
}
