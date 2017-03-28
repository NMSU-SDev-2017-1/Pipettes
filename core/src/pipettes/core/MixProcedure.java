package pipettes.core;

import java.util.Iterator;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Point2D;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class DispenseProcedure extends Procedure
{
  private ObjectProperty<Container> destination = new SimpleObjectProperty<Container>();
  private DoubleProperty mix_volume = new SimpleDoubleProperty();

  @XmlIDREF
  @XmlAttribute
  public Container getDestination()
  {
    return destination.get();
  }

  public void setDestination(Container destination)
  {
    this.destination.set(destination);
  }
  
  public ObjectProperty<Container> destinationProperty()
  {
    return destination;
  }


  @XmlAttribute
  public double getVolume()
  {
    return volume.get();
  }

  public void setVolume(double volume)
  {
    this.volume.set(volume);
  }

  public DoubleProperty volumeProperty()
  {
    return volume;
  }
  
  private void mixRecursivley(ProcessContext context, Device device,
      Container destination) throws PositioningException
  {
    if (destination.hasSubcontainers())
    {
      Iterator<Container> subcontainers = destination.getSubcontainerIterator();
      while (subcontainers.hasNext())
      {
        performRecursively(context, device, source, subcontainers.next());
      }
    }
    else
    {
      Point2D startLocation = device.getLocation();
      Point2D drawLocation = source.getDrawLocation();
      Point2D dispenseLocation = destination.getDispenseLocation();
      double startToDrawClearance = context.getClearanceHeight(startLocation,
          drawLocation);

      device.moveHeight(startToDrawClearance);

      device.move(drawLocation);
      device.moveHeight(source.getDrawHeight());
      device.drawFluid(getVolume()*0.8);
      device.dispenseFluid(getVolume()*0.8);
      device.drawFluid(getVolume()*0.8);
      device.dispenseFluid(getVolume()*0.8);
    }
  }

  public void perform(ProcessContext context, Device device)
      throws PositioningException
  {
    performRecursively(context, device, getSource(), getDestination());
  }
}
