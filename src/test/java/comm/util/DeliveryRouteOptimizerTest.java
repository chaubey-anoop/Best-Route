package comm.util;

import comm.delivery.model.Location;
import comm.delivery.model.OptimalRoute;
import comm.delivery.util.DeliveryRouteOptimizer;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeliveryRouteOptimizerTest {

    @Test
    public void testFindOptimalRoute() {

        Location amanLocation = new Location(12.9716, 77.5946);
        List<Location> restaurantLocations = Arrays.asList(
                new Location(12.9352, 77.6245),
                new Location(12.9719, 77.6412)
        );
        List<Location> customerLocations = Arrays.asList(
                new Location(12.9538, 77.6304),
                new Location(12.9769, 77.5726)
        );
        List<Double> prepTimes = Arrays.asList(10.0, 15.0);

        OptimalRoute expectedOptimalRoute = new OptimalRoute(25.856579897739238, Arrays.asList("R1", "C1", "R2", "C2"));

        OptimalRoute actualOptimalRoute = DeliveryRouteOptimizer.findOptimalRoute(
                amanLocation, restaurantLocations, customerLocations, prepTimes);

        assertEquals(expectedOptimalRoute.getTime(), actualOptimalRoute.getTime());
        assertEquals(expectedOptimalRoute.getPath(), actualOptimalRoute.getPath());
    }

    @Test
    public void testFindOptimalRouteEmptyLists() {

        Location amanLocation = new Location(12.9716, 77.5946);
        List<Location> emptyRestaurantLocations = Collections.emptyList();
        List<Location> emptyCustomerLocations = Collections.emptyList();
        List<Double> emptyPrepTimes = Collections.emptyList();

        OptimalRoute expectedOptimalRoute = new OptimalRoute(0.0, Collections.emptyList());

        OptimalRoute actualOptimalRoute = DeliveryRouteOptimizer.findOptimalRoute(
                amanLocation, emptyRestaurantLocations, emptyCustomerLocations, emptyPrepTimes);

        assertEquals(expectedOptimalRoute.getTime(), actualOptimalRoute.getTime());
        assertEquals(expectedOptimalRoute.getPath(), actualOptimalRoute.getPath());
    }
}
