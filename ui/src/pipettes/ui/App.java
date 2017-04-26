package pipettes.ui;

import pipettes.core.Container;
import pipettes.core.Device;
import pipettes.core.Procedure;
import pipettes.ui.mainwindow.MainWindowView;

import com.airhacks.afterburner.injection.Injector;

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
    //Library<Device> deviceLibrary = new Library<Device>("devices.xml");
    //Library<Container> containerLibrary = new Library<Container>("containers.xml");
    
    // Properties of any type can be easily injected.
    Map<Object, Object> customProperties = new HashMap<>();

    Injector.setConfigurationSource(customProperties::get);
    
    //customProperties.put("deviceLibrary", deviceLibrary);
    //customProperties.put("containerLibrary", containerLibrary);
    customProperties.put("deviceLibrary", null);
    customProperties.put("containerLibrary", null);
    
    ObjectProperty<Process> activeProcess = new SimpleObjectProperty<Process>();
    customProperties.put("activeProcess", activeProcess);
    
    ObjectProperty<Device> activeDevice = new SimpleObjectProperty<Device>();
    customProperties.put("activeDevice", activeDevice);

    ObjectProperty<Container> activeContainer = new SimpleObjectProperty<Container>();
    customProperties.put("activeContainer", activeContainer);

    ObjectProperty<Procedure> activeProcedure = new SimpleObjectProperty<Procedure>();
    customProperties.put("activeProcedure", activeProcedure);

    // TODO: Load application configuration/preferences
    
    MainWindowView appView = new MainWindowView();
    Scene scene = new Scene(appView.getView());
    stage.setTitle("Pipette Process Editor");
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
