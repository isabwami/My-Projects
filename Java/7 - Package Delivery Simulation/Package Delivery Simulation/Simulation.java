import java.util.Scanner;
import java.util.PriorityQueue;
import java.util.ArrayList;
import java.util.Collections;
import java.io.FileNotFoundException;
import java.io.File;

public class Simulation {
    
    // 47.0% (0.47) is the optimal percentage of drones 
    private static final int NUM_PACKAGES = 1500;
    private static final double PERCENT_BY_DRONE = 0.47;
    private static final double PERCENT_BY_TRUCK = 1 - PERCENT_BY_DRONE;
    private static final int NUM_DRONES = (int) (NUM_PACKAGES * PERCENT_BY_DRONE);
    private static final int NUM_TRUCKS = (int) Math.ceil((NUM_PACKAGES * PERCENT_BY_TRUCK) / 10);
    private static final int DRONE_SPEED = 500;
    private static final int TRUCK_SPEED = 30;
    private static final int TOTAL_DISTANCE = 30000;
    private static final int DISTANCE_TO_CROSSING = 3000;
    private static final int DISTANCE_FROM_CROSSING = TOTAL_DISTANCE - DISTANCE_TO_CROSSING;
    private static double simClock = 0.0;
    private static boolean isBlocking = false;
    private static double averageTruckTime = 0.0;
    private static double singleDroneTime = 0.0;
    private static double totalTruckTime = 0.0;
    private static double totalDroneTime = 0.0;

