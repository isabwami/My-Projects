public class Event implements Comparable<Event> {
    private double eventStart;
    private int ID;

    public Event(double eventStart, int ID) {
        this.eventStart = eventStart;
        this.ID = ID;
    } // end Event constructor
    
    public double getEventStart() {
        return eventStart;
    } // end getEventStart() method

    public int getID() {
        return ID;
    } // end getID() method

    public int compareTo(Event o) {

        if (this.getEventStart() < o.getEventStart()) {
            return -1;
        } // end if-statement
        else if (this.getEventStart() > o.getEventStart()) {
            return 1;
        } // end else-if-statement
        else {
            if (this instanceof TrainEvent && o instanceof TruckEvent) {
                return -1;
            } // end if-statement
            return 0;
        } // end else statement
    } // end compareTo() method
} // end Event class
