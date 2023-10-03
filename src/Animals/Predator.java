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

    public void hunt(Field currentField, Field updatedField, List<? super Predator> babyStorage) {
        incrementAge();
        if (alive) {
            int births = breed();
            for (int b = 0; b < births; b++) {
                Predator newPredator;
                try {
                    newPredator = this.getClass().getConstructor(boolean.class).newInstance(false);
                } catch (Exception e) {
                    newPredator = new Predator(false);
                }
                newPredator.setFoodLevel(this.foodLevel);
                babyStorage.add(newPredator);
                Location loc = updatedField.randomAdjacentLocation(location);
                newPredator.setLocation(loc);
                updatedField.put(newPredator, loc);
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
