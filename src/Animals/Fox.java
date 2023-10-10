package Animals;

import Field.*;
import java.util.HashMap;
import java.util.List;

/**
 * A simple model of a fox. Foxes age, move, eat rabbits, and die.
 * 
 * @author David J. Barnes and Michael Kolling. Modified by David Dobervich
 *         2007-2022
 */
public class Fox extends Predator {
	public Fox(boolean startWithRandomAge) {
		super(startWithRandomAge);
		this.BREEDING_AGE = 15;
		this.MAX_AGE = 40;
		this.BREEDING_PROBABILITY = 0.1;
		this.MAX_LITTER_SIZE = 2;
		this.FOOD_VALUE = new HashMap<Animal, Integer>() {
			{
				put(new Rabbit(false), 3);
			}
		};
		setAge(startWithRandomAge);
	}

	protected void generateBaby(List<Animal> babyStorage, Field updatedField) {
		Location birthLocation = updatedField.freeAdjacentLocation(location);
		if (birthLocation != null) {
			Animal baby = new Fox(false);
			baby.setLocation(birthLocation);
			babyStorage.add(baby);
			updatedField.put(baby, birthLocation);
		}
	}

}
