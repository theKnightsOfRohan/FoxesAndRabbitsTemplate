package Animals;

import Field.*;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

public class Hawk extends Predator {
    List<Hawk> eggClutch;
    private int clutchSize;

    public Hawk(boolean startWithRandomAge) {
        super(startWithRandomAge);
        this.BREEDING_AGE = 5;
        this.MAX_AGE = 30;
        this.BREEDING_PROBABILITY = 0.01;
        this.MAX_LITTER_SIZE = 1;
        this.FOOD_VALUE = new HashMap<Animal, Integer>() {
            {
                put(new Rabbit(false), 3);
            }
        };
        this.MAX_FOOD_VALUE = 20;
        this.eggClutch = new ArrayList<Hawk>();
        this.clutchSize = 3;

        setAge(startWithRandomAge);
    }

    protected void generateBaby(List<Animal> babyStorage, Field updatedField) {
        if (eggClutch.isEmpty()) {
            for (int i = 0; i < clutchSize; i++) {
                eggClutch.add(new Hawk(false));
            }
        } else {
            for (Hawk hawk : eggClutch) {
                Location birthLocation = updatedField.freeAdjacentLocation(location);
                if (birthLocation != null) {
                    hawk.setLocation(birthLocation);
                    babyStorage.add(hawk);
                    updatedField.put(hawk, birthLocation);
                }
            }
            eggClutch.clear();
        }
    }
}
