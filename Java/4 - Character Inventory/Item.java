public class Item implements Comparable<Item> {
    
    // declare fields 
    private String name;
    private int value;

    // constructors
    public Item(String name, int value) {
        this.name = name;
        this.value = value; 
    }

    // to string method for 'System.out.print()'
    public String toString() {
        return name + " (" + value + ")";
    }

    // compare method which allows 'Collections.sort()'
    public int compareTo(Item other) {
        // go after
        if (this.value < other.value) {
            return 1;
        }
        // go before
        else if (this.value > other.value) {
            return -1;
        }
        // equal
        else {
            return 0;
        }
    }

    // getter setter methods
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;   
    }
}
