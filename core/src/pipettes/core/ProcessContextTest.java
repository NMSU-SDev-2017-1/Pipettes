package pipettes.core;

import static org.junit.Assert.*;
import javafx.geometry.*;

import org.junit.Test;

import pipettes.core.ProcessContext.Orientation;

public class ProcessContextTest
{

  @Test
  public void testGetClearanceHeight()
  {
    ProcessContext context = new ProcessContext(null,null);
    
    Container sample = new Container();
    sample.setLocalPosition(new Point3D(-90.0, 90.0, 0.0));
    sample.setSize(new Point3D(10.0, 10.0, 40.0));
    sample.setShape(ContainerShape.Rectangular);
    sample.setClearanceHeightAboveTop(7.0);
    context.addContainer(sample);
    
    Point2D fromPoint = new Point2D(-90,90);
    Point2D toPoint = new Point2D(10,10);
    
    double height = context.getClearanceHeight(fromPoint, toPoint);
    System.out.println("Height = "+height);
    
    // TODO: Finish writing test case for getClearanceHeight()
    
    //fail("Not yet implemented");
  }
  
  // (Container container, Point2D fromLocation, Point2D toLocation)
  @Test
  public void testBoxIntersects()
  {
    fail("Not yet implemented");
  }
  
  // (Point2D A1, Point2D A2, Point2D B1, Point2D B2)
  @Test
  public void testLinesIntersect()
  {
    fail("Not yet implemented");
  }
  
  // (Point2D A, Point2D B, Point2D C)
  @Test
  public void testOnSegment()
  {
    ProcessContext context = new ProcessContext(null,null);
    
    if (context.onSegment(new Point2D(10,10), new Point2D(10,10), new Point2D(10,10))==false)
      fail("Failed to recognize colinear points.");
    if (context.onSegment(new Point2D(10,10), new Point2D(15,15), new Point2D(10,10))==true)
      fail("Failed to recognize non-colinear points.");
  }
  
}
