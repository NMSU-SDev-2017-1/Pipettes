package pipettes.core;

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
      // TODO: include code for cylindrical containers
      if (boxIntersects(container, fromLocation, toLocation)){
        result = Math.max(result, container.getClearanceHeight());
      }
    }
    return result;
  }
  
  // TODO: finish implementing box intersection code
  //note algorithm at:
  // https://www.scratchapixel.com/lessons/3d-basic-rendering/minimal-ray-tracer-rendering-simple-shapes/ray-box-intersection
  // https://github.com/BSVino/MathForGameDevelopers/blob/line-box-intersection/math/collision.cpp
  private boolean boxIntersects(Container container, Point2D fromLocation, Point2D toLocation)
  {
    // coordinates of the near and far (minimum and maximum) edges of the cube in the X direction
    double xmin = container.getLocalPositionX() - 0.5*container.getSizeX();
    double xmax = container.getLocalPositionX() + 0.5*container.getSizeX();
    
    // coordinates of the near and far (minimum and maximum) edges of the cube in the Y direction
    double ymin = container.getLocalPositionY() - 0.5*container.getSizeY();
    double ymax = container.getLocalPositionY() + 0.5*container.getSizeY();
    
    // CALCULATE X INTERCEPTS
    // dX is the x component of the total vector
    // t0x is the intercept with the plane along the minimum box edge
    // t1x is the intercept with the plane along the maximum box edge 
    double dX = (toLocation.getX() - fromLocation.getX());
    double t0x = (xmin - fromLocation.getX()/dX);
    double t1x = (xmax - fromLocation.getX()/dX);
    
    // CALCULATE Y INTERCEPTS
    // dY is the y component of the total vector
    // t0x is the intercept with the plane along the minimum box edge
    // t1x is the intercept with the plane along the maximum box edge 
    double dY = (toLocation.getY() - fromLocation.getY());
    double t0y = (ymin - fromLocation.getY()/dY);
    double t1y = (ymax - fromLocation.getY()/dY);
    
    // Determine if the box is intersected
    if (t0x > t1y || t0y > t1x)
      return false; // the line misses the box
    else return true;
  }
}