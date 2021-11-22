import jade.core.Agent;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        int[][] matrix = {{0, 1, 0, 1, 1},
                          {1, 0, 0, 1, 1},
                          {0, 0, 0, 0, 1},
                          {1, 1, 0, 0, 1},
                          {1, 1, 1, 1, 0}};

        double[] values = {-59, 3, 17.5, 42, 10};

        int startAgent = 2;

        Controller controller = new Controller();
        controller.initAgents(matrix, values, startAgent);
    }
}