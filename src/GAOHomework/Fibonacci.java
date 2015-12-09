package GAOHomework;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * @author chris_ge
 */
public class Fibonacci {

    public static void main (String[] args) {

        while ( true ) {
            System.out.print("Which Fibonacci number would you like?");
            Scanner scanner = new Scanner(System.in);
            try {

                int n = scanner.nextInt();

                if ( n < 0 || n > 70 ) continue;

                switch ( n ) {

                    case 0:
                        System.out.println("Fibonacci #0 is 0");
                        return;
                    case 1:
                        System.out.println("Fibonacci #1 is 1");
                        return;
                    default:
                        System.out.printf("Fibonacci #%d is %.0f", n, fibcalc(n));
                        return;

                }
            } catch ( InputMismatchException e ) {
                System.out.println("Invalid input!");
            }

        }

    }

    private static double fibcalc (int n) {

//        return Stream.iterate(new double[]{0, 1}, t -> new double[]{t[1], t[0] + t[1]})
//                     .limit(n)
//                     .map(d -> d[1])
//                     .reduce((ints, ints2) -> ints2)
//                     .get();

        int f = 0;
        int s = 1;

        for ( int i = 2; i <= n; i++ ) {
            int res = f + s;
            f = s;
            s = res;
        }
        return s;

    }

}
