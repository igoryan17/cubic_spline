package igoryan.vichmaty;

import static org.apache.commons.math3.util.ArithmeticUtils.factorial;

/**
 * Created by igoryan on 11.12.15.
 */
public class StartFunction implements Function {
    private static final double A = 0;
    private static final double B = 10;
    private static final long N = 8;
    private final Interval interval = new Interval(A, B);

    public double getValue(double x) {
        double sum = 0;
        for (int n = 0; n < N; ++n) {
            int helpMember = 2 * n + 1;
            int factor = (n % 2 == 0) ? 1 : -1;
            sum += factor * Math.pow(x, helpMember) / (factorial(helpMember) * (helpMember));
        }
        return sum;
    }

    public Interval getInterval() {
        return interval;
    }

}
