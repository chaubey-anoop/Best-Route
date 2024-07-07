package comm.delivery.model;

import java.util.List;


public class OptimalRoute {

  private double time;
  private List<String> path;

  public OptimalRoute(double time, List<String> path) {
    this.time = time;
    this.path = path;
  }

  public double getTime() {
    return time;
  }

  public void setTime(double time) {
    this.time = time;
  }

  public List<String> getPath() {
    return path;
  }

  public void setPath(List<String> path) {
    this.path = path;
  }
}
