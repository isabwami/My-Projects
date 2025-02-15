import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;


public class Simulation {

    public static void main(String[] args) throws FileNotFoundException {
        
        double p = 0.01;
        int l = 1;

        for (int k=0; k<99; k++) {
            final int NUM_PACKAGES = 1500;
            double PERCENT_BY_DRONE = p;
            final double PERCENT_BY_TRUCK = 1 - PERCENT_BY_DRONE;
            final int NUM_DRONES = (int) (NUM_PACKAGES * PERCENT_BY_DRONE);
            final int NUM_TRUCKS = (int) Math.ceil((NUM_PACKAGES * PERCENT_BY_TRUCK) / 10);
            final int DRONE_SPEED = 500;
            final int TRUCK_SPEED = 30;
            final int TOTAL_DISTANCE = 30000;
            final int DISTANCE_TO_CROSSING = 3000;
            final int DISTANCE_FROM_CROSSING = TOTAL_DISTANCE - DISTANCE_TO_CROSSING;
            double simClock = 0.0;
            boolean isBlocking = false;
            double averageTruckTime = 0.0;
            double singleDroneTime = 0.0;
            double totalTruckTime = 0.0;
            double totalDroneTime = 0.0;

            // calculate drone completionTimes
            singleDroneTime = TOTAL_DISTANCE / DRONE_SPEED;
            
            totalDroneTime = singleDroneTime;
            for (int i=0; i<NUM_DRONES-1; i++) {
                totalDroneTime += 3;
            }

            // read from train schedule
            Scanner in = new Scanner(new File("train_schedule.txt"));

            // create priority queue for events
            PriorityQueue<Event> events = new PriorityQueue<>();
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
            }

            // create truck start events and truck at crossing events 
            int truckOffset = 0; 
            for (int i=0; i<NUM_TRUCKS; i++) {
                Event truckStart = new TruckBegin(truckOffset, i);
                truckOffset += 15;

                double arrivesAtCrossing = truckStart.getEventStart() + (DISTANCE_TO_CROSSING / TRUCK_SPEED);
                TruckAtCrossing crossingArrival = new TruckAtCrossing(arrivesAtCrossing, i);

                events.offer(truckStart);
                events.offer(crossingArrival);
            }        

            PriorityQueue<Event> waitingTrucks = new PriorityQueue<>();
            
            // loop over priority queue while it still contains events
            while (!events.isEmpty()) {
                Event e = events.poll();
                simClock = e.getEventStart();
                
                if (e instanceof TruckBegin) {
                    //System.out.println(simClock + ": TRUCK #" + e.getID() + " begins journey");
                }
                else if (e instanceof TruckAtCrossing) {
                    if (isBlocking || !waitingTrucks.isEmpty()) {
                        waitingTrucks.offer(e);
                        //System.out.println(simClock + ": TRUCK #" + e.getID() + " waits at crossing");
                    }
                    else if (!isBlocking && waitingTrucks.isEmpty()) {
                        //System.out.println(simClock + ": TRUCK #" + e.getID() + " crosses crossing");

                        Event truckEnd = new TruckEnd((simClock + (DISTANCE_FROM_CROSSING / TRUCK_SPEED)), e.getID());
                        events.offer(truckEnd);
                    }
                }
                else if (e instanceof TruckEnd) {
                    //System.out.println(simClock + ": TRUCK #" + e.getID() + " completes journey");

                    Truck truck = new Truck((simClock - (e.getID() * 15)), e.getID());
                    complete.add(truck);
                    completionTimes.add(simClock);
                }
                else if (e instanceof TrainBlock) {
                    //System.out.println(simClock + ": TRAIN" + " arrives at crossing");
                    isBlocking = true;
                }
                else if (e instanceof TrainClear) {
                    isBlocking = false;
                    //System.out.println(simClock + ": TRAIN" + " leaves crossing");

                    if (!waitingTrucks.isEmpty()) {
                        while (!waitingTrucks.isEmpty()) {
                            if (events.peek() instanceof TruckAtCrossing && events.peek().getEventStart() == simClock) {
                                Event temp = events.poll();
                                waitingTrucks.offer(temp);
                                //System.out.println(simClock + ": TRUCK #" + temp.getID() + " waits at crossing");
                            }
                            // TODO fix truck 38. Should begin at 570
                            if (events.peek() instanceof TruckBegin && events.peek().getEventStart() == simClock) {
                                //Event temp = events.poll();
                                //System.out.println(simClock + ": TRUCK #" + temp.getID() + " begins journey");
                            }


                            simClock++;
                            Event currentWaitingTruck = waitingTrucks.poll();
                            //System.out.println(simClock + ": TRUCK #" + currentWaitingTruck.getID() + " crosses crossing");

                            Event truckEnd = new TruckEnd((simClock + (DISTANCE_FROM_CROSSING / TRUCK_SPEED)), currentWaitingTruck.getID());
                            events.offer(truckEnd);
                        }
                    }
                }
            }

            // print stats

            //System.out.println();
            //System.out.println("STATS");
            //System.out.println("-----");

            
            Collections.sort(complete);

            double sumTimes = 0;
            for (Truck truck : complete) {
                double truckTime = truck.getTime();
                sumTimes += truckTime;

                //System.out.println(truck + " total trip time: " + truckTime + " minutes");
            }
            

            Collections.sort(completionTimes);
            
            averageTruckTime = Math.round((sumTimes / completionTimes.size()) * 10 / 10);

            totalTruckTime = completionTimes.get(completionTimes.size()-1);
            
            //System.out.println();
            //System.out.println("TRUCK AVG TRIP TIME: " + averageTruckTime + " minutes");
            //System.out.println("TRUCK TOTAL TIME: " + totalTruckTime + " minutes");

            //System.out.println();

            //System.out.println("DRONE TRIP TIME: " + singleDroneTime + " minutes");
            //System.out.println("DRONE TOTAL TIME: " + totalDroneTime + " minutes");
            
            //System.out.println();
            
            // between total drone time and total truck time, the greater one is the total time for all packages to be delivered
            double totalTime = Math.max(totalDroneTime, totalTruckTime);
            System.out.println(l + " TOTAL TIME: " + totalTime + " minutes");
            p+=0.01;
            l++;
        }
    }
}