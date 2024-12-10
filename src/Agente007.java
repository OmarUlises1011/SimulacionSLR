import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;

public class Agente007 extends Agent {

    @Override
    protected void setup() {
        imprimirMensajeInicio();
        inicializarAgentesDeRegresion();
        iniciarClasificador();
        addBehaviour(new RecibirClasificacion()); // recibir la clasificación
    }

    private void imprimirMensajeInicio() {
        System.out.println("==============================================");
        System.out.println("       AGENTE PRINCIPAL: Agente007          ");
        System.out.println("==============================================");
        System.out.println("Estado: Configurando agentes de regresión...");
        System.out.println("BY:OMAR ULISES HERNANDEZ PEREZ ");
    }

    private void inicializarAgentesDeRegresion() {
        try {
            ContainerController container = getContainerController();

            AgentController agenteClasificador = container.createNewAgent(
                    "AgenteClasificador",
                    AgenteClasificador.class.getName(),
                    null
            );
            agenteClasificador.start();
            System.out.println("-> AgenteClasificador iniciado correctamente.");
            doWait(2000);

            crearAgenteDeRegresion("AgenteLinear", AgenteLinear.class);
            crearAgenteDeRegresion("AgenteCuadratica", AgenteCuadratica.class);
            crearAgenteDeRegresion("AgenteCubica", AgenteCubica.class);
            crearAgenteDeRegresion("AgenteMultiple", AgenteMultiple.class);
            crearAgenteDeRegresion("AlgoritmoG",AgenteG.class);

        } catch (StaleProxyException e) {
            System.err.println("Error al iniciar los agentes de regresión:");
            e.printStackTrace();
        }
    }

    private void crearAgenteDeRegresion(String nombreAgente, Class<?> claseAgente) {
        try {
            ContainerController container = getContainerController();
            AgentController agente = container.createNewAgent(
                    nombreAgente,
                    claseAgente.getName(),
                    null
            );
            agente.start();
            System.out.println("-> " + nombreAgente + " iniciado correctamente.");
            doWait(2000);
        } catch (StaleProxyException e) {
            e.printStackTrace();
        }
    }

    private void iniciarClasificador() {
        String variablesModelo = "x1, x2, x3, x4, x5";
        ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
        msg.addReceiver(getAID("AgenteClasificador"));
        msg.setContent(variablesModelo);
        send(msg);
        System.out.println("Enviando solicitud de clasificación al AgenteClasificador.");
    }

    private class RecibirClasificacion extends CyclicBehaviour {
        @Override
        public void action() {
            ACLMessage msg = receive();
            if (msg != null) {
                String tipoDeRegresion = msg.getContent();
                System.out.println("Clasificación recibida: " + tipoDeRegresion );


                if (tipoDeRegresion.equals("RegresionLineal")) {
                    ejecutarRegresionConGeneticAlgorithm("RegresionLineal");
                } else if (tipoDeRegresion.equals("RegresionMultiple")) {
                    ejecutarRegresionConGeneticAlgorithm("RegresionMultiple");
                } else if (tipoDeRegresion.equals("RegresionCuadratica")) {
                    ejecutarRegresionConGeneticAlgorithm("RegresionCuadratica");
                } else if (tipoDeRegresion.equals("RegresionCubica")) {
                    ejecutarRegresionConGeneticAlgorithm("RegresionCubica");
                } else {
                    System.out.println("Tipo de regresión desconocido.");
                }
            } else {
                block();
            }
        }
    }

    private void ejecutarRegresionConGeneticAlgorithm(String tipoRegresion) {
        ACLMessage mensaje = new ACLMessage(ACLMessage.REQUEST);
        mensaje.addReceiver(getAID("AlgoritmoG"));
        mensaje.setContent(tipoRegresion);
        send(mensaje);
        System.out.println("Solicitud enviada al Agente AlgoritmoG  " );
    }
}
