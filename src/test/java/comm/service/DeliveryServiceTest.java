package comm.service;

import comm.delivery.model.Location;
import comm.delivery.model.OptimalRoute;
import comm.delivery.service.DeliveryService;
import comm.delivery.util.DeliveryRouteOptimizer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class DeliveryServiceTest {

    @InjectMocks
    private DeliveryService deliveryService;

    @Mock
    private DeliveryRouteOptimizer deliveryRouteOptimizer;

    @BeforeEach
    public void setUp() {
        deliveryService = new DeliveryService();
    }

    @Test
    public void testFindOptimalRoute() {
        Location deliveryExecutiveLocation = new Location(12.9716, 77.5946);
        List<Location> restaurantLocations = Arrays.asList(
                new Location(12.9352, 77.6245),
                new Location(12.9719, 77.6412)
        );
        List<Location> customerLocations = Arrays.asList(
                new Location(12.9538, 77.6304),
                new Location(12.9769, 77.5726)
        );
        List<Double> prepTimes = Arrays.asList(10.0, 15.0);

        OptimalRoute expectedOptimalRoute = new OptimalRoute(120.0, Arrays.asList("R1", "C1", "R2", "C2"));

        try (var mockedStatic = mockStatic(DeliveryRouteOptimizer.class)) {
            mockedStatic.when(() -> DeliveryRouteOptimizer.findOptimalRoute(
                    deliveryExecutiveLocation, restaurantLocations, customerLocations, prepTimes
            )).thenReturn(expectedOptimalRoute);

            OptimalRoute actualOptimalRoute = deliveryService.findOptimalRoute(
                    deliveryExecutiveLocation, restaurantLocations, customerLocations, prepTimes
            );

            assertEquals(expectedOptimalRoute.getTime(), actualOptimalRoute.getTime());
            assertEquals(expectedOptimalRoute.getPath(), actualOptimalRoute.getPath());
        }
    }


    @Test
    public void testFindOptimalRouteEmptyLists() {
        Location deliveryExecutiveLocation = new Location(12.9716, 77.5946);
        List<Location> emptyRestaurantLocations = Collections.emptyList();
        List<Location> emptyCustomerLocations = Collections.emptyList();
        List<Double> emptyPrepTimes = Collections.emptyList();

        OptimalRoute expectedOptimalRoute = new OptimalRoute(0.0, Collections.emptyList());

        try (var mockedStatic = mockStatic(DeliveryRouteOptimizer.class)) {
            mockedStatic.when(() -> DeliveryRouteOptimizer.findOptimalRoute(
                    any(Location.class),
                    any(List.class),
                    any(List.class),
                    any(List.class)
            )).thenReturn(expectedOptimalRoute);

            OptimalRoute actualOptimalRoute = deliveryService.findOptimalRoute(
                    deliveryExecutiveLocation,
                    emptyRestaurantLocations,
                    emptyCustomerLocations,
                    emptyPrepTimes
            );

            assertEquals(expectedOptimalRoute.getTime(), actualOptimalRoute.getTime());
            assertEquals(expectedOptimalRoute.getPath(), actualOptimalRoute.getPath());
        }
    }
}
