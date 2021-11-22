import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;


public class ReceiverBehaviour extends CyclicBehaviour {
    private final AverageAgent agent;

    public ReceiverBehaviour(AverageAgent agent) {
        this.agent = agent;
    }

    @Override
    public void action() {
        ACLMessage msg = agent.receive();
        if (msg == null) return;
        if (msg.getContent().equals("get")) {
            if (agent.getMessageFrom().equals("")) {
                agent.setMessageFrom(msg.getSender().getLocalName());
                agent.sendGetAllMessage(agent.getLocalName());
                agent.sendMyValue(agent.getMessageFrom(), agent.getValue());
            }
        } else {
            if (agent.isStart()) {
                System.out.println("Received from " + msg.getContent().split(" ")[1]);
                agent.handleAdd(Double.parseDouble(msg.getContent().split(" ")[0]), msg.getContent().split(" ")[1]);
                System.out.println("Current avg is " + agent.getResult() / agent.getCounter() +
                        " for number of agents " + agent.getCounter());
            } else {
                agent.sendMessageTo(agent.getMessageFrom(), Double.parseDouble(msg.getContent().split(" ")[0]), msg.getContent().split(" ")[1]);
            }
        }
    }
}