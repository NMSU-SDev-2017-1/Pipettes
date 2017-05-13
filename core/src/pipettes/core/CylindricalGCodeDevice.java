package pipettes.core;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Point2D;
import javafx.geometry.Point3D;

@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class CylindricalGCodeDevice extends GCodeDevice
{
  private DoubleProperty radius = new SimpleDoubleProperty();
  private DoubleProperty minimumZ = new SimpleDoubleProperty();
  private DoubleProperty maximumZ = new SimpleDoubleProperty();

  public DeviceType getType()
  {
    return DeviceType.CylindricalGCode;
  }

  @XmlElement
  public double getRadius()
  {
    return radius.get();
  }

  public void setRadius(double r)
  {
    radius.set(r);
  }

  public DoubleProperty radiusProperty()
  {
    return radius;
  }

  @XmlElement
  public double getMinimumZ()
  {
    return minimumZ.get();
  }

  public void setMinimumZ(double z)
  {
    minimumZ.set(z);
  }

  public DoubleProperty minimumZProperty()
  {
    return minimumZ;
  }

  @XmlElement
  public double getMaximumZ()
  {
    return maximumZ.get();
  }

  public void setMaximumZ(double z)
  {
    maximumZ.set(z);
  }

  public DoubleProperty maximumZProperty()
  {
    return maximumZ;
  }

  @Override
  public Point3D getHomePosition()
  {
    return new Point3D(0.0f, 0.0f, getMaximumZ());
  }

  @Override
  protected void checkPosition(Point3D position) throws PositioningException
  {
    if ((position.getZ() < getMinimumZ()) || (position.getZ() > getMaximumZ()))
    {
      throw new PositioningException();
    }

    Point2D location = new Point2D(position.getX(), position.getY());

    if (location.magnitude() > getRadius())
    {
      throw new PositioningException();
    }
  }
}
