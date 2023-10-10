package Animals;

import Field.*;
import java.util.List;

/**
 * A simple model of a rabbit.
 * Rabbits age, move, breed, and die.
 * 
 * @author David J. Barnes and Michael Kolling. Modified by David Dobervich
 *         2007-2022
 */
public class Rabbit extends Animal {
    /**
     * Create a new rabbit. A rabbit may be created with age
     * zero (a new born) or with a random age.
     * 
     * @param startWithRandomAge If true, the rabbit will have a random age.
     */
    public Rabbit(boolean startWithRandomAge) {
        super(startWithRandomAge);
        this.BREEDING_AGE = 5;
        this.MAX_AGE = 30;
        this.BREEDING_PROBABILITY = 0.1;
        this.MAX_LITTER_SIZE = 8;
        setAge(startWithRandomAge);
    }

    /**
     * This is what the rabbit does most of the time - it runs
     * around. Sometimes it will breed or die of old age.
     * 
     * @param updatedField      The field to transfer to.
     * @param babyRabbitStorage A list to add newly born rabbits to.
     */
    public void act(Field currentfield, Field updatedField, List<Animal> babyRabbitStorage) {
        incrementAge();
        if (alive) {
            int births = breed();
            for (int b = 0; b < births; b++) {
                Animal newRabbit = new Rabbit(false);
                babyRabbitStorage.add(newRabbit);
                Location loc = updatedField.randomAdjacentLocation(location);
                newRabbit.setLocation(loc);
                updatedField.put(newRabbit, loc);
            }
            Location newLocation = updatedField.freeAdjacentLocation(location);
            // Only transfer to the updated field if there was a free location
            if (newLocation != null) {
                setLocation(newLocation);
                updatedField.put(this, newLocation);
            } else {
                // can neither move nor stay - overcrowding - all locations taken
                alive = false;
            }
        }
    }
}
