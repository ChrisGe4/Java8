package GAOHomework;

import java.util.GregorianCalendar;
import java.util.Scanner;

/**
 * @author chris_ge
 */
public class Numerology {

    public static void main (String[] args) {

        String[] output =
                {"test1",
                        "test2",
                        " test3",
                        "test4",
                        "test5",
                        "test6",
                        "test7",
                        "test8",
                        "test9"};

        while ( true ) {
            int date = getDate();

            int index = crunch(date);

            System.out.println("=" + index + "= " + output[index - 1]);

        }

    }

    private static int getDate () {
        int res;
        while ( true ) {
            res = 0;
            System.out.print("Enter the birth date (mm/dd/yyyy): ");
            Scanner scanner = new Scanner(System.in);

            String month = scanner.next();
            if ( !month.matches("[0-9]{1,2}") || Integer.parseInt(month) < 1 || Integer.parseInt(month) > 12 ) {
                System.out.println("Bad month: " + month);
                continue;
            }
            //int monInt =  Integer.parseInt(month);
            if ( !scanner.next()
                         .equals("/") ) {
                System.out.println("Use forward slashes between mm/dd/yyyy!");
                continue;
            }

            String day = scanner.next();

            if ( !day.matches("[0-9]{1,2}") || Integer.parseInt(day) < 1 || Integer.parseInt(day) > 31 ) {
                System.out.println("Bad day: " + day);
                continue;
            }
            //int dayInt =  Integer.parseInt(day);

            if ( !scanner.next()
                         .equals("/") ) {
                System.out.println("Use forward slashes between mm/dd/yyyy!");
                continue;
            }
            String year = scanner.next();
            if ( !year.matches("[0-9]{4}") || Integer.parseInt(year) < 1880 || Integer.parseInt(year) > 2280 ) {
                System.out.println("Bad year: " + year);
                continue;
            }
            //int yearInt =  Integer.parseInt(year);

            try {
                GregorianCalendar gc = new GregorianCalendar(Integer.parseInt(year)
                                                                    , Integer.parseInt(month) - 1,
                                                             Integer.parseInt(day)
                );
                gc.setLenient(false);
                gc.getTime();
            } catch ( IllegalArgumentException e ) {
                System.out.println("Bad day for " + month + "/" + year + " :" + day);
                continue;
            }
            res = Integer.parseInt(year) + Integer.parseInt(month) + Integer.parseInt(day);
            break;

        }
        return res;

    }

    public static int crunch (int date) {

        while ( date > 9 ) {

            int res = 0;
            while ( date > 0 ) {
                res += date % 10;
                date /= 10;
            }
            date = res;
        }
        return date;
    }

}
