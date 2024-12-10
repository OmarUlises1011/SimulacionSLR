import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import java.util.List;

public class AgenteCubica extends Agent {

    @Override
    protected void setup() {
        System.out.println("AgenteCubica iniciado...");
        realizarRegresionCubica();
    }

    private void realizarRegresionCubica() {
        List<Dataset> data = Dataset.getExampleData();
        DiscretMath discretMath = new DiscretMath();
        discretMath.fitCubic(data);

        double beta0 = discretMath.getBeta0();
        double beta1 = discretMath.getBeta1();
        double beta2 = discretMath.getBeta2();
        double beta3 = discretMath.getBeta3();

        double rSquared = CalculoRSquared.calcularRSquaredCubico(data, beta0, beta1, beta2, beta3);
        System.out.println("R^2 de la regresión cúbica: " + rSquared);

        String resultado = "Resultado de la regresión cúbica: Beta0 = " + beta0 + ", Beta1 = " + beta1 + ", Beta2 = " + beta2 + ", Beta3 = " + beta3 + ", R^2 = " + rSquared;

        ACLMessage mensaje = new ACLMessage(ACLMessage.INFORM);
        mensaje.addReceiver(getAID("AgenteClasificador"));
        mensaje.setContent(resultado);
        send(mensaje);
        System.out.println("Resultado de regresión cúbica enviado al AgenteClasificador.");

        double[] advertisingToPredict = {40.0, 45.0, 50.0, 55.0, 60.0};
        StringBuilder predicciones = new StringBuilder();
        for (double advertising : advertisingToPredict) {
            double predictedSales = beta0 + beta1 * advertising + beta2 * Math.pow(advertising, 2) + beta3 * Math.pow(advertising, 3);
            predicciones.append("Advertising = ").append(advertising)
                    .append(", Predicción para Sales (Cúbica) = ").append(predictedSales).append("\n");
        }

        ACLMessage mensajePredicciones = new ACLMessage(ACLMessage.INFORM);
        mensajePredicciones.addReceiver(getAID("AgenteClasificador"));
        mensajePredicciones.setContent(predicciones.toString());
        send(mensajePredicciones);
        System.out.println("Predicciones enviadas al AgenteClasificador.");
    }
}
