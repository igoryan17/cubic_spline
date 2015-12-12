package igoryan.vichmaty;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * Hello world!
 */
public class App {
    public static final int N = 15;
    public static final String fileStart = "pointsStartFunction.txt";
    public static final String fileSpline = "pointsOfSpline.txt";
    public static final String infelicityName = "infelicirt.txt";
    public static final double point = 3.7;
    public static final double step = 0.07;

    public static void main(String[] args) throws FileNotFoundException {
        Function startFunction = new StartFunction();
        Function cubicSpline = new Interpolyant(N, startFunction);
        File fileStartFunction = new File(fileStart);
        File fileSplineFunction = new File(fileSpline);
        PresentationX usual = new PresentationX() {
            public double present(double x) {
                return x;
            }
        };
        PresentationX log = new PresentationX() {
            public double present(double x) {
                return Math.log(x);
            }
        };
        /*
        createPoints(fileStartFunction, startFunction, usual);
        createPoints(fileSplineFunction, cubicSpline, usual);
        */
        File infelicity = new File(infelicityName);
        PrintWriter writer = new PrintWriter(infelicity);
        double[] points = new double[N];
        double[] values = new double[N];
        double size = startFunction.getInterval().length;
        double h0 = 1.0;
        for (int i = 0; i < N; ++i) {
            points[i] = h0;
            Function spline = new Interpolyant((int) Math.round(size / h0), startFunction);
            values[i] = spline.getValue(point);
            h0 -= step;
        }
        for (int i = 1; i < N; ++i) {
            writer.println(log.present(points[i]) + " " + Math.log(Math.abs(values[i] - values[i - 1])));
        }
        writer.close();
    }

    public static void createPoints(File file, Function function, PresentationX presentation) {
        try {
            PrintWriter writer = new PrintWriter(file);
            Interval interval = function.getInterval();
            double x = interval.getBegin();
            double h = interval.length / (double) N;
            for (int i = 0; i < N; ++i) {
                writer.println(presentation.present(x) + " " + function.getValue(x));
                x += h;
            }
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public interface PresentationX {
        double present(double x);
    }
}
