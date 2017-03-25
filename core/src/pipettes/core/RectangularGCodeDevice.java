package pipettes.core;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Point3D;

@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class RectangularGCodeDevice extends GCodeDevice
{
  private DoubleProperty homePositionX = new SimpleDoubleProperty();
  private DoubleProperty homePositionY = new SimpleDoubleProperty();
  private DoubleProperty homePositionZ = new SimpleDoubleProperty();

  private DoubleProperty minimumExtentX = new SimpleDoubleProperty();
  private DoubleProperty minimumExtentY = new SimpleDoubleProperty();
  private DoubleProperty minimumExtentZ = new SimpleDoubleProperty();

  private DoubleProperty maximumExtentX = new SimpleDoubleProperty();
  private DoubleProperty maximumExtentY = new SimpleDoubleProperty();
  private DoubleProperty maximumExtentZ = new SimpleDoubleProperty();
  
  public double getHomePositionX()
  {
    return homePositionX.get();
  }
  
  public void setHomePositionX(double x)
  {
    homePositionX.set(x);
  }
  
  public DoubleProperty homePositionXProperty()
  {
    return homePositionX;
  }
  
  public double getHomePositionY()
  {
    return homePositionY.get();
  }
  
  public void setHomePositionY(double y)
  {
    homePositionY.set(y);
  }
  
  public DoubleProperty homePositionYProperty()
  {
    return homePositionY;
  }
  
  public double getHomePositionZ()
  {
    return homePositionZ.get();
  }
  
  public void setHomePositionZ(double z)
  {
    homePositionZ.set(z);
  }
  
  public DoubleProperty homePositionZProperty()
  {
    return homePositionZ;
  }
  
  @XmlElement
  @XmlJavaTypeAdapter(Point3DXmlAdapter.class)
  @Override
  public Point3D getHomePosition()
  {
    return new Point3D(getHomePositionX(), getHomePositionY(), getHomePositionZ()); 
  }

  public void setHomePosition(Point3D homePosition)
  {
    setHomePositionX(homePosition.getX());
    setHomePositionY(homePosition.getY());
    setHomePositionZ(homePosition.getZ());
  }

  public double getMinimumExtentX()
  {
    return minimumExtentX.get();
  }
  
  public void setMinimumExtentX(double x)
  {
    minimumExtentX.set(x);
  }
  
  public DoubleProperty minimumExtentXProperty()
  {
    return minimumExtentX;
  }
  
  public double getMinimumExtentY()
  {
    return minimumExtentY.get();
  }
  
  public void setMinimumExtentY(double y)
  {
    minimumExtentY.set(y);
  }
  
  public DoubleProperty minimumExtentYProperty()
  {
    return minimumExtentY;
  }
  
  public double getMinimumExtentZ()
  {
    return minimumExtentZ.get();
  }
  
  public void setMinimumExtentZ(double z)
  {
    minimumExtentZ.set(z);
  }
  
  public DoubleProperty minimumExtentZProperty()
  {
    return minimumExtentZ;
  }
  
  @XmlElement
  @XmlJavaTypeAdapter(Point3DXmlAdapter.class)
  public Point3D getMinimumExtent()
  {
    return new Point3D(getMinimumExtentX(), getMinimumExtentY(), getMinimumExtentZ()); 
  }

  public void setMinimumExtent(Point3D minimumExtent)
  {
    setMinimumExtentX(minimumExtent.getX());
    setMinimumExtentY(minimumExtent.getY());
    setMinimumExtentZ(minimumExtent.getZ());
  }

  public double getMaximumExtentX()
  {
    return maximumExtentX.get();
  }
  
  public void setMaximumExtentX(double x)
  {
    maximumExtentX.set(x);
  }
  
  public DoubleProperty maximumExtentXProperty()
  {
    return maximumExtentX;
  }
  
  public double getMaximumExtentY()
  {
    return maximumExtentY.get();
  }
  
  public void setMaximumExtentY(double y)
  {
    maximumExtentY.set(y);
  }
  
  public DoubleProperty maximumExtentYProperty()
  {
    return maximumExtentY;
  }
  
  public double getMaximumExtentZ()
  {
    return maximumExtentZ.get();
  }
  
  public void setMaximumExtentZ(double z)
  {
    maximumExtentZ.set(z);
  }
  
  public DoubleProperty maximumExtentZProperty()
  {
    return maximumExtentZ;
  }
  
  @XmlElement
  @XmlJavaTypeAdapter(Point3DXmlAdapter.class)
  public Point3D getMaximumExtent()
  {
    return new Point3D(getMaximumExtentX(), getMaximumExtentY(), getMaximumExtentZ()); 
  }

  public void setMaximumExtent(Point3D maximumExtent)
  {
    setMaximumExtentX(maximumExtent.getX());
    setMaximumExtentY(maximumExtent.getY());
    setMaximumExtentZ(maximumExtent.getZ());
  }
  
  @Override
  protected void checkPosition(Point3D position) throws PositioningException
  {
    if ((position.getX() < getMinimumExtentX()) || (position.getY() < getMinimumExtentY())
        || (position.getZ() < getMinimumExtentZ()))
    {
      throw new PositioningException();
    }

    if ((position.getX() > getMaximumExtentX()) || (position.getY() > getMaximumExtentY())
        || (position.getZ() > getMaximumExtentZ()))
    {
      throw new PositioningException();
    }
  }
}
