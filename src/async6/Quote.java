package async6;

/**
 * @author chris_ge
 */
public class Quote {

    private final double price;
    private final String shopName;
    private final Discount.Code code;

    public Quote (double price, String shopName, Discount.Code code) {
        this.price = price;
        this.shopName = shopName;
        this.code = code;
    }

    public static Quote parse (String s) {

        String[] split = s.split(":");
        String shopName = split[0];
        double price = Double.valueOf(split[1]);
        Discount.Code code = Discount.Code.valueOf(split[2]);

        System.out.println("code = " + code);
        return new Quote(price, shopName, code);

    }

    public double getPrice () {
        return price;
    }

    public String getShopName () {
        return shopName;
    }

    public Discount.Code getCode () {
        return code;
    }
}
