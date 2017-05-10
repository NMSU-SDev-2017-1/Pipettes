package pipettes.core;

import java.util.Iterator;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Point2D;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class MixProcedure extends Procedure
{
  private ObjectProperty<Container> destination = new SimpleObjectProperty<Container>();
  private DoubleProperty volume = new SimpleDoubleProperty();
  private IntegerProperty count = new SimpleIntegerProperty();

  public String getName()
  {
    return "Mix";
  }
  
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

  public Container[] getReferencedContainers()
  {
    if (getDestination() != null)
    {
      return new Container[] { getDestination() };
    }
    return new Container[] {};
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
  
  @XmlAttribute
  public double getCount()
  {
    return count.get();
  }

  public void setCount(int count)
  {
    this.count.set(count);
  }

  public IntegerProperty countProperty()
  {
    return count;
  }

  public MixProcedure()
  {
    setCount(1);
  }
  
  private void performRecursively(ProcessContext context, Container destination)
      throws PositioningException
  {
    if (destination.hasSubcontainers())
    {
      Iterator<Container> subcontainers = destination.getSubcontainerIterator();
      while (subcontainers.hasNext())
      {
        performRecursively(context, subcontainers.next());
      }
    }
    else
    {
      Device device = context.getDevice();
      ProcessLogger logger = context.getLogger();
      Point2D startLocation = device.getLocation();
      Point2D mixLocation = destination.getDispenseLocation();
      double startToDrawClearance = context.getClearanceHeight(startLocation,
          mixLocation);
      double volume = getVolume();

      device.moveHeight(startToDrawClearance);
      
      device.move(mixLocation);
      device.moveHeight(destination.getDrawHeight());
      
      for (int i = 0; i < getCount(); i++)
      {
        device.drawFluid(volume);
        logger.logDraw(destination.getName(), volume);
        
        device.dispenseFluid(volume);
        logger.logDispense(destination.getName(), volume);
      }
    }
  }

  public void perform(ProcessContext context) throws PositioningException
  {
    performRecursively(context, getDestination());
  }
}
