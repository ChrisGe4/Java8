package GAOHomework;

import java.util.Scanner;
import java.util.stream.IntStream;

/**
 * @author chris_ge
 */
public class Mathstuff {

    public static void main (String[] args) {

        String[] questions =
                {"Please enter the length of a side of your square:",
                        "Please enter the length of a side of your cube:",
                        " Please enter the radius of your circle:",
                        "Please enter the length of a side of your cube:",
                        "Please enter two numbers separated by spaces:",
                        "Please enter two numbers separated by spaces:"};
        String[] answers = {"Area of your square is :",
                "Volume of your cube is:",
                "Area of your circle is:",
                "Surface area of your cube is:",
                "Larger of first two numbers is:",
                "Smaller of second two numbers is:"};
        IntStream.rangeClosed(0, questions.length - 1)
                 .forEach(i -> {

                     double res = 0;
                     double in[] = null;
                     switch ( i ) {
                         case 0:
                             res = Math.pow(getInput(1, questions, i)[0], 2);
                             break;
                         case 1:
                             res = Math.pow(getInput(1, questions, i)[0], 3);
                             break;

                         case 2:
                             res = Math.PI * Math.pow(getInput(1, questions, i)[0], 2);
                             break;

                         case 3:
                             res = 6 * Math.pow(getInput(1, questions, i)[0], 2);
                             break;

                         case 4:
                             in = getInput(2, questions, i);
                             res = Math.max(in[0], in[1]);
                             break;

                         case 5:
                             in = getInput(2, questions, i);
                             res = Math.min(in[0], in[1]);

                     }

                     System.out.printf(answers[i] + "%.3f\n", res);

                 });

    }

    private static double[] getInput (int times, String[] questions, int index) {
        double[] res = new double[times];
        while ( true ) {
            System.out.print(questions[index]);
            Scanner scanner = new Scanner(System.in);
            try {
                for ( int i = 0; i < times; i++ ) {
                    res[i] = scanner.nextDouble();
                }
                break;
            } catch ( Exception e ) {
                System.out.println("Invalid input!");
            }

        }

        return res;

    }

}
