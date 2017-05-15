package evg.algorithm;

import java.util.ArrayList;

public class Nest extends ArrayList<Integer> {

    public Nest(){
        super();
    }

    public Nest(Nest nest){
        super(nest);
    }

    public Nest(int size){
        super(size);
    }

    @Override
    public int hashCode(){
        int result = 0;
        for (int i = 0; i < size(); i++) {
            result += (i + 1) * get(i);
        }
        return result;
    }
}
