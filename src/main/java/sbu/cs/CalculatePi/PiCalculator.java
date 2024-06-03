package sbu.cs.CalculatePi;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class PiCalculator {

    /**
     * Calculate pi and represent it as a BigDecimal object with the given floating point number (digits after . )
     * There are several algorithms designed for calculating pi, it's up to you to decide which one to implement.
     Experiment with different algorithms to find accurate results.

     * You must design a multithreaded program to calculate pi. Creating a thread pool is recommended.
     * Create as many classes and threads as you need.
     * Your code must pass all of the test cases provided in the test folder.

     * @param "floatingPoint" the exact number of digits after the floating point
     * @return pi in string format (the string representation of the BigDecimal object)
     */

    public static class Calculatepi implements Runnable {
        int n;
        MathContext mc = new MathContext(10000);
        public Calculatepi (int n) {
            this.n = n;
        }
        @Override
        public void run() {
            BigDecimal sign = new BigDecimal(1);
            if (n%2 == 1) {
                sign = new BigDecimal(-1);
            }
            //BigDecimal n = new BigDecimal(m);
            BigDecimal numerator = new BigDecimal(1);
            BigDecimal denominator = new BigDecimal(1);
            BigDecimal a = factorial(6*n);
            BigDecimal b = new BigDecimal((545140134*n)+13591409);
            numerator = numerator.multiply(sign,mc);
            numerator = numerator.multiply(a,mc);
            numerator = numerator.multiply(b,mc);
            BigDecimal c = factorial(3*n);
            BigDecimal d =factorial(n);
            d = d.pow(3);
            BigDecimal e = new BigDecimal(640320);
            e = e.pow(3*n);
            BigDecimal f = new BigDecimal(640320);
            f = f.pow(3);
            MathContext g = new MathContext(10000);
            f = f.sqrt(g);
            denominator = denominator.multiply(c,mc);
            denominator = denominator.multiply(d,mc);
            denominator = denominator.multiply(e,mc);
            denominator = denominator.multiply(f,mc);

            MathContext mc = new MathContext(10000);
            BigDecimal result = numerator.divide(denominator, mc);
            addTosum(result);
        }
        public BigDecimal factorial(int k) {
            BigDecimal temp = new BigDecimal(1);
            for (int i = 1; i <= k; i++) {
                temp = temp.multiply(new BigDecimal(i));
            }
            return temp;
        }
    }
    public static BigDecimal sum;

    public static synchronized void addTosum(BigDecimal value) {
        sum = sum.add(value);
    }
    public static String calculate(int floatingPoint)
    {
        // TODO
        //MathContext mc = new MathContext(floatingPoint);
        ExecutorService threadpool = Executors.newFixedThreadPool(10);
        sum = new BigDecimal(0);

        for (int i = 0; i < 1000; i++) {
            Calculatepi task = new Calculatepi(i);
            threadpool.execute(task);
        }
        threadpool.shutdown();

        try {
            threadpool.awaitTermination(1000000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        BigDecimal factor = new BigDecimal(12);
        MathContext mc = new MathContext(10000);
        sum = sum.multiply(factor,mc);
        sum = sum.pow(-1, mc);
        sum = sum.setScale(floatingPoint, RoundingMode.DOWN);
        String pi = String.valueOf(sum);
        pi = pi.substring(0,floatingPoint+2);
        return pi;
    }

    public static void main(String[] args) {
        // Use the main function to test the code yourself
        /*ExecutorService threadpool = Executors.newFixedThreadPool(6);
        sum = new BigDecimal(0);

        for (int i = 0; i < 1000; i++) {
            Calculatepi task = new Calculatepi(i);
            threadpool.execute(task);
        }
        threadpool.shutdown();

        try {
            threadpool.awaitTermination(100000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        BigDecimal factor = new BigDecimal(12);
        MathContext mc = new MathContext(10000);
        sum = sum.multiply(factor);
        sum = sum.pow(-1, mc);
        sum = sum.setScale(2, RoundingMode.DOWN);
        String pi = String.valueOf(sum);
        System.out.println(pi);
*/

    }
}
