package pipettes.ui;

import pipettes.core.Container;
import pipettes.core.CylindricalGCodeDevice;
import pipettes.core.Device;
import pipettes.core.Procedure;
import pipettes.ui.dashboard.DashboardView;

import com.airhacks.afterburner.injection.Injector;

import java.time.LocalDate;
import java.time.Month;
import java.util.HashMap;
import java.util.Map;

import javafx.application.Application;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application
{
  @Override
  public void start(Stage stage) throws Exception
  {
    // Properties of any type can be easily injected.
    LocalDate date = LocalDate.of(4242, Month.JULY, 21);
    Map<Object, Object> customProperties = new HashMap<>();
    customProperties.put("date", date);
    
    // Any function which accepts an Object as key and returns and return an
    // Object as result can be used as source.
    Injector.setConfigurationSource(customProperties::get);

    // System properties will override custom properties.
    System.setProperty("happyFlight", " Enjoy the flight!");
    
    // Properties from "configuration.properties" are applied only if no
    // custom properties or system properties apply

    ObjectProperty<Process> activeProcess = new SimpleObjectProperty<Process>();
    customProperties.put("activeProcess", activeProcess);
    
    ObjectProperty<Device> activeDevice = new SimpleObjectProperty<Device>();
    customProperties.put("activeDevice", activeDevice);

    ObjectProperty<Container> activeContainer = new SimpleObjectProperty<Container>();
    customProperties.put("activeContainer", activeContainer);

    ObjectProperty<Procedure> activeProcedure = new SimpleObjectProperty<Procedure>();
    customProperties.put("activeProcedure", activeProcedure);

    // TODO: Load application configuration/preferences
    
    // TODO: Load device profiles
    
    // TODO: Load container library
    
    // TODO: Remove this example when
    CylindricalGCodeDevice exampleDevice = new CylindricalGCodeDevice();
    exampleDevice.setName("SeeMeCNC Rostock MAX v2");
    exampleDevice.setExtrudePerVolume(20.0);
    exampleDevice.setDispenseExtrudeRatio(1.01);
    exampleDevice.setRadius(140.0);
    exampleDevice.setMinimumZ(0.0);
    exampleDevice.setMaximumZ(375.0);
    
    activeDevice.set(exampleDevice);
    
    DashboardView appView = new DashboardView();
    Scene scene = new Scene(appView.getView());
    stage.setTitle("Pipette Process Editor");
    final String uri = getClass().getResource("app.css").toExternalForm();
    scene.getStylesheets().add(uri);
    stage.setScene(scene);
    stage.show();
  }

  @Override
  public void stop() throws Exception
  {
    Injector.forgetAll();
  }

  public static void main(String[] args)
  {
    launch(args);
  }
}
