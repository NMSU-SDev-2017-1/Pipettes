import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import javafx.geometry.Point3D;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class RectangularGCodeDevice extends GCodeDevice
{
  @XmlJavaTypeAdapter(Point3DXmlAdapter.class)
  private Point3D homePosition = Point3D.ZERO;
  
  @XmlJavaTypeAdapter(Point3DXmlAdapter.class)
  private Point3D minimumExtent = Point3D.ZERO;
  
  @XmlJavaTypeAdapter(Point3DXmlAdapter.class)
  private Point3D maximumExtent = Point3D.ZERO;

  @Override
  public Point3D getHomePosition()
  {
    return Common.newPoint3D(homePosition);
  }

  public void setHomePosition(Point3D homePosition)
  {
    this.homePosition = Common.newPoint3D(homePosition);
  }

  public Point3D getMinimumExtent()
  {
    return Common.newPoint3D(minimumExtent);
  }

  public void setMinimumExtent(Point3D minimumExtent)
  {
    this.minimumExtent = Common.newPoint3D(minimumExtent);
  }

  public Point3D getMaximumExtent()
  {
    return Common.newPoint3D(maximumExtent);
  }

  public void setMaximumExtent(Point3D maximumExtent)
  {
    this.maximumExtent = Common.newPoint3D(maximumExtent);
  }
  
  @Override
  protected void checkPosition(Point3D position) throws PositioningException
  {
    if ((position.getX() < minimumExtent.getX()) || (position.getY() < minimumExtent.getY())
        || (position.getZ() < minimumExtent.getZ()))
    {
      throw new PositioningException();
    }

    if ((position.getX() > maximumExtent.getX()) || (position.getY() > maximumExtent.getY())
        || (position.getZ() > maximumExtent.getZ()))
    {
      throw new PositioningException();
    }
  }
}
