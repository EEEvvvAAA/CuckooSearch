package evg.algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Cuckoo {

    private List<Integer> position;
    private Double estimate;

    private Task task;
    private float a;
    private float t;

    public Cuckoo(Task task, float a, float t){
        this.task = task;
        this.a = a;
        this.t = t;
    }

    public List<Integer> generateCuckoo(Map.Entry<List<Integer>, Double> bestNestInPopulation) {
        position = new ArrayList<Integer>(bestNestInPopulation.getKey());
        estimate = bestNestInPopulation.getValue();
        return LevyFlight();
    }

    private List<Integer> LevyFlight(){
        for (int i = 0; i < position.size(); i++) {
            position.set(i, (int) Math.round(position.get(i) + 1 + a * Math.pow(position.get(i) + 1, -t)) - 1);
        }
        return uniquePosition();
    }

    private List<Integer> uniquePosition(){
        int[] tmp = new int[position.size()];
        for (int i = 0; i < position.size(); i++) {
            tmp[position.get(i)] += 1;
        }
        for (int i = 0; i < tmp.length; i++) {
            if (tmp[i] == 0){
                for (int j = 0; j < tmp.length; j++) {
                    if (tmp[j] > 1){
                        for (int k = 0; k < position.size(); k++) {
                            if (position.get(k) == j) {
                                position.set(k, i);
                                break;
                            }
                        }
                        tmp[j] -= 1;
                        break;
                    }
                }
            }

        }
        return position;
    }

    public void randomChooseOfNest(Population population){
        int random_nest = (int) Math.floor(Math.random() * population.getNests().size());
        int i = 0;
        for (Map.Entry<List<Integer>, Double> entry : population.getNests().entrySet()){
            if (i == random_nest){
                Double random_nest_estimate = entry.getValue();
                if (estimate < random_nest_estimate) {
                    population.getNests().remove(entry.getKey());
                    population.getNests().put(position, estimate);
                }
                return;
            }
            i++;
        }

    }

    public Double getEstimate() {
        return estimate;
    }
}
