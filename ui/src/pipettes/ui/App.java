package pipettes.ui;

import pipettes.core.Container;
import pipettes.core.Device;
import pipettes.core.Library;
import pipettes.core.Procedure;
import pipettes.core.Process;
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
  private Library<Device> deviceLibrary;
  private Library<Container> containerLibrary;
  
  @Override
  public void start(Stage stage) throws Exception
  {
    deviceLibrary = new Library<Device>("devices.xml");
    containerLibrary = new Library<Container>("containers.xml");
    
    Map<Object, Object> customProperties = new HashMap<>();

    Injector.setConfigurationSource(customProperties::get);

    customProperties.put("scene", deviceLibrary);
    
    customProperties.put("deviceLibrary", deviceLibrary);
    customProperties.put("containerLibrary", containerLibrary);

    ObjectProperty<Process> activeProcess = new SimpleObjectProperty<Process>();
    customProperties.put("activeProcess", activeProcess);

    ObjectProperty<Device> activeDevice = new SimpleObjectProperty<Device>();
    customProperties.put("activeDevice", activeDevice);

    ObjectProperty<Container> activeLibraryContainer = new SimpleObjectProperty<Container>();
    customProperties.put("activeLibraryContainer", activeLibraryContainer);
    
    ObjectProperty<Container> activeContainer = new SimpleObjectProperty<Container>();
    customProperties.put("activeContainer", activeContainer);

    ObjectProperty<Procedure> activeProcedure = new SimpleObjectProperty<Procedure>();
    customProperties.put("activeProcedure", activeProcedure);

    activeProcess.set(new Process());
    
    MainWindowView appView = new MainWindowView();
    Scene scene = new Scene(appView.getView());
    stage.setScene(scene);
    stage.show();
  }

  @Override
  public void stop() throws Exception
  {
    deviceLibrary.save();
    containerLibrary.save();
    
    Injector.forgetAll();
  }

  public static void main(String[] args)
  {
    try
    {
      launch(args);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }
}
