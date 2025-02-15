public class Item {
    
    //declare Item fields
    private final String NAME;
    private final double WEIGHT;
    private final int VALUE;
    private boolean included;
    
    // constructor
    public Item(String name, double weight, int value) {
        NAME = name;
        WEIGHT = weight;
        VALUE = value;
        included = false;
    }

    // secondary constructor
    public Item(Item other) {
        this.NAME = other.NAME;
        this.WEIGHT = other.WEIGHT;
        this.VALUE = other.VALUE;
        this.included = other.included;
    }
    
    // getters and setters
    public double getWEIGHT() {
        return WEIGHT;
    }

    public int getVALUE() {
        return VALUE;
    }

    public boolean isIncluded() {
        return included;
    }

    public void setIncluded(boolean included) {
        this.included = included;
    }

    // to string
    public String toString() {
        return NAME + " (" + WEIGHT + " lbs, $" + VALUE + ")";
    }
}