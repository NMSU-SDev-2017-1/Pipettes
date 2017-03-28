package pipettes.ui.deviceeditor;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.ObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

import javax.inject.Inject;

import pipettes.core.Device;

public class DeviceEditorPresenter implements Initializable
{
  @FXML
  Label name;

  @Inject
  ObjectProperty<Device> activeDevice;

  @Override
  public void initialize(URL url, ResourceBundle rb)
  {
  }
}
