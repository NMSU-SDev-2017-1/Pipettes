package pipettes.ui.mainwindow;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import javafx.util.converter.NumberStringConverter;

import javax.inject.Inject;
import javax.xml.bind.JAXBException;

import pipettes.core.ChangeTipProcedure;
import pipettes.core.Common;
import pipettes.core.Container;
import pipettes.core.ContainerShape;
import pipettes.core.CylindricalGCodeDevice;
import pipettes.core.Device;
import pipettes.core.DispenseProcedure;
import pipettes.core.Library;
import pipettes.core.MixProcedure;
import pipettes.core.PositioningException;
import pipettes.core.Procedure;
import pipettes.core.Process;
import pipettes.core.Processor;
import pipettes.core.RectangularGCodeDevice;
import pipettes.ui.ContainerListItem;
import pipettes.ui.ContainerTreeItem;
import pipettes.ui.DeviceListItem;

public class MainWindowPresenter implements Initializable
{
  private static String title = "Pipette Process Editor";

  private static FileChooser.ExtensionFilter extensionProcess = new FileChooser.ExtensionFilter(
      "Pipette Process File", "*.ppxml");
  private static FileChooser.ExtensionFilter extensionGCode = new FileChooser.ExtensionFilter(
      "G-Code File", "*.gcode");
  private static FileChooser.ExtensionFilter extensionAll = new FileChooser.ExtensionFilter(
      "All Files", "*.*");

  @FXML
  private VBox root;

  @FXML
  private ListView<DeviceListItem> deviceLibraryListView;

  @FXML
  private ListView<ContainerListItem> containerLibraryListView;

  @FXML
  private TreeView<ContainerTreeItem> containersTreeView;

  @FXML
  private TableView<Procedure> proceduresTableView;

  @FXML
  private TableColumn<Procedure, String> proceduresTypeTableColumn;

  @FXML
  private ChoiceBox<ContainerShape> containerShapeChoiceBox;

  // Beginning of FXML
  @FXML
  private TextArea deviceRectangularFooterTextArea;

  @FXML
  private MenuItem fileExitMenuItem;

  @FXML
  private TextField containerNameTextField;

  @FXML
  private TextField containerDrawHeightTextField;

  @FXML
  private MenuItem fileExportMenuItem;

  @FXML
  private TextField deviceRectangularMinimumYTextField;

  @FXML
  private GridPane procedureMixGridPane;

  @FXML
  private MenuItem fileSaveAsMenuItem;

  @FXML
  private GridPane procedureChangeTipGridPane;

  @FXML
  private TextField deviceRectangularMaximumZTextField;

  @FXML
  private TextField deviceRectangularDispenseExtrudeRatioTextField;

  @FXML
  private TextField containerPositionYTextField;

  @FXML
  private Button containersRemoveFromLibraryButton;

  @FXML
  private TextField procedureMixVolumeTextField;

  @FXML
  private MenuItem fileSaveMenuItem;

  @FXML
  private TextField deviceRectangularHomeZTextField;

  @FXML
  private Button containersRemoveButton;

  @FXML
  private TextField deviceCylindricalRadiusTextField;

  @FXML
  private MenuItem fileNewMenuItem;

  @FXML
  private MenuButton procedureTipChangeDisposalMenuButton;

  @FXML
  private Button procedureMoveUpButton;

  @FXML
  private TextField deviceRectangularHomeXTextField;

  @FXML
  private TextField containerPositionZTextField;

  @FXML
  private TextField containerSizeXTextField;

  @FXML
  private TextField deviceRectangularMinimumXTextField;

  @FXML
  private TextField procedureDispenseVolumeTextField;

  @FXML
  private Button procedureRemoveButton;

  @FXML
  private MenuButton procedureDispenseSourceMenuButton;

  @FXML
  private TextField containerSizeYTextField;

  @FXML
  private TextField containerDispenseHeightTextField;

  @FXML
  private TextField deviceRectangularMaximumYTextField;

  @FXML
  private GridPane deviceRectangularGridPane;

  @FXML
  private MenuButton procedureMixContainerMenuButton;

  @FXML
  private TextField deviceRectangularNameTextField;

  @FXML
  private TextField deviceRectangularMaximumXTextField;

  @FXML
  private TextField deviceRectangularHomeYTextField;

  @FXML
  private TextField deviceCylindricalDispenseExtrudeRatioTextField;

