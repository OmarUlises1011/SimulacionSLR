public class RegresionMultiple {

    //  invertir  matriz usando Gauss-Jordan
    public double[][] invertMatrix(double[][] matrix) {
        int n = matrix.length;
        double[][] augmentedMatrix = new double[n][2 * n];

        // Crear la matriz aumentada [A | I]
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                augmentedMatrix[i][j] = matrix[i][j];
                augmentedMatrix[i][j + n] = (i == j) ? 1.0 : 0.0;
            }
        }

        // Procedimiento de Gauss-Jordan
        for (int i = 0; i < n; i++) {
            double pivot = augmentedMatrix[i][i];
            if (Math.abs(pivot) < 1e-10) {  // Tolerancia para evitar divisiones por cero
                throw new ArithmeticException("La matriz no es invertible.");
            }
            for (int j = 0; j < 2 * n; j++) {
                augmentedMatrix[i][j] /= pivot;
            }
            for (int k = 0; k < n; k++) {
                if (k != i) {
                    double factor = augmentedMatrix[k][i];
                    for (int j = 0; j < 2 * n; j++) {
                        augmentedMatrix[k][j] -= factor * augmentedMatrix[i][j];
                    }
                }
            }
        }

        // Extraer la matriz inversa
        double[][] inverse = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                inverse[i][j] = augmentedMatrix[i][j + n];
            }
        }
        return inverse;
    }

    // multiplicar una matriz por un vector
    public double[] multiplyMatrix(double[][] matrix, double[] vector) {
        int rows = matrix.length;
        int cols = matrix[0].length;

        if (cols != vector.length) {
            throw new IllegalArgumentException("Dimensiones no compatibles para la multiplicación.");
        }

        double[] result = new double[rows];
        for (int i = 0; i < rows; i++) {
            result[i] = 0;
            for (int j = 0; j < cols; j++) {
                result[i] += matrix[i][j] * vector[j];
            }
        }
        return result;
    }
}
