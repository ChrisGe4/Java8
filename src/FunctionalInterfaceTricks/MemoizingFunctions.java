package FunctionalInterfaceTricks;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.IntStream;

/**
 * @author Chris.Ge
 */
public class MemoizingFunctions {

    public static void main(String[] args) {
        System.out.println(Doubler.doubleValue(2));
        //version 2 eg:
        IntStream.range(1, 10).boxed().map(Doubler2.doubleValue);
        /**  This is equivalent to using the method version with the following syntax:

         IntStream.range(1, 10).boxed().map(ThisClass::doubleValue);*/

        automaticMemoizationExample();

    }

    public static void automaticMemoizationExample() {
        Function<Integer, Integer> f = x -> x * 2;
        Function<Integer, Integer> g = Memoizer.memoize(f);
        long startTime = System.currentTimeMillis();
        Integer result1 = g.apply(1);
        long time1 = System.currentTimeMillis() - startTime;
        startTime = System.currentTimeMillis();
        Integer result2 = g.apply(1);
        long time2 = System.currentTimeMillis() - startTime;
        System.out.println(result1);
        System.out.println(result2);
        System.out.println(time1);
        System.out.println(time2);
    }
    //version 2 : return function: This gives no advantage compared to the “method” solution.
    // However, we may also use this function in more idiomatic examples, such as:


    public static void moreArguments() {
        Function<Integer, Function<Integer, Function<Integer, Integer>>> f3 =
            x -> y -> z -> x + y + z;
        Function<Integer, Function<Integer, Function<Integer, Integer>>> f3m =
            Memoizer.memoize(x -> Memoizer.memoize(y -> Memoizer.memoize(z -> x + y + z)));
        //if we already have this
        BiFunction<Integer, Integer, Integer> mul = (x, y) -> x * y;
        //and want to reuse it
        Function<Integer, Function<Integer, Integer>> memoizationFunction =
            Memoizer.memoize(x -> Memoizer.memoize(y -> mul.apply(x, y)));

    }

    //first version eg:
    public static class Doubler {

        private static Map<Integer, Integer> cache = new ConcurrentHashMap<>();

        public static Integer doubleValue(Integer x) {

            return cache.computeIfAbsent(x, y -> y * 2);
        }

    }


    public static class Doubler2 {

        private static Map<Integer, Integer> cache = new ConcurrentHashMap<>();

        public static Function<Integer, Integer> doubleValue =
            x -> cache.computeIfAbsent(x, y -> y * 2);
    }


    public static class Memoizer<T, U> {
        //version 3 The main problem is that while solving the second issue, we have made the first one(We have to repeat this modification for all functions.
        //) more acute, which make automatic memoization more desirable.


        private Memoizer() {

        }

        private static <T, U> Function<T, U> doMemoize(final Function<T, U> function) {
            /**each function will have its own cache, with diff keys*/
            Map<T, U> cache = new ConcurrentHashMap<>();
            return input -> cache.computeIfAbsent(input, function::apply);
        }


        public static <T, U> Function<T, U> memoize(final Function<T, U> function) {
            return doMemoize(function);
        }

    }



}
