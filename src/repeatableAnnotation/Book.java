package repeatableAnnotation;

import java.util.Arrays;

/**
 * @author chris_ge
 */
@Author(name = "Chris")
@Author(name = "Ge")
public class Book {

    public static void main (String[] args) {
        Author[] authors = Book.class.getAnnotationsByType(Author.class);
        Arrays.asList(authors)
              .forEach(author -> System.out.println(author.name()));

    }

}
