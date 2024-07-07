package comm.delivery.model;

import java.util.List;

public class DriverRequest {

  private Location driver;
  private List<RestaurantRequest> restaurants;
  private List<CustomerRequest> customers;

  // Getters and setters
  public Location getDriver() {
    return driver;
  }

  public void setDriver(Location driver) {
    this.driver = driver;
  }

  public List<RestaurantRequest> getRestaurants() {
    return restaurants;
  }

  public void setRestaurants(List<RestaurantRequest> restaurants) {
    this.restaurants = restaurants;
  }

  public List<CustomerRequest> getCustomers() {
    return customers;
  }

  public void setCustomers(List<CustomerRequest> customers) {
    this.customers = customers;
  }
}
