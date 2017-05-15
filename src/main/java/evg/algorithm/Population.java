package evg.algorithm;

import java.util.*;

public class Population {

    private Map<List<Integer>, Double> nests = new HashMap<List<Integer>, Double>();

    private List<Integer> template;
    private Set<List<Integer>> cacheNests;

    private Task task;
    private int numOfNests;
    private float E;

    public Population(Task task, int numOfVariables, int numOfNests, float E){
        this.task = task;
        this.template = new ArrayList<Integer>(numOfVariables);
        for (int i = 0; i < numOfVariables; i++) {
            this.template.add(i);
        }
        this.numOfNests = numOfNests;
        this.E = E;

        cacheNests = new HashSet<List<Integer>>();
    }

    // return best Nest in population
    public Map.Entry<List<Integer>, Double> generate(){
        int num_nests = numOfNests - nests.size();
        return generateSequence(num_nests);
    }

    private Map.Entry<List<Integer>, Double> generateSequence(int n){
        Double target;
        Map.Entry<List<Integer>, Double> best = getBestNest();
        List<Integer> bestNest = best.getKey();
        Double min = best.getValue();
        for (int i = 0; i < n; ++i) {
            List<Integer> nest = new ArrayList<Integer>(template);
            do {
                Collections.shuffle(nest);
            } while (!cacheNests.add(nest));
            target = task.targetFunction(nest);
            nests.put(nest, target);
            if (min == null || target < min) {
                min = target;
                bestNest = nest;
            }
        }
        return new AbstractMap.SimpleEntry<List<Integer>, Double>(bestNest, min);
    }

    public void naturalSelection(){
        arrangeNests();
        removeWorstNests();
    }

    private void removeWorstNests(){
        int count = Math.round(nests.size() * E);
        List<List<Integer>> removedNests = new ArrayList<List<Integer>>();
        int i = 0;
        for (Map.Entry<List<Integer>, Double> entry : nests.entrySet()){
            if (nests.size() - count < i)
                removedNests.add(entry.getKey());
            i++;
        }
        for (List<Integer> removedNest : removedNests) {
            nests.remove(removedNest);
        }
    }

    public Map<List<Integer>, Double> arrangeNests() {
        List<Map.Entry<List<Integer>, Double>> sortedList = new ArrayList<Map.Entry<List<Integer>, Double>>(nests.entrySet());
        Collections.sort(sortedList, new Comparator<Map.Entry<List<Integer>, Double>>() {
            @Override
            public int compare(Map.Entry<List<Integer>, Double> entry1, Map.Entry<List<Integer>, Double> entry2) {
                return entry1.getValue().compareTo(entry2.getValue());
            }
        });
        nests = new HashMap<List<Integer>, Double>();
        for (int i = 0; i < sortedList.size(); i++) {
            Map.Entry<List<Integer>, Double> entry = sortedList.get(i);
            nests.put(entry.getKey(), entry.getValue());
        }
        return nests;
    }

    public Map.Entry<List<Integer>, Double> getBestNest(){
        List<Integer> bestNest = null;
        Double min = null;
        for (Map.Entry<List<Integer>, Double> entry : nests.entrySet()){
            List<Integer> nest = entry.getKey();
            Double target = task.targetFunction(nest);
            if (min == null || target < min) {
                min = target;
                bestNest = nest;
            }
        }
        return new AbstractMap.SimpleEntry<List<Integer>, Double>(bestNest, min);
    }

    public Map<List<Integer>, Double> getNests() {
        return nests;
    }

}
