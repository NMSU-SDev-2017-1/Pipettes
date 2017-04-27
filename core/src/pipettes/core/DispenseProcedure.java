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
  private ObjectProperty<Container> source = new SimpleObjectProperty<Container>();
  private ObjectProperty<Container> destination = new SimpleObjectProperty<Container>();
  private DoubleProperty volume = new SimpleDoubleProperty();

  public String getName()
  {
    return "Dispense";
  }
  
  @XmlIDREF
  @XmlAttribute
  public Container getSource()
  {
    return source.get();
  }

  public void setSource(Container source)
  {
    this.source.set(source);
  }

  public ObjectProperty<Container> sourceProperty()
  {
    return source;
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
    if (getSource() != null)
    {
      if (getDestination() != null)
      {
        return new Container[] { getSource(), getDestination() };
      }
      else
      {
        return new Container[] { getSource() };
      }
    }
    else
    {
      if (getDestination() != null)
      {
        return new Container[] { getDestination() };
      }
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

  private void performRecursively(ProcessContext context, Container source,
      Container destination) throws PositioningException
  {
    if (source.hasSubcontainers())
    {
      Iterator<Container> subcontainers = source.getSubcontainerIterator();
      while (subcontainers.hasNext())
      {
        performRecursively(context, subcontainers.next(), destination);
      }
    }
    else if (destination.hasSubcontainers())
    {
      Iterator<Container> subcontainers = destination.getSubcontainerIterator();
      while (subcontainers.hasNext())
      {
        performRecursively(context, source, subcontainers.next());
      }
    }
    else
    {
      Device device = context.getDevice();
      ProcessLogger logger = context.getLogger();
      Point2D startLocation = device.getLocation();
      Point2D drawLocation = source.getDrawLocation();
      Point2D dispenseLocation = destination.getDispenseLocation();
      double startToDrawClearance = context.getClearanceHeight(startLocation,
          drawLocation);
      double drawToDispenseClearance = context.getClearanceHeight(drawLocation,
          dispenseLocation);
      double volume = getVolume();

      device.moveHeight(startToDrawClearance);

      device.move(drawLocation);
      device.moveHeight(source.getDrawHeight());

      device.drawFluid(volume);
      logger.logDraw(source.getName(), volume);

      device.moveHeight(drawToDispenseClearance);

      device.move(dispenseLocation);
      device.moveHeight(destination.getDispenseHeight());

      device.dispenseFluid(volume);
      logger.logDispense(destination.getName(), volume);
    }
  }

  public void perform(ProcessContext context) throws PositioningException
  {
    performRecursively(context, getSource(), getDestination());
  }
}
