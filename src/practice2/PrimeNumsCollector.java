package practice2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.IntStream;

/**
 * @author chris_ge
 */
public class PrimeNumsCollector implements Collector<Integer,Map<Boolean,List<Integer>>,Map<Boolean,List<Integer>>> {

    public static List<Integer> takeWhile (List<Integer> list, Predicate<Integer> p) {
        int i = 0;
        for ( Integer in : list ) {
            if ( !p.test(in) ) return list.subList(0, i);
            i++;
        }
        return list;

    }

    public static boolean isPrime (List<Integer> primes, int in) {

        int root = (int) Math.sqrt(in);
        return takeWhile(primes, i -> i <= root).stream()
                                                .allMatch(i -> in % i != 0);

    }

    @Override
    public Supplier<Map<Boolean,List<Integer>>> supplier () {

        return () -> new HashMap<Boolean,List<Integer>>() {
            {
                put(true, new ArrayList<>());
                put(false, new ArrayList<>());
            }
        };
        /*{
            Map<Boolean,List<Integer>> map = new HashMap<>();
            map.put(true, new ArrayList<>());
            map.put(true, new ArrayList<>());
            return map;
        };*/
    }

    @Override
    public BiConsumer<Map<Boolean,List<Integer>>,Integer> accumulator () {
        return (m, i) -> {
            m.get(isPrime(m.get(true), i))
             .add(i);
        };
    }

    @Override
    public BinaryOperator<Map<Boolean,List<Integer>>> combiner () {
        return (m1, m2) -> {
            m1.get(false)
              .addAll(m2.get(false));
            m1.get(true)
              .addAll(m2.get(true));
            return m1;
        };
    }

    @Override
    public Function<Map<Boolean,List<Integer>>,Map<Boolean,List<Integer>>> finisher () {
        return Function.identity();
    }

    @Override
    public Set<Characteristics> characteristics () {
        return Collections.unmodifiableSet(EnumSet.of(Characteristics.IDENTITY_FINISH));

    }

    public static void main (String[] args) {

        System.out.println(IntStream.rangeClosed(2, 10)
                                    .boxed()
                                    .collect(new PrimeNumsCollector()));

    }
}
