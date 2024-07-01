package martin.ufc.server.infra.handlers;

import java.util.HashSet;
import java.util.Set;

public class ConnectedTamagotchisManager {
    private ConnectedTamagotchisManager() {}
    private static final Set<Integer> tamagotchisOnline = new HashSet<>();

    public static boolean connectTamagotchi(int id) {
        if (checkTamagotchiIsAvailable(id)) {
            tamagotchisOnline.add(id);
            return true;
        } else {
            return false;
        }
    }

    public static void disconnectTamagotchi(int id) {
        tamagotchisOnline.remove(id);
    }

    public static boolean checkTamagotchiIsAvailable(int id) {
        return !tamagotchisOnline.contains(id);
    }

}