  @FXML
  private MenuButton procedureTipChangeNewContainerMenuButton;

  @FXML
  private MenuItem procedureAddChangeTipMenuItem;

  @FXML
  private GridPane containerGridPane;

  @FXML
  private TextField deviceRectangularExtrudeVolumeTextField;

  @FXML
  private Button containersCopyToLibraryButton;

  @FXML
  private TextField deviceCylindricalMinimumZTextField;

  @FXML
  private TextField containerSizeZTextField;

  @FXML
  private Button containersAddButton;

  @FXML
  private MenuButton procedureDispenseDestinationMenuButton;

  @FXML
  private MenuItem procedureAddMixMenuItem;

  @FXML
  private MenuItem deviceAddRectangularMenuItem;

  @FXML
  private Button deviceRemoveButton;

  @FXML
  private MenuItem fileOpenMenuItem;

  @FXML
  private TextField deviceCylindricalNameTextField;

  @FXML
  private TextField deviceRectangularMinimumZTextField;

  @FXML
  private GridPane deviceCylindricalGridPane;

  @FXML
  private TextField containerClearanceHeightTextField;

  @FXML
  private TextArea deviceCylindricalHeaderTextArea;

  @FXML
  private MenuItem deviceAddCylindricalMenuItem;

  @FXML
  private Button procedureMoveDownButton;

  @FXML
  private GridPane procedureDispenseGridPane;

  @FXML
  private TextArea deviceRectangularHeaderTextArea;

  @FXML
  private Button containersCopyToProcessButton;

  @FXML
  private MenuItem procedureAddDispenseMenuItem;

  @FXML
  private TextField deviceCylindricalMaximumZTextField;

  @FXML
  private TextArea deviceCylindricalFooterTextArea;

  @FXML
  private TextField deviceCylindricalExtrudeVolumeTextField;

  @FXML
  private MenuItem helpAboutMenuItem;

  @FXML
  private TextField containerPositionXTextField;

  // End of FXML

  @Inject
  private Library<Device> deviceLibrary;

  @Inject
  private Library<Container> containerLibrary;

  @Inject
  private ObjectProperty<Process> activeProcess;

  @Inject
  private ObjectProperty<Device> activeDevice;

  @Inject
  private ObjectProperty<Container> activeContainer;

  @Inject
  private ObjectProperty<Procedure> activeProcedure;

  private RectangularGCodeDevice lastActiveDeviceRectangular;
  private CylindricalGCodeDevice lastActiveDeviceCylindrical;
  private Container lastActiveContainer;
  private DispenseProcedure lastActiveProcedureDispense;
  private MixProcedure lastActiveProcedureMix;
  private ChangeTipProcedure lastActiveProcedureChangeTip;

  private NumberStringConverter positionPropertyConverter = new NumberStringConverter();

  private Scene getScene()
  {
    return root.getScene();
  }

  private Window getWindow()
  {
    return getScene().getWindow();
  }

  private Stage getStage()
  {
    return (Stage) getWindow();
  }

