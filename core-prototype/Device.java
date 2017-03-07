import java.io.File;
import java.io.FileNotFoundException;

import javafx.geometry.Point2D;
import javafx.geometry.Point3D;

public abstract class Device
{
  public abstract String getName();

  public abstract void setName(String name);

  public abstract void beginProcess(File outputFile) throws FileNotFoundException;

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
