package Spliterator4;

import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author chris_ge
 */
public class Main {
    public static void main (String[] args) {
        final String SENTENCE =
                " Nel mezzo del cammin di nostra vita " +
                        "mi ritrovai in una selva oscura" + " ché la dritta via era smarrita ";

        Stream<Character> stream = IntStream.range(0, SENTENCE.length())
                                            .mapToObj(SENTENCE::charAt);

        //--> System.out.println("Found " + countWords(stream) + " words");
    }
}
