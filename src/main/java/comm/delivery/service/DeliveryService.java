package comm.delivery.service;

import comm.delivery.model.Location;
import comm.delivery.model.OptimalRoute;
import comm.delivery.util.DeliveryRouteOptimizer;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class DeliveryService {

  public OptimalRoute findOptimalRoute(
      Location deliveryExecutiveLocation,
      List<Location> restaurantLocations,
      List<Location> customerLocations,
      List<Double> prepTimes
  ) {
    return DeliveryRouteOptimizer.findOptimalRoute(deliveryExecutiveLocation, restaurantLocations,
        customerLocations, prepTimes);
  }
}

