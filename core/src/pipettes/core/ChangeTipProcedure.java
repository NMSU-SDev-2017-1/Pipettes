package pipettes.core;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Point2D;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class ChangeTipProcedure extends Procedure
{
  private ObjectProperty<Container> tipDisposal = new SimpleObjectProperty<Container>();
  private ObjectProperty<Container> newTip = new SimpleObjectProperty<Container>();;

  public String getName()
  {
    return "Change Tip";
  }

  @XmlIDREF
  @XmlAttribute
  public Container getTipDisposal()
  {
    return tipDisposal.get();
  }

  public void setTipDisposal(Container tipDisposal)
  {
    this.tipDisposal.set(tipDisposal);
  }

  public ObjectProperty<Container> tipDisposalProperty()
  {
    return tipDisposal;
  }

  @XmlIDREF
  @XmlAttribute
  public Container getNewTip()
  {
    return newTip.get();
  }

  public void setNewTip(Container newTip)
  {
    this.newTip.set(newTip);
  }

  public ObjectProperty<Container> newTipProperty()
  {
    return newTip;
  }

  public Container[] getReferencedContainers()
  {
    if (getTipDisposal() != null)
    {
      if (getNewTip() != null)
      {
        return new Container[] { getTipDisposal(), getNewTip() };
      }
      else
      {
        return new Container[] { getTipDisposal() };
      }
    }
    else
    {
      if (getNewTip() != null)
      {
        return new Container[] { getNewTip() };
      }
    }

    return new Container[] {};
  }

  private void performChange(ProcessContext context, Container tipDisposal,
      Container newTip) throws PositioningException
  {
    Device device = context.getDevice();
    ProcessLogger logger = context.getLogger();
    Point2D startLocation = device.getLocation();
    Point2D disposalLocation = tipDisposal.getDispenseLocation();
    Point2D newTipLocation = newTip.getDrawLocation();
    double startToDisposeClearance = context.getClearanceHeight(startLocation,
        disposalLocation);
    double knockOffX = tipDisposal.getLocalPositionX()
        + (tipDisposal.getSizeX() * 0.5);
    double knockOffY = tipDisposal.getLocalPositionY()
        + (tipDisposal.getSizeY() * 0.5);
    Point2D knockOffLocation = new Point2D(knockOffX, knockOffY);
    double disposeToNewTipClearance = context.getClearanceHeight(
        knockOffLocation, newTipLocation);

    device.moveHeight(startToDisposeClearance);

    device.move(disposalLocation);
    device.moveHeight(tipDisposal.getDispenseHeight());

    device.move(knockOffLocation);
    logger.logTipDrop(tipDisposal.getName());

    device.moveHeight(disposeToNewTipClearance);

    device.move(newTipLocation);
    device.moveHeight(newTip.getDrawHeight());
    logger.logTipNew(newTip.getName());
  }

  public void perform(ProcessContext context) throws PositioningException
  {
    performChange(context, getTipDisposal(), getNewTip());
  }
}
