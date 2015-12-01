package stream;

import static java.util.stream.Collectors.averagingInt;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.maxBy;
import static java.util.stream.Collectors.partitioningBy;
import static java.util.stream.Collectors.summarizingInt;
import static java.util.stream.Collectors.summingInt;
import static java.util.stream.Collectors.toCollection;
import static java.util.stream.Collectors.toSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author chris_ge
 */
public class Dish {
    private final String name;
    private final boolean vegetarian;
    private final int calories;
    private final Type type;

    public Dish (String name, boolean vegetarian, int calories, Type type) {
        this.name = name;
        this.vegetarian = vegetarian;
        this.calories = calories;
        this.type = type;
    }

    public String getName () {
        return name;
    }

    public boolean isVegetarian () {
        return vegetarian;
    }

    public int getCalories () {
        return calories;
    }

    public Type getType () {
        return type;
    }

    @Override
    public String toString () {
        return name;
    }

    public enum Type {MEAT, FISH, OTHER}

    public enum Caloriclevel {DIET, NORMAL, FAT}

    ;

    public static void main (String[] args) {
        List<Dish> dishes = new ArrayList<>();
        dishes.add(new Dish(
                                   "fish",
                                   false,
                                   100,
                                   Type.FISH
        ));
        dishes.add(new Dish(
                                   "steak",
                                   true,
                                   120,
                                   Type.MEAT
        ));
        dishes.add(new Dish(
                                   "cake",
                                   false,
                                   900,
                                   Type.OTHER
        ));
        dishes.add(new Dish(
                                   "carrot",
                                   true,
                                   500,
                                   Type.OTHER
        ));

        //dishes.stream().map(Dish::getName).map(String::length).forEach(System.out::println);
        dishes.stream()
              .map(Dish::getName)
              .map(s -> s.split(""))
              .flatMap(Arrays::stream)
              .distinct()
              .forEach(System.out::println);

        List<String> menu = dishes.stream()
                                  .map(Dish::getName)
                                  .collect(Collectors.toList());
        menu.stream()
            .map(s -> s.split(""))
            .map(Arrays::stream)
            .distinct()
            .forEach(System.out::println);

        if ( dishes.stream()
                   .anyMatch(Dish::isVegetarian) ) {
            System.out.println("The menu is (somewhat) vegetarian friendly!!");
        }

        if ( dishes.stream()
                   .allMatch(dish -> dish.getCalories() < 300) ) {
            System.out.println("The menu is (somewhat) calories friendly!!");

        }

        dishes.stream()
              .map(dish -> 1)
              .reduce(Integer::sum)
              .ifPresent(System.out::println);

        System.out.println(dishes.stream()
                                 .count());

        System.out.println(dishes.parallelStream()
                                 .map(Dish::getCalories)
                                 .reduce(Integer::max));

        System.out.println(dishes.parallelStream()
                                 .mapToInt(Dish::getCalories)
                                 .sum());

        System.out.println(dishes.parallelStream()
                                 .mapToInt(Dish::getCalories)
                                 .max());
        System.out.println(2.3 % 1);

        System.out.println(dishes.stream()
                                 .collect(summarizingInt(Dish::getCalories)));
        System.out.println(dishes.stream()
                                 .collect(averagingInt(Dish::getCalories)));
        System.out.println(dishes.stream()
                                 .collect(summingInt(Dish::getCalories)));

        Map<Type,List<Dish>> dishesByType = dishes.stream()
                                                  .collect(groupingBy(Dish::getType));

        Map<Caloriclevel,List<Dish>> dishesByCaloriclevel = dishes.stream()
                                                                  .collect(groupingBy(dish -> {
                                                                      if ( dish.getCalories() <= 400 )
                                                                          return Caloriclevel.DIET;
                                                                      else if ( dish.getCalories() <= 700 )
                                                                          return Caloriclevel.NORMAL;
                                                                      else return Caloriclevel.FAT;
                                                                  }));
        Map<Type,Map<Caloriclevel,List<Dish>>> dishesByTypeCaloriclevel = dishes.stream()
                                                                                .collect(groupingBy(
                                                                                        Dish::getType,
                                                                                        groupingBy(dish -> {
                                                                                            if ( dish.getCalories() <= 400 )
                                                                                                return Caloriclevel.DIET;
                                                                                            else if ( dish.getCalories() <= 700 )
                                                                                                return Caloriclevel.NORMAL;
                                                                                            else
                                                                                                return Caloriclevel.FAT;

                                                                                        })
                                                                                         )
                                                                                );

        Map<Type,Optional<Dish>> mostCaloricByType = dishes.stream()
                                                           .collect(groupingBy(
                                                                   Dish::getType,
                                                                   maxBy(Comparator.comparingInt(Dish::getCalories))
                                                           ));
        //The groupingBy collector lazily adds a new key in the grouping Map only the first time it finds an element in the stream,
        // producing that key when applying on it the grouping criteria being used.
        Map<Type,Dish> mostCaloricByType1 = dishes.stream()
                                                  .collect(groupingBy(
                                                          Dish::getType,
                                                          collectingAndThen(
                                                                  maxBy(Comparator.comparingInt(Dish::getCalories)),
                                                                  Optional::get
                                                          )
                                                  ));

        Function<Dish,Caloriclevel> caloricTypeFunc = dish -> {
            if ( dish.getCalories() <= 400 )
                return Caloriclevel.DIET;
            else if ( dish.getCalories() <= 700 )
                return Caloriclevel.NORMAL;
            else
                return Caloriclevel.FAT;

        };

        Map<Type,Set<Caloriclevel>> caloricLevelsByType = dishes.stream()
                                                                .collect(groupingBy(
                                                                        Dish::getType,
                                                                        mapping(caloricTypeFunc, toSet())
                                                                ));
        System.out.println("caloricLevelsByType = " + caloricLevelsByType);

        caloricLevelsByType = dishes.stream()
                                    .collect(groupingBy(Dish::getType, mapping(
                                            caloricTypeFunc,
                                            toCollection(HashSet::new)
                                    )));
        System.out.println("caloricLevelsByType = " + caloricLevelsByType);

        Map<Boolean,List<Dish>> partitionedDishes = dishes.stream()
                                                          .collect(partitioningBy(Dish::isVegetarian));
        Map<Boolean,Map<Type,List<Dish>>> vegetarianDishesByType = dishes.stream()
                                                                         .collect(
                                                                                 partitioningBy(
                                                                                         Dish::isVegetarian,
                                                                                         groupingBy(
                                                                                                 Dish::getType)
                                                                                 )

                                                                         );

        Map<Boolean,Dish> mostCaloricPartitionedByVegetarian = dishes.stream()
                                                                     .collect(partitioningBy(
                                                                             Dish::isVegetarian,
                                                                             collectingAndThen(
                                                                                     maxBy(Comparator.comparingInt(
                                                                                             Dish::getCalories)),
                                                                                     Optional::get
                                                                             )

                                                                     ));

        System.out.println("mostCaloricPartitionedByVegetarian = " + mostCaloricPartitionedByVegetarian);
        Map<Boolean,Integer> mostCaloricNumberPartitionedByVegetarian = dishes.stream()
                                                                              .collect(partitioningBy(
                                                                                      Dish::isVegetarian,
                                                                                      mapping(Dish::getCalories,
                                                                                              collectingAndThen(
                                                                                                      maxBy(Comparator.comparingInt(
                                                                                                              c -> c)),
                                                                                                      Optional::get
                                                                                              ))

                                                                              ));

        System.out.println("mostCaloricNumberPartitionedByVegetarian = " + mostCaloricNumberPartitionedByVegetarian);
        
        
        
    }
}
