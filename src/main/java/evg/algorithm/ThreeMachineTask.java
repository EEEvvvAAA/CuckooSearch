package evg.algorithm;

import java.util.List;

public class ThreeMachineTask extends Task {

    @Override
    public Double targetFunction(List<Integer> priority){
        int n = priority.size();
        Double[][] matrix = new Double[3][n];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = matrixOfData[i][priority.get(j)];
            }
        }
        int[] st = {0, -1, -1};
        int[] m = new int[3];
        Double result = 0d;
        while (matrix[2][n-1] != 0){
            Double min = minimum(matrix, st);
            result += min;
            for (int i = 0; i < 3; i++) {
                if (st[i] != -1 && st[i] < n && matrix[i][st[i]] != 0){
                    matrix[i][st[i]] -= min;
                }
            }
            m[0] = 0; m[1] = 0; m[2] = 0;
            if (st[0] != -1 && st[0] < n && matrix[0][st[0]] == 0 ){
                m[0] = 1;
                if (st[1] == -1)
                    m[1] = 1;
            }
            if (st[1] != -1 && st[1] < n && matrix[1][st[1]] == 0
                    && (st[1] < n-1 && matrix[0][st[1]+1] == 0)){
                m[1] = 1;
                if (st[2] == -1)
                    m[2] = 1;
            }
            if (st[2] != -1 && st[2] < n && matrix[2][st[2]] == 0
                    && (st[2] < n-1 && matrix[1][st[2]+1] == 0)) {
                m[2] = 1;
            }
            for (int i = 0; i < 3; i++) {
                st[i] += m[i];
            }
        }
        return result;
    }
    
    private static Double minimum(Double[][] matrix, int[] st){
        Double res;
        if (st[0] >= matrix[0].length || matrix[0][st[0]] == 0)
            res = 100000d;
        else res = matrix[0][st[0]];
        if (st[1] == -1 || st[1] >= matrix[0].length || matrix[1][st[1]] == 0)
            res = Math.min(res, 100000);
        else res = Math.min(res, matrix[1][st[1]]);
        if (st[2] == -1 || st[2] >= matrix[0].length || matrix[2][st[2]] == 0)
            res = Math.min(res, 100000);
        else res = Math.min(res, matrix[2][st[2]]);
        return res;
    }
}
