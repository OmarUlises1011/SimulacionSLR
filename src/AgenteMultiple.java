import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import java.util.List;

public class AgenteMultiple extends Agent {

    @Override
    protected void setup() {
        System.out.println("AgenteMultiple iniciado...");
        realizarRegresionMultiple();
    }

    private void realizarRegresionMultiple() {

        List<Dataset> data = Dataset.getExampleData();

        if (data.isEmpty()) {
            System.out.println("No hay datos disponibles para la regresión múltiple.");
            return;
        }

        DiscretMathM discretMath = new DiscretMathM();

        try {
            // Ajuste del modelo de regresión múltiple
            discretMath.fit(data);

            //  Beta (beta0, beta1, beta2)
            double beta0 = discretMath.getBeta0();
            double beta1 = discretMath.getBeta1();
            double beta2 = discretMath.getBeta2();

            //  resultado de la regresión
            String resultado = "Resultado de la regresión múltiple: Beta0 = " + beta0 + ", Beta1 = " + beta1 + ", Beta2 = " + beta2;

            // Realización de múltiples predicciones
            double[] advertisingToPredict = {30.0, 40.0, 58.0, 60.0, 70.0};  // Lista de valores de Advertising
            double otherVariable = 900.0;  // Valor fijo  otra variable

            StringBuilder predicciones = new StringBuilder();
            for (double advertising : advertisingToPredict) {
                //  predicción para cada valor de advertising
                double predictedSales = beta0 + beta1 * advertising + beta2 * Math.pow(advertising, 2);
                predicciones.append("Advertising = ").append(advertising)
                        .append(", Predicción para Sales (Multiple) = ").append(predictedSales).append("\n");
            }

            resultado += "\n" + predicciones.toString();

            // Enviar el resultado al AgenteClasificador
            ACLMessage mensaje = new ACLMessage(ACLMessage.INFORM);
            mensaje.addReceiver(getAID("AgenteClasificador"));
            mensaje.setContent(resultado);
            send(mensaje);

            System.out.println("Resultado de regresión múltiple y predicciones enviados al AgenteClasificador.");

        } catch (Exception e) {
            System.out.println("Error al realizar la regresión múltiple: " + e.getMessage());
        }
    }
}
