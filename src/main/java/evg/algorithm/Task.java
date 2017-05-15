package evg.algorithm;

import java.util.List;

public abstract class Task {

    public Double[][] matrixOfData;

    public void setMatrixOfData(Double[][] matrixOfData){
        this.matrixOfData = matrixOfData;
    }

    public abstract Double targetFunction(List<Integer> decision);
}
