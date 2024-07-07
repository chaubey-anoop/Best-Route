package comm.delivery.util;

import comm.delivery.model.Location;
import comm.delivery.model.OptimalRoute;
import comm.delivery.constants.DeliveryOptimizerConstants;

import java.util.ArrayList;
import java.util.List;

public class DeliveryRouteOptimizer {

  // Function to calculate the Haversine distance between two points
  private static double haversine(double lat1, double lon1, double lat2, double lon2) {
    double dLat = Math.toRadians(lat2 - lat1);
    double dLon = Math.toRadians(lon2 - lon1);
    lat1 = Math.toRadians(lat1);
    lat2 = Math.toRadians(lat2);

    double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
        Math.sin(dLon / 2) * Math.sin(dLon / 2) * Math.cos(lat1) * Math.cos(lat2);
    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

    return DeliveryOptimizerConstants.EARTH_RADIUS * c;
  }

  // Function to calculate travel time in hours given distance and speed
  private static double travelTime(double distance) {
    return distance / DeliveryOptimizerConstants.SPEED;
  }

  // Function to calculate the total travel time for a given route
  private static double calculateRouteTime(Location amanLocation,
                                           List<Location> restaurants,
                                           List<Location> customers,
                                           List<Double> prepTimes,
                                           List<Integer> route
  ) {
    double totalTime = 0.0;
    Location currentLocation = amanLocation;

    for (int i = 0; i < route.size(); i++) {
      int index = route.get(i);
      Location nextLocation;
      if (index < restaurants.size()) {
        // Going to a restaurant
        nextLocation = new Location(
            restaurants.get(index).getLatitude(),
            restaurants.get(index).getLongitude());

        totalTime += travelTime(
            haversine(currentLocation.getLatitude(), currentLocation.getLongitude(),
                nextLocation.getLatitude(),
                nextLocation.getLongitude()));

        totalTime += prepTimes.get(index);
      } else {
        // Going to a customer
        int customerIndex = index - restaurants.size();
        nextLocation = new Location(customers.get(customerIndex).getLatitude(),
            customers.get(customerIndex).getLongitude());

        totalTime += travelTime(
            haversine(currentLocation.getLatitude(), currentLocation.getLongitude(),
                nextLocation.getLatitude(), nextLocation.getLongitude()));
      }

      currentLocation = nextLocation;
    }

    return totalTime;
  }

  // Helper function to generate all valid permutations of orders
  private static void generateValidPermutations(int n, List<Integer> currentPerm,
      boolean[] restaurantVisited, boolean[] customerVisited, List<List<Integer>> permutations) {
    if (currentPerm.size() == 2 * n) {
      permutations.add(new ArrayList<Integer>(currentPerm));
      return;
    }

    for (int i = 0; i < n; i++) {
      if (!restaurantVisited[i]) {
        // Visit restaurant
        restaurantVisited[i] = true;
        currentPerm.add(i);
        generateValidPermutations(n, currentPerm, restaurantVisited, customerVisited, permutations);
        currentPerm.remove(currentPerm.size() - 1);
        restaurantVisited[i] = false;
      }
      if (restaurantVisited[i] && !customerVisited[i]) {
        // Visit customer
        customerVisited[i] = true;
        currentPerm.add(i + n);
        generateValidPermutations(n, currentPerm, restaurantVisited, customerVisited, permutations);
        currentPerm.remove(currentPerm.size() - 1);
        customerVisited[i] = false;
      }
    }
  }

  // Function to find the optimal route
  public static OptimalRoute findOptimalRoute(Location amanLocation,
                                              List<Location> restaurants,
                                              List<Location> customers, List<Double> prepTimes) {
    int n = restaurants.size();

    List<List<Integer>> validPermutations = new ArrayList<List<Integer>>();
    generateValidPermutations(n, new ArrayList<Integer>(), new boolean[n], new boolean[n],
        validPermutations);

    double minTime = Double.MAX_VALUE;
    List<Integer> bestRoute = new ArrayList<>();

    for (List<Integer> perm : validPermutations) {
      double currentTime = calculateRouteTime(amanLocation, restaurants, customers, prepTimes,
          perm);
      if (currentTime < minTime) {
        minTime = currentTime;
        bestRoute = new ArrayList<>(perm);
      }
    }

    List<String> optimalPath = new ArrayList<>();
    for (int index : bestRoute) {
      if (index < n) {
        optimalPath.add("R" + (index + 1));
      } else {
        optimalPath.add("C" + (index - n + 1));
      }
    }

    return new OptimalRoute(minTime, optimalPath);
  }
}
