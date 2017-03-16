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

  public double getRadius()
  {
    return radius;
  }
  
  public void setRadius(double radius)
  {
    this.radius = radius;
  }

  public double getMinimumZ()
  {
    return minimumZ;
  }
  
  public void setMinimumZ(double minimum)
  {
    minimumZ = minimum;
  }

  public double getMaximumZ()
  {
    return maximumZ;
  }
  
  public void setMaximumZ(double maximum)
  {
    maximumZ = maximum;
  }
  
  @Override
  public Point3D getHomePosition()
  {
    return new Point3D(0.0, 0.0, maximumZ);
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
