package igoryan.vichmaty;

/**
 * Created by igoryan on 12.12.15.
 */
public class LogarithmicScale implements Function {
    private Function infelicity = new Infelicity(new StartFunction().getInterval());
    private final Interval interval = new Interval(Math.pow(10, -6), 1);

    public double getValue(double x) {
        return Math.log(infelicity.getValue(x));
    }

    public Interval getInterval() {
        return interval;
    }
}
