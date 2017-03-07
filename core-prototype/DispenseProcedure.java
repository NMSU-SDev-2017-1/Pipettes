import java.util.Iterator;

import javafx.geometry.Point2D;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class DispenseProcedure extends Procedure
{
  @XmlIDREF
  @XmlAttribute
  private Container source;
  
  @XmlIDREF
  @XmlAttribute
  private Container destination;
  
  @XmlAttribute
  private double volume;

  public Container getSource()
  {
    return source;
  }

  public void setSource(Container source)
  {
    this.source = source;
  }

  public Container getDestination()
  {
    return destination;
  }

  public void setDestination(Container destination)
  {
    this.destination = destination;
  }

  public Container[] getReferencedContainers()
  {
    return new Container[] { source, destination };
  }

  public double getVolume()
  {
    return volume;
  }

  public void setVolume(double volume)
  {
    this.volume = volume;
  }

  private void performRecursively(ProcessContext context, Device device,
      Container source, Container destination) throws PositioningException
  {
    if (source.hasSubcontainers())
    {
      Iterator<Container> subcontainers = source.getSubcontainers();
      while (subcontainers.hasNext())
      {
        performRecursively(context, device, subcontainers.next(), destination);
      }
    }
    else if (destination.hasSubcontainers())
    {
      Iterator<Container> subcontainers = destination.getSubcontainers();
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
      double drawToDispenseClearance = context.getClearanceHeight(
          drawLocation, dispenseLocation);

      device.moveHeight(startToDrawClearance);

      device.move(drawLocation);
      device.moveHeight(source.getDrawHeight());
      device.drawFluid(volume);

      device.moveHeight(drawToDispenseClearance);

      device.move(dispenseLocation);
      device.moveHeight(destination.getDispenseHeight());
      device.dispenseFluid(volume);
    }
  }

  public void perform(ProcessContext context, Device device)
      throws PositioningException
  {
    performRecursively(context, device, source, destination);
  }
}
