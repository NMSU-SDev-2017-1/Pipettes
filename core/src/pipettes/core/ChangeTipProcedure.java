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
public class ChangeTipProcedure extends Procedure
{
  private ObjectProperty<Container> tipDisposal = new SimpleObjectProperty<Container>();
  private ObjectProperty<Container> newTip = new SimpleObjectProperty<Container>();;

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
    
    return new Container[] { };
  }  
  
  private void performChange(ProcessContext context, Device device,
      Container tipDisposal, Container newTip) throws PositioningException
  {
    if (tipDisposal.hasSubcontainers())
    {
      Iterator<Container> subcontainers = tipDisposal.getSubcontainerIterator();
      while (subcontainers.hasNext())
      {
        performRecursively(context, device, subcontainers.next());
      }
    }
        else
    {
      Point2D startLocation = device.getLocation();
      Point2D mixLocation = tipDisposal.getDispenseLocation();
      double startToDrawClearance = context.getClearanceHeight(startLocation,
          mixLocation);

      device.moveHeight(startToDrawClearance);
      device.move(mixLocation);
      device.moveHeight(tipDisposal.getDrawHeight());
      for(int i=0; i<10; i++){
        device.drawFluid(getVolume()*0.8);
        device.dispenseFluid(getVolume()*0.8);
      }
      device.dispenseFluid(getVolume()*0.05);
    }
  }

  public void perform(ProcessContext context, Device device)
      throws PositioningException
  {
    performRecursively(context, device, getTipDisposal());
  }
}
