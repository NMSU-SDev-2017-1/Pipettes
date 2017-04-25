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

  public void setTipDisposal(Container TipDisposal)
  {
    this.tipDisposal.set(TipDisposal);
  }

  public ObjectProperty<Container> TipDisposalProperty()
  {
    return tipDisposal;
  }

  @XmlIDREF
  @XmlAttribute
  public Container getNewTip()
  {
    return newTip.get();
  }

  public void setNewTip(Container NewTip)
  {
    this.newTip.set(NewTip);
  }

  public ObjectProperty<Container> NewTipProperty()
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
    Point2D startLocation = device.getLocation();
    Point2D disposalLocation = tipDisposal.getDispenseLocation();
    Point2D newTipLocation = newTip.getDrawLocation();
    double startToDisposeClearance = context.getClearanceHeight(startLocation,
        disposalLocation);
    Point2D knockOffLocation = new Point2D((tipDisposal.getLocalPositionX()
        + tipDisposal.getSizeX() + 1), tipDisposal.getLocalPositionY());
    double disposeToNewTipClearance = context.getClearanceHeight(
        disposalLocation, newTipLocation);

    device.moveHeight(startToDisposeClearance);
    device.move(disposalLocation);
    device.moveHeight(tipDisposal.getDrawHeight());
    device.move(knockOffLocation);
    device.moveHeight(disposeToNewTipClearance);
    device.move(newTipLocation);
    device.moveHeight(newTip.getDrawHeight());

  }

  public void perform(ProcessContext context) throws PositioningException
  {
    performChange(context, getTipDisposal(), getNewTip());
  }
}
