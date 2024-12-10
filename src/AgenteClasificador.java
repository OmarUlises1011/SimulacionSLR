import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class AgenteClasificador extends Agent {

    @Override
    protected void setup() {
        System.out.println("AgenteClasificador: Iniciado.");
        addBehaviour(new ClasificarProblema());
    }

    private class ClasificarProblema extends CyclicBehaviour {
        @Override
        public void action() {
            ACLMessage msg = receive();
            if (msg != null) {
                String content = msg.getContent();
                System.out.println("AgenteClasificador: Recibido " + content);

                // Clasificar según el número de variables
                String tipoDeRegresion = clasificarSegunNumeroDeVariables(content);

                // Enviar la técnica de análisis al agente que solicitó
                ACLMessage respuesta = new ACLMessage(ACLMessage.INFORM);
                respuesta.setContent(tipoDeRegresion);
                respuesta.addReceiver(msg.getSender());
                send(respuesta);
                System.out.println("AgenteClasificador: Respuesta enviada a " + msg.getSender().getName());
            } else {
                block();
            }
        }

        private String clasificarSegunNumeroDeVariables(String content) {
            String[] valores = content.split(",");
            int numeroDeVariables = valores.length;

            if (numeroDeVariables == 2) {
                return "RegresionLineal";
            } else if (numeroDeVariables == 3) {
                return "Regresioncuadratica";
            } else if (numeroDeVariables == 4) {
                return "RegresionCubica";
            } else if (numeroDeVariables >= 5) {
                return "RegresionMultiple";
            } else {
                return "Tipo de regresión no identificado";
            }
        }
    }
}
