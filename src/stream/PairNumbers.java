package stream;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author chris_ge
 */
public class PairNumbers {

    public static void main (String[] args) {
        int[] in1 = {1, 2, 3};
        int[] in2 = {3, 4};

        Integer[] sourceArray = {0, 1, 2, 3, 4, 5};
        List<Integer> targetList = Arrays.asList(sourceArray);

        targetList.stream()
                  .forEach(System.out::println);

        //List<Integer> l1 =
        List<int[]> pairs = Arrays.stream(in1)
                                  .boxed()
                                  .flatMap(i -> Arrays.stream(in2)
                                                      .boxed()
                                                      .filter(j -> (i + j) % 3 == 0)
                                                      .map(
                                                              j -> new int[]{i, j}))
                                  .collect(Collectors.toList());
        pairs.stream()
             .forEach(ints -> System.out.println(ints[0] + " " + ints[1]));

        pairs = Arrays.stream(in1)
                      .boxed()
                      .flatMap(i -> Arrays.stream(in2)
                                          .filter(j -> (i + j) % 3 == 0)
                                          .mapToObj(j -> new int[]{i, j}))
                      .collect(Collectors.toList());
        pairs.stream()
             .forEach(ints -> System.out.println(ints[0] + " " + ints[1]));
    }
}
