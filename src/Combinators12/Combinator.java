package Combinators12;

import java.util.function.Function;

/**
 * @author chris_ge
 */
public class Combinator {

    public static void main (String[] args) {
        System.out.println(repeat(3,(Integer o) -> 2*o).apply(5));
        System.out.println(fo(3,5));
    }

    static <A,B,C> Function<A,C> compose(Function<B,C> g,Function<A,B> f){

        return x ->g.apply(f.apply(x));

    }

    static <A> Function<A,A> repeat(int n , Function<A,A> f){

        return n==0?x->x:compose(f,repeat(n-1,f));

    }


    static int fo(int n , int in){
        if(n==0) return in;
        return 2*fo(n-1,in);
    }
}
