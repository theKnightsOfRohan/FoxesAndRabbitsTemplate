package Animals;

import Field.*;
import java.util.HashMap;
import java.util.List;

public class Predator extends Animal {
    protected static HashMap<Animal, Integer> FOOD_VALUE = new HashMap<Animal, Integer>();
    protected static int MAX_FOOD_VALUE;
    protected int foodLevel;

    public Predator(boolean startWithRandomAge) {
        super(startWithRandomAge);
    }

    @Override
    public void setAge(boolean startWithRandomAge) {
        super.setAge(startWithRandomAge);
        if (startWithRandomAge) {
            foodLevel = (int) (Math.random() * MAX_FOOD_VALUE);
        } else {
            foodLevel = MAX_FOOD_VALUE;
        }
    }

    public void act(Field currentField, Field updatedField, List<Animal> babyStorage) {
        incrementAge();
        if (alive) {
            int births = breed();
            for (int b = 0; b < births; b++) {
                generateBaby(babyStorage, updatedField);
            }
            Location newLocation = findFood(currentField, location);
            if (newLocation == null) {
                newLocation = updatedField.freeAdjacentLocation(location);
            }
            if (newLocation != null) {
                setLocation(newLocation);
                updatedField.put(this, newLocation);
            } else {
                alive = false;
            }
        }
    }

    private void generateBaby(List<Animal> babyStorage, Field updatedField) {
        return;
    }

    protected void incrementHunger() {
        foodLevel--;
        if (foodLevel <= 0) {
            alive = false;
        }
    }

    protected Location findFood(Field field, Location location) {
        List<Location> adjacentLocations = field.adjacentLocations(location);
        for (Location where : adjacentLocations) {
            Object animal = field.getObjectAt(where);
            if (animal instanceof Predator || animal == null)
                continue;

            for (Animal prey : FOOD_VALUE.keySet()) {
                if (typeof(animal) == typeof(prey)) {
                    prey.setEaten();
                    foodLevel = Math.min(foodLevel + FOOD_VALUE.get(prey), MAX_FOOD_VALUE);
                    return where;
                }
            }
        }
        return null;
    }

    private Object typeof(Object animal) {
        return animal.getClass();
    }

    protected void setFoodLevel(int foodLevel) {
        this.foodLevel = foodLevel;
    }
}
