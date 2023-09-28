package Animals;

import Field.*;
import java.util.List;

/**
 * The Bear class represents a bear in the simulation. Bears age, die, move,
 * eat, and breedA bear can eat both rabbits and foxes.
 * 
 * @author github.com/theKnightsOfRohan
 * @see Animal
 * @see Rabbit
 * @see Fox
 * @see Field
 * @see Location
 */
public class Bear {
    private static int BREEDING_AGE = 20;
    private static int MAX_AGE = 50;
    private static double BREEDING_PROBABILITY = 0.03;
    private static int MAX_LITTER_SIZE = 1;
    private static int FOOD_VALUE = 20;

    // Amount of food that a rabbit or fox fills a bear's food level by
    // when eaten.
    // Foxes are bigger than rabbits, so they are more filling.
    private static int RABBIT_AMOUNT_FOOD = 3;
    private static int FOX_AMOUNT_FOOD = 6;

    private int age;
    private boolean alive;
    private Location location;
    private int foodLevel;

    /**
     * This class represents a Bear in the simulation. It inherits from the Animal
     * class.
     * A Bear can have a maximum age and a food value. It can eat rabbits and foxes.
     * A Bear can move, breed, and die of old age or hunger.
     * 
     * @param startWithRandomAge A boolean value indicating whether the Bear should
     *                           start with a random age or not.
     */
    public Bear(boolean startWithRandomAge) {
        age = 0;
        alive = true;
        if (startWithRandomAge) {
            age = (int) (Math.random() * MAX_AGE);
            foodLevel = (int) (Math.random() * FOOD_VALUE);
        } else {
            foodLevel = FOOD_VALUE;
        }
    }

    /**
     * This method represents the hunting behavior of a bear. It increments the age
     * and hunger of the bear.
     * If the bear is alive, it breeds and creates new baby bears. It then looks for
     * food in the current field,
     * and if it finds food, it moves to that location. If it doesn't find food, it
     * moves to a free adjacent location.
     * If it doesn't find any free adjacent location, it dies.
     * 
     * @param currentField    The field where the bear is currently located.
     * @param updatedField    The field where the bear will be moved to.
     * @param babyBearStorage A list of baby bears that are created during the
     *                        breeding process.
     */
    public void hunt(Field currentField, Field updatedField, List<Bear> babyBearStorage) {
        incrementAge();
        incrementHunger();
        if (alive) {
            int births = breed();
            for (int b = 0; b < births; b++) {
                Bear newBear = new Bear(false);
                newBear.setFoodLevel(this.foodLevel);
                babyBearStorage.add(newBear);
                Location loc = updatedField.randomAdjacentLocation(location);
                newBear.setLocation(loc);
                updatedField.put(newBear, loc);
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

    /**
     * Increments the age of the bear by 1. If the age of the bear exceeds the
     * maximum age,
     * the bear is marked as dead.
     */
    private void incrementAge() {
        age++;
        if (age > MAX_AGE) {
            alive = false;
        }
    }

    /**
     * Decreases the food level of the bear by 1 and sets the bear as not alive if
     * the food level is less than or equal to 0.
     */
    private void incrementHunger() {
        foodLevel--;
        if (foodLevel <= 0) {
            alive = false;
        }
    }

    /**
     * Finds food for the bear in the given field and location.
     * If a rabbit or fox is found, it is eaten and the bear's food level is
     * increased accordingly.
     * The bear's food level is capped at FOOD_VALUE.
     * 
     * @param field    The field where the bear is located.
     * @param location The location of the bear in the field.
     * @return The location of the food source, or null if no food was found.
     */
    private Location findFood(Field field, Location location) {
        List<Location> adjacentLocations = field.adjacentLocations(location);

        for (Location where : adjacentLocations) {
            Object animal = field.getObjectAt(where);
            if (animal instanceof Rabbit) {
                Rabbit rabbit = (Rabbit) animal;
                if (rabbit.isAlive()) {
                    rabbit.setEaten();
                    foodLevel += RABBIT_AMOUNT_FOOD;
                    foodLevel = Math.min(foodLevel, FOOD_VALUE);
                    return where;
                }
            } else if (animal instanceof Fox) {
                Fox fox = (Fox) animal;
                if (fox.isAlive()) {
                    fox.setEaten();
                    foodLevel += FOX_AMOUNT_FOOD;
                    foodLevel = Math.min(foodLevel, FOOD_VALUE);
                    return where;
                }
            }
        }

        return null;
    }

    /**
     * Calculates the number of births that a bear can have based on its breeding
     * probability and maximum litter size.
     * 
     * @return The number of births that the bear can have.
     */
    private int breed() {
        int numBirths = 0;
        if (canBreed() && Math.random() <= BREEDING_PROBABILITY) {
            numBirths = (int) (Math.random() * MAX_LITTER_SIZE) + 1;
        }
        return numBirths;
    }

    private boolean canBreed() {
        return age >= BREEDING_AGE;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setLocation(int row, int col) {
        this.location = new Location(row, col);
    }

    public void setFoodLevel(int foodLevel) {
        this.foodLevel = foodLevel;
    }
}
