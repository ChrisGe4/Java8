package async6;

import static async6.Util.format;
import static async6.Util.randomDelay;
import java.util.Random;

/**
 * @author chris_ge
 */
public class Shop {
    private final String name;
    private final Random random;

    public Shop (String name) {
        this.name = name;
        this.random = new Random(name.charAt(0) * name.charAt(1) * name.charAt(2));
    }

    public String getPrice (String product) {
        double price = calculatePrice(product);
        System.out.println("price = " + price);
        Discount.Code code = Discount.Code.values()[random.nextInt(Discount.Code.values().length)];
        return String.format("%s:%.2f:%s", name, price, code);
    }

    public double calculatePrice (String product) {

        randomDelay();
        return format(random.nextDouble() * product.charAt(0) * product.charAt(1));

    }

    public String getName () {
        return name;
    }
}
