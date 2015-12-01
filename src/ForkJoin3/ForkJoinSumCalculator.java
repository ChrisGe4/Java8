package ForkJoin3;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.LongStream;

/**
 * @author chris_ge
 */
public class ForkJoinSumCalculator extends RecursiveTask<Long> {

    private final long[] numbers;
    private final int start;
    private final int end;

    public static final long THREADHOLD = 10_000;

    public ForkJoinSumCalculator (long[] numbers) {
        this(numbers, 0, numbers.length);
    }

    private ForkJoinSumCalculator (long[] numbers, int start, int end) {
        this.numbers = numbers;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute () {
        if ( (end - start) <= THREADHOLD ) return computeSequentially();
        ForkJoinSumCalculator leftTask = new ForkJoinSumCalculator(numbers, start, end / 2);
        leftTask.fork();
        ForkJoinSumCalculator rightTask = new ForkJoinSumCalculator(numbers, start, end / 2);
        long rightRes = rightTask.compute();
        long leftRes = leftTask.join();
        return rightRes + leftRes;
    }

    private long computeSequentially () {

        // Arrays.asList(numbers).stream().collect(reducing(0L,Long::sum));
//        return LongStream.of(numbers)
//                         .sum();
        long res = 0;
        for ( int i = start; i < end; i++ ) {
            res += numbers[i];
        }
        return res;
    }

    public static void main (String[] args) {
        long[] num = LongStream.rangeClosed(1, 10_000_000)
                               .toArray();
        ForkJoinTask<Long> task = new ForkJoinSumCalculator(num);
        System.out.println(new ForkJoinPool().invoke(task));//only in main thread
    }
}
