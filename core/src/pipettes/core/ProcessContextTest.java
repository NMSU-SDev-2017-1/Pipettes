package pipettes.core;

import static org.junit.Assert.*;
import javafx.geometry.*;

import org.junit.Test;

public class ProcessContextTest
{
  @Test
  public void testLinesIntersect()
  {
    ProcessContext context = new ProcessContext(null, null);

    if (context.linesIntersect(new Point2D(0, 0), new Point2D(10, 10),
        new Point2D(10, 0), new Point2D(0, 10)) == false)
      fail("Did not recognize intersecting lines.");
    if (context.linesIntersect(new Point2D(0, 0), new Point2D(10, 10),
        new Point2D(0, 10), new Point2D(10, 20)) == true)
      fail("Did not recognize parallel diagonal lines.");
    if (context.linesIntersect(new Point2D(0, 10), new Point2D(10, 10),
        new Point2D(20, 10), new Point2D(30, 10)) == true)
      fail("Did not recognize co-linear non-intersecting horizontal lines.");
    if (context.linesIntersect(new Point2D(0, 0), new Point2D(10, 10),
        new Point2D(0, 10), new Point2D(4, 6)) == true)
      fail("Did not recognize non-intersecting perpendicular lines.");

  }

  @Test
  public void testContainerIntersects()
  {
    ProcessContext context = new ProcessContext(null, null);

    Container sample = new Container();
    sample.setLocalPosition(new Point3D(5.0, 5.0, 0.0));
    sample.setSize(new Point3D(10.0, 10.0, 40.0));
    sample.setShape(ContainerShape.Rectangular);
    sample.setClearanceHeightAboveTop(7.0);
    context.addContainer(sample);

    if (context.containerIntersects(sample, new Point2D(-5, 5), new Point2D(15,
        5)) == false)
      fail("Did not recognize container intersection.");
    if (context.containerIntersects(sample, new Point2D(-5, -5), new Point2D(
        15, 15)) == false)
      fail("Did not recognize container intersection.");
    if (context.containerIntersects(sample, new Point2D(-5, 25), new Point2D(
        15, 25)) == true)
      fail("Falsely recognized container intersection.");

    // check line entirely within container
    if (context.containerIntersects(sample, new Point2D(1, 9),
        new Point2D(9, 1)) == false)
      fail("Did not recognize interior line segment.");
  }

  @Test
  public void testGetClearanceHeight()
  {
    ProcessContext context = new ProcessContext(null, null);

    // small container
    Container sample1 = new Container();
    sample1.setLocalPosition(new Point3D(-90.0, 90.0, 0.0));
    sample1.setSize(new Point3D(10.0, 10.0, 40.0));
    sample1.setShape(ContainerShape.Rectangular);
    sample1.setClearanceHeightAboveTop(7.0);
    context.addContainer(sample1);

    // larger container
    Container sample2 = new Container();
    sample2.setLocalPosition(new Point3D(10, 10, 0.0));
    sample2.setSize(new Point3D(10.0, 10.0, 42.0));
    sample2.setShape(ContainerShape.Cylindrical);
    sample2.setClearanceHeightAboveTop(21.0);
    context.addContainer(sample2);

    // tall container not on line
    Container sample3 = new Container();
    sample3.setLocalPosition(new Point3D(100, 100, 0.0));
    sample3.setSize(new Point3D(10.0, 10.0, 314.0));
    sample3.setShape(ContainerShape.Cylindrical);
    sample3.setClearanceHeightAboveTop(271);
    context.addContainer(sample3);

    Point2D fromPoint = new Point2D(-90, 90);
    Point2D toPoint = new Point2D(10, 10);

    double height = context.getClearanceHeight(fromPoint, toPoint);

    if (height != 63)
      fail("Wrong height clearance: " + height);
  }

}
