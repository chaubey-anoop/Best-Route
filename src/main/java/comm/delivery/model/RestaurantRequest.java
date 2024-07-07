package comm.delivery.model;

public class RestaurantRequest {

  private double latitude;
  private double longitude;
  private double preparationTime;

  // Getters and setters
  public double getLatitude() {
    return latitude;
  }

  public void setLatitude(double latitude) {
    this.latitude = latitude;
  }

  public double getLongitude() {
    return longitude;
  }

  public void setLongitude(double longitude) {
    this.longitude = longitude;
  }

  public double getPreparationTime() {
    return preparationTime;
  }

  public void setPreparationTime(double preparationTime) {
    this.preparationTime = preparationTime;
  }
}
