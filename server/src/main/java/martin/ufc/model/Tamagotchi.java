package martin.ufc.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Tamagotchi {
    public static final int DEFAULT_ATTRIBUTE_VALUE = 50;
    public static final int MINOR_VALUE = 5;
    public static final int MEDIUM_VALUE = 15;
    public static final int MAJOR_VALUE = 30;
    public static final int HUGE_VALUE = 50;
    private LocalDate birthday;
    private final String name;
    private boolean isSleeping;
    private final Attribute food;
    private final Attribute happy;
    private final Attribute energy;


    public String toJSON() {
        return "{"
                + "\"name\": \"" + name + "\","
                + "\"ageInDays\": " + getAgeInDays() + ","
                + "\"isSleeping\": " + isSleeping + ","
                + "\"food\": " + getFood() + ","
                + "\"happy\": " + getHappy() + ","
                + "\"energy\": " + getEnergy()
                + "}";
    }

    public Tamagotchi(String name) {
        this.name = name;
        birthday = LocalDate.now();
        isSleeping = false;
        food = new Attribute(DEFAULT_ATTRIBUTE_VALUE);
        happy= new Attribute(DEFAULT_ATTRIBUTE_VALUE);
        energy = new Attribute(DEFAULT_ATTRIBUTE_VALUE);
    }

    public void play() {
        if (isNotSleeping()) {
            energy.decrementPercent(MINOR_VALUE);
            food.decrementPercent(MINOR_VALUE);
            happy.incrementPercent(MEDIUM_VALUE);
        }
    }

    public void sleep() {
        if (isNotSleeping()) {
            isSleeping = true;
        }
    }

    public void awake(int timeSleepingInMinutes) {
        if (isSleeping) {
            int totalRecovered = timeSleepingInMinutes/6;
            energy.incrementPercent(totalRecovered);
            food.decrementPercent(totalRecovered/2);
            happy.decrementPercent(MINOR_VALUE);
            isSleeping = false;
        }
    }

    public void eat() {
        if (isNotSleeping()) {
            food.incrementPercent(MAJOR_VALUE);
            happy.incrementPercent(MINOR_VALUE);
        }
    }

    public String getName() {
        return name;
    }

    public int getAgeInDays() {
        return (int) ChronoUnit.DAYS.between(birthday, LocalDate.now());
    }

    public int getFood() {
        return food.getPercent();
    }

    public int getHappy() {
        return happy.getPercent();
    }

    public int getEnergy() {
        return energy.getPercent();
    }

    private boolean isNotSleeping() {
        return !isSleeping;
    }
}
