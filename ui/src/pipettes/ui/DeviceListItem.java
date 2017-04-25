package pipettes.ui;

import pipettes.core.Device;
import javafx.beans.property.StringProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class DeviceListItem extends HBox
{
  private Device device;
  private Label label = new Label();

  public DeviceListItem(StringProperty name, Device device)
  {
    super();

    this.device = device;
    
    if (name != null)
    {
      label.textProperty().bindBidirectional(name);
    }
    else
    {
      label.textProperty().bindBidirectional(device.nameProperty());
    }

    this.getChildren().add(label);
    this.setAlignment(Pos.CENTER_LEFT);
  }
}
