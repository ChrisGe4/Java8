package async6;

import static async6.Util.format;
import static async6.Util.randomDelay;

/**
 * @author chris_ge
 */
public class Discount {
    public enum Code {
        NONE(0), SILVER(5), GOLD(10), PLATINUM(15), DIAMOND(20);
        private final int percentage;

        Code (int percentage) {

            this.percentage = percentage;
        }

    }

    public static String applyDiscount (Quote quote) {

        return quote.getShopName() + " price is " + Discount.apply(quote.getPrice(), quote.getCode());

    }

    private static double apply (double price, Discount.Code code) {
        randomDelay();
        return format(price * (100 - code.percentage) / 100);
    }

}
