package tailRecursion8;

/**
 * @author chris_ge
 */
public class Main {

    public static void main (String[] args) {


        long start = System.nanoTime();
        System.out.println(factorialR(20));
        long duration = (System.nanoTime() - start);// / 1_000_000;
        System.out.println(" done in " + duration + " msecs");


         start = System.nanoTime();
        System.out.println(factorialR(20));
         duration = (System.nanoTime() - start);// / 1_000_000;
        System.out.println(" done in " + duration + " msecs");


        start = System.nanoTime();
        System.out.println(factorialTailR(1,20));
         duration = (System.nanoTime() - start) ;/// 1_000_000;
        System.out.println(" done in " + duration + " msecs");
    }

    static long factorialTailR (long acc, long n) {
        return n == 1 ? acc : factorialTailR(acc * n, n - 1);
    }

    static long factorialR (long n) {
        return n == 1 ? 1 : n * factorialR(n - 1);
    }

}