  @Override
  public void initialize(URL arg0, ResourceBundle arg1)
  {
    root.sceneProperty().addListener(new ChangeListener<Scene>()
    {
      @Override
      public void changed(ObservableValue<? extends Scene> observableValue,
          Scene oldValue, Scene newValue)
      {
        onScene();
      }
    });

    containerShapeChoiceBox.setItems(FXCollections
        .observableArrayList(ContainerShape.values()));

    activeProcess.addListener(new ChangeListener<Process>()
    {
      @Override
      public void changed(ObservableValue<? extends Process> observableValue,
          Process oldValue, Process newValue)
      {
        rebindActiveProcess();
      }
    });

    activeDevice.addListener(new ChangeListener<Device>()
    {
      @Override
      public void changed(ObservableValue<? extends Device> observableValue,
          Device oldValue, Device newValue)
      {
        rebindActiveDeviceControls();
      }
    });

    activeContainer.addListener(new ChangeListener<Container>()
    {
      @Override
      public void changed(ObservableValue<? extends Container> observableValue,
          Container oldValue, Container newValue)
      {
        rebindActiveContainerControls();
      }
    });

    activeProcedure.addListener(new ChangeListener<Procedure>()
    {
      @Override
      public void changed(ObservableValue<? extends Procedure> observableValue,
          Procedure oldValue, Procedure newValue)
      {
        rebindActiveProcedureControls();
      }
    });

    for (Device device : deviceLibrary.getItems().values())
    {
      deviceLibraryListView.getItems().add(new DeviceListItem(device));
    }

    for (Container container : containerLibrary.getItems().values())
    {
      containerLibraryListView.getItems().add(new ContainerListItem(container));
    }

    deviceLibraryListView.getSelectionModel().selectedItemProperty()
        .addListener(new ChangeListener<DeviceListItem>()
        {
          @Override
          public void changed(
              ObservableValue<? extends DeviceListItem> observable,
              DeviceListItem oldValue, DeviceListItem newValue)
          {
            activeDevice.set(newValue.getDevice());
          }
        });

    containerLibraryListView.getSelectionModel().selectedItemProperty()
        .addListener(new ChangeListener<ContainerListItem>()
        {
          @Override
          public void changed(
              ObservableValue<? extends ContainerListItem> observable,
              ContainerListItem oldValue, ContainerListItem newValue)
          {
            activeContainer.set(newValue.getContainer());
          }
        });

    proceduresTableView.getSelectionModel().selectedItemProperty()
        .addListener(new ChangeListener<Procedure>()
        {
          @Override
          public void changed(ObservableValue<? extends Procedure> observable,
              Procedure oldValue, Procedure newValue)
          {
            activeProcedure.set(newValue);
          }
        });

    proceduresTypeTableColumn
        .setCellValueFactory(new Callback<CellDataFeatures<Procedure, String>, ObservableValue<String>>()
        {
          public ObservableValue<String> call(
              CellDataFeatures<Procedure, String> value)
          {
            return new SimpleStringProperty(value.getValue().getName());
          }
        });
    
    rebindActiveDeviceControls();
    rebindActiveContainerControls();
    intializeProceduresControls();

    rebindActiveProcess();
  }
  
  private void intializeProceduresControls()
  {
    rebindActiveProcedureControls();
  }

  private Device getActiveDevice()
  {
    return activeDevice.get();
  }

  private Process getActiveProcess()
  {
    return activeProcess.get();
  }
  
  private ObservableList<Procedure> getProcedures()
  {
    return getActiveProcess().getProcedures();
  }

  private void rebindActiveProcess()
  {
    proceduresTableView.setItems(getProcedures());
  }

  private void rebindActiveDeviceNoneControls()
  {
    deviceRectangularGridPane.setVisible(false);
    deviceCylindricalGridPane.setVisible(false);
  }

