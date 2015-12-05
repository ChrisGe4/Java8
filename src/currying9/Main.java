package currying9;

import java.util.function.DoubleUnaryOperator;

/**
 * @author chris_ge
 */
public class Main {

    public static void main (String[] args) {

        DoubleUnaryOperator convertCtoF = curried(9.0 / 5, 32);

        DoubleUnaryOperator convertKmtoMi = curried(0.6214, 0);

        double ktm = convertKmtoMi.applyAsDouble(1000);

    }

    static double converter (double x, double factor, double base) {

        return x * factor + base;

    }

    //You could obviously call the converter method with three arguments on each occasion,
    //but supplying the factor and baseline each time would be tedious and you might accidentally mistype them.
    //You could write a completely new method for each application, but that would miss the reuse of the underlying logic.

    static DoubleUnaryOperator curried (double factor, double base) {
        return x -> x * factor + base;
    }

}
