package igoryan.vichmaty;

/**
 * Created by igoryan on 11.12.15.
 */
public class CubicSpline implements Function{
    private final double x;
    private final double a;
    private final double b;
    private final double c;
    private final double d;
    private final Interval interval;

    public CubicSpline(double x, double a, double b, double c, double d, Interval interval) {
        this.x = x;
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.interval = interval;
    }

    public double getValue(double x) {
        double difference = x - this.x;
        return a + b * (difference) + c * Math.pow(difference, 2) / 2.0 + d * Math.pow(difference, 3) / 6.0;
    }

    public Interval getInterval() {
        return interval;
    }
}
