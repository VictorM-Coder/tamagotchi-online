package martin.ufc.server.infra.services;

import martin.ufc.exception.RequestException;
import martin.ufc.exception.SQLiteException;
import martin.ufc.model.tamagotchi.Tamagotchi;
import martin.ufc.persistence.repository.TamagotchiRepository;

public class TamagotchiService {
    public Tamagotchi createTamagotchi(String name) throws RequestException, SQLiteException {
        if (name.isEmpty()) {
            throw new RequestException("Name cannot be empty");
        } else {
            try {
                var tamagotchi = new Tamagotchi(name);
                int id = TamagotchiRepository.add(tamagotchi);
                tamagotchi.setId(id);
                return tamagotchi;
            } catch (SQLiteException e) {
                throw new RequestException("Internal error");
            }
        }
    }
}
