package evg.algorithm;

import java.util.List;

public class SalesmanTask extends Task {

    @Override
    public Double targetFunction(List<Integer> route){
        Double distance = 0d;
        for (int i = 0; i < route.size() - 1; i++) {
            distance += matrixOfData[route.get(i)][route.get(i+1)];
        }
        distance += matrixOfData[route.get(route.size()-1)][route.get(0)];
        return distance;
    }
}
