package pipettes.ui.dashboard;

import pipettes.ui.dashboard.light.LightView;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javax.inject.Inject;

public class DashboardPresenter implements Initializable
{
  // Injected control from FXML file
  @FXML
  Label message;

  // Injected control from FXML file
  @FXML
  Pane lightsBox;

  // Injected by creation of new Tower object
  @Inject
  Tower tower;

  // Injected from configuration.properties in current folder
  @Inject
  private String prefix;

  // Injected from system property created in App
  @Inject
  private String happyFlight;

  // Injected from custom property created in App 
  @Inject
  private LocalDate date;

  private String theVeryEnd;

  @Override
  public void initialize(URL url, ResourceBundle rb)
  {
    // Retrieved from dashboard.properties in current folder
    this.theVeryEnd = rb.getString("theEnd");
  }

  // Called by button onAction defined in FXML
  public void createLights()
  {
    for (int i = 0; i < 255; i++)
    {
      final int red = i;
      LightView view = new LightView((f) -> red);
      view.getViewAsync(lightsBox.getChildren()::add);
    }
  }

  // Called by button onAction defined in FXML
  public void launch()
  {
    message.setText("Date: " + date + " -> " + prefix + tower.readyToTakeoff()
        + happyFlight + theVeryEnd);
  }
}
