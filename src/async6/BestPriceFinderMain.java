package async6;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * @author chris_ge
 */
public class BestPriceFinderMain {

    public static void main (String[] args) {

        BestPriceFinder finder = new BestPriceFinder();
        execute("parellel", () -> finder.findPriceParallel("myphone27s"));
        runFindPricesParell(finder);
        // System.out.println(finder.findPricesAsync("myPhone"));
    }

    public static void runFindPricesParell (BestPriceFinder finder) {
        long start = System.nanoTime();
        Stream<CompletableFuture<String>> stream = finder.findPricesStream("myPhone");
        CompletableFuture[] futures = (CompletableFuture[]) stream.map(f -> f.thenAccept(
                s -> System.out.println(s + "(done in)" + ((System.nanoTime() - start) / 1000000) + "msecs")))
                                                                  .toArray(size -> new CompletableFuture[size]);
        CompletableFuture.allOf(futures)
                         .join();
        System.out.println("All shops have now responded in "
                                   + ((System.nanoTime() - start) / 1_000_000) + " msecs");

    }

    private static void execute (String msg, Supplier<List<String>> s) {
        long start = System.nanoTime();
        System.out.println(s.get());
        long duration = (System.nanoTime() - start) / 1_000_000;
        System.out.println(msg + " done in " + duration + " msecs");

    }
}
