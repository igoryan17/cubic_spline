package igoryan.vichmaty;

/**
 * Created by igoryan on 11.12.15.
 */
public class Interval {
    private final double begin;
    private final double end;
    public final double length;

    public Interval(double begin, double end) {
        this.begin = begin;
        this.end = end;
        length = end - begin;
    }

    public double getBegin() {
        return begin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Interval interval = (Interval) o;

        if (Double.compare(interval.begin, begin) != 0) return false;
        return Double.compare(interval.end, end) == 0;

    }

    public double getEnd() {
        return end;
    }
}
