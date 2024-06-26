package martin.ufc.server.infra.services;

import martin.ufc.exception.InternalException;
import martin.ufc.exception.SQLiteException;
import martin.ufc.exception.TamagotchiNotFoundException;
import martin.ufc.model.tamagotchi.Tamagotchi;
import martin.ufc.persistence.repository.TamagotchiRepository;

public class TamagotchiService {
    private TamagotchiService() {}

    public static Tamagotchi createTamagotchi(String name) throws InternalException {
        try {
            var tamagotchi = new Tamagotchi(name);
            int id = TamagotchiRepository.add(tamagotchi);
            tamagotchi.setId(id);
            return tamagotchi;
        } catch (SQLiteException e) {
            throw new InternalException("Error while creating the tamagotchi");
        }
    }

    public static Tamagotchi feedTamagotchi(int id) throws TamagotchiNotFoundException, InternalException {
        final var tamagotchi = findTamagotchiById(id);
        tamagotchi.eat();

        return updateTamagotchi(id, tamagotchi);
    }

    public static Tamagotchi putTamagotchiToSleep(int id) throws TamagotchiNotFoundException, InternalException {
        final var tamagotchi = findTamagotchiById(id);
        tamagotchi.sleep();

        return updateTamagotchi(id, tamagotchi);
    }

    public static Tamagotchi awakeTamagotchi(int id) throws TamagotchiNotFoundException, InternalException {
        final var tamagotchi = findTamagotchiById(id);
        tamagotchi.awake();

        return updateTamagotchi(id, tamagotchi);
    }

    public static Tamagotchi playWithTamagotchi(int id) throws TamagotchiNotFoundException, InternalException {
        final var tamagotchi = findTamagotchiById(id);
        tamagotchi.play();

        return updateTamagotchi(id, tamagotchi);
    }

    public static Tamagotchi findTamagotchiById(int id) throws TamagotchiNotFoundException {
        try {
            return TamagotchiRepository.findById(id);
        } catch (SQLiteException e) {
            throw new TamagotchiNotFoundException("this tamagotchi not exists");
        }
    }

    private static Tamagotchi updateTamagotchi(int id, Tamagotchi tamagotchi) throws InternalException {
        try {
            TamagotchiRepository.update(id, tamagotchi);
            return tamagotchi;
        } catch (SQLiteException e) {
            throw new InternalException(e.getMessage());
        }
    }
}
