package martin.ufc.model.tamagotchi;

import martin.ufc.model.JSONfier;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Tamagotchi implements JSONfier {
    public static final int DEFAULT_ATTRIBUTE_VALUE = 50;
    public static final int MINOR_VALUE = 5;
    public static final int MEDIUM_VALUE = 15;
    public static final int MAJOR_VALUE = 30;
    public static final int HUGE_VALUE = 50;
    private int id;
    private final LocalDate birthday;
    private final String name;
    private boolean isSleeping;
    private LocalDateTime startedSleeping;
    private final Attribute food;
    private final Attribute happy;
    private final Attribute energy;

    public Tamagotchi(int id, String name, LocalDate birthday, boolean isSleeping, Attribute food, Attribute happy, Attribute energy, LocalDateTime startedSleeping) {
        this.id = id;
        this.name = name;
        this.birthday = birthday;
        this.isSleeping = isSleeping;
        this.food = food;
        this.happy = happy;
        this.energy = energy;
        this.startedSleeping = startedSleeping;
    }

    @Override
    public String toJSON() {
        return "{"
                + "\"id\": \"" + id + "\","
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
            startedSleeping = LocalDateTime.now();
        }
    }

    public void awake() {
        if (isSleeping) {
            int timeSleepingInMinutes = (int) ChronoUnit.MINUTES.between(LocalDateTime.now(), startedSleeping);
            int totalRecovered = timeSleepingInMinutes/6;
            energy.incrementPercent(totalRecovered);
            food.decrementPercent(totalRecovered/12);
            happy.decrementPercent(MINOR_VALUE);
            isSleeping = false;
            startedSleeping = null;
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

    public boolean isSleeping() {
        return isSleeping;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getStartedSleeping() {
        return startedSleeping;
    }

    public int getId() {
        return id;
    }
}
