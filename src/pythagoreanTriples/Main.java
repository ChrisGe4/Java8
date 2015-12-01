package pythagoreanTriples;

import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author chris_ge
 */
public class Main {
    public static void main(String[] args) {

        Stream<int[]> ps = IntStream.rangeClosed(1, 100).boxed().flatMap(a -> IntStream.rangeClosed(a, 100)
                .filter(b -> Math.sqrt(a * a + b * b) % 1 == 0)
                .mapToObj(b -> new int[]{a, b, (int) Math.sqrt(a * a + b * b)}));

        Stream<double[]>ps1 = IntStream.rangeClosed(1, 100).boxed().flatMap(a -> IntStream.rangeClosed(a, 100)

                .mapToObj(b -> new double[]{a, b, Math.sqrt(a * a + b * b)}).filter(t -> t[2]%1==0));

        ps1.limit(5).forEach(t -> System.out.println((int)t[0] + ", " + (int)t[1] + ", " + (int)t[2]));
    }
}
