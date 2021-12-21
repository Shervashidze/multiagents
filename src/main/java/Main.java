

public class Main {
    public static final int NUMBER_OF_AGENTS = 5;
    public static void main(String[] args) {
        int[][] matrix = {{0, 1, 0, 1, 0},
                          {1, 0, 0, 1, 1},
                          {1, 1, 0, 1, 1},
                          {0, 1, 1, 0, 1},
                          {0, 1, 1, 1, 0}};

        double[] values = {4, 0, 1, 3, 3};


        Controller controller = new Controller();
        controller.initAgents(matrix, values);
    }
}