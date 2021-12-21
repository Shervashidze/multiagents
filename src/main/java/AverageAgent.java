import jade.core.Agent;

import java.util.LinkedList;


public class AverageAgent extends Agent {
    private double value;
    public LinkedList<Integer> receivers;

    @Override
    protected void setup() {
        Object[] arguments = getArguments();
        value = (double) arguments[0];
        receivers = (LinkedList<Integer>) arguments[1];

        addBehaviour(new TickBehaviour(this));
        System.out.println("Agent: " + getAID().getLocalName() + " with value = " + value + " with neighbours = " + receivers + " is created");
    }

    public synchronized double getValue() {
        return value;
    }

    public synchronized void setValue(double value) {
        this.value = value;
    }
}