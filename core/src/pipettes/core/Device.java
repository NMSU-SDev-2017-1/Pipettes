package pipettes.core;

import java.io.PrintStream;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Point2D;
import javafx.geometry.Point3D;

@XmlAccessorType(XmlAccessType.NONE)
public abstract class Device extends LibraryItem
{
  public static final String prefix = "Device";

  private StringProperty name = new SimpleStringProperty();

  private Library<LibraryItem> library;

  @XmlElement
  public String getName()
  {
    return name.get();
  }

  public void setName(String name)
  {
    this.name.set(name);
  }

  public StringProperty nameProperty()
  {
    return name;
  }

  public String getLibraryName()
  {
    return getName();
  }

  public void setLibraryName(String name)
  {
    setName(name);
  }

  public StringProperty libraryNameProperty()
  {
    return nameProperty();
  }

  @SuppressWarnings("unchecked")
  @Override
  public void setLibrary(Library<? extends LibraryItem> library)
  {
    this.library = (Library<LibraryItem>) library;
  }

  public Device()
  {
    name.addListener(new ChangeListener<String>()
    {
      @Override
      public void changed(ObservableValue<? extends String> observable,
          String oldValue, String newValue)
      {
        if (library != null)
        {
          if (!library.isValidNameChange(newValue))
          {
            name.set(oldValue);
          }
        }
      }
    });
  }

  public abstract DeviceType getType();

  public abstract void beginProcess(PrintStream output);

  public abstract void endProcess();

  public abstract Point3D getHomePosition();

  public abstract Point2D getLocation();

  public abstract Point3D getPosition();

  public abstract void setSpeed(double speed);

  public abstract void moveHeight(double z) throws PositioningException;

  public abstract void move(Point2D location) throws PositioningException;

  public abstract void move(Point3D position) throws PositioningException;

  // TODO: Define maximum draw/dispense volume

  public abstract void drawFluid(double volume);

  public abstract void dispenseFluid(double volume);
}
