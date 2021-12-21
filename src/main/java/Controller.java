import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;

import java.util.LinkedList;


class Controller {
    private final static String AGENT_CLASS = "AverageAgent";
    private final static String LOCALHOST = "localhost";
    private final static String PORT = "10098";
    private final static String GUI = "false";

    public void initAgents(int[][] matrix, double[] values) {
        Runtime rt = Runtime.instance();
        Profile p = new ProfileImpl();
        p.setParameter(Profile.MAIN_HOST, LOCALHOST);
        p.setParameter(Profile.MAIN_PORT, PORT);
        p.setParameter(Profile.GUI, GUI);
        ContainerController cc = rt.createMainContainer(p);
        try {
            int agentsNumber = values.length;

            for (int i = 0; i < agentsNumber; i++) {
                LinkedList<Integer> neighbours = new LinkedList<>();
                for (int j = 0; j < agentsNumber; j++) {
                    if (matrix[i][j] == 1) {
                        neighbours.add(j);
                    }
                }
                double agentValue = values[i];

                Object[] setupArguments = new Object[] {agentValue, neighbours};
                AgentController agentController = cc.createNewAgent(String.valueOf(i), AGENT_CLASS, setupArguments);
                agentController.start();
            }
        } catch (StaleProxyException e) {
            System.out.println("Error during creating agents.");
        }
    }
}