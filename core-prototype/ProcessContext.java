import java.util.ArrayList;

import javafx.geometry.Point2D;

public class ProcessContext
{
  private ArrayList<Container> containers = new ArrayList<Container>();

  public void addContainer(Container container)
  {
    containers.add(container);
  }
  
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