  private void rebindActiveDeviceRectangularControls(
      RectangularGCodeDevice device)
  {
    RectangularGCodeDevice last = lastActiveDeviceRectangular;

    if (device != last)
    {
      if (last != null)
      {
        deviceRectangularNameTextField.textProperty().unbindBidirectional(
            last.nameProperty());

        deviceRectangularHomeXTextField.textProperty().unbindBidirectional(
            last.homePositionXProperty());

        deviceRectangularHomeYTextField.textProperty().unbindBidirectional(
            last.homePositionXProperty());

        deviceRectangularHomeZTextField.textProperty().unbindBidirectional(
            last.homePositionXProperty());

        deviceRectangularMinimumXTextField.textProperty().unbindBidirectional(
            last.minimumExtentXProperty());

        deviceRectangularMinimumYTextField.textProperty().unbindBidirectional(
            last.minimumExtentYProperty());

        deviceRectangularMinimumZTextField.textProperty().unbindBidirectional(
            last.minimumExtentZProperty());

        deviceRectangularMaximumXTextField.textProperty().unbindBidirectional(
            last.maximumExtentXProperty());

        deviceRectangularMaximumYTextField.textProperty().unbindBidirectional(
            last.maximumExtentYProperty());

        deviceRectangularMaximumZTextField.textProperty().unbindBidirectional(
            last.maximumExtentZProperty());

        deviceRectangularExtrudeVolumeTextField.textProperty()
            .unbindBidirectional(last.extrudePerVolumeProperty());

        deviceRectangularDispenseExtrudeRatioTextField.textProperty()
            .unbindBidirectional(last.dispenseExtrudeRatioProperty());

        deviceRectangularHeaderTextArea.textProperty().unbindBidirectional(
            last.headerProperty());

        deviceRectangularFooterTextArea.textProperty().unbindBidirectional(
            last.footerProperty());
      }

      deviceRectangularNameTextField.textProperty().bindBidirectional(
          device.nameProperty());

      deviceRectangularHomeXTextField.textProperty().bindBidirectional(
          device.homePositionXProperty(), positionPropertyConverter);

      deviceRectangularHomeYTextField.textProperty().bindBidirectional(
          device.homePositionYProperty(), positionPropertyConverter);

      deviceRectangularHomeZTextField.textProperty().bindBidirectional(
          device.homePositionZProperty(), positionPropertyConverter);

      deviceRectangularMinimumXTextField.textProperty().bindBidirectional(
          device.minimumExtentXProperty(), positionPropertyConverter);

      deviceRectangularMinimumYTextField.textProperty().bindBidirectional(
          device.minimumExtentYProperty(), positionPropertyConverter);

      deviceRectangularMinimumZTextField.textProperty().bindBidirectional(
          device.minimumExtentZProperty(), positionPropertyConverter);

      deviceRectangularMaximumXTextField.textProperty().bindBidirectional(
          device.maximumExtentXProperty(), positionPropertyConverter);

      deviceRectangularMaximumYTextField.textProperty().bindBidirectional(
          device.maximumExtentYProperty(), positionPropertyConverter);

      deviceRectangularMaximumZTextField.textProperty().bindBidirectional(
          device.maximumExtentZProperty(), positionPropertyConverter);

      deviceRectangularExtrudeVolumeTextField.textProperty().bindBidirectional(
          device.extrudePerVolumeProperty(), positionPropertyConverter);

      deviceRectangularDispenseExtrudeRatioTextField.textProperty()
          .bindBidirectional(device.dispenseExtrudeRatioProperty(),
              positionPropertyConverter);

      deviceRectangularHeaderTextArea.textProperty().bindBidirectional(
          device.headerProperty());

      deviceRectangularFooterTextArea.textProperty().bindBidirectional(
          device.footerProperty());

      lastActiveDeviceRectangular = device;
    }

    deviceRectangularGridPane.setVisible(true);
    deviceCylindricalGridPane.setVisible(false);
  }

  private void rebindActiveDeviceCylindricalControls(
      CylindricalGCodeDevice device)
  {
    CylindricalGCodeDevice last = lastActiveDeviceCylindrical;

    if (device != last)
    {
      if (last != null)
      {
        deviceCylindricalNameTextField.textProperty().unbindBidirectional(
            last.nameProperty());

        deviceCylindricalRadiusTextField.textProperty().unbindBidirectional(
            last.radiusProperty());

        deviceCylindricalMinimumZTextField.textProperty().unbindBidirectional(
            last.minimumZProperty());

        deviceCylindricalMaximumZTextField.textProperty().unbindBidirectional(
            last.maximumZProperty());

        deviceCylindricalExtrudeVolumeTextField.textProperty()
            .unbindBidirectional(last.extrudePerVolumeProperty());

        deviceCylindricalDispenseExtrudeRatioTextField.textProperty()
            .unbindBidirectional(last.dispenseExtrudeRatioProperty());

        deviceCylindricalHeaderTextArea.textProperty().unbindBidirectional(
            last.headerProperty());

        deviceCylindricalFooterTextArea.textProperty().unbindBidirectional(
            last.footerProperty());
      }

      deviceCylindricalNameTextField.textProperty().bindBidirectional(
          device.nameProperty());

      deviceCylindricalRadiusTextField.textProperty().bindBidirectional(
          device.radiusProperty(), positionPropertyConverter);

      deviceCylindricalMinimumZTextField.textProperty().bindBidirectional(
          device.minimumZProperty(), positionPropertyConverter);

      deviceCylindricalMaximumZTextField.textProperty().bindBidirectional(
          device.maximumZProperty(), positionPropertyConverter);

      deviceCylindricalExtrudeVolumeTextField.textProperty().bindBidirectional(
          device.extrudePerVolumeProperty(), positionPropertyConverter);

      deviceCylindricalDispenseExtrudeRatioTextField.textProperty()
          .bindBidirectional(device.dispenseExtrudeRatioProperty(),
              positionPropertyConverter);

      deviceCylindricalHeaderTextArea.textProperty().bindBidirectional(
          device.headerProperty());

      deviceCylindricalFooterTextArea.textProperty().bindBidirectional(
          device.footerProperty());

      lastActiveDeviceCylindrical = device;
    }

    deviceRectangularGridPane.setVisible(false);
    deviceCylindricalGridPane.setVisible(true);
  }

