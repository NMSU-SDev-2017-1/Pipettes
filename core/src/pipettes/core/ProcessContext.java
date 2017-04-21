package pipettes.core;

import java.util.ArrayList;

import javafx.geometry.Point2D;

public class ProcessContext
{
  private Device device;
  private ProcessLogger logger;
  private ArrayList<Container> containers = new ArrayList<Container>();

  public ProcessContext(Device device, ProcessLogger logger)
  {
    this.device = device;
    this.logger = logger;
  }
  
  public Device getDevice()
  {
    return device;
  }

  public ProcessLogger getLogger()
  {
    return logger;
  }
  
  public void addContainer(Container container)
  {
    containers.add(container);
  }
  
  public double getClearanceHeight(Point2D fromLocation, Point2D toLocation)
  {
    double result = 0;
    for (Container container : containers)
    {
      // TODO: include code for cylindrical containers
      if (containerIntersects(container, fromLocation, toLocation)){
        result = Math.max(result, container.getClearanceHeight());
      }
    }
    return result;
  }
  
  // TODO: Handle the case where the start and end points are both inside a single container (return that container height)
  public boolean containerIntersects(Container container, Point2D fromLocation, Point2D toLocation)
  {
    Point2D corner1 = new Point2D(container.getLocalPositionX()+container.getSizeX()/2,container.getLocalPositionY()+container.getSizeY()/2);
    Point2D corner2 = new Point2D(container.getLocalPositionX()+container.getSizeX()/2,container.getLocalPositionY()-container.getSizeY()/2);
    Point2D corner3 = new Point2D(container.getLocalPositionX()-container.getSizeX()/2,container.getLocalPositionY()+container.getSizeY()/2);
    Point2D corner4 = new Point2D(container.getLocalPositionX()-container.getSizeX()/2,container.getLocalPositionY()-container.getSizeY()/2);
    if (linesIntersect(corner1,corner2,fromLocation,toLocation) || linesIntersect(corner1,corner3,fromLocation,toLocation) ||
        linesIntersect(corner2,corner4,fromLocation,toLocation) || linesIntersect(corner3,corner4,fromLocation,toLocation))
      return true;
    else return false;
  }
  
  // see https://www.topcoder.com/community/data-science/data-science-tutorials/geometry-concepts-line-intersection-and-its-applications/
  public boolean linesIntersect(Point2D P11, Point2D P12, Point2D P21, Point2D P22){
    // line 1
    double A1 = P12.getY() - P11.getY();
    double B1 = P11.getX() - P12.getX();
    double C1 = A1*P11.getX() + B1*P11.getY();
    
    // line 2
    double A2 = P22.getY() - P21.getY();
    double B2 = P21.getX() - P22.getX();
    double C2 = A2*P21.getX() + B2*P21.getY();
    
    double det = A1*B2 - A2*B1;
    if (det!=0) { // lines not parallel
      
      // find intersection
      double x = (B2*C1 - B1*C2)/det;
      double y = (A1*C2 - A2*C1)/det;
      
      // TODO: why does this code fail but the below code work?
//      // check intersection is on line segment 1
//      if( (x>=Math.min(P11.getX(),P12.getX())) && (x<=Math.max(P11.getX(),P12.getX())) &&
//          (y>=Math.min(P11.getY(),P12.getY())) && (y<=Math.max(P11.getY(),P12.getY())) ){
//        
//        // check intersection is on line segment 2
//        if( (x>=Math.min(P11.getX(),P12.getX())) && (x<=Math.max(P11.getX(),P12.getX())) &&
//            (y>=Math.min(P11.getY(),P12.getY())) && (y<=Math.max(P11.getY(),P12.getY())) ){
//          return true;
//        }
//      } 
      
   // check intersection is on line segment 1
      boolean xAboveX1Min = x>=Math.min(P11.getX(),P12.getX());
      boolean xBelowX1Max = x<=Math.max(P11.getX(),P12.getX());
      boolean yAboveY1Min = y>=Math.min(P11.getY(),P12.getY());
      boolean yBelowY1Max = y<=Math.max(P11.getY(),P12.getY());
      if( xAboveX1Min && xBelowX1Max && yAboveY1Min && yBelowY1Max ){
        
        // check intersection is on line segment 2
        boolean xAboveX2Min = x>=Math.min(P21.getX(),P22.getX());
        boolean xBelowX2Max = x<=Math.max(P21.getX(),P22.getX());
        boolean yAboveY2Min = y>=Math.min(P21.getY(),P22.getY());
        boolean yBelowY2Max = y<=Math.max(P21.getY(),P22.getY());
        if( xAboveX2Min && xBelowX2Max && yAboveY2Min && yBelowY2Max ){
          return true;
        }
      }
       
    }
    return false;
  }
  
}