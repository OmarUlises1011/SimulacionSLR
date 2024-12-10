import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class AgenteG extends Agent {
    @Override
    protected void setup() {
        addBehaviour(new RecibirSolicitud());
    }

    private class RecibirSolicitud extends CyclicBehaviour {
        @Override
        public void action() {
            ACLMessage msg = receive();
            if (msg != null) {
                double[] X = {1, 2, 3, 4, 5, 6, 7, 8, 9};
                double[] Y = {6, 12, 18, 24, 30, 36, 42, 48, 54,};

                AlgoritmoGenetico algoritmo = new AlgoritmoGenetico(700, 0.9, 0.05, 2, 700, X, Y);
                double[] mejoresParametros = algoritmo.ejecutar();

                ACLMessage respuesta = msg.createReply();
                respuesta.setContent("Mejores parámetros: Beta0 = " + mejoresParametros[0] + ", Beta1 = " + mejoresParametros[1]);
                send(respuesta);
            } else {
                block();
            }
        }
    }
}
