package repeatableAnnotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author chris_ge
 */
@Retention(RetentionPolicy.RUNTIME)//has to be runtime

public @interface Authors {
    Author[] value ();
}
