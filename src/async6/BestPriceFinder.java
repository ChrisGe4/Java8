package async6;

import static java.util.stream.Collectors.toList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.stream.Stream;

/**
 * @author chris_ge
 */
public class BestPriceFinder {

    private final List<Shop> shops = Arrays.asList(
            new Shop("BestPrice"),
            new Shop("LetsSaveBig"),
            new Shop("MyFavoriteShop"),
            new Shop("BuyItAll"),
            new Shop("ShopEasy")
    );

    private final Executor executor = Executors.newFixedThreadPool(Math.min(shops.size(), 100), new ThreadFactory() {
        @Override
        public Thread newThread (Runnable r) {
            Thread t = new Thread(r);
            t.setDaemon(true);
            return t;
        }
    });

    public List<String> findPriceSequential (String product) {

        return shops.stream()
                    .map(s -> String.format("%s price is %.2f", s.getName(), s.getPrice(product)))
                    .collect(toList());

    }

    public List<String> findPriceParallel (String product) {
        return shops.parallelStream()
                    .map(s -> String.format("%s price is %.2f", s.getName(), s.getPrice(product)))
                    .collect(toList());
    }

    public List<String> findPriceFuture (String product) {
        List<CompletableFuture<String>> priceFutures = shops.stream()
                                                            .map(s -> CompletableFuture.supplyAsync(
                                                                    () -> String.format("%s price is %.2f", s.getName(),
                                                                                        s.getPrice(product)
                                                                    )))
                                                            .collect(
                                                                    toList());

        return priceFutures.stream()
                           .map(CompletableFuture::join)
                           .collect(toList());

    }

    public List<String> findPriceFutureWithExecutor (java.lang.String product) {
        List<CompletableFuture<java.lang.String>> priceFutures = shops.stream()
                                                                      .map(s -> CompletableFuture.supplyAsync(
                                                                              () -> java.lang.String.format(
                                                                                      "%s price is %.2f", s.getName(),
                                                                                      s.getPrice(product)
                                                                              ), executor)
                                                                      )
                                                                      .collect(
                                                                              toList());

        return priceFutures.stream()
                           .map(CompletableFuture::join)
                           .collect(toList());

    }

    public List<String> findPrices (String product) {

        return shops.stream()
                    .map(s -> s.getPrice(product))
                    .map(Quote::parse)
                    .map(Discount::applyDiscount)
                    .collect(toList());
    }

    public List<String> findPricesAsync (String product) {
        List<CompletableFuture<String>> priceFutures = shops.stream()
                                                            .map(s -> CompletableFuture.supplyAsync(
                                                                    () -> s.getPrice(product), executor))
                                                            .map(future -> future.thenApply(Quote::parse))

                                                            .map(future -> future.thenCompose(
                                                                    //We chose to use the one with thenCompose only because it’s slightly more efficient
                                                                    //due to less thread-switching overhead.
                                                                    quote -> CompletableFuture.supplyAsync(
                                                                            () -> Discount.applyDiscount(quote),
                                                                            executor
                                                                    )))
                                                            .collect(toList());

        //In general, a method without the Async suffix in its name executes its task in the same thread as the previous task,
        // whereas a method terminating with Async always submits the succeeding task to the thread pool, so each of the tasks
        // can be handled by a different thread.

        return priceFutures.stream()
                           .map(CompletableFuture::join)
                           .collect(toList());

        //then combine :combine the results of the operations performed by two completely independent CompletableFutures,
        // and you don’t want to wait for the first to complete before starting on the second.
    }

    public Stream<CompletableFuture<String>> findPricesStream (String product) {
        return shops.stream()
                    .map(shop -> CompletableFuture.supplyAsync(
                            () -> shop.getPrice(product), executor))
                    .map(future -> future.thenApply(Quote::parse))
                    .map(future -> future.thenCompose(
                            quote -> CompletableFuture.supplyAsync(() -> Discount.applyDiscount(quote), executor)));
    }

}