  private void rebindActiveDeviceControls()
  {
    Device device = activeDevice.get();

    if (device == null)
    {
      rebindActiveDeviceNoneControls();
    }
    else if (device instanceof RectangularGCodeDevice)
    {
      rebindActiveDeviceRectangularControls((RectangularGCodeDevice) device);
    }
    else if (device instanceof CylindricalGCodeDevice)
    {
      rebindActiveDeviceCylindricalControls((CylindricalGCodeDevice) device);
    }
  }

  private void rebindActiveContainerControls()
  {
    Container container = activeContainer.get();
    Container last = lastActiveContainer;

    if (container == null)
    {
      containerGridPane.setVisible(false);
    }
    else
    {
      if (container != last)
      {
        if (last != null)
        {
          containerNameTextField.textProperty().unbindBidirectional(
              last.localNameProperty());

          containerShapeChoiceBox.valueProperty().unbindBidirectional(
              last.shapeProperty());

          containerPositionXTextField.textProperty().unbindBidirectional(
              last.localPositionXProperty());

          containerPositionYTextField.textProperty().unbindBidirectional(
              last.localPositionYProperty());

          containerPositionZTextField.textProperty().unbindBidirectional(
              last.localPositionZProperty());

          containerSizeXTextField.textProperty().unbindBidirectional(
              last.sizeXProperty());

          containerSizeYTextField.textProperty().unbindBidirectional(
              last.sizeYProperty());

          containerSizeZTextField.textProperty().unbindBidirectional(
              last.sizeZProperty());

          containerDrawHeightTextField.textProperty().unbindBidirectional(
              last.drawHeightAboveBottomProperty());

          containerDispenseHeightTextField.textProperty().unbindBidirectional(
              last.dispenseHeightAboveTopProperty());

          containerClearanceHeightTextField.textProperty().unbindBidirectional(
              last.clearanceHeightAboveTopProperty());
        }

        containerNameTextField.textProperty().bindBidirectional(
            container.localNameProperty());

        containerShapeChoiceBox.valueProperty().bindBidirectional(
            container.shapeProperty());

        containerPositionXTextField.textProperty().bindBidirectional(
            container.localPositionXProperty(), positionPropertyConverter);

        containerPositionYTextField.textProperty().bindBidirectional(
            container.localPositionYProperty(), positionPropertyConverter);

        containerPositionZTextField.textProperty().bindBidirectional(
            container.localPositionZProperty(), positionPropertyConverter);

        containerSizeXTextField.textProperty().bindBidirectional(
            container.sizeXProperty(), positionPropertyConverter);

        containerSizeYTextField.textProperty().bindBidirectional(
            container.sizeYProperty(), positionPropertyConverter);

        containerSizeZTextField.textProperty().bindBidirectional(
            container.sizeZProperty(), positionPropertyConverter);

        containerDrawHeightTextField.textProperty().bindBidirectional(
            container.drawHeightAboveBottomProperty(),
            positionPropertyConverter);

        containerDispenseHeightTextField.textProperty().bindBidirectional(
            container.dispenseHeightAboveTopProperty(),
            positionPropertyConverter);

        containerClearanceHeightTextField.textProperty().bindBidirectional(
            container.clearanceHeightAboveTopProperty(),
            positionPropertyConverter);

        lastActiveContainer = container;
      }

      containerGridPane.setVisible(true);
    }
  }

  private void rebindActiveProcedureNoneControls()
  {
    procedureDispenseGridPane.setVisible(false);
    procedureMixGridPane.setVisible(false);
    procedureChangeTipGridPane.setVisible(false);
  }

  private void rebindActiveProcedureDispenseControls(DispenseProcedure procedure)
  {
    DispenseProcedure last = lastActiveProcedureDispense;

    if (procedure != last)
    {
      if (last != null)
      {
        procedureDispenseSourceMenuButton.textProperty().unbindBidirectional(
            last.sourceProperty().getValue().localNameProperty());

        procedureDispenseDestinationMenuButton.textProperty()
            .unbindBidirectional(
                last.destinationProperty().getValue().localNameProperty());

        procedureDispenseVolumeTextField.textProperty().unbindBidirectional(
            last.volumeProperty());
      }

      procedureDispenseSourceMenuButton.textProperty().bindBidirectional(
          procedure.sourceProperty().getValue().localNameProperty());

      procedureDispenseDestinationMenuButton.textProperty().bindBidirectional(
          procedure.destinationProperty().getValue().localNameProperty());

      procedureDispenseVolumeTextField.textProperty().bindBidirectional(
          procedure.volumeProperty(), positionPropertyConverter);

      lastActiveProcedureDispense = procedure;
    }

    procedureDispenseGridPane.setVisible(true);
    procedureMixGridPane.setVisible(false);
    procedureChangeTipGridPane.setVisible(false);
  }

