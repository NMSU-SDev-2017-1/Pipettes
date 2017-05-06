package pipettes.ui;

import pipettes.core.ChangeTipProcedure;
import pipettes.core.Container;
import pipettes.core.ContainerShape;
import pipettes.core.CylindricalGCodeDevice;
import pipettes.core.Device;
import pipettes.core.DispenseProcedure;
import pipettes.core.Library;
import pipettes.core.MixProcedure;
import pipettes.core.NameConflictException;
import pipettes.core.Procedure;
import pipettes.core.Process;
import pipettes.core.RectangularGCodeDevice;
import pipettes.ui.mainwindow.MainWindowView;

import com.airhacks.afterburner.injection.Injector;

import java.util.HashMap;
import java.util.Map;

import javafx.application.Application;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Point3D;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application
{
  @Override
  public void start(Stage stage) throws Exception
  {
    // Library<Device> deviceLibrary = new Library<Device>("devices.xml");
    // Library<Container> containerLibrary = new Library<Container>("containers.xml");
    Library<Device> deviceLibrary = getExampleDeviceLibrary();
    Library<Container> containerLibrary = getExampleContainerLibrary();
    
    // Properties of any type can be easily injected.
    Map<Object, Object> customProperties = new HashMap<>();

    Injector.setConfigurationSource(customProperties::get);

    customProperties.put("scene", deviceLibrary);
    
    customProperties.put("deviceLibrary", deviceLibrary);
    customProperties.put("containerLibrary", containerLibrary);

    ObjectProperty<Process> activeProcess = new SimpleObjectProperty<Process>();
    activeProcess.set(getExampleProcess());
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
    try
    {
      launch(args);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }

  private Library<Device> getExampleDeviceLibrary()
  {
    CylindricalGCodeDevice exampleDevice1 = new CylindricalGCodeDevice();

    exampleDevice1.setName("SeeMeCNC Rostock MAX v2");
    exampleDevice1.setExtrudePerVolume(20.0);
    exampleDevice1.setDispenseExtrudeRatio(1.01);
    exampleDevice1.setRadius(140.0);
    exampleDevice1.setMinimumZ(0.0);
    exampleDevice1.setMaximumZ(375.0);

    RectangularGCodeDevice exampleDevice2 = new RectangularGCodeDevice();
    
    exampleDevice2.setName("MakerBot Replicator");
    exampleDevice2.setExtrudePerVolume(20.0);
    exampleDevice2.setDispenseExtrudeRatio(1.01);
    exampleDevice2.setHomePosition(new Point3D(50.0, 75.0, 130.0));
    exampleDevice2.setMinimumExtent(new Point3D(-110.0, -75.0, 0.0));
    exampleDevice2.setMaximumExtent(new Point3D(110.0, 75.0, 130.0));

    Library<Device> library = new Library<Device>("Device");

    library.getItems().add(exampleDevice1);
    library.getItems().add(exampleDevice2);

    return library;
  }
  
  private Library<Container> getExampleContainerLibrary()
  {
    Container exampleContainer1 = new Container();
    Container exampleContainer2 = new Container();
    Container exampleContainer3 = new Container();
    
    try
    {
      exampleContainer1.setLocalName("Beaker 1");
      exampleContainer1.setLocalPosition(new Point3D(-70.0, -50.0, 0.0));
      exampleContainer1.setSize(new Point3D(10.0, 10.0, 60.0));
      exampleContainer1.setShape(ContainerShape.Cylindrical);
      exampleContainer1.setDrawHeightAboveBottom(4.0);
      exampleContainer1.setDispenseHeightAboveTop(5.0);
      exampleContainer1.setClearanceHeightAboveTop(5.0);
      
      exampleContainer2.setLocalName("Beaker 2");
      exampleContainer2.setLocalPosition(new Point3D(60.0, 40.0, 0.0));
      exampleContainer2.setSize(new Point3D(10.0, 10.0, 40.0));
      exampleContainer2.setShape(ContainerShape.Cylindrical);
      exampleContainer2.setDrawHeightAboveBottom(5.0);
      exampleContainer2.setDispenseHeightAboveTop(10.0);
      exampleContainer2.setClearanceHeightAboveTop(10.0);

      exampleContainer3.setLocalName("Beaker 3");
      exampleContainer3.setLocalPosition(new Point3D(-90.0, 90.0, 0.0));
      exampleContainer3.setSize(new Point3D(10.0, 10.0, 40.0));
      exampleContainer3.setShape(ContainerShape.Rectangular);
      exampleContainer3.setDrawHeightAboveBottom(6.0);
      exampleContainer3.setDispenseHeightAboveTop(7.0);
      exampleContainer3.setClearanceHeightAboveTop(7.0);
    }
    catch (NameConflictException e1)
    {
      e1.printStackTrace();
    }
    
    Library<Container> library = new Library<Container>("Container");

    library.getItems().add(exampleContainer1);
    library.getItems().add(exampleContainer2);

    return library;
  }
  
  private Process getExampleProcess()
  {
    Process process = new Process();

    Container source = new Container();
    Container destination = new Container();
    Container sample = new Container();
    
    try
    {
      source.setLocalName("Beaker 1");
      source.setLocalPosition(new Point3D(-70.0, -50.0, 0.0));
      source.setSize(new Point3D(10.0, 10.0, 60.0));
      source.setShape(ContainerShape.Cylindrical);
      source.setDrawHeightAboveBottom(4.0);
      source.setDispenseHeightAboveTop(5.0);
      source.setClearanceHeightAboveTop(5.0);
      
      destination.setLocalName("Beaker 2");
      destination.setLocalPosition(new Point3D(60.0, 40.0, 0.0));
      destination.setSize(new Point3D(10.0, 10.0, 40.0));
      destination.setShape(ContainerShape.Cylindrical);
      destination.setDrawHeightAboveBottom(5.0);
      destination.setDispenseHeightAboveTop(10.0);
      destination.setClearanceHeightAboveTop(10.0);

      sample.setLocalName("Beaker 3");
      sample.setLocalPosition(new Point3D(-90.0, 90.0, 0.0));
      sample.setSize(new Point3D(10.0, 10.0, 40.0));
      sample.setShape(ContainerShape.Rectangular);
      sample.setDrawHeightAboveBottom(6.0);
      sample.setDispenseHeightAboveTop(7.0);
      sample.setClearanceHeightAboveTop(7.0);
    }
    catch (NameConflictException e1)
    {
      e1.printStackTrace();
    }
    
    DispenseProcedure procedure1 = new DispenseProcedure();
    procedure1.setSource(source);
    procedure1.setDestination(destination);
    procedure1.setVolume(9.5);
    process.addProcedure(procedure1);
    
    DispenseProcedure procedure2 = new DispenseProcedure();
    procedure2.setSource(source);
    procedure2.setDestination(destination);
    procedure2.setVolume(11.0);
    process.addProcedure(procedure2);

    MixProcedure procedure5 = new MixProcedure();
    procedure5.setDestination(destination);
    procedure5.setVolume(2.0);
    process.addProcedure(procedure5);
    
    DispenseProcedure procedure3 = new DispenseProcedure();
    procedure3.setSource(source);
    procedure3.setDestination(destination);
    procedure3.setVolume(5.25);
    process.addProcedure(procedure3);

    ChangeTipProcedure procedure6 = new ChangeTipProcedure();
    procedure6.setNewTip(destination);
    procedure6.setTipDisposal(source);
    process.addProcedure(procedure6);
    
    DispenseProcedure procedure4 = new DispenseProcedure();
    procedure4.setSource(destination);
    procedure4.setDestination(sample);
    procedure4.setVolume(5.0);
    process.addProcedure(procedure4);
    
    return process;
  }
}
