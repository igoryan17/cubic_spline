package igoryan.vichmaty;

/**
 * Created by igoryan on 12.12.15.
 */
public class Infelicity implements Function {
    private final Function func = new StartFunction();
    private Interval interval;
    private final double point = 3.7;

    public Infelicity(Interval interval) {
        this.interval = interval;
    }

    public double getValue(double x) {
        int n = (int) Math.round(interval.length / x);
        Function spline1 = new Interpolyant(n, func);
        Function spline2 = new Interpolyant(n / 2, func);
        return Math.abs(spline2.getValue(point) - spline1.getValue(point));
    }

    public Interval getInterval() {
        return null;
    }
}
