package pipettes.core;

import java.io.PrintStream;

import javax.xml.bind.annotation.XmlElement;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Point2D;
import javafx.geometry.Point3D;

public abstract class Device
{
  private StringProperty name =  new SimpleStringProperty();

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
