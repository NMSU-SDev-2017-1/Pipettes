package pipettes.core;

import java.util.Iterator;

import javafx.beans.Observable;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
import javafx.geometry.Point3D;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class Container extends LibraryItem
{
  public static final String prefix = "Container";

  private static final char nestingSeparator = '/';

  // TODO: Ideally, this should not be serialized and should be managed
  // only through housekeeping
  @XmlIDREF
  @XmlAttribute
  private Container parent;

  private Library<LibraryItem> library;

  private StringProperty localName = new SimpleStringProperty();

  // Local position relative to parent
  // Defined as center of bottom of object to make rotation sensible
  private DoubleProperty localPositionX = new SimpleDoubleProperty();
  private DoubleProperty localPositionY = new SimpleDoubleProperty();
  private DoubleProperty localPositionZ = new SimpleDoubleProperty();

  private DoubleProperty sizeX = new SimpleDoubleProperty();
  private DoubleProperty sizeY = new SimpleDoubleProperty();
  private DoubleProperty sizeZ = new SimpleDoubleProperty();

  private ObjectProperty<ContainerShape> shape = new SimpleObjectProperty<ContainerShape>(
      ContainerShape.Cylindrical);

  private DoubleProperty drawHeightAboveBottom = new SimpleDoubleProperty();
  private DoubleProperty dispenseHeightAboveTop = new SimpleDoubleProperty();
  private DoubleProperty clearanceHeightAboveTop = new SimpleDoubleProperty();

  @XmlElement
  private ObservableList<Container> subcontainers;

  @XmlElement
  public String getLocalName()
  {
    return localName.get();
  }

  public void setLocalName(String name)
  {
    localName.set(name);
  }

  public StringProperty localNameProperty()
  {
    return localName;
  }

  public String getLibraryName()
  {
    return getLocalName();
  }

  public void setLibraryName(String name)
  {
    setLocalName(name);
  }

  public StringProperty libraryNameProperty()
  {
    return localNameProperty();
  }

  @SuppressWarnings("unchecked")
  @Override
  public void setLibrary(Library<? extends LibraryItem> library)
  {
    this.library = (Library<LibraryItem>) library;
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

  public double getLocalPositionX()
  {
    return localPositionX.get();
  }

  public void setLocalPositionX(double x)
  {
    localPositionX.set(x);
  }

  public DoubleProperty localPositionXProperty()
  {
    return localPositionX;
  }

  public double getLocalPositionY()
  {
    return localPositionY.get();
  }

  public void setLocalPositionY(double y)
  {
    localPositionY.set(y);
  }

  public DoubleProperty localPositionYProperty()
  {
    return localPositionY;
  }

  public double getLocalPositionZ()
  {
    return localPositionZ.get();
  }

  public void setLocalPositionZ(double z)
  {
    localPositionZ.set(z);
  }

  public DoubleProperty localPositionZProperty()
  {
    return localPositionZ;
  }

  @XmlElement
  @XmlJavaTypeAdapter(Point3DXmlAdapter.class)
  public Point3D getLocalPosition()
  {
    return new Point3D(getLocalPositionX(), getLocalPositionY(),
        getLocalPositionZ());
  }

  public void setLocalPosition(Point3D position)
  {
    setLocalPositionX(position.getX());
    setLocalPositionY(position.getY());
    setLocalPositionZ(position.getZ());
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

  public double getSizeX()
  {
    return sizeX.get();
  }

  public void setSizeX(double x)
  {
    sizeX.set(x);
  }

  public DoubleProperty sizeXProperty()
  {
    return sizeX;
  }

  public double getSizeY()
  {
    return sizeY.get();
  }

  public void setSizeY(double y)
  {
    sizeY.set(y);
  }

  public DoubleProperty sizeYProperty()
  {
    return sizeY;
  }

  public double getSizeZ()
  {
    return sizeZ.get();
  }

  public void setSizeZ(double z)
  {
    sizeZ.set(z);
  }

  public DoubleProperty sizeZProperty()
  {
    return sizeZ;
  }

  @XmlElement
  @XmlJavaTypeAdapter(Point3DXmlAdapter.class)
  public Point3D getSize()
  {
    return new Point3D(getSizeX(), getSizeY(), getSizeZ());
  }

  public void setSize(Point3D size)
  {
    setSizeX(size.getX());
    setSizeY(size.getY());
    setSizeZ(size.getZ());
  }

  @XmlElement
  public ContainerShape getShape()
  {
    return shape.get();
  }

  public void setShape(ContainerShape shape)
  {
    this.shape.set(shape);
  }

  public ObjectProperty<ContainerShape> shapeProperty()
  {
    return shape;
  }

  @XmlElement
  public double getDrawHeightAboveBottom()
  {
    return drawHeightAboveBottom.get();
  }

  public void setDrawHeightAboveBottom(double height)
  {
    drawHeightAboveBottom.set(height);
  }

  public DoubleProperty drawHeightAboveBottomProperty()
  {
    return drawHeightAboveBottom;
  }

  @XmlElement
  public double getDispenseHeightAboveTop()
  {
    return dispenseHeightAboveTop.get();
  }

  public void setDispenseHeightAboveTop(double height)
  {
    dispenseHeightAboveTop.set(height);
  }

  public DoubleProperty dispenseHeightAboveTopProperty()
  {
    return dispenseHeightAboveTop;
  }

  @XmlElement
  public double getClearanceHeightAboveTop()
  {
    return clearanceHeightAboveTop.get();
  }

  public void setClearanceHeightAboveTop(double height)
  {
    clearanceHeightAboveTop.set(height);
  }

  public DoubleProperty clearanceHeightAboveTopProperty()
  {
    return clearanceHeightAboveTop;
  }

  public Point2D getDrawLocation()
  {
    return new Point2D(getPosition().getX(), getPosition().getY());
  }

  private double getBottomHeight()
  {
    return getPosition().getZ();
  }

  private double getTopHeight()
  {
    return getPosition().getZ() + getSizeZ();
  }

  public double getDrawHeight()
  {
    return getBottomHeight() + getDrawHeightAboveBottom();
  }

  public Point2D getDispenseLocation()
  {
    return getDrawLocation();
  }

  public double getDispenseHeight()
  {
    return getTopHeight() + getDispenseHeightAboveTop();
  }

  public double getClearanceHeight()
  {
    return getTopHeight() + getClearanceHeightAboveTop();
  }

  public boolean hasSubcontainers()
  {
    return (subcontainers.size() > 0);
  }

  public Iterator<Container> getSubcontainerIterator()
  {
    return subcontainers.iterator();
  }

  public ObservableList<Container> getSubcontainers()
  {
    return subcontainers;
  }

  public Container()
  {
    subcontainers = FXCollections
        .observableArrayList(container -> new Observable[] {
            container.localNameProperty(), container.localPositionXProperty(),
            container.localPositionYProperty(),
            container.localPositionZProperty(), container.sizeXProperty(),
            container.sizeYProperty(), container.sizeZProperty(),
            container.shapeProperty(),
            container.drawHeightAboveBottomProperty(),
            container.dispenseHeightAboveTopProperty(),
            container.clearanceHeightAboveTopProperty(),
            container.getSubcontainers() });

    initializeSubcontainerListeners();
    
    localName.addListener(new ChangeListener<String>()
    {
      @Override
      public void changed(ObservableValue<? extends String> observable,
          String oldValue, String newValue)
      {
        if (library != null)
        {
          if (!library.isValidNameChange(newValue))
          {
            localName.set(oldValue);
          }
        }
      }
    });
  }

  public Container(ObservableList<Container> containers)
  {
    subcontainers = containers;
    initializeSubcontainerListeners();
  }

  private void initializeSubcontainerListeners()
  {
    Container thisContainer = this;
    
    subcontainers.addListener(new ListChangeListener<Container>()
    {
      @Override
      public void onChanged(
          ListChangeListener.Change<? extends Container> change)
      {
        while (change.next())
        {
          for (Container subcontainer : change.getAddedSubList())
          {
            subcontainer.setParent(thisContainer);
          }
        }
      }
    });
  }
  
  public Container clone()
  {
    Container newContainer = new Container();

    newContainer.setLocalName(getLocalName());
    newContainer.setLocalPosition(getLocalPosition());
    newContainer.setSize(getSize());
    newContainer.setShape(getShape());
    newContainer.setDrawHeightAboveBottom(getDrawHeightAboveBottom());
    newContainer.setDispenseHeightAboveTop(getDispenseHeightAboveTop());
    newContainer.setClearanceHeightAboveTop(getClearanceHeightAboveTop());

    for (Container subcontainer : subcontainers)
    {
      newContainer.subcontainers.add(subcontainer.clone());
    }

    return newContainer;
  }

  public String getAvailableSubcontainerName(String prefix)
  {
    String result = Common.removeTrailingInteger(prefix);
    
    if (result.length() == 0)
    {
      result = prefix;
    }
    
    boolean collision;
    int number = 0;

    do
    {
      collision = false;

      for (Container container : subcontainers)
      {
        if (container.getLocalName().equals(Common.appendInteger(result, number)))
        {
          number++;
          collision = true;
          break;
        }
      }
    } while (collision);

    return Common.appendInteger(result, number);
  }

  public String toString()
  {
    return getLocalName();
  }
}
