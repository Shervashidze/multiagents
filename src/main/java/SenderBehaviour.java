import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

public class SenderBehaviour extends OneShotBehaviour {
    private final AverageAgent agent;

    public SenderBehaviour(AverageAgent agent) {
        this.agent = agent;
    }

    @Override
    public void action() {
        if (agent.isStart()) {
            agent.sendGetAllMessage(agent.getLocalName());
            block();
        }
    }
}