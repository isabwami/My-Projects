public class Truck implements Comparable<Truck> {
    
    private double time;
    private int ID;

    public Truck(double time, int ID) {
        this.time = time;
        this.ID = ID;
    }

    public int getID() {
        return ID;
    }

    public double getTime() {
        return time;
    }

    public int compareTo(Truck o) {
        
        if (this.getID() < o.getID()) {
            return -1;
        }        
        return 1;
    }

    public String toString() {

        return "TRUCK #" + ID;
    }
}
