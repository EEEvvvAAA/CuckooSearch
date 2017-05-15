package evg.program;

import evg.algorithm.*;
import evg.utils.Checker;
import evg.utils.Reader;

import java.util.Map;

public class ThreeMachineMain {

    private static TaskName taskName = TaskName.THREE_MACHINE_SHEDULLING_PROBLEM;
    private static int N = 10;
    private static float E = 0.5f;
    private static int ITER = 10;
    private static float t = 2f;
    private static float a = 1.2f;

    private static String filePath = Reader.getFilePath("threemachine/ThreeMachine(5)_20.xlsx");

    public static void main(String[] args) throws Exception {
        Double[][] data = Reader.getData(filePath, true);
        int n = data[0].length;

        Checker.checkParams(N, E, ITER, t, a, n);

        Task task = null;
        switch (taskName){
            case TRAVELLING_SALSMEN_PROBLEM:
                task = new SalesmanTask();
                break;
            case THREE_MACHINE_SHEDULLING_PROBLEM:
                task = new ThreeMachineTask();
                break;
        }

        Map<String, String> result = CuckooSearch.decideTask(task, data, N, E, ITER, a, t);

        System.out.println("Results");
        System.out.println("Approximate solution: " + result.get("solution"));
        System.out.println("Value of target function: " + result.get("target"));
    }
}
