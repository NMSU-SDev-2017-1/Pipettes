import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import javafx.geometry.Point2D;
import javafx.geometry.Point3D;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class CylindricalGCodeDevice extends GCodeDevice
{
  private double radius;
  private double minimumZ;
  private double maximumZ;

  // TODO: Add getters and setters (update radiusSquared when radius is set)

  @Override
  public Point3D getHomePosition()
  {
    return new Point3D(0.0f, 0.0f, maximumZ);
  }

  @Override
  protected void checkPosition(Point3D position) throws PositioningException
  {
    if ((position.getZ() < minimumZ) || (position.getZ() > maximumZ))
    {
      throw new PositioningException();
    }

    Point2D location = new Point2D(position.getX(), position.getY());

    if (location.magnitude() > radius)
    {
      throw new PositioningException();
    }
  }
}
