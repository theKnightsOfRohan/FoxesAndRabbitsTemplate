package Animals;

import Field.*;
import java.util.HashMap;
import java.util.List;

public class Bear extends Predator {
    public Bear(boolean startWithRandomAge) {
        super(startWithRandomAge);
        this.BREEDING_AGE = 15;
        this.MAX_AGE = 40;
        this.BREEDING_PROBABILITY = 0.1;
        this.MAX_LITTER_SIZE = 2;
        this.FOOD_VALUE = new HashMap<Animal, Integer>() {
            {
                put(new Rabbit(false), 3);
                put(new Fox(false), 5);
            }
        };
        this.MAX_FOOD_VALUE = 20;
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
