package practice2;

import static java.util.stream.Collectors.partitioningBy;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

/**
 * @author chris_ge
 */
public class Prime {

    static boolean isPrime (int in) {
        int root = (int) Math.sqrt(in);
        return IntStream.rangeClosed(2, root)
                        .noneMatch(i -> in % i == 0);

    }

    static Map<Boolean,List<Integer>> partitionPrimes (int n) {

        return IntStream.rangeClosed(2, n)
                        .boxed()
                        .collect(partitioningBy(Prime::isPrime));

    }

    public static void main (String[] args) {
        System.out.println(partitionPrimes(15));
    }

}
