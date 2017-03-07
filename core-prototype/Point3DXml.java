import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

import javafx.geometry.Point3D;

@XmlType(name = "Point3D")
class Point3DXml
{
  @XmlAttribute
  double x;
  @XmlAttribute
  double y;
  @XmlAttribute
  double z;

  public Point3DXml()
  {
  }

  public Point3DXml(Point3D point)
  {
    x = point.getX();
    y = point.getY();
    z = point.getZ();
  }

  public Point3D getPoint3D()
  {
    return new Point3D(x, y, z);
  }
}