public class Event implements Comparable<Event> {
    private double eventStart;
    private int ID;

    public Event(double eventStart, int ID) {
        this.eventStart = eventStart;
        this.ID = ID;
    }
    
    public double getEventStart() {
        return eventStart;
    }

    public int getID() {
        return ID;
    }

    public int compareTo(Event o) {

        if (this.getEventStart() < o.getEventStart()) {
            return -1;
        }
        else if (this.getEventStart() > o.getEventStart()) {
            return 1;
        }
        else {
            if (this instanceof TrainEvent && o instanceof TruckEvent) {
                return -1;
            }
            return 0;
        }
    }
} 
