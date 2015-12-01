package practice1;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toSet;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * @author chris_ge
 */
public class Main {
    public static void main(String[] args) {
        Trader raoul = new Trader("Raoul", "Cambridge");
        Trader mario = new Trader("Mario", "Milan");
        Trader alan = new Trader("Alan", "Cambridge");
        Trader brian = new Trader("Brian", "Cambridge");

        List<Transaction> transactions = Arrays.asList(new Transaction(brian, 2011, 300),
                new Transaction(raoul, 2012, 1000),
                new Transaction(raoul, 2011, 400),
                new Transaction(mario, 2012, 710), new Transaction(mario, 2012, 700), new Transaction(alan, 2012, 950)
        );

        //Find all transactions in 2011 and sort by value (small to high)
        transactions.stream().filter(t -> t.getYear() == 2011).sorted(Comparator.comparing(Transaction::getValue)).forEach(System.out::println);
        //What are all the unique cities where the traders work?
        transactions.stream().map(t -> t.getTrader().getCity()).distinct().forEach(System.out::println);
        transactions.stream().map(t -> t.getTrader().getCity()).collect(toSet());
        //Find all traders from Cambridge and sort them by name
        transactions.stream().map(Transaction::getTrader).filter(trader -> trader.getCity().equalsIgnoreCase("Cambridge")).distinct().sorted(Comparator.comparing(Trader::getName)).forEach(System.out::println);
        //Return a string of all traders’ names sorted alphabetically
        System.out.println(transactions.stream().map(t -> t.getTrader().getName()).distinct().sorted().reduce("", String::concat));
        System.out.println(transactions.stream().map(t -> t.getTrader().getName()).distinct().sorted().collect(joining(",")));
        //Are any traders based in Milan?
        System.out.println(transactions.stream().anyMatch(t -> t.getTrader().getCity().equalsIgnoreCase("Milan")));
        //Print all transactions’ values from the traders living in Cambridge
        transactions.stream().filter(transaction -> transaction.getTrader().getCity().equalsIgnoreCase("Cambridge")).forEach(t -> System.out.println(t.getValue()));
        transactions.stream().filter(transaction -> transaction.getTrader().getCity().equalsIgnoreCase("Cambridge")).map(Transaction::getValue).forEach(System.out::println);
        //What’s the highest value of all the transactions?
        System.out.println(transactions.stream().sorted(Comparator.comparing(Transaction::getValue).reversed()).findFirst());
        System.out.println(transactions.stream().map(Transaction::getValue).reduce(Integer::max));
        //Find the transaction with the smallest value
        System.out.println(transactions.stream().reduce((t1,t2)->t1.getValue()>t2.getValue()?t2:t1));
        System.out.println(transactions.stream().min(Comparator.comparing(Transaction::getValue)));

    }

}
