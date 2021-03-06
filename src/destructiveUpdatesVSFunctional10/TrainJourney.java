package destructiveUpdatesVSFunctional10;

/**
 * @author chris_ge
 */
public class TrainJourney {
    public int price;
    public TrainJourney onward;

    public TrainJourney (int p, TrainJourney t) {
        price = p;
        onward = t;
    }

    public static void main (String[] args) {

    }

    static TrainJourney link (TrainJourney a, TrainJourney b) {
        if ( a == null ) return b;

        TrainJourney t = a;

        while ( t.onward == null ) {
            t = t.onward;
        }
        t.onward = b;

        return a;
    }

    //This code is clearly functional style (it uses no mutation at all, even locally)
    // and doesn’t modify any existing data structures.

    static TrainJourney append (TrainJourney a, TrainJourney b) {
        return a == null ? b : new TrainJourney(a.price, append(a.onward, b));

    }
}
