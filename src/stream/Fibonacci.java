package stream;

import java.util.stream.Stream;

/**
 * @author chris_ge
 */
public class Fibonacci {
    public static void main(String[] args) {
        Stream<int[]> f = Stream.iterate(new int[]{0, 1}, t -> new int[]{t[1], t[0] + t[1]});

        f.limit(20).map(t -> t[0]).forEach(System.out::println);

        //.forEach(t -> System.out.println("(" + t[0] + "," + t[1] +")"));
    }
}
