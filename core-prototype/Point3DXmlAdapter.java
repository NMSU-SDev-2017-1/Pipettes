import javafx.geometry.Point3D;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class Point3DXmlAdapter extends XmlAdapter<Point3DXml, Point3D>
{
  public Point3DXml marshal(Point3D point) throws Exception
  {
    return new Point3DXml(point);
  }

  public Point3D unmarshal(Point3DXml point) throws Exception
  {
    return point.getPoint3D();
  }
}
