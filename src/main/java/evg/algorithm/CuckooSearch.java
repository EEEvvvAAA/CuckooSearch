package evg.algorithm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CuckooSearch {

    public static Map<String, String> decideTask(Task task, Double[][] data, int N, float E, int ITER, float a, float t) throws Exception {
        Population population = new Population(task, data[0].length, N, E);
        Cuckoo cuckoo = new Cuckoo(task, a, t);
        task.setMatrixOfData(data);

        for (int i = 0; i < ITER; i++) {
            Map.Entry<List<Integer>, Double> bestNestInPopulation = population.generate();
            cuckoo.generateCuckoo(bestNestInPopulation);
            cuckoo.randomChooseOfNest(population);
            population.naturalSelection();
        }

        Map.Entry<List<Integer>, Double> bestNestInPopulation = population.getBestNest();
        Map<String, String> result = new HashMap<String, String>();
        result.put("solution", getResult(bestNestInPopulation.getKey()));
        result.put("target", bestNestInPopulation.getValue() + "");
        return result;
    }

    private static String getResult(List<Integer> result){
        String resultString = "{";
        for (int i = 0; i < result.size()-1; i++) {
            resultString += result.get(i) + ", ";
        }
        resultString += result.get(result.size()-1) + "}";
        return resultString;
    }
}
