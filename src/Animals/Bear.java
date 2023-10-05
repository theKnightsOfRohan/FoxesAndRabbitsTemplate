package Animals;

import Field.*;
import java.util.HashMap;
import java.util.List;

public class Bear extends Predator {
    public Bear(boolean startWithRandomAge) {
        super(startWithRandomAge);
        Bear.BREEDING_AGE = 15;
        Bear.MAX_AGE = 40;
        Bear.BREEDING_PROBABILITY = 0.1;
        Bear.MAX_LITTER_SIZE = 2;
        Bear.FOOD_VALUE = new HashMap<Animal, Integer>() {
            {
                put(new Rabbit(false), 3);
                put(new Fox(false), 5);
            }
        };
        Bear.MAX_FOOD_VALUE = 20;
        setAge(startWithRandomAge);
    }

    protected void generateBaby(List<Animal> babyStorage, Field updatedField) {
        Location birthLocation = updatedField.freeAdjacentLocation(location);
        if (birthLocation != null) {
            Animal baby = new Bear(false);
            baby.setLocation(birthLocation);
            babyStorage.add(baby);
            updatedField.put(baby, birthLocation);
        }
    }
}
