package decorationPattern;

import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleUnaryOperator;
import java.util.stream.Stream;

/**
 * @author Chris.Ge
 */
public class Main {
    public static void main(String[] args) {

        List<String> list = new ArrayList<String>();
        list.add("java");
        list.add("php");
        list.add("python");
        list.add("perl");
        list.add("c");
        list.add("lisp");
        list.add("c#");


        System.out.println(list.stream().map(s -> s.length()).reduce(Integer::sum));

        System.out.println(list.stream().reduce(0, (u, s) -> u + s.length(), (i1, i2) -> i1 + i2));



        //v1:

        double netSalary =
            new DefaultSalaryCalculator().andThen(Taxes::generalTax).andThen(Taxes::regionalTax)
                .andThen(Taxes::healthInsurance).applyAsDouble(3000.00);
        System.out.println("netSalary = " + netSalary);
        //v2

        netSalary =
            calculate(3000.00, new DefaultSalaryCalculator(), Taxes::generalTax, Taxes::regionalTax,
                Taxes::healthInsurance);
        System.out.println("netSalary = " + netSalary);
    }


    public static double calculate(double gorss, DoubleUnaryOperator... fs) {
        return Stream.of(fs).reduce(DoubleUnaryOperator.identity(), DoubleUnaryOperator::andThen)
            .applyAsDouble(gorss);
    }


    public static class DefaultSalaryCalculator implements DoubleUnaryOperator {



        @Override
        public double applyAsDouble(double grossAnnual) {
            return grossAnnual / 12;
        }
    }


    public static class Taxes {
        public static double generalTax(double salary) {
            return salary * 0.8;
        }

        public static double regionalTax(double salary) {
            return salary * 0.95;
        }

        public static double healthInsurance(double salary) {
            return salary - 200.0;
        }
    }

}
