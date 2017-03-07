import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import javafx.geometry.Point2D;
import javafx.geometry.Point3D;

@XmlAccessorType(XmlAccessType.FIELD)
public abstract class GCodeDevice extends Device
{
  private String name;
  private String header;

  @XmlTransient
  private Point3D position;
  
  @XmlTransient
  private PrintStream output;
  
  private double extrudePerVolume;
  private double dispenseExtrudeRatio;

  public String getName()
  {
    return name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public String getDefaultHeader()
  {
    return "G21 ; Set units to millimeters\n"
        + "G90 ; Switch to absolute coordinates\n"
        + "M83 ; Set extruder to relative mode\n" + "G28 ; Home all axes\n";
  }

  public String getHeader()
  {
    return header;
  }

  public void setHeader(String header)
  {
    this.header = header;
  }

  public double getExtrudePerVolumeRatio()
  {
    return extrudePerVolume;
  }

  public void setExtrudePerVolumeRatio(double ratio)
  {
    extrudePerVolume = ratio;
  }

  public void beginProcess(File outputFile) throws FileNotFoundException
  {
    if (output != null)
    {
      // TODO: Throw exception
    }

    output = new PrintStream(outputFile);

    output.println(header);

    position = getHomePosition();
  }

  public void endProcess()
  {
    output.flush();
    output.close();
    output = null;
  }
  
  public Point2D getLocation()
  {
    return new Point2D(position.getX(), position.getY());
  }

  public Point3D getPosition()
  {
    return Common.newPoint3D(position);
  }

  protected abstract void checkPosition(Point3D position)
      throws PositioningException;

  public void setSpeed(double speed)
  {
    double feedrateMmPerMinute = speed * 60.0f;
    output.printf("G1 F%.2f\n", feedrateMmPerMinute);
  }

  public void moveHeight(double z) throws PositioningException
  {
    Point3D pos = new Point3D(position.getX(), position.getY(), z);

    checkPosition(pos);

    output.printf("G1 Z%.2f\n", pos.getZ());

    position = pos;
  }

  public void move(Point2D location) throws PositioningException
  {
    Point3D pos = new Point3D(location.getX(), location.getY(), position.getZ());

    checkPosition(pos);

    output.printf("G1 X%.2f Y%.2f\n", pos.getX(), pos.getY());

    position = pos;
  }

  public void move(Point3D position) throws PositioningException
  {
    Point3D pos = Common.newPoint3D(position);

    checkPosition(pos);

    output.printf("G1 X%.2f Y%.2f Z%.2f\n", pos.getX(), pos.getY(), pos.getZ());

    this.position = pos;
  }

  public void drawFluid(double volume)
  {
    double extrude = -extrudePerVolume * volume;
    output.printf("G1 E%.2f\n", extrude);
  }

  public void dispenseFluid(double volume)
  {
    double extrude = extrudePerVolume * dispenseExtrudeRatio * volume;
    output.printf("G1 E%.2f\n", extrude);
  }
}
