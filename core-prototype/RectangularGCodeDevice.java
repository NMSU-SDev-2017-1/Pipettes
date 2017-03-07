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

  // TODO: Add getters and setters

  @Override
  public Point3D getHomePosition()
  {
    return new Point3D(homePosition.getX(), homePosition.getY(), homePosition.getZ());
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
