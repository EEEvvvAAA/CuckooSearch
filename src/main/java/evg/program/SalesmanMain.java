package evg.program;

import evg.algorithm.*;
import evg.utils.Checker;
import evg.utils.Reader;

import java.util.Map;

public class SalesmanMain {

    private static TaskName taskName = TaskName.TRAVELLING_SALSMEN_PROBLEM;
    private static int N = 1000;
    private static float E = 0.5f;
    private static int ITER = 10000;
    private static float t = 2f;
    private static float a = 1.2f;

    private static String filePath = Reader.getFilePath("salesman/Salesman(20).xlsx");
    //private static String filePath = Reader.getFilePath("salesman/Salesman(6)_26.xlsx");

    public static void main(String[] args) throws Exception {
        Double[][] data = Reader.getData(filePath, false);
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

        long start_time = System.currentTimeMillis();
        Map<String, String> result = CuckooSearch.decideTask(task, data, N, E, ITER, a, t);
        long finish_time = System.currentTimeMillis();

        System.out.println("Results");
        System.out.println("Approximate solution: " + result.get("solution"));
        System.out.println("Value of target function: " + result.get("target"));
        System.out.println("Time of work: " + (finish_time - start_time));
    }
}
