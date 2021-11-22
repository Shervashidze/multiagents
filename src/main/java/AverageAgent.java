import jade.core.AID;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;

import java.util.LinkedList;


public class AverageAgent extends Agent {
    private double value;
    private LinkedList<Integer> receivers;
    private LinkedList<String> visited = new LinkedList<>();
    private double result = 0;
    private String messageFrom = "";
    private boolean isStart = false;
    private int counter = 1;
    private boolean isSent = false;

    public double getValue() {
        return value;
    }

    public double getResult() {
        return result;
    }

    public int getCounter() {
        return counter;
    }

    public void handleAdd(double add, String from) {
        if (visited.contains(from)) {
            return;
        }
        result+=add;
        counter++;
    }

    public LinkedList<Integer> getReceivers() {
        return receivers;
    }

    public void sendGetAllMessage(String from) {
        receivers.forEach(r -> {
            if (r.equals(from)) return;
            ACLMessage aclMsg = new ACLMessage(ACLMessage.INFORM);

            aclMsg.setContent("get");
            aclMsg.addReceiver(new AID(String.valueOf(r), false));
            aclMsg.setSender(getAID());

            send(aclMsg);
        });
    }

    public void sendMessageTo(String to, Double msg, String from) {
        if (isStart) return;
        ACLMessage aclMsg = new ACLMessage(ACLMessage.INFORM);

        aclMsg.setContent(String.valueOf(msg) + " " + from);
        aclMsg.addReceiver(new AID(String.valueOf(to), false));
        aclMsg.setSender(getAID());

        send(aclMsg);
    }

    public void sendMyValue(String to, Double msg) {
        if (isSent) return;
        if (isStart) return;
        isSent = true;
        if (to.equals(getLocalName())) return;
        ACLMessage aclMsg = new ACLMessage(ACLMessage.INFORM);

        aclMsg.setContent(msg + " " + this.getLocalName());
        aclMsg.addReceiver(new AID(String.valueOf(to), false));
        aclMsg.setSender(getAID());

        send(aclMsg);
    }

    @Override
    protected void setup() {
        Object[] arguments = getArguments();
        value = (double) arguments[0];
        result = (double) arguments[0];
        receivers = (LinkedList<Integer>) arguments[1];
        isStart = (boolean) arguments[2];
        visited.add(this.getLocalName());

        addBehaviour(new ReceiverBehaviour(this));
        addBehaviour(new SenderBehaviour(this));
        System.out.println("Agent: " + getAID().getLocalName() + " with value = " + value + " with neighbours = " + receivers + " is created");
    }

    public String getMessageFrom() {
        return messageFrom;
    }

    public void setMessageFrom(String messageFrom) {
        this.messageFrom = messageFrom;
    }

    public boolean isStart() {
        return isStart;
    }

    public void setStart(boolean start) {
        isStart = start;
    }
}