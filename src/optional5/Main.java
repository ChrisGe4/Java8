package optional5;

import java.util.Optional;
import java.util.Properties;

/**
 * @author chris_ge
 */
public class Main {
    public Optional<Insurance> nullSafeFindCheapestInsurance (Optional<Person> person, Optional<Car> car) {
        return person.flatMap(p -> car.map(c -> findCheapestInsurance(p, c)));
    }

    public String getCarInsuranceName (Optional<Person> person, int minAge) {
        return person.filter(p -> p.getAge() >= minAge)
                     .flatMap(Person::getCar)
                     .flatMap(Car::getInsurance)
                     .map(Insurance::getName)
                     .orElse("Unknown");

    }

    public int readDuration2 (Properties props, String name) {
        try {
            return Optional.of(Integer.valueOf(props.getProperty(name)))
                           .map(i -> i > 0 ? i : 0)
                           .get();
        } catch ( NumberFormatException e ) {
            return 0;
        }

    }

    public int readDuration (Properties props, String name) {

        return Optional.ofNullable(props.getProperty(name))
                       // .map(s -> Integer.valueOf(s))
                       .flatMap(Main::stringToInt)
                       .filter(i -> i > 0)
                       .orElse(0);

    }

    public static Optional<Integer> stringToInt (String s) {

        try {
            return Optional.of(Integer.valueOf(s));

        } catch ( NumberFormatException e ) {
            return Optional.empty();
        }

    }

    public class Car {

        private Optional<Insurance> insurance;

        public Optional<Insurance> getInsurance () {
            return insurance;
        }
    }

    public class Insurance {

        private String name;

        public String getName () {
            return name;
        }
    }

    public class Person {

        private Optional<Car> car;
        private int age;

        public Optional<Car> getCar () {
            return car;
        }

        public int getAge ()

        {
            return age;
        }
    }

    public Insurance findCheapestInsurance (Person p, Car c) {
        return null;
    }

}