    public static void main(String[] args) throws FileNotFoundException {
        
        // System.nanoTime() - https://stackoverflow.com/questions/5204051/how-to-calculate-the-running-time-of-my-program 
        final long startTime = System.nanoTime();

        // print initial info
        System.out.println("With " + (PERCENT_BY_DRONE * 100) + "% drones and " + NUM_PACKAGES + " packages,");
        System.out.println("There will be: ");
        System.out.println("- " + NUM_DRONES + " Drones");
        System.out.println("- " + NUM_TRUCKS + " Trucks");
        
        System.out.println();

        System.out.println("TRAIN SCHEDULE");
        System.out.println("--------------");

        // read from train schedule
        Scanner in = new Scanner(new File("train_schedule.txt"));

        // create data structures
        PriorityQueue<Event> events = new PriorityQueue<>();
        PriorityQueue<Event> waitingTrucks = new PriorityQueue<>();
        ArrayList<Truck> complete = new ArrayList<>();
        ArrayList<Double> completionTimes = new ArrayList<>();

        // create train events
        int TrainID = 0;
        while (in.hasNextLine()) {
            Event block = new TrainBlock(in.nextInt(), TrainID);
            Event clear = new TrainClear((block.getEventStart() + in.nextInt()), TrainID);
            events.offer(block);
            events.offer(clear);
            TrainID++;

            System.out.println((int)(block.getEventStart()) + "-" + (int)(clear.getEventStart()));
        } // end while-loop

        System.out.println();

        // calculate drone completionTimes
        singleDroneTime = TOTAL_DISTANCE / DRONE_SPEED;
        
        totalDroneTime = singleDroneTime;
        for (int i=0; i<NUM_DRONES-1; i++) {
            totalDroneTime += 3;
        } // end for-loop

        // create truck start events and truck at crossing events 
        int truckOffset = 0; 
        for (int i=0; i<NUM_TRUCKS; i++) {
            Event truckStart = new TruckBegin(truckOffset, i);
            truckOffset += 15;

            double arrivesAtCrossing = truckStart.getEventStart() + (DISTANCE_TO_CROSSING / TRUCK_SPEED);
            TruckAtCrossing crossingArrival = new TruckAtCrossing(arrivesAtCrossing, i);

            events.offer(truckStart);
            events.offer(crossingArrival);
        } // end for-loop      

        // loop over 'events' priority queue while it still contains events
        while (!events.isEmpty()) {
            Event e = events.poll();
            simClock = e.getEventStart();
            
            if (e instanceof TruckBegin) {
                System.out.println(simClock + ": TRUCK #" + e.getID() + " begins journey");
            } // end if-statement
            else if (e instanceof TruckAtCrossing) {
                if (isBlocking || !waitingTrucks.isEmpty()) {
                    waitingTrucks.offer(e);
                    System.out.println(simClock + ": TRUCK #" + e.getID() + " waits at crossing");
                } // end if-statetment
                else if (!isBlocking && waitingTrucks.isEmpty()) {
                    System.out.println(simClock + ": TRUCK #" + e.getID() + " crosses crossing");

                    Event truckEnd = new TruckEnd((simClock + (DISTANCE_FROM_CROSSING / TRUCK_SPEED)), e.getID());
                    events.offer(truckEnd);
                } // end else-if-statement
            } // end else-if-statement
            else if (e instanceof TruckEnd) {
                System.out.println(simClock + ": TRUCK #" + e.getID() + " completes journey");

                Truck truck = new Truck((simClock - (e.getID() * 15)), e.getID());
                complete.add(truck);
                completionTimes.add(simClock);
            } // end else-if-statement
            else if (e instanceof TrainBlock) {
                System.out.println(simClock + ": TRAIN" + " arrives at crossing");
                isBlocking = true;
            } // end else-if-statement
            else if (e instanceof TrainClear) {
                isBlocking = false;
                System.out.println(simClock + ": TRAIN" + " leaves crossing");

                if (!waitingTrucks.isEmpty()) {
                    while (!waitingTrucks.isEmpty()) {
                        if (events.peek() instanceof TruckAtCrossing && events.peek().getEventStart() == simClock) {
                            Event temp = events.poll();
                            waitingTrucks.offer(temp);
                            System.out.println(simClock + ": TRUCK #" + temp.getID() + " waits at crossing");
                        } // end if-statement
                        if (events.peek() instanceof TruckBegin && events.peek().getEventStart() == simClock) {
                            Event temp = events.poll();
                            System.out.println(simClock + ": TRUCK #" + temp.getID() + " begins journey");
                        } // end if-statement

                        simClock++;
                        Event currentWaitingTruck = waitingTrucks.poll();
                        System.out.println(simClock + ": TRUCK #" + currentWaitingTruck.getID() + " crosses crossing");

                        Event truckEnd = new TruckEnd((simClock + (DISTANCE_FROM_CROSSING / TRUCK_SPEED)), currentWaitingTruck.getID());
                        events.offer(truckEnd);
                    } // end while-loop
                } // end if-statement
            } // end else-if-statement
        } // end while-loop

        // print stats
        System.out.println();
        System.out.println("STATS");
        System.out.println("-----");

        Collections.sort(complete);

        // get individual truck times and update sum of truck times for average
        double sumTimes = 0;
        for (Truck truck : complete) {
            double truckTime = truck.getTime();
            sumTimes += truckTime;

            System.out.println(truck + " total trip time: " + truckTime + " minutes");
        } // end for-loop
        
        Collections.sort(completionTimes);
        
        averageTruckTime = Math.round((sumTimes / completionTimes.size()) * 10 / 10);

        totalTruckTime = completionTimes.get(completionTimes.size()-1);
        
        System.out.println();
        System.out.println("TRUCK AVG TRIP TIME: " + averageTruckTime + " minutes");
        System.out.println("TRUCK TOTAL TIME: " + totalTruckTime + " minutes");

        System.out.println();

        System.out.println("DRONE TRIP TIME: " + singleDroneTime + " minutes");
        System.out.println("DRONE TOTAL TIME: " + totalDroneTime + " minutes");
        
        System.out.println();

        // between total drone time and total truck time, the greater one is the total time for all packages to be delivered
        double totalTime = Math.max(totalDroneTime, totalTruckTime);
        System.out.println("TOTAL TIME: " + totalTime + " minutes");

        // calculate build time
        final long duration = (System.nanoTime() - startTime) / 1000000000;

        System.out.println("BUILD SUCCESSFUL (total time: " + duration + " seconds)");
    } // end main() method
} // end Simulation class