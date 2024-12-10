import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AlgoritmoGenetico {
    private int populationSize;
    private double crossoverRate;
    private double mutationRate;
    private int geneSize;
    private int numGenerations;
    private double[] X;
    private double[] Y;
    private Random random;

    public AlgoritmoGenetico(int populationSize, double crossoverRate, double mutationRate, int geneSize, int numGenerations, double[] X, double[] Y) {
        this.populationSize = populationSize;
        this.crossoverRate = crossoverRate;
        this.mutationRate = mutationRate;
        this.geneSize = geneSize;
        this.numGenerations = numGenerations;
        this.X = X;
        this.Y = Y;
        this.random = new Random();
    }

    public double[] ejecutar() {
        double[] mejoresParametros = new double[geneSize];
        double mejorR2 = Double.MIN_VALUE;

        System.out.println("Generando población inicial...");
        List<Cromosoma> poblacion = generarPoblacionInicial();
        imprimirPoblacion(poblacion, "Población Inicial");

        for (int generacion = 0; generacion < numGenerations; generacion++) {
            System.out.println("\nGeneración " + (generacion + 1));
            List<Cromosoma> nuevaPoblacion = new ArrayList<>();
            Cromosoma mejorCromosoma = obtenerMejorCromosoma(poblacion);
            nuevaPoblacion.add(mejorCromosoma);
            System.out.println("Mejor cromosoma de la generación: " + mejorCromosoma);

            for (int i = 0; i < (populationSize - 1) / 2; i++) {
                Cromosoma padre1 = ruletaSeleccion(poblacion);
                Cromosoma padre2 = ruletaSeleccion(poblacion);
                System.out.println("Seleccionados para cruce: ");
                System.out.println("  Padre 1: " + padre1);
                System.out.println("  Padre 2: " + padre2);

                Cromosoma[] hijos = crossover(padre1, padre2);
                System.out.println("Hijos después de cruce: ");
                System.out.println("  Hijo 1: " + hijos[0]);
                System.out.println("  Hijo 2: " + hijos[1]);

                nuevaPoblacion.add(mutar(hijos[0]));
                nuevaPoblacion.add(mutar(hijos[1]));
            }

            for (Cromosoma cromosoma : nuevaPoblacion) {
                double r2 = evaluarRecta(cromosoma);
                cromosoma.setR2(r2);
                if (r2 > mejorR2) {
                    mejorR2 = r2;
                    mejoresParametros = cromosoma.getGenes();
                }
            }

            imprimirPoblacion(nuevaPoblacion, "Nueva Población");
            poblacion = nuevaPoblacion;
        }

        System.out.println("\nMejores parámetros encontrados: ");
        for (int i = 0; i < mejoresParametros.length; i++) {
            System.out.println("Parametro[" + i + "] = " + mejoresParametros[i]);
        }
        System.out.println("Mejor R2: " + mejorR2);

        return mejoresParametros;
    }

    private List<Cromosoma> generarPoblacionInicial() {
        List<Cromosoma> poblacion = new ArrayList<>();
        for (int i = 0; i < populationSize; i++) {
            double[] genes = new double[geneSize];
            for (int j = 0; j < geneSize; j++) {
                genes[j] = random.nextDouble() * 10 - 5;
            }
            Cromosoma cromosoma = new Cromosoma(genes);
            poblacion.add(cromosoma);
        }
        return poblacion;
    }

    private Cromosoma obtenerMejorCromosoma(List<Cromosoma> poblacion) {
        return poblacion.stream()
                .max((a, b) -> Double.compare(a.getR2(), b.getR2()))
                .orElse(poblacion.get(0));
    }

    private Cromosoma ruletaSeleccion(List<Cromosoma> poblacion) {
        double sumaAptitudes = poblacion.stream().mapToDouble(Cromosoma::getR2).sum();
        double puntoSeleccion = random.nextDouble() * sumaAptitudes;

        double sumaParcial = 0;
        for (Cromosoma cromosoma : poblacion) {
            sumaParcial += cromosoma.getR2();
            if (sumaParcial >= puntoSeleccion) {
                return cromosoma;
            }
        }
        return poblacion.get(poblacion.size() - 1);
    }

    private Cromosoma[] crossover(Cromosoma padre1, Cromosoma padre2) {
        Cromosoma[] hijos = new Cromosoma[2];
        double[] genes1 = padre1.getGenes().clone();
        double[] genes2 = padre2.getGenes().clone();

        if (random.nextDouble() < crossoverRate) {
            int puntoCorte = random.nextInt(geneSize);
            for (int i = 0; i < puntoCorte; i++) {
                genes1[i] = padre2.getGenes()[i];
                genes2[i] = padre1.getGenes()[i];
            }
        }

        hijos[0] = new Cromosoma(genes1);
        hijos[1] = new Cromosoma(genes2);
        return hijos;
    }

    private Cromosoma mutar(Cromosoma cromosoma) {
        double[] genes = cromosoma.getGenes().clone();
        if (random.nextDouble() < mutationRate) {
            int puntoMutacion = random.nextInt(geneSize);
            genes[puntoMutacion] += random.nextGaussian();
        }
        return new Cromosoma(genes);
    }

    private double evaluarRecta(Cromosoma cromosoma) {
        double[] genes = cromosoma.getGenes();
        double beta0 = genes[0];
        double beta1 = genes[1];
        double ssTotal = 0;
        double ssResidual = 0;
        double yPromedio = 0;

        for (double y : Y) {
            yPromedio += y;
        }
        yPromedio /= Y.length;

        for (int i = 0; i < X.length; i++) {
            double yReal = Y[i];
            double yPredicho = beta0 + beta1 * X[i];
            ssTotal += Math.pow(yReal - yPromedio, 2);
            ssResidual += Math.pow(yReal - yPredicho, 2);
        }

        return (ssTotal == 0) ? 0 : 1 - (ssResidual / ssTotal);
    }

    private void imprimirPoblacion(List<Cromosoma> poblacion, String titulo) {
        System.out.println("\n" + titulo + ":");
        for (Cromosoma cromosoma : poblacion) {
            System.out.println(cromosoma);
        }
    }
}
