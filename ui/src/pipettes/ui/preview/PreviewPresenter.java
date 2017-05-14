package pipettes.ui.preview;

import java.net.URL;
import java.util.ResourceBundle;

import javax.inject.Inject;

import pipettes.core.Device;
import pipettes.core.Process;

import javafx.beans.property.ObjectProperty;
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

    activeProcess.addListener((observable, oldValue, newValue) ->
    {
      onActiveProcess();
    });

    activeDevice.addListener((observable, oldValue, newValue) ->
    {
      onActiveDevice();
    });

    onActiveDevice();
    onActiveProcess();

    initializeSubScene();

    // TODO: Needed?
    model.subSceneProperty().addListener((observable, oldValue, newValue) ->
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
    model.setDevice(activeDevice.get());
  }

  private void onActiveProcess()
  {
    model.setContainers(activeProcess.get().getBaseContainers());
  }

  public void onReset()
  {
    model.resetCamera();
  }
}
