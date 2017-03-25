package pipettes.core;

import java.io.PrintStream;

import javax.xml.bind.annotation.XmlElement;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Point2D;
import javafx.geometry.Point3D;

public abstract class GCodeDevice extends Device
{
  private StringProperty header = new SimpleStringProperty();
  private StringProperty footer = new SimpleStringProperty();

  private DoubleProperty extrudePerVolume = new SimpleDoubleProperty();
  private DoubleProperty dispenseExtrudeRatio = new SimpleDoubleProperty();
  
  private PrintStream output;
  private Point3D position;

  public GCodeDevice()
  {
    header.set(getDefaultHeader());
    footer.set(getDefaultFooter());
  }
  
  public String getDefaultHeader()
  {
    return "G21 ; Set units to millimeters\n"
        + "G90 ; Switch to absolute coordinates\n"
        + "M83 ; Set extruder to relative mode\n" + "G28 ; Home all axes";
  }
  
  public String getDefaultFooter()
  {
    return "G28 ; Home all axes";
  }

  @XmlElement
  public String getHeader()
  {
    return header.get();
  }

  public void setHeader(String header)
  {
    this.header.set(header);
  }

  public StringProperty headerProperty()
  {
    return header;
  }
  
  @XmlElement
  public String getFooter()
  {
    return footer.get();
  }

  public void setFooter(String footer)
  {
    this.footer.set(footer);
  }
  
  public StringProperty footerProperty()
  {
    return footer;
  }

  @XmlElement
  public double getExtrudePerVolume()
  {
    return extrudePerVolume.get();
  }

  public void setExtrudePerVolume(double ratio)
  {
    extrudePerVolume.set(ratio);
  }

  public DoubleProperty extrudePerVolumeProperty()
  {
    return extrudePerVolume;
  }
  
  @XmlElement
  public double getDispenseExtrudeRatio()
  {
    return dispenseExtrudeRatio.get();
  }

  public void setDispenseExtrudeRatio(double ratio)
  {
    dispenseExtrudeRatio.set(ratio);
  }
  
  public DoubleProperty dispenseExtrudeRatioProperty()
  {
    return dispenseExtrudeRatio;
  }

  public void beginProcess(PrintStream output)
  {
    this.output = output;
    
    output.println(getHeader());
    
    position = getHomePosition();
  }

  public void endProcess()
  {
    output.println(getFooter());
	
    output.flush();
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
    double feedrateMmPerMinute = speed * 60.0;
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
    double extrude = -getExtrudePerVolume() * volume;
    output.printf("G1 E%.2f\n", extrude);
  }

  public void dispenseFluid(double volume)
  {
    double extrude = getExtrudePerVolume() * getDispenseExtrudeRatio() * volume;
    output.printf("G1 E%.2f\n", extrude);
  }
}
