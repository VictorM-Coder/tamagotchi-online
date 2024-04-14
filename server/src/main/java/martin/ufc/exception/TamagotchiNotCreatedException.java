package martin.ufc.exception;

public class TamagotchiNotCreatedException extends Exception{
    public TamagotchiNotCreatedException() {
        super("Tamagotchi not created yet");
    }
}
