package martin.ufc.model;

public class Attribute {
    private byte percent;
    public Attribute(int percent){
        setPercent(percent);
    }

    public void incrementPercent(int increment) {
        sumPercent(increment < 0 ? increment*-1: increment);
    }

    public void decrementPercent(int decrement) {
        sumPercent(decrement < 0 ? decrement: decrement*-1);
    }


    private void sumPercent(int value) {
        int total = percent + value;

        if (total >= 100) {
            percent = 100;
        } else if (total <= 0) {
            percent = 0;
        } else {
            percent = (byte) total;
        }
    }

    public void setPercent(int percent) {
        if (checkValidPercent(percent)) {
            this.percent = (byte) percent;
        } else {
            throw new InvalidPercentageException(percent);
        }
    }

    public byte getPercent() {
        return percent;
    }

    private boolean checkValidPercent(int percent) {
        return percent >= 0 && percent <= 100;
    }
}
