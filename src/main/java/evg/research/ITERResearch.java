package evg.research;

import evg.algorithm.*;
import evg.utils.Checker;
import evg.utils.Reader;

import java.io.FileOutputStream;
import java.util.Map;

public class ITERResearch {
    private static TaskName taskName = TaskName.TRAVELLING_SALSMEN_PROBLEM;
    private static int N = 100;
    private static float E = 0.5f;
    private static float t = 2f;
    private static float a = 1.2f;

    private static String filePath = Reader.getFilePath("salesman/Salesman(20).xlsx");
    private static Double optimSolution = 71d;

    private static int[] ITER = {100, 300, 500, 700, 1000, 1500, 2000, 3000, 5000, 10000};
    private static long[] averageTimes = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    private static Double[] averageResults = {0d, 0d, 0d, 0d, 0d, 0d, 0d, 0d, 0d, 0d};

    public static void main(String[] args) throws Exception {
        Double[][] data = Reader.getData(filePath, false);
        int n = data[0].length;

        Task task = null;
        switch (taskName){
            case TRAVELLING_SALSMEN_PROBLEM:
                task = new SalesmanTask();
                break;
            case THREE_MACHINE_SHEDULLING_PROBLEM:
                task = new ThreeMachineTask();
                break;
        }

        FileOutputStream fos = new FileOutputStream("ITERResearch.txt");
        byte[] buffer;
        String parametersStr = "Parameters:\nN = " + N + "\nE = " + E + "\nt = " + t + "\na = " + a +
                "\n+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n\n";
        buffer = parametersStr.getBytes();
        fos.write(buffer);

        for (int i = 0; i < 10; i++) {
            long start_time, finish_time, time;

            String iterNumStr = "Iteration № " + (i + 1) + "\n";
            buffer = iterNumStr.getBytes();
            fos.write(buffer);

            for (int j = 0; j < ITER.length; j++) {
                Checker.checkParams(N, E, ITER[j], t, a, n);
                start_time = System.currentTimeMillis();
                Map<String, String> result = CuckooSearch.decideTask(task, data, N, E, ITER[j], a, t);
                finish_time = System.currentTimeMillis();
                time = finish_time - start_time;

                averageTimes[j] += time;

                StringBuilder testStr = new StringBuilder();

                String testNumStr = "Test № " + (j + 1) + " - ITER = " + ITER[j] + "\n";
                String timeWorkStr = "Time of work:" + time + "\n";
                String resultsStr = "Results" + "\n";
                String apprSolStr = "Approximate solution: " + result.get("solution") + "\n";

                testStr.append(testNumStr).append(timeWorkStr).append(resultsStr).append(apprSolStr);

                String targetStr = result.get("target");
                Double target = Double.parseDouble(targetStr);
                averageResults[j] += target;

                String valTargStr = "Value of target function: " + targetStr + "\n";
                String percentStr = "Coincidence with optimality solution: " + (100*optimSolution/target) + "%" + "\n";
                String endTestStr = "-----------------------------------------------------------------------" + "\n\n";

                testStr.append(valTargStr).append(percentStr).append(endTestStr);

                buffer = testStr.toString().getBytes();
                fos.write(buffer);
            }

            String starsStr = "************************************************************************************************\n\n";
            buffer = starsStr.getBytes();
            fos.write(buffer);
        }

        String averageStr = "Average results:\n";
        buffer = averageStr.getBytes();
        fos.write(buffer);
        for (int i = 0; i < ITER.length; i++) {
            String ITERStr = "ITER = " + ITER[i] + "\n";
            String averageTimeStr = "\tAverage time: " + (averageTimes[i]/ITER.length) + "\n";
            String averageResStr = "\tAverage result: " + (averageResults[i]/ITER.length) + "\n";
            String averageCoincStr = "\tAverage coincidence with optimality solution: " + (100*optimSolution/(averageResults[i]/ITER.length)) + "\n\n";
            String averageRes = ITERStr + averageTimeStr + averageResStr + averageCoincStr;
            buffer = averageRes.getBytes();
            fos.write(buffer);
        }
    }
}
