public class Cromosoma {
    private double[] genes;
    private double r2;

    public Cromosoma(double[] genes) {
        this.genes = genes;
    }

    public double[] getGenes() {
        return genes;
    }

    public double getGen(int index) {
        return genes[index];
    }

    public double getR2() {
        return r2;
    }

    public void setR2(double r2) {
        this.r2 = r2;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Cromosoma{genes=[");
        for (int i = 0; i < genes.length; i++) {
            sb.append(String.format("%.3f", genes[i]));
            if (i < genes.length - 1) sb.append(", ");
        }
        sb.append("], R2=").append(String.format("%.5f", r2)).append("}");
        return sb.toString();
    }
}
