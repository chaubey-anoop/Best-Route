package comm.delivery.controller;

import comm.delivery.model.DriverRequest;
import comm.delivery.model.Location;
import comm.delivery.model.OptimalRoute;
import comm.delivery.model.RestaurantRequest;
import comm.delivery.service.DeliveryService;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static java.util.stream.Collectors.toList;


@RestController
@RequestMapping("/api/v1")
public class DeliveryController {

  @Autowired
  private DeliveryService deliveryService;

  @GetMapping("/bestRoute")
  public OptimalRoute getBestRoute(@RequestBody DriverRequest driverRequest) {
    Location driverLocation = new Location(driverRequest.getDriver().getLatitude(),
        driverRequest.getDriver().getLongitude());

    List<Location> restaurantLocations = driverRequest.getRestaurants().stream()
            .map(restaurant -> new Location(restaurant.getLatitude(), restaurant.getLongitude()))
            .collect(toList());

    List<Location> customerLocations = driverRequest.getCustomers().stream()
            .map(customer -> new Location(customer.getLatitude(), customer.getLongitude()))
            .collect(toList());

    List<Double> preparationTime = driverRequest.getRestaurants().stream()
        .map(RestaurantRequest::getPreparationTime)
        .collect(toList());

    return deliveryService.findOptimalRoute(driverLocation, restaurantLocations, customerLocations,
            preparationTime);
  }
}