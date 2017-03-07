import java.util.ArrayList;

import javafx.geometry.Point2D;

public class ProcessContext
{
  // TODO: Populate during construction to include all containers and subcontainers
  private ArrayList<Container> containers;

  public double getClearanceHeight(Point2D fromLocation, Point2D toLocation)
  {
    double result = 0;

    // TODO: Replace this with code that actually finds maximum clearance
    // of containers intersected by movement from and to the specified
    // locations
    for (Container container : containers)
    {
      result = Math.max(result, container.getClearanceHeight());
    }

    return result;
  }
}
