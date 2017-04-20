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
      if (boxIntersects(container, fromLocation, toLocation)){
        result = Math.max(result, container.getClearanceHeight());
      }
    }
    return result;
  }
  
  // TODO: Handle the case where the start and end points are both inside a single container (return that container height)
  public boolean boxIntersects(Container container, Point2D fromLocation, Point2D toLocation)
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
    double B1 = P12.getX() - P11.getX();
    double C1 = A1*P11.getX() + B1*P11.getY();
    
    // line 2
    double A2 = P22.getY() - P21.getY();
    double B2 = P22.getX() - P21.getX();
    double C2 = A2*P21.getX() + B2*P21.getY();
    
    double det = A1*B2 - A2*B1;
    if (det!=0) { // lines not parallel
      
      // find intersection
      double x = (B2*C1 - B1*C2)/det;
      double y = (A1*C2 - A2*C1)/det;
      
      // check intersection is on line segment 1
      if( (x>=Math.min(P11.getX(),P12.getX())) && (x<=Math.min(P11.getX(),P12.getX())) &&
          (y>=Math.min(P11.getY(),P12.getY())) && (x<=Math.min(P11.getY(),P12.getY())) ){
        
        // check intersection is on line segment 2
        if( (x>=Math.min(P11.getX(),P12.getX())) && (x<=Math.min(P11.getX(),P12.getX())) &&
            (y>=Math.min(P11.getY(),P12.getY())) && (x<=Math.min(P11.getY(),P12.getY())) ){
          return true;
        }
      }
    }
    
    return false;
  }
  
  // see http://www.geeksforgeeks.org/check-if-two-given-line-segments-intersect/
//  public boolean linesIntersect(Point2D A1, Point2D A2, Point2D B1, Point2D B2){
//    Orientation o1 = getOrientation(A1,A2,B1);
//    Orientation o2 = getOrientation(A1,A2,B2);
//    Orientation o3 = getOrientation(B1,B2,A1);
//    Orientation o4 = getOrientation(B1,B2,A2);
//    if (o1 != o2 && o3 != o4) return true;
//    else if (o1 == Orientation.COLINEAR && onSegment(A1,B1,A2)) return true;
//    else if (o2 == Orientation.COLINEAR && onSegment(A1,B2,A2)) return true;
//    else if (o3 == Orientation.COLINEAR && onSegment(B1,A1,B2)) return true;
//    else if (o4 == Orientation.COLINEAR && onSegment(B1,A2,B2)) return true;
//    else return false;
//  }
  
  // see http://www.geeksforgeeks.org/check-if-two-given-line-segments-intersect/
  public boolean onSegment(Point2D A, Point2D B, Point2D C){
    if (B.getX() <= Math.max(A.getX(),C.getX()) && B.getX() >= Math.min(A.getX(), B.getX()) &&
        B.getY() <= Math.max(A.getY(),C.getY()) && B.getY() >= Math.min(A.getY(), C.getY()))
      return true;
    return false;
  }
  
  // http://www.geeksforgeeks.org/orientation-3-ordered-points/
  public Orientation getOrientation(Point2D A, Point2D B, Point2D C){
    // check for vertical line (avoid divide by zero)
    if (((B.getX()-A.getX())==0) || ((C.getX()-B.getX())==0)){
      return Orientation.COLINEAR;}
    // if not vertical, compare slopes to find orientation
    double slopeAB = (B.getY()-A.getY())/(B.getX()-A.getX());
    double slopeBC = (C.getY()-B.getY())/(C.getX()-B.getX());
    if (slopeAB < slopeBC) return Orientation.COUNTERCLOCKWISE;
    else if (slopeAB==slopeBC) return Orientation.COLINEAR;
    else return Orientation.CLOCKWISE;
  }
  
  // http://www.geeksforgeeks.org/check-if-two-given-line-segments-intersect/
  public enum Orientation{
    CLOCKWISE, COUNTERCLOCKWISE, COLINEAR
  }
  
}