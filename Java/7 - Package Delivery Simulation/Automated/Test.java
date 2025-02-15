public class Test {
    public static void main(String[] args) {
        final int NUM_PACKAGES = 1500;
        double PERCENT_BY_DRONE = 1;
        final int NUM_DRONES = (int) (NUM_PACKAGES * PERCENT_BY_DRONE);
        final int DRONE_SPEED = 500;
        final int TOTAL_DISTANCE = 30000;
        double singleDroneTime = 0.0;
        double totalDroneTime = 0.0;

        // calculate drone completionTimes
        singleDroneTime = TOTAL_DISTANCE / DRONE_SPEED;
        
        totalDroneTime = singleDroneTime;
        for (int i=0; i<NUM_DRONES-1; i++) {
            totalDroneTime += 3;
        }

        System.out.println(totalDroneTime);
    }
}
