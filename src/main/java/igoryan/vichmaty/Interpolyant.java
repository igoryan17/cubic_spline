package igoryan.vichmaty;

import org.apache.commons.math3.linear.*;

/**
 * Created by igoryan on 11.12.15.
 */
public class Interpolyant implements Function {
    private final int countSplines;
    private final Function func;
    private final Interval interval;
    private CubicSpline[] splines;
    private Interval[] intervals;
    private final double h;
    private RealVector constants;
    private RealMatrix coefficients;
    private RealVector solution;

    public Interpolyant(int n, Function func) {
        this.countSplines = n;
        this.func = func;
        this.interval = func.getInterval();
        splines = new CubicSpline[countSplines];
        h = createH();
        createIntervals();
        constants = new ArrayRealVector(createRightPart(), false);
        coefficients = new Array2DRowRealMatrix(createMatrix(), false);
        solution = solve();
        writeCoefficients();
    }

    public double createH() {
        return interval.length / (double) countSplines;
    }

    public double getValue(double x) {
        for (int i = 0; i < intervals.length; ++i) {
            if (intervals[i].getBegin() <= x && x <= intervals[i].getEnd()) {
                return splines[i].getValue(x);
            }
        }
        return 0;
    }

    public Interval getInterval() {
        return interval;
    }

    public void createIntervals() {
        intervals = new Interval[countSplines];
        double x = interval.getBegin();
        for (int i = 0; i < countSplines; ++i) {
            intervals[i] = new Interval(x, x += h);
        }
    }

    public Interpolyant() {
        countSplines = 3;
        func = new StartFunction();
        interval = new Interval(0, 3);
        h = createH();
        createIntervals();
    }

    public double[] createRightPart() {
        double[] res = new double[countSplines + 1];
        res[0] = 0;
        res[countSplines - 1] = 0;
        for (int i = 1; i < (res.length - 1); ++i) {
            res[i] = 6 * (functionValue(i+ 1) - 2 * functionValue(i) + functionValue(i - 1)) / h;
        }
        return res;
    }

    public double[][] createMatrix() {
        int n = countSplines + 1;
        double[][] matrix = new double[n][n];
        for (int j = 1; j < (n - 1); ++j) {
            matrix[0][j] = 0;
            matrix[n - 1][j] = 0;
        }
        matrix[0][n - 1] = 0;
        matrix[0][0] = 1;
        matrix[n - 1][0] = 0;
        matrix[n - 1][n - 1] = 1;
        for (int i = 1; i < (n - 1); ++i) {
            for (int j = 0; j < n; ++j) {
                boolean flag = true;
                if (i == j) {
                    flag = false;
                    matrix[i][j] = 4 * h;
                }
                if (j == (i - 1)) {
                    flag = false;
                    matrix[i][j] = h;
                }
                if (j == (i + 1)) {
                    matrix[i][j] = h;
                }
                if (flag) {
                    matrix[i][j] = 0;
                }
            }
        }
        return matrix;
    }

    public RealVector solve() {
        DecompositionSolver solver = new LUDecomposition(coefficients).getSolver();
        return solver.solve(constants);
    }

    public void writeCoefficients() {
        for (int i = 0; i < splines.length; ++i) {
            double d = (solution.getEntry(i + 1) - solution.getEntry(i)) / h;
            double a = func.getValue(intervals[i].getEnd());
            double b = ((functionValue(i + 1) - functionValue(i)) / h) -
                    h * (solution.getEntry(i + 1) + 2 * solution.getEntry(i)) / 6.0;
            splines[i] = new CubicSpline(intervals[i].getEnd(), a, b, solution.getEntry(i + 1), d, intervals[i]);
        }
    }

    public double functionValue(int i) {
        if (i >= interval.length) {
            return func.getValue(intervals[intervals.length - 1].getEnd());
        }
        return func.getValue(intervals[i].getBegin());
    }

    public Interval[] getIntervals() {
        return intervals;
    }
}
