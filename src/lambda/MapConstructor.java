package lambda;

import java.util.HashMap;
import java.util.Map;
import java.util.function.IntFunction;

/**
 * @author chris_ge
 */
public class MapConstructor {

    public static void main(String[] args) {
        Map<String,IntFunction<Fruit>> map = new HashMap<>();
        map.put("apple", Apple::new);
        map.put("orange", Orange::new);

        System.out.println(getMeFruit("orange",10,map).getClass().getCanonicalName());

    }

    public static Fruit getMeFruit(String fruit, int weight, Map<String,IntFunction<Fruit>> map) {
        return map.get(fruit.toLowerCase()).apply(weight);

    }

    static class Apple implements Fruit {
        int weight;
        String type;

        public Apple(int weight) {
            this.weight = weight;
        }

        @Override
        public int getWeight() {
            return weight;
        }
    }

    static class Orange implements Fruit {
        int weight;
        String type;

        public Orange(int weight) {
            this.weight = weight;
        }

        @Override
        public int getWeight() {
            return weight;
        }
    }

    interface Fruit {
        int getWeight();
    }
}
