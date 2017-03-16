import java.util.Iterator;

import javafx.geometry.Point2D;
import javafx.geometry.Point3D;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
public abstract class Container
{
  private static final char nestingSeparator = '/';
  
  @XmlIDREF
  private Container parent;
  
  private String localName;
  
  // Local position relative to parent
  @XmlJavaTypeAdapter(Point3DXmlAdapter.class)
  private Point3D localPosition;
  
  public String getLocalName()
  {
    return localName;
  }
  
  public void setLocalName(String name)
  {
    this.localName = name;
  }
  
  @XmlID
  public String getName()
  {
    if (parent == null)
    {
      return getLocalName();
    }
    else
    {
      return parent.getName() + nestingSeparator + getLocalName();
    }
  }
  
  public Container getParent()
  {
    return parent;
  }
  
  public void setParent(Container parent)
  {
    this.parent = parent;
  }

  // Defined as center of bottom of object to make rotation sensible
  public Point3D getLocalPosition()
  {
    return Common.newPoint3D(localPosition); 
  }

  public void setLocalPosition(Point3D position)
  {
    this.localPosition = Common.newPoint3D(position);
  }

  public Point3D getPosition()
  {
    if (parent == null)
    {
      return getLocalPosition();
    }
    else
    {
      return parent.getPosition().add(getLocalPosition());
    }
  }
  
  public abstract Point3D getSize();

  public abstract void setSize(Point3D size);

  public abstract ContainerShape getShape();
  
  public abstract Point2D getDrawLocation();

  public abstract double getDrawHeight();

  public abstract Point2D getDispenseLocation();

  public abstract double getDispenseHeight();

  public abstract double getClearanceHeight();

  public abstract boolean hasSubcontainers();

  public abstract Iterator<Container> getSubcontainerIterator();

  public abstract Container getSubcontainer(int index);

  public abstract Container getSubcontainer(String name);
}