  private void rebindActiveProcedureMixControls(MixProcedure procedure)
  {
    MixProcedure last = lastActiveProcedureMix;

    if (procedure != last)
    {
      if (last != null)
      {
        procedureMixContainerMenuButton.textProperty().unbindBidirectional(
            last.destinationProperty().getValue().localNameProperty());

        procedureMixVolumeTextField.textProperty().unbindBidirectional(
            last.volumeProperty());
      }

      procedureMixContainerMenuButton.textProperty().bindBidirectional(
          procedure.destinationProperty().getValue().localNameProperty());

      procedureMixVolumeTextField.textProperty().bindBidirectional(
          procedure.volumeProperty(), positionPropertyConverter);

      lastActiveProcedureMix = procedure;
    }

    procedureDispenseGridPane.setVisible(false);
    procedureMixGridPane.setVisible(true);
    procedureChangeTipGridPane.setVisible(false);
  }

  private void rebindActiveProcedureChangeTipControls(
      ChangeTipProcedure procedure)
  {
    ChangeTipProcedure last = lastActiveProcedureChangeTip;

    if (procedure != last)
    {
      if (last != null)
      {
        procedureTipChangeNewContainerMenuButton.textProperty()
            .unbindBidirectional(
                last.newTipProperty().getValue().localNameProperty());

        procedureTipChangeDisposalMenuButton.textProperty()
            .unbindBidirectional(
                last.tipDisposalProperty().getValue().localNameProperty());
      }

      procedureTipChangeNewContainerMenuButton.textProperty()
          .bindBidirectional(
              procedure.newTipProperty().getValue().localNameProperty());

      procedureTipChangeDisposalMenuButton.textProperty().bindBidirectional(
          procedure.tipDisposalProperty().getValue().localNameProperty());

      lastActiveProcedureChangeTip = procedure;
    }

    procedureDispenseGridPane.setVisible(false);
    procedureMixGridPane.setVisible(false);
    procedureChangeTipGridPane.setVisible(true);
  }

  private void rebindActiveProcedureControls()
  {
    Procedure procedure = activeProcedure.get();

    if (procedure == null)
    {
      rebindActiveProcedureNoneControls();
    }
    else if (procedure instanceof DispenseProcedure)
    {
      rebindActiveProcedureDispenseControls((DispenseProcedure) procedure);
    }
    else if (procedure instanceof MixProcedure)
    {
      rebindActiveProcedureMixControls((MixProcedure) procedure);
    }
    else if (procedure instanceof ChangeTipProcedure)
    {
      rebindActiveProcedureChangeTipControls((ChangeTipProcedure) procedure);
    }
  }

  private void onScene()
  {
    getScene().windowProperty().addListener(new ChangeListener<Window>()
    {
      @Override
      public void changed(ObservableValue<? extends Window> observableValue,
          Window oldValue, Window newValue)
      {
        onWindow();
      }
    });
  }

  private void onWindow()
  {
    getStage().setTitle(title);

    getStage().setOnCloseRequest(windowEvent ->
    {
      if (allowClose())
      {
        getStage().close();
      }
      else
      {
        windowEvent.consume();
      }
    });
  }

  public void onNew()
  {
    activeProcess.set(new Process());
  }

  public void onOpen()
  {
    FileChooser fileChooser = new FileChooser();

    fileChooser.setTitle("Open");
    fileChooser.getExtensionFilters().addAll(extensionProcess, extensionAll);

    File selectedFile = fileChooser.showOpenDialog(getWindow());

    if (selectedFile != null)
    {
      try
      {
        Process process = Process.open(selectedFile);
        activeProcess.set(process);
      }
      catch (JAXBException e)
      {
        // TODO: Show error message
        e.printStackTrace();
      }
    }
  }

