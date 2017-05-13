package pipettes.ui.preview;

import java.net.URL;
import java.util.ResourceBundle;

import javax.inject.Inject;

import pipettes.core.CylindricalGCodeDevice;
import pipettes.core.Device;
import pipettes.core.Process;
import pipettes.core.RectangularGCodeDevice;

import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class PreviewPresenter implements Initializable
{
  @FXML
  private VBox root;

  @FXML
  private Button startButton;

  @FXML
  private Button rewindButton;

  @FXML
  private ToggleButton playButton;

  @FXML
  private Button fastForwardButton;

  @FXML
  private Button endButton;

  @FXML
  private ToggleButton loopButton;

  @FXML
  private AnchorPane viewPane;

  @Inject
  private ObjectProperty<Process> activeProcess;

  @Inject
  private ObjectProperty<Device> activeDevice;

  private PreviewModel model = new PreviewModel();

  @Override
  public void initialize(URL arg0, ResourceBundle arg1)
  {
    TimelineController timelineController = new TimelineController(startButton,
        rewindButton, playButton, fastForwardButton, endButton, loopButton);
    timelineController.timelineProperty().bind(model.timelineProperty());

    activeProcess.addListener(new ChangeListener<Process>()
    {
      @Override
      public void changed(ObservableValue<? extends Process> observableValue,
          Process oldValue, Process newValue)
      {
        onActiveProcess();
      }
    });

    activeDevice.addListener(new ChangeListener<Device>()
    {
      @Override
      public void changed(ObservableValue<? extends Device> observableValue,
          Device oldValue, Device newValue)
      {
        onActiveDevice();
      }
    });

    onActiveDevice();
    onActiveProcess();

    initializeSubScene();

    // TODO: Needed?
    model.subSceneProperty().addListener((ov, oldVal, newVal) ->
    {
      viewPane.getChildren().clear();
      initializeSubScene();
    });
  }

  private void initializeSubScene()
  {
    SubScene subScene = model.getSubScene();
    subScene.widthProperty().bind(viewPane.widthProperty());
    subScene.heightProperty().bind(viewPane.heightProperty());
    viewPane.getChildren().add(subScene);
  }

  private void onActiveDevice()
  {
    Device device = activeDevice.get();

    if (device == null)
    {
      // TODO: Implement
    }
    else if (device instanceof RectangularGCodeDevice)
    {
      // rebindActiveDeviceRectangularControls((RectangularGCodeDevice) device);
    }
    else if (device instanceof CylindricalGCodeDevice)
    {
      // rebindActiveDeviceCylindricalControls((CylindricalGCodeDevice) device);
    }
  }

  private void onActiveProcess()
  {
  }

  public void onReset()
  {
    model.resetCamera();
  }
}
