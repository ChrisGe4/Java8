package lazyEvaluation11;

import java.util.function.Predicate;

/**
 * Created by GEPOEMRUN on 12/9/2015.
 */
interface MyList<T> {
    T head ();

    MyList<T> tail ();

    default boolean isEmpty () {
        return true;
    }
    MyList<T> filter(Predicate<T> p);


}
