package pipettes.core;

import static org.junit.Assert.*;
import javafx.geometry.*;

import org.hamcrest.CoreMatchers;
import org.junit.Rule;
import org.junit.Test;

import pipettes.core.ProcessContext.Orientation;

public class ProcessContextTest
{
  @Test
  public void testGetOrientation()
  {
    ProcessContext context = new ProcessContext(null,null);
    if (context.getOrientation(new Point2D(10,10), new Point2D(20,20), new Point2D(30,10)) != Orientation.CLOCKWISE)
      fail("Did not recognize clockwise points.");
    if (context.getOrientation(new Point2D(10,10), new Point2D(30,10), new Point2D(20,20)) != Orientation.COUNTERCLOCKWISE)
      fail("Did not recognize counterclockwise points.");    
    if (context.getOrientation(new Point2D(10,10), new Point2D(20,20), new Point2D(30,30)) != Orientation.COLINEAR)
      fail("Did not recognize diagonal colinear points.");
    if (context.getOrientation(new Point2D(10,10), new Point2D(10,20), new Point2D(10,30)) != Orientation.COLINEAR)
      fail("Did not recognize vertical colinear points.");
    if (context.getOrientation(new Point2D(10,10), new Point2D(20,10), new Point2D(30,10)) != Orientation.COLINEAR)
      fail("Did not recognize horizontal colinear points.");
    if (context.getOrientation(new Point2D(0,0), new Point2D(10,10), new Point2D(0,10)) != Orientation.CLOCKWISE)
      fail("Did not recognize clockwise points.");
    if (context.getOrientation(new Point2D(0,0), new Point2D(10,10), new Point2D(10,0)) != Orientation.COUNTERCLOCKWISE)
      fail("Did not recognize counterclockwise points.");
    if (context.getOrientation(new Point2D(10,0), new Point2D(0,10), new Point2D(10,10)) != Orientation.CLOCKWISE)
      fail("Did not recognize clockwise points.");
    if (context.getOrientation(new Point2D(10,0), new Point2D(0,10), new Point2D(0,0)) != Orientation.COUNTERCLOCKWISE)
      fail("Did not recognize counterclockwise points.");  
    if (context.getOrientation(new Point2D(10,0), new Point2D(0,10), new Point2D(0,0)) != Orientation.CLOCKWISE)
      fail("Did not recognize clockwise points.");
      
  }
  
  @Test
  public void testOnSegment()
  {
    ProcessContext context = new ProcessContext(null,null);
    
    if (context.onSegment(new Point2D(10,10), new Point2D(20,20), new Point2D(30,30))==false)
      fail("Failed to recognize colinear points.");
    if (context.onSegment(new Point2D(10,10), new Point2D(15,15), new Point2D(10,20))==true)
      fail("Failed to recognize non-colinear points.");
    if (context.onSegment(new Point2D(10,10), new Point2D(50,50), new Point2D(30,30))==true)
      fail("Failed to recognize point colinear point not in line segment.");
  }
  
  @Test
  public void testLinesIntersect()
  {
    ProcessContext context = new ProcessContext(null,null);
    
    if (context.linesIntersect(new Point2D(0,0), new Point2D(10,10), new Point2D(10,0), new Point2D(0,10))==true)
      fail("Did not recognize intersecting lines.");
    
  }
  
  @Test
  public void testBoxIntersects()
  {
    fail("Not yet implemented");
  }
  
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
  
  
  
  
  
  
  
  
  
}
