import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import java.util.List;

public class AgenteLinear extends Agent {

    @Override
    protected void setup() {
        System.out.println("AgenteLinear iniciado...");
        realizarRegresionLineal();
    }

    private void realizarRegresionLineal() {
        List<Dataset> data = Dataset.getExampleData();
        DiscretMath discretMath = new DiscretMath();
        discretMath.fitLinear(data);

        double beta0 = discretMath.getBeta0();
        double beta1 = discretMath.getBeta1();

        double rSquared = CalculoRSquared.calcularRSquaredLineal(data, beta0, beta1);
        System.out.println("R^2 de la regresión lineal: " + rSquared);

        String resultado = "Resultado de la regresión lineal: Beta0 = " + beta0 + ", Beta1 = " + beta1 + ", R^2 = " + rSquared;

        ACLMessage mensaje = new ACLMessage(ACLMessage.INFORM);
        mensaje.addReceiver(getAID("AgenteClasificador"));
        mensaje.setContent(resultado);
        send(mensaje);
        System.out.println("Resultado de regresión lineal enviado al AgenteClasificador.");

        double[] advertisingToPredict = {40.0, 45.0, 50.0, 55.0, 60.0};
        StringBuilder predicciones = new StringBuilder();
        for (double advertising : advertisingToPredict) {
            double predictedSales = beta0 + beta1 * advertising;
            predicciones.append("Advertising = ").append(advertising)
                    .append(", Predicción para Sales (Lineal) = ").append(predictedSales).append("\n");
        }

        ACLMessage mensajePredicciones = new ACLMessage(ACLMessage.INFORM);
        mensajePredicciones.addReceiver(getAID("AgenteClasificador"));
        mensajePredicciones.setContent(predicciones.toString());
        send(mensajePredicciones);
        System.out.println("Predicciones enviadas al AgenteClasificador.");
    }
}
