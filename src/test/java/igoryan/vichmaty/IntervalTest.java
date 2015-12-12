package igoryan.vichmaty;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by igoryan on 11.12.15.
 */
public class IntervalTest {
    private static final double DELTA = Math.pow(10, -9);
    @Test
    public void constructorTest() {
        Interval interval = new Interval(0, 1);
        assertEquals(0, interval.getBegin(), DELTA);
        assertEquals(1, interval.length, DELTA);
        assertEquals(1, interval.getEnd(), DELTA);
    }
}
