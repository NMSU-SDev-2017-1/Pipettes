import java.util.Iterator;

import javafx.geometry.Point2D;
import javafx.geometry.Point3D;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class BasicContainer extends Container
{
  @XmlJavaTypeAdapter(Point3DXmlAdapter.class)
  private Point3D size;
  
  private ContainerShape shape;
  private double drawHeightAboveBottom;
  private double dispenseHeightAboveTop;
  private double clearanceHeightAboveTop;

  public Point3D getSize()
  {
    return Common.newPoint3D(size);
  }

  public void setSize(Point3D size)
  {
    this.size = Common.newPoint3D(size);
  }

  public ContainerShape getShape()
  {
    return shape;
  }
  
  public void setShape(ContainerShape shape)
  {
    this.shape = shape;
  }

  public double getDrawHeightAboveBottom()
  {
    return drawHeightAboveBottom;
  }

  public void setDrawHeightAboveBottom(double height)
  {
    drawHeightAboveBottom = height;
  }
  
  public double getDispenseHeightAboveTop()
  {
    return dispenseHeightAboveTop;
  }
  
  public void setDispenseHeightAboveTop(double height)
  {
    dispenseHeightAboveTop = height;
  }
  
  public double getClearanceHeightAboveTop()
  {
    return clearanceHeightAboveTop;
  }
  
  public void setClearanceHeightAboveTop(double height)
  {
    clearanceHeightAboveTop = height;
  }
  
  private double getBottomHeight()
  {
    return getPosition().getZ();
  }

  private double getTopHeight()
  {
    return getPosition().getZ() + size.getZ();
  }

  public Point2D getDrawLocation()
  {
    return new Point2D(getPosition().getX(), getPosition().getY());
  }

  public double getDrawHeight()
  {
    return getBottomHeight() + drawHeightAboveBottom;
  }

  public Point2D getDispenseLocation()
  {
    return getDrawLocation();
  }

  public double getDispenseHeight()
  {
    return getTopHeight() + dispenseHeightAboveTop;
  }

  public double getClearanceHeight()
  {
    return getTopHeight() + clearanceHeightAboveTop;
  }

  public boolean hasSubcontainers()
  {
    return false;
  }

  public Iterator<Container> getSubcontainers()
  {
    return null;
  }

  public Container getSubcontainer(int index)
  {
    return null;
  }

  public Container getSubcontainer(String name)
  {
    return null;
  }
}
