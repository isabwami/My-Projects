public class Truck implements Comparable<Truck> {
    
    private double time;
    private int ID;

    public Truck(double time, int ID) {
        this.time = time;
        this.ID = ID;
    } // end Truck constructor

    public int getID() {
        return ID;
    } // end getID method

    public double getTime() {
        return time;
    } // end getTime() method

    public int compareTo(Truck o) {
        if (this.getID() < o.getID()) {
            return -1;
        }        
        return 1;
    } // end compareTo() method

    public String toString() {
        return "TRUCK #" + ID;
    } // end toString() method
} // end Truck class
