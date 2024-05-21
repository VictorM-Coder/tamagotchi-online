package martin.ufc.model.tamagotchi;

import martin.ufc.exception.TamagotchiNotCreatedException;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class TamagotchiKeeper {
    private Tamagotchi tamagotchi;
    private boolean isCreated = false;
    private LocalDateTime sleepTime;

    public void createTamagotchi(String name) {
        if (name.isEmpty()) {
            throw new NullPointerException();
        } else {
            tamagotchi = new Tamagotchi(name);
            isCreated = true;
        }
    }

    public void feed() throws TamagotchiNotCreatedException {
        checkTamagotchiWasCreated();
        tamagotchi.eat();
    }

    public void putToSleep() throws TamagotchiNotCreatedException {
        checkTamagotchiWasCreated();
        tamagotchi.sleep();
        sleepTime = LocalDateTime.now();
    }

    public void awake() throws TamagotchiNotCreatedException {
        checkTamagotchiWasCreated();
        tamagotchi.awake(calculateTimeSleeping());
        sleepTime = null;
    }

    public void putToPlay() throws TamagotchiNotCreatedException {
        checkTamagotchiWasCreated();
        tamagotchi.play();
    }

    public Tamagotchi getTamagotchi() throws TamagotchiNotCreatedException {
        checkTamagotchiWasCreated();
        return tamagotchi;
    }

    private void checkTamagotchiWasCreated() throws TamagotchiNotCreatedException {
        if (!isCreated) throw new TamagotchiNotCreatedException();
    }

    private int calculateTimeSleeping() {
        if (sleepTime == null) {
            return 0;
        } else {
            return (int) ChronoUnit.MINUTES.between(sleepTime, LocalDateTime.now());
        }
    }
}
