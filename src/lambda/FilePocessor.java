package lambda;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * @author chris_ge
 */
public class FilePocessor<T> {

    public Optional<List<T>> processFile(BufferdReaderProcessor<T> p) {
//        try (BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getClassLoader().getResourceAsStream("resource/info-for-work.txt")))) {
        Optional<List<T>> lines = Optional.empty();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/resource/info-for-work.txt")))) {
            lines = Optional.of(p.process(br));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;

    }

    public <T, R> List<R> processList(List<T> in, Function<T,R> f) {
        List<R> processed = new ArrayList<>();
        for (T t : in) {
            processed.add(f.apply(t));

        }

        return processed;
    }

    public long wordCount() {

        long uniqueWords = 0;

        try (Stream<String> lines = Files.lines(Paths.get(getClass().getResource("/resource/info-for-work.txt").toURI()), Charset.defaultCharset())) {
            uniqueWords = lines.flatMap(s -> Arrays.stream(s.split(" "))).distinct().count();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return uniqueWords;
    }

    public static void main(String[] args) {

        Supplier<FilePocessor<String>> fps = FilePocessor::new;
        FilePocessor<String> fp = fps.get();
        Optional<List<String>> list = fp.processFile(br -> {
            List<String> res = new ArrayList<String>();
            String line = br.readLine();
            while (line != null) {
                if (!line.trim().isEmpty())
                    res.add(line);
                line = br.readLine();
            }
            return res;
        });
        if (list.isPresent()) {
            List<String> modifiedList = fp.processList(list.get(), s -> {
                if (!s.trim().isEmpty()) return s;
                return null;
            });

            for (int i = 0; i < modifiedList.size(); i++) {
                System.out.println(i + " " + modifiedList.get(i));

            }

        }

        System.out.println(fp.wordCount());
    }

}
