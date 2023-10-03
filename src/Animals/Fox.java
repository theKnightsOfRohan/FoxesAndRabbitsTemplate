package Animals;

import Animals.*;
import Field.*;
import Graph.*;

import java.io.Serializable;
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
		Fox.BREEDING_AGE = 15;
		Fox.MAX_AGE = 40;
		Fox.BREEDING_PROBABILITY = 0.1;
		Fox.MAX_LITTER_SIZE = 2;
		Fox.FOOD_VALUE = new HashMap<Animal, Integer>() {
			{
				put(new Rabbit(false), 3);
			}
		};
		setAge(startWithRandomAge);
	}

}
