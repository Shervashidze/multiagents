import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
public class SystemState {
    private static final int AGENT_NUMBER = Main.NUMBER_OF_AGENTS;

    private double alpha = 1d / AGENT_NUMBER;

    private double beta = 1;

    private int counter = 0;

    private static final int MAX_COUNTER = 1000;

    private List<Boolean> sentAgentsList = new ArrayList<>(Collections.nCopies(AGENT_NUMBER, false));

    private List<Boolean> updatedAgentsList = new ArrayList<>(Collections.nCopies(AGENT_NUMBER, false));

    private List<Double> uList = new ArrayList<>(Collections.nCopies(AGENT_NUMBER, 0.0));

    private static SystemState self = new SystemState();

    private boolean isResultCounted = false;

    public double getAlpha() {
        return alpha;
    }

    public double getBeta() {
        return beta;
    }

    public void setResultCounted(boolean b) {
        isResultCounted = b;
    }

    public boolean isResultCounted() {
        return isResultCounted;
    }

    public static SystemState getInstance() { return self; }

    public void incrementCounter() {
        counter++;
    }

    public boolean counterFinished() {
        return counter >= MAX_COUNTER;
    }

    public boolean isSendByAid(int id) {
        return sentAgentsList.get(id);
    }

    public boolean isUpdatedByAid(int aid) {
        return updatedAgentsList.get(aid);
    }

    public void setUpdatedByAid(int aid) {
        updatedAgentsList.set(aid, true);
    }

    public boolean isAllUpdated() {
        return !updatedAgentsList.contains(false);
    }

    public void resetUpdatedList() {
        updatedAgentsList = new ArrayList<>(Collections.nCopies(AGENT_NUMBER, false));
    }

    public boolean checkAllSentAgents() {
        return !sentAgentsList.contains(false);
    }

    public void setSentAgent(int id) {
        sentAgentsList.set(id, true);
    }

    public double getUValue(int id) {
        return uList.get(id);
    }

    public void setUValue(int id, double val) {
        uList.set(id, val);
    }

    public void resetSentList() {
        sentAgentsList = new ArrayList<>(Collections.nCopies(AGENT_NUMBER, false));
    }

    public void resetUS() {
        uList = new ArrayList<>(Collections.nCopies(AGENT_NUMBER, 0.0));
    }
}