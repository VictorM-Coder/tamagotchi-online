package martin.ufc.server.infra.services;

import martin.ufc.exception.RequestException;
import martin.ufc.exception.TamagotchiNotFoundException;
import martin.ufc.exception.SQLiteException;
import martin.ufc.model.tamagotchi.Tamagotchi;
import martin.ufc.persistence.repository.TamagotchiRepository;

public class TamagotchiService {
    public Tamagotchi createTamagotchi(String name) throws RequestException {
        try {
            var tamagotchi = new Tamagotchi(name);
            int id = TamagotchiRepository.add(tamagotchi);
            tamagotchi.setId(id);
            return tamagotchi;
        } catch (SQLiteException e) {
            throw new RequestException("Internal error");
        }
    }

    public Tamagotchi feedTamagotchi(int id) throws RequestException, TamagotchiNotFoundException {
        final var tamagotchi = findTamagotchiById(id);
        tamagotchi.eat();

        return updateTamagotchi(id, tamagotchi);
    }

    public Tamagotchi putTamagotchiToSleep(int id) throws RequestException, TamagotchiNotFoundException {
        final var tamagotchi = findTamagotchiById(id);
        tamagotchi.sleep();

        return updateTamagotchi(id, tamagotchi);
    }

    public Tamagotchi awakeTamagotchi(int id) throws RequestException, TamagotchiNotFoundException {
        final var tamagotchi = findTamagotchiById(id);
        tamagotchi.awake();

        return updateTamagotchi(id, tamagotchi);
    }

    public Tamagotchi playWithTamagotchi(int id) throws RequestException, TamagotchiNotFoundException {
        final var tamagotchi = findTamagotchiById(id);
        tamagotchi.play();

        return updateTamagotchi(id, tamagotchi);
    }

    public Tamagotchi findTamagotchiById(int id) throws RequestException {
        try {
            return TamagotchiRepository.findById(id);
        } catch (SQLiteException e) {
            throw new RequestException("Id not exists");
        }
    }

    private Tamagotchi updateTamagotchi(int id, Tamagotchi tamagotchi) throws TamagotchiNotFoundException {
        try {
            TamagotchiRepository.update(id, tamagotchi);
            return tamagotchi;
        } catch (SQLiteException e) {
            throw new TamagotchiNotFoundException(e.getMessage());
        }
    }
}
