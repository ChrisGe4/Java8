package repeatableAnnotation;

import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author chris_ge
 */
@Repeatable(Authors.class)
//has to be runtime
@Retention(RetentionPolicy.RUNTIME)
public @interface Author {
    String name();
}
