package pipettes.ui.deviceeditor;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point3D;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import javax.inject.Inject;

import pipettes.core.CylindricalGCodeDevice;
import pipettes.core.Device;
import pipettes.core.RectangularGCodeDevice;

public class DeviceEditorPresenter implements Initializable
{
  @FXML
  TextField nameTextField;

  @FXML
  Label typeLabel;

  @FXML
  Button example1Button;

  @FXML
  Button example2Button;

  @Inject
  ObjectProperty<Device> activeDevice;

  Device lastActiveDevice;

  BooleanProperty validDevice = new SimpleBooleanProperty();

  // TODO: Remove when examples no longer needed
  CylindricalGCodeDevice exampleDevice1 = new CylindricalGCodeDevice();
  RectangularGCodeDevice exampleDevice2 = new RectangularGCodeDevice();

  public DeviceEditorPresenter()
  {
    exampleDevice1.setName("SeeMeCNC Rostock MAX v2");
    exampleDevice1.setExtrudePerVolume(20.0);
    exampleDevice1.setDispenseExtrudeRatio(1.01);
    exampleDevice1.setRadius(140.0);
    exampleDevice1.setMinimumZ(0.0);
    exampleDevice1.setMaximumZ(375.0);

    exampleDevice2.setName("MakerBot Replicator");
    exampleDevice2.setExtrudePerVolume(20.0);
    exampleDevice2.setDispenseExtrudeRatio(1.01);
    exampleDevice2.setHomePosition(new Point3D(50.0, 75.0, 130.0));
    exampleDevice2.setMinimumExtent(new Point3D(-110.0, -75.0, 0.0));
    exampleDevice2.setMaximumExtent(new Point3D(110.0, 75.0, 130.0));
  }

  @Override
  public void initialize(URL url, ResourceBundle rb)
  {
    nameTextField.visibleProperty().bind(validDevice);
    typeLabel.visibleProperty().bind(validDevice);

    activeDevice.addListener(new ChangeListener<Device>()
    {
      @Override
      public void changed(ObservableValue<? extends Device> observableValue,
          Device oldValue, Device newValue)
      {
        rebindDeviceControls();
      }
    });

    rebindDeviceControls();
  }

  private void rebindDeviceControls()
  {
    if (lastActiveDevice != null)
    {
      nameTextField.textProperty().unbindBidirectional(
          lastActiveDevice.nameProperty());
    }

    Device device = activeDevice.get();

    if (device != null)
    {
      validDevice.set(true);

      nameTextField.textProperty().bindBidirectional(device.nameProperty());
      typeLabel.setText(device.getType().toString());
    }
    else
    {
      validDevice.set(false);
    }

    lastActiveDevice = device;
  }

  public void selectExample1()
  {
    activeDevice.set(exampleDevice1);
  }

  public void selectExample2()
  {
    activeDevice.set(exampleDevice2);
  }

  public void selectNothing()
  {
    activeDevice.set(null);
  }
}
