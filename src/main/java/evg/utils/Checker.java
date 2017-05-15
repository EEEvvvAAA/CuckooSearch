package evg.utils;

import evg.exceptions.BadParametersException;

public class Checker {

    public static void checkParams(int N, float E, int ITER, float t, float a, int n) throws BadParametersException {
        String errors = "";
        long fact_n = fact(n);
        long N_max = Math.round(0.7*fact_n);
        if (N <= 0 || N > N_max){
            errors += "0 < N <= " + N_max + " must be";
        }
        if (E <= 0 || E >= 1){
            errors += (errors.isEmpty()) ? "0 < E < 1 must be" : "\n0 < E < 1 must be";
        }
        long ITER_max = (long) Math.floor((N_max - N)/Math.round(N*E)) + 1;
        if (N > 0 && N <= N_max && (ITER <= 0 || ITER > ITER_max)){
            errors += (errors.isEmpty()) ? "0 < ITER <= " + ITER_max + " must be" : "\n0 < ITER <= " + ITER_max + " must be";
        }
        if (t <= 1 || t > 3){
            errors += (errors.isEmpty()) ? "1 < t <= 3 must be" : "\n1 < t <= 3 must be";
        }
        double a_max = Math.min(n-1, 0.5*Math.pow(n, t));
        if (a <=0 || a >= a_max){
            errors += (errors.isEmpty()) ? "0 < a  < " + + a_max + " must be" : "\n0 < a  < " + + a_max + " must be";
        }
        if (!errors.isEmpty()) {
            errors = "Entering parameters are invalid:\n" + errors;
            throw new BadParametersException(errors);
        }
    }

    private static long fact(int n){
        if (n < 0)
            return 0;
        if (n == 1)
            return 1;
        else
            return n * fact(n-1);
    }
}
