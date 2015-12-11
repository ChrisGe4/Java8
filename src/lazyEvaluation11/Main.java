package lazyEvaluation11;

/**
 * Created by GEPOEMRUN on 12/10/2015.
 */
public class Main {

    public static void main(String[] args) {
       LazyList<Integer> numbers = from(2);
       int two = numbers.head();
        int three = numbers.tail().head();
        int four = numbers.tail().tail().head();
        System.out.println(two + " " + three + " " + four);

        numbers = from(2);
        int prime_two = primes(numbers).head();
        int prime_three = primes(numbers).tail().head();
        int prime_five = primes(numbers).tail().tail().head();
        System.out.println(prime_two + " " + prime_three + " " + prime_five);

    }

    public static  LazyList<Integer> from(int n){

        return new LazyList<>(n,() -> from(n+1));


    }


    public static final MyList<Integer> primes(MyList<Integer> numbers){
        return new LazyList<>(numbers.head(),() -> primes(numbers.tail().filter(n->n%numbers.head()!=0)));

    }

    static void printAll(MyList<Integer> numbers){
        if (numbers.isEmpty()   ) return;

        System.out.println(numbers.head());
        printAll(numbers.tail());
    }
}
