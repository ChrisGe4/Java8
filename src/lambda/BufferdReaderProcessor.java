package lambda;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

/**
 * @author chris_ge
 */
@FunctionalInterface
public interface BufferdReaderProcessor<T> {
    List<T> process(BufferedReader br) throws IOException;

}
