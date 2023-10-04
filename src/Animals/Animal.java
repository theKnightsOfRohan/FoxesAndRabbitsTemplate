package Animals;

import Field.*;

public class Animal {
    protected static int BREEDING_AGE;
    protected static int MAX_AGE;
    protected static int MAX_LITTER_SIZE;
    protected static double BREEDING_PROBABILITY;
    protected int age;
    protected boolean alive;
    protected Location location;

    public Animal(boolean startWithRandomAge) {
        age = 0;
        alive = true;
    }

    protected void setAge(boolean startWithRandomAge) {
        if (startWithRandomAge) {
            age = (int) (Math.random() * MAX_AGE);
        }
    }

    public void incrementAge() {
        age++;
        if (age > MAX_AGE) {
            alive = false;
        }
    }

    protected int breed() {
        int numBirths = 0;
        if (canBreed() && Math.random() <= BREEDING_PROBABILITY) {
            numBirths = (int) (Math.random() * MAX_LITTER_SIZE) + 1;
        }
        return numBirths;
    }

    protected boolean canBreed() {
        return age >= BREEDING_AGE;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setEaten() {
        alive = false;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setLocation(int row, int col) {
        this.location = new Location(row, col);
    }
}