  public void onSave()
  {
    if (!getActiveProcess().hasSetFileName())
    {
      onSaveAs();
    }
    else
    {
      try
      {
        getActiveProcess().save();
      }
      catch (JAXBException e)
      {
        // TODO: Show error message
        e.printStackTrace();
      }
    }
  }

  public void onSaveAs()
  {
    FileChooser fileChooser = new FileChooser();

    fileChooser.setTitle("Save As");
    fileChooser.getExtensionFilters().addAll(extensionProcess, extensionAll);
    fileChooser.setInitialFileName(activeProcess.get().getFileName());

    File selectedFile = fileChooser.showSaveDialog(getWindow());

    if (selectedFile != null)
    {
      try
      {
        getActiveProcess().saveAs(selectedFile);
      }
      catch (JAXBException e)
      {
        // TODO: Show error message
        e.printStackTrace();
      }
    }
  }

  public void onExport()
  {
    FileChooser fileChooser = new FileChooser();

    fileChooser.setTitle("Export G-Code");
    fileChooser.getExtensionFilters().addAll(extensionGCode, extensionAll);
    fileChooser.setInitialFileName(activeProcess.get().getFileName());

    File outputFile = fileChooser.showSaveDialog(getWindow());

    if (outputFile != null)
    {
      String logFileName = outputFile.getAbsolutePath();
      logFileName = Common.removeFileNameExtension(logFileName) + ".csv";

      File logFile = new File(logFileName);

      PrintStream output = null;
      PrintStream log = null;

      try
      {
        output = new PrintStream(outputFile);
        log = new PrintStream(logFile);

        Processor.run(getActiveProcess(), getActiveDevice(), output, log);
      }
      catch (FileNotFoundException e)
      {
        // TODO: Show error message
        e.printStackTrace();
      }
      catch (PositioningException e)
      {
        // TODO: Show error message
        e.printStackTrace();
      }
      finally
      {
        if (output != null)
        {
          output.close();
        }

        if (log != null)
        {
          log.close();
        }
      }
    }
  }

  public void onExit()
  {
    getStage().fireEvent(
        new WindowEvent(getStage(), WindowEvent.WINDOW_CLOSE_REQUEST));
  }

  public void onAbout()
  {
  }

  private boolean allowClose()
  {
    if (getActiveProcess().getDirty())
    {
      Alert alert = new Alert(AlertType.CONFIRMATION,
          "Do you want to save changes to "
              + getActiveProcess().getFileNameWithExtension() + "?",
          ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);

      Optional<ButtonType> result = alert.showAndWait();

      if (result.isPresent())
      {
        if (result.get() == ButtonType.YES)
        {
          onSave();
        }

        return true;
      }
      else
      {
        return false;
      }

    }
    else
    {
      return true;
    }
  }

  public void onAddDispenseProcedure()
  {
    DispenseProcedure procedure = new DispenseProcedure();
    int selectionIndex = proceduresTableView.getSelectionModel().getSelectedIndex();
    
    if (selectionIndex >= 0)
    {
      getProcedures().add(selectionIndex, procedure);
    }
    else
    {
      getProcedures().add(procedure);
    }
  }

  public void onAddMixProcedure()
  {

  }

  public void onAddChangeTipProcedure()
  {

  }
  
  public void onRemoveProcedure()
  {
    int selectionIndex = proceduresTableView.getSelectionModel().getSelectedIndex();
    
    if (selectionIndex >= 0)
    {
      getProcedures().remove(selectionIndex);
    }
  }
  
  public void onMoveUpProcedure()
  {
    int selectionIndex = proceduresTableView.getSelectionModel().getSelectedIndex();
    
    if (selectionIndex > 0)
    {
      Procedure procedure = getProcedures().get(selectionIndex);
      getProcedures().remove(selectionIndex);
      getProcedures().add(selectionIndex - 1, procedure);
      proceduresTableView.getSelectionModel().select(selectionIndex - 1);
    }
  }
  
  public void onMoveDownProcedure()
  {
    int selectionIndex = proceduresTableView.getSelectionModel().getSelectedIndex();
    
    if ((selectionIndex >= 0) && (selectionIndex < (getProcedures().size() - 1)))
    {
      Procedure procedure = getProcedures().get(selectionIndex);
      getProcedures().remove(selectionIndex);
      getProcedures().add(selectionIndex + 1, procedure);
      proceduresTableView.getSelectionModel().select(selectionIndex + 1);
    }
  }
}
