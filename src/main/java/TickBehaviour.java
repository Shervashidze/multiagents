import jade.core.AID;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;

import java.util.concurrent.ThreadLocalRandom;

public class TickBehaviour extends TickerBehaviour {
    private final AverageAgent agent;
    private final int currentAgentAid;
    private final double CONNECTION_DROP_PROBABILITY = 0.1;
    private static final long MILLIS_TO_SET_CONNECTION = 50;

    public TickBehaviour(AverageAgent agent) {
        super(agent, 1); //TimeUnit.SECONDS.toMillis(1)
        this.agent = agent;
        this.currentAgentAid = Integer.parseInt(agent.getAID().getLocalName());
    }

    @Override
    protected void onTick() {
        if (SystemState.getInstance().isResultCounted()) {
            stop();
        }

        if (SystemState.getInstance().isUpdatedByAid(currentAgentAid)) {
            return;
        }

        if (!SystemState.getInstance().isSendByAid(currentAgentAid)) {
            for (int agentAid : agent.receivers) {
                simulateDisconnection();
                sendValueWithInterference(String.valueOf(agentAid));
            }

            SystemState.getInstance().setSentAgent(currentAgentAid);
        }

        receiveMessage();
        if (!SystemState.getInstance().checkAllSentAgents()) {
            return;
        }

        updateAgentValue();

        if (SystemState.getInstance().isAllUpdated()) {
            SystemState.getInstance().resetSentList();
            SystemState.getInstance().resetUS();
            SystemState.getInstance().resetUpdatedList();
            SystemState.getInstance().incrementCounter();
            System.out.println("");
        }

        double result = agent.getValue();
        if (SystemState.getInstance().counterFinished()) {
            System.out.println("Result = " + result);
            SystemState.getInstance().setResultCounted(true);
            stop();
        }
    }

    private void sendValueWithInterference(String agentAid) {
        ACLMessage message = new ACLMessage(ACLMessage.INFORM);
        message.addReceiver(new AID(String.valueOf(agentAid), false));
        double agentValue = agent.getValue();
        double prob = ThreadLocalRandom.current().nextDouble();
        double interference = 0;
        if (prob > 0.5) {
            interference = ThreadLocalRandom.current().nextDouble(-0.5, 0.5);
        }
        double sendValue = agentValue + interference;
        message.setContent(String.valueOf(sendValue));
        agent.send(message);
    }

    private void simulateDisconnection() {
        if (Math.random() < CONNECTION_DROP_PROBABILITY) {
            agent.doWait(MILLIS_TO_SET_CONNECTION);
        }
    }

    private void receiveMessage() {
        ACLMessage message = agent.receive();
        if (message == null) {
            return;
        }

        double curX = agent.getValue();
        double receivedX = Double.parseDouble(message.getContent());
        double delta = SystemState.getInstance().getBeta() * (receivedX - curX);
        double prevU = SystemState.getInstance().getUValue(currentAgentAid);
        SystemState.getInstance().setUValue(currentAgentAid, prevU + delta);
    }

    private void updateAgentValue() {
        double countedU = SystemState.getInstance().getUValue(currentAgentAid);
        double alpha = SystemState.getInstance().getAlpha();
        double prevValue = agent.getValue();
        double nextValue = prevValue + alpha * countedU;
        System.out.println(String.format("Agent %d set new value: %f", currentAgentAid, nextValue));
        agent.setValue(nextValue);
        SystemState.getInstance().setUpdatedByAid(currentAgentAid);
    }
}