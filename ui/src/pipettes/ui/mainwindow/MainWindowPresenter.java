package pipettes.ui.mainwindow;

import java.io.File;
import java.io.PrintStream;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import javafx.util.converter.NumberStringConverter;

import javax.inject.Inject;

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
import pipettes.ui.RecursiveTreeItem;
import pipettes.ui.preview.PreviewView;

public class MainWindowPresenter implements Initializable
{
  private static String title = "Pipette Process Editor";
  private static String titleSeparator = " - ";

  private static String unselectedContainer = "(Select a container)";

  private static FileChooser.ExtensionFilter extensionProcess = new FileChooser.ExtensionFilter(
      "Pipette Process File", "*.ppxml");
  private static FileChooser.ExtensionFilter extensionGCode = new FileChooser.ExtensionFilter(
      "G-Code File", "*.gcode");
  private static FileChooser.ExtensionFilter extensionAll = new FileChooser.ExtensionFilter(
      "All Files", "*.*");

  @FXML
  private VBox root;

  @FXML
  private TableView<Device> devicesTableView;

  @FXML
  private TableColumn<Device, String> devicesNameTableColumn;

  @FXML
  private TableColumn<Device, String> devicesTypeTableColumn;

  @FXML
  private TableView<Container> containerLibraryTableView;

  @FXML
  private TableColumn<Container, String> containerLibraryNameTableColumn;

  @FXML
  private TableColumn<Container, String> containerLibraryShapeTableColumn;

  @FXML
  private TreeView<Container> containersTreeView;

  @FXML
  private TableView<Procedure> proceduresTableView;

  @FXML
  private TableColumn<Procedure, String> proceduresTypeTableColumn;

  @FXML
  private TreeView<Container> procedureDispenseSourceContainersTreeView;

  @FXML
  private TitledPane procedureDispenseSourceContainersTitledPane;

  @FXML
  private TreeView<Container> procedureDispenseDestinationContainersTreeView;

  @FXML
  private TitledPane procedureDispenseDestinationContainersTitledPane;

  @FXML
  private TreeView<Container> procedureMixContainersTreeView;

  @FXML
  private TitledPane procedureMixContainersTitledPane;

  @FXML
  private TreeView<Container> procedureChangeTipNewContainersTreeView;

  @FXML
  private TitledPane procedureChangeTipNewContainersTitledPane;

  @FXML
  private TreeView<Container> procedureChangeTipDisposalContainersTreeView;

  @FXML
  private TitledPane procedureChangeTipDisposalContainersTitledPane;

  @FXML
  private ChoiceBox<ContainerShape> containerShapeChoiceBox;

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
  private TextField procedureMixCountTextField;

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
  private TextField containerSizeYTextField;

  @FXML
  private TextField containerDispenseHeightTextField;

  @FXML
  private TextField deviceRectangularMaximumYTextField;

  @FXML
  private GridPane deviceRectangularGridPane;

  @FXML
  private TextField deviceRectangularNameTextField;

  @FXML
  private TextField deviceRectangularMaximumXTextField;

  @FXML
  private TextField deviceRectangularHomeYTextField;

  @FXML
  private TextField deviceCylindricalDispenseExtrudeRatioTextField;

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

  @FXML
  private BorderPane previewBorderPane;

  @Inject
  private Library<Device> deviceLibrary;

  @Inject
  private Library<Container> containerLibrary;

  @Inject
  private ObjectProperty<Process> activeProcess;

  @Inject
  private ObjectProperty<Device> activeDevice;

  @Inject
  private ObjectProperty<Container> activeLibraryContainer;

  @Inject
  private ObjectProperty<Container> activeContainer;

  @Inject
  private ObjectProperty<Procedure> activeProcedure;

  private Container rootContainer;
  private TreeItem<Container> rootContainerTreeItem;

  private ObjectProperty<Container> dispenseProcedureSource = new SimpleObjectProperty<Container>();
  private ObjectProperty<Container> dispenseProcedureDestination = new SimpleObjectProperty<Container>();
  private ObjectProperty<Container> mixProcedureContainer = new SimpleObjectProperty<Container>();
  private ObjectProperty<Container> changeTipProcedureNew = new SimpleObjectProperty<Container>();
  private ObjectProperty<Container> changeTipProcedureDisposal = new SimpleObjectProperty<Container>();

  private RectangularGCodeDevice lastActiveDeviceRectangular;
  private CylindricalGCodeDevice lastActiveDeviceCylindrical;
  private Container lastActiveContainer;
  private DispenseProcedure lastActiveProcedureDispense;
  private MixProcedure lastActiveProcedureMix;
  private ChangeTipProcedure lastActiveProcedureChangeTip;
  private Container lastDispenseProcedureSource;
  private Container lastDispenseProcedureDestination;
  private Container lastMixProcedureContainer;
  private Container lastChangeTipProcedureNew;
  private Container lastChangeTipProcedureDisposal;

  private NumberStringConverter numberConverter = new NumberStringConverter();

  private Scene getScene()
  {
    return root.getScene();
  }

  private Window getWindow()
  {
    if (getScene() == null)
    {
      return null;
    }
    else
    {
      return getScene().getWindow();
    }
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

    activeContainer.addListener(new ChangeListener<Container>()
    {
      @Override
      public void changed(ObservableValue<? extends Container> observableValue,
          Container oldValue, Container newValue)
      {
        onActiveContainer();
      }
    });

    activeProcedure.addListener(new ChangeListener<Procedure>()
    {
      @Override
      public void changed(ObservableValue<? extends Procedure> observableValue,
          Procedure oldValue, Procedure newValue)
      {
        onActiveProcedure();
      }
    });

    devicesTableView.getSelectionModel().selectedItemProperty()
        .addListener(new ChangeListener<Device>()
        {
          @Override
          public void changed(ObservableValue<? extends Device> observable,
              Device oldValue, Device newValue)
          {
            activeDevice.set(newValue);
          }
        });

    devicesNameTableColumn
        .setCellValueFactory(new Callback<CellDataFeatures<Device, String>, ObservableValue<String>>()
        {
          public ObservableValue<String> call(
              CellDataFeatures<Device, String> value)
          {
            return value.getValue().nameProperty();
          }
        });

    devicesTypeTableColumn
        .setCellValueFactory(new Callback<CellDataFeatures<Device, String>, ObservableValue<String>>()
        {
          public ObservableValue<String> call(
              CellDataFeatures<Device, String> value)
          {
            return new SimpleStringProperty(value.getValue().getType()
                .toString());
          }
        });

    devicesTableView.setItems(deviceLibrary.getItems());

    containerLibraryTableView.getSelectionModel().selectedItemProperty()
        .addListener(new ChangeListener<Container>()
        {
          @Override
          public void changed(ObservableValue<? extends Container> observable,
              Container oldValue, Container newValue)
          {
            activeLibraryContainer.set(newValue);
          }
        });

    containerLibraryNameTableColumn
        .setCellValueFactory(new Callback<CellDataFeatures<Container, String>, ObservableValue<String>>()
        {
          public ObservableValue<String> call(
              CellDataFeatures<Container, String> value)
          {
            return value.getValue().localNameProperty();
          }
        });

    containerLibraryShapeTableColumn
        .setCellValueFactory(new Callback<CellDataFeatures<Container, String>, ObservableValue<String>>()
        {
          public ObservableValue<String> call(
              CellDataFeatures<Container, String> value)
          {
            return new SimpleStringProperty(value.getValue().getShape()
                .toString());
          }
        });

    containerLibraryTableView.setItems(containerLibrary.getItems());

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

    dispenseProcedureSource.addListener(new ChangeListener<Container>()
    {
      @Override
      public void changed(ObservableValue<? extends Container> observableValue,
          Container oldValue, Container newValue)
      {
        onActiveDispenseProcedureSource();
      }
    });

    dispenseProcedureDestination.addListener(new ChangeListener<Container>()
    {
      @Override
      public void changed(ObservableValue<? extends Container> observableValue,
          Container oldValue, Container newValue)
      {
        onActiveDispenseProcedureDestination();
      }
    });

    mixProcedureContainer.addListener(new ChangeListener<Container>()
    {
      @Override
      public void changed(ObservableValue<? extends Container> observableValue,
          Container oldValue, Container newValue)
      {
        onActiveMixProcedureContainer();
      }
    });

    changeTipProcedureNew.addListener(new ChangeListener<Container>()
    {
      @Override
      public void changed(ObservableValue<? extends Container> observableValue,
          Container oldValue, Container newValue)
      {
        onActiveChangeTipProcedureNew();
      }
    });

    changeTipProcedureDisposal.addListener(new ChangeListener<Container>()
    {
      @Override
      public void changed(ObservableValue<? extends Container> observableValue,
          Container oldValue, Container newValue)
      {
        onActiveChangeTipProcedureDisposal();
      }
    });

    onActiveDevice();
    onActiveContainer();
    onActiveProcedure();
    onActiveDispenseProcedureSource();
    onActiveDispenseProcedureDestination();
    onActiveMixProcedureContainer();
    onActiveChangeTipProcedureNew();
    onActiveChangeTipProcedureDisposal();

    onActiveProcess();

    PreviewView preview = new PreviewView();
    preview.getViewAsync(previewBorderPane::setCenter);
  }

  private void onActiveDevice()
  {
    Device device = activeDevice.get();

    if (device == null)
    {
      rebindActiveDeviceNoneControls();
      rebindActiveDeviceRectangularControls(null);
      rebindActiveDeviceCylindricalControls(null);
    }
    else if (device instanceof RectangularGCodeDevice)
    {
      rebindActiveDeviceRectangularControls((RectangularGCodeDevice) device);
      rebindActiveDeviceCylindricalControls(null);
    }
    else if (device instanceof CylindricalGCodeDevice)
    {
      rebindActiveDeviceRectangularControls(null);
      rebindActiveDeviceCylindricalControls((CylindricalGCodeDevice) device);
    }
  }

  private void onActiveContainer()
  {
    Container container = activeContainer.get();
    Container last = lastActiveContainer;

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

      if (container != null)
      {
        containerNameTextField.textProperty().bindBidirectional(
            container.localNameProperty());

        containerShapeChoiceBox.valueProperty().bindBidirectional(
            container.shapeProperty());

        containerPositionXTextField.textProperty().bindBidirectional(
            container.localPositionXProperty(), numberConverter);

        containerPositionYTextField.textProperty().bindBidirectional(
            container.localPositionYProperty(), numberConverter);

        containerPositionZTextField.textProperty().bindBidirectional(
            container.localPositionZProperty(), numberConverter);

        containerSizeXTextField.textProperty().bindBidirectional(
            container.sizeXProperty(), numberConverter);

        containerSizeYTextField.textProperty().bindBidirectional(
            container.sizeYProperty(), numberConverter);

        containerSizeZTextField.textProperty().bindBidirectional(
            container.sizeZProperty(), numberConverter);

        containerDrawHeightTextField.textProperty().bindBidirectional(
            container.drawHeightAboveBottomProperty(), numberConverter);

        containerDispenseHeightTextField.textProperty().bindBidirectional(
            container.dispenseHeightAboveTopProperty(), numberConverter);

        containerClearanceHeightTextField.textProperty().bindBidirectional(
            container.clearanceHeightAboveTopProperty(), numberConverter);
      }

      lastActiveContainer = container;
    }

    containerGridPane.setVisible(container != null);
  }

  private void onActiveProcedure()
  {
    Procedure procedure = activeProcedure.get();

    if (procedure == null)
    {
      rebindActiveProcedureNoneControls();
      rebindActiveProcedureDispenseControls(null);
      rebindActiveProcedureMixControls(null);
      rebindActiveProcedureChangeTipControls(null);
    }
    else if (procedure instanceof DispenseProcedure)
    {
      rebindActiveProcedureDispenseControls((DispenseProcedure) procedure);
      rebindActiveProcedureMixControls(null);
      rebindActiveProcedureChangeTipControls(null);
    }
    else if (procedure instanceof MixProcedure)
    {
      rebindActiveProcedureDispenseControls(null);
      rebindActiveProcedureMixControls((MixProcedure) procedure);
      rebindActiveProcedureChangeTipControls(null);
    }
    else if (procedure instanceof ChangeTipProcedure)
    {
      rebindActiveProcedureDispenseControls(null);
      rebindActiveProcedureMixControls(null);
      rebindActiveProcedureChangeTipControls((ChangeTipProcedure) procedure);
    }
  }

  private void onActiveProcess()
  {
    updateTitle();
    rebindContainerTreeView();
    rebindProceduresTableView();
  }

  private TreeItem<Container> findContainerTreeItem(Container container)
  {
    return findContainerTreeItem(rootContainerTreeItem, container);
  }

  private TreeItem<Container> findContainerTreeItem(TreeItem<Container> root,
      Container container)
  {
    TreeItem<Container> result;

    if (root.getValue() == container)
    {
      result = root;
    }
    else
    {
      result = null;

      for (TreeItem<Container> subcontainer : root.getChildren())
      {
        result = findContainerTreeItem(subcontainer, container);

        if (result != null)
        {
          break;
        }
      }
    }

    return result;
  }

  private void onActiveProcedureContainer(Container last, Container container,
      TitledPane pane, TreeView<Container> tree)
  {
    if (container != last)
    {
      if (last != null)
      {
        pane.textProperty().unbind();
      }

      if (container != null)
      {
        pane.textProperty().bind(container.localNameProperty());
      }

      tree.getSelectionModel().select(findContainerTreeItem(container));
    }

    if (container == null)
    {
      pane.textProperty().set(unselectedContainer);
    }
  }

  private void onActiveDispenseProcedureSource()
  {
    onActiveProcedureContainer(lastDispenseProcedureSource,
        dispenseProcedureSource.get(),
        procedureDispenseSourceContainersTitledPane,
        procedureDispenseSourceContainersTreeView);
    lastDispenseProcedureSource = dispenseProcedureSource.get();
  }

  private void onActiveDispenseProcedureDestination()
  {
    onActiveProcedureContainer(lastDispenseProcedureDestination,
        dispenseProcedureDestination.get(),
        procedureDispenseDestinationContainersTitledPane,
        procedureDispenseDestinationContainersTreeView);
    lastDispenseProcedureDestination = dispenseProcedureDestination.get();
  }

  private void onActiveMixProcedureContainer()
  {
    onActiveProcedureContainer(lastMixProcedureContainer,
        mixProcedureContainer.get(), procedureMixContainersTitledPane,
        procedureMixContainersTreeView);
    lastMixProcedureContainer = mixProcedureContainer.get();
  }

  private void onActiveChangeTipProcedureNew()
  {
    onActiveProcedureContainer(lastChangeTipProcedureNew,
        changeTipProcedureNew.get(), procedureChangeTipNewContainersTitledPane,
        procedureChangeTipNewContainersTreeView);
    lastChangeTipProcedureNew = changeTipProcedureNew.get();
  }

  private void onActiveChangeTipProcedureDisposal()
  {
    onActiveProcedureContainer(lastChangeTipProcedureDisposal,
        changeTipProcedureDisposal.get(),
        procedureChangeTipDisposalContainersTitledPane,
        procedureChangeTipDisposalContainersTreeView);
    lastChangeTipProcedureDisposal = changeTipProcedureDisposal.get();
  }

  private void rebindContainerTreeView()
  {
    rootContainer = new Container(activeProcess.get().getBaseContainers());

    rootContainerTreeItem = new RecursiveTreeItem<Container>(rootContainer,
        Container::getSubcontainers);

    containersTreeView.getSelectionModel().selectedItemProperty()
        .addListener(new ChangeListener<TreeItem<Container>>()
        {
          @Override
          public void changed(
              ObservableValue<? extends TreeItem<Container>> observable,
              TreeItem<Container> oldValue, TreeItem<Container> newValue)
          {
            if (newValue == null)
            {
              activeContainer.set(null);
            }
            else
            {
              activeContainer.set(newValue.getValue());
            }
          }
        });

    containersTreeView.setRoot(rootContainerTreeItem);

    procedureDispenseSourceContainersTreeView.getSelectionModel()
        .selectedItemProperty()
        .addListener(new ChangeListener<TreeItem<Container>>()
        {
          @Override
          public void changed(
              ObservableValue<? extends TreeItem<Container>> observable,
              TreeItem<Container> oldValue, TreeItem<Container> newValue)
          {
            if (newValue == null)
            {
              dispenseProcedureSource.set(null);
            }
            else
            {
              dispenseProcedureSource.set(newValue.getValue());
            }
          }
        });

    procedureDispenseSourceContainersTreeView.setRoot(rootContainerTreeItem);

    procedureDispenseDestinationContainersTreeView.getSelectionModel()
        .selectedItemProperty()
        .addListener(new ChangeListener<TreeItem<Container>>()
        {
          @Override
          public void changed(
              ObservableValue<? extends TreeItem<Container>> observable,
              TreeItem<Container> oldValue, TreeItem<Container> newValue)
          {
            if (newValue == null)
            {
              dispenseProcedureDestination.set(null);
            }
            else
            {
              dispenseProcedureDestination.set(newValue.getValue());
            }
          }
        });

    procedureDispenseDestinationContainersTreeView
        .setRoot(rootContainerTreeItem);

    procedureMixContainersTreeView.getSelectionModel().selectedItemProperty()
        .addListener(new ChangeListener<TreeItem<Container>>()
        {
          @Override
          public void changed(
              ObservableValue<? extends TreeItem<Container>> observable,
              TreeItem<Container> oldValue, TreeItem<Container> newValue)
          {
            if (newValue == null)
            {
              mixProcedureContainer.set(null);
            }
            else
            {
              mixProcedureContainer.set(newValue.getValue());
            }
          }
        });

    procedureMixContainersTreeView.setRoot(rootContainerTreeItem);

    procedureChangeTipNewContainersTreeView.getSelectionModel()
        .selectedItemProperty()
        .addListener(new ChangeListener<TreeItem<Container>>()
        {
          @Override
          public void changed(
              ObservableValue<? extends TreeItem<Container>> observable,
              TreeItem<Container> oldValue, TreeItem<Container> newValue)
          {
            if (newValue == null)
            {
              changeTipProcedureNew.set(null);
            }
            else
            {
              changeTipProcedureNew.set(newValue.getValue());
            }
          }
        });

    procedureChangeTipNewContainersTreeView.setRoot(rootContainerTreeItem);

    procedureChangeTipDisposalContainersTreeView.getSelectionModel()
        .selectedItemProperty()
        .addListener(new ChangeListener<TreeItem<Container>>()
        {
          @Override
          public void changed(
              ObservableValue<? extends TreeItem<Container>> observable,
              TreeItem<Container> oldValue, TreeItem<Container> newValue)
          {
            if (newValue == null)
            {
              changeTipProcedureDisposal.set(null);
            }
            else
            {
              changeTipProcedureDisposal.set(newValue.getValue());
            }
          }
        });

    procedureChangeTipDisposalContainersTreeView.setRoot(rootContainerTreeItem);
  }

  private void rebindProceduresTableView()
  {
    proceduresTableView.setItems(activeProcess.get().getProcedures());
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

      if (device != null)
      {
        deviceRectangularNameTextField.textProperty().bindBidirectional(
            device.nameProperty());

        deviceRectangularHomeXTextField.textProperty().bindBidirectional(
            device.homePositionXProperty(), numberConverter);

        deviceRectangularHomeYTextField.textProperty().bindBidirectional(
            device.homePositionYProperty(), numberConverter);

        deviceRectangularHomeZTextField.textProperty().bindBidirectional(
            device.homePositionZProperty(), numberConverter);

        deviceRectangularMinimumXTextField.textProperty().bindBidirectional(
            device.minimumExtentXProperty(), numberConverter);

        deviceRectangularMinimumYTextField.textProperty().bindBidirectional(
            device.minimumExtentYProperty(), numberConverter);

        deviceRectangularMinimumZTextField.textProperty().bindBidirectional(
            device.minimumExtentZProperty(), numberConverter);

        deviceRectangularMaximumXTextField.textProperty().bindBidirectional(
            device.maximumExtentXProperty(), numberConverter);

        deviceRectangularMaximumYTextField.textProperty().bindBidirectional(
            device.maximumExtentYProperty(), numberConverter);

        deviceRectangularMaximumZTextField.textProperty().bindBidirectional(
            device.maximumExtentZProperty(), numberConverter);

        deviceRectangularExtrudeVolumeTextField.textProperty()
            .bindBidirectional(device.extrudePerVolumeProperty(),
                numberConverter);

        deviceRectangularDispenseExtrudeRatioTextField.textProperty()
            .bindBidirectional(device.dispenseExtrudeRatioProperty(),
                numberConverter);

        deviceRectangularHeaderTextArea.textProperty().bindBidirectional(
            device.headerProperty());

        deviceRectangularFooterTextArea.textProperty().bindBidirectional(
            device.footerProperty());

        deviceRectangularGridPane.setVisible(true);
        deviceCylindricalGridPane.setVisible(false);
      }

      lastActiveDeviceRectangular = device;
    }
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

      if (device != null)
      {
        deviceCylindricalNameTextField.textProperty().bindBidirectional(
            device.nameProperty());

        deviceCylindricalRadiusTextField.textProperty().bindBidirectional(
            device.radiusProperty(), numberConverter);

        deviceCylindricalMinimumZTextField.textProperty().bindBidirectional(
            device.minimumZProperty(), numberConverter);

        deviceCylindricalMaximumZTextField.textProperty().bindBidirectional(
            device.maximumZProperty(), numberConverter);

        deviceCylindricalExtrudeVolumeTextField.textProperty()
            .bindBidirectional(device.extrudePerVolumeProperty(),
                numberConverter);

        deviceCylindricalDispenseExtrudeRatioTextField.textProperty()
            .bindBidirectional(device.dispenseExtrudeRatioProperty(),
                numberConverter);

        deviceCylindricalHeaderTextArea.textProperty().bindBidirectional(
            device.headerProperty());

        deviceCylindricalFooterTextArea.textProperty().bindBidirectional(
            device.footerProperty());

        deviceRectangularGridPane.setVisible(false);
        deviceCylindricalGridPane.setVisible(true);
      }

      lastActiveDeviceCylindrical = device;
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
        dispenseProcedureSource.unbindBidirectional(last.sourceProperty());
        dispenseProcedureDestination.unbindBidirectional(last
            .destinationProperty());

        procedureDispenseVolumeTextField.textProperty().unbindBidirectional(
            last.volumeProperty());
      }

      if (procedure != null)
      {
        dispenseProcedureSource.bindBidirectional(procedure.sourceProperty());
        dispenseProcedureDestination.bindBidirectional(procedure
            .destinationProperty());

        procedureDispenseVolumeTextField.textProperty().bindBidirectional(
            procedure.volumeProperty(), numberConverter);

        procedureDispenseGridPane.setVisible(true);
        procedureMixGridPane.setVisible(false);
        procedureChangeTipGridPane.setVisible(false);
      }

      lastActiveProcedureDispense = procedure;
    }
  }

  private void rebindActiveProcedureMixControls(MixProcedure procedure)
  {
    MixProcedure last = lastActiveProcedureMix;

    if (procedure != last)
    {
      if (last != null)
      {
        mixProcedureContainer.unbindBidirectional(last.destinationProperty());

        procedureMixVolumeTextField.textProperty().unbindBidirectional(
            last.volumeProperty());

        procedureMixCountTextField.textProperty().unbindBidirectional(
            last.countProperty());
      }

      if (procedure != null)
      {
        mixProcedureContainer
            .bindBidirectional(procedure.destinationProperty());

        procedureMixVolumeTextField.textProperty().bindBidirectional(
            procedure.volumeProperty(), numberConverter);

        procedureMixCountTextField.textProperty().bindBidirectional(
            procedure.countProperty(), numberConverter);

        procedureDispenseGridPane.setVisible(false);
        procedureMixGridPane.setVisible(true);
        procedureChangeTipGridPane.setVisible(false);
      }

      lastActiveProcedureMix = procedure;
    }
  }

  private void rebindActiveProcedureChangeTipControls(
      ChangeTipProcedure procedure)
  {
    ChangeTipProcedure last = lastActiveProcedureChangeTip;

    if (procedure != last)
    {
      if (last != null)
      {
        changeTipProcedureNew.unbindBidirectional(last.newTipProperty());
        changeTipProcedureDisposal.unbindBidirectional(last
            .tipDisposalProperty());
      }

      if (procedure != null)
      {
        changeTipProcedureNew.bindBidirectional(procedure.newTipProperty());
        changeTipProcedureDisposal.bindBidirectional(procedure
            .tipDisposalProperty());

        procedureDispenseGridPane.setVisible(false);
        procedureMixGridPane.setVisible(false);
        procedureChangeTipGridPane.setVisible(true);
      }

      lastActiveProcedureChangeTip = procedure;
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

  private void updateTitle()
  {
    if (getStage() != null)
    {
      if (activeProcess.get() == null)
      {
        getStage().setTitle(title);
      }
      else
      {
        getStage().setTitle(
            activeProcess.get().getFileName() + titleSeparator + title);
      }
    }
  }

  private void displayAlert(AlertType type, String message)
  {
    Alert alert = new Alert(type, message, ButtonType.OK);
    alert.setHeaderText(null);
    alert.showAndWait();
  }

  private void onWindow()
  {
    updateTitle();

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
      catch (Exception e)
      {
        e.printStackTrace();
        displayAlert(AlertType.ERROR, "Unable to open file.");
      }
    }
  }

  public boolean onSave()
  {
    if (!activeProcess.get().hasSetFileName())
    {
      return onSaveAs();
    }
    else
    {
      try
      {
        activeProcess.get().save();
        return true;
      }
      catch (Exception e)
      {
        e.printStackTrace();
        displayAlert(AlertType.ERROR, "Unable to save file.");
        return false;
      }
    }
  }

  public boolean onSaveAs()
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
        activeProcess.get().saveAs(selectedFile);
        updateTitle();
        return true;
      }
      catch (Exception e)
      {
        e.printStackTrace();
        displayAlert(AlertType.ERROR, "Unable to save file.");
        return false;
      }
    }

    return true;
  }

  public void onExport()
  {
    if (activeDevice.get() == null)
    {
      displayAlert(AlertType.INFORMATION,
          "Select a device to perform an export of the process for that device.");
      return;
    }

    String exportFileName = Common.removeFileNameExtension(activeProcess.get()
        .getFileName());

    FileChooser fileChooser = new FileChooser();

    fileChooser.setTitle("Export G-Code");
    fileChooser.getExtensionFilters().addAll(extensionGCode, extensionAll);
    fileChooser.setInitialFileName(exportFileName + ".gcode");

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

        Processor.run(activeProcess.get(), activeDevice.get(), output, log);

        displayAlert(AlertType.INFORMATION, "Export complete.");
      }
      catch (PositioningException e)
      {
        e.printStackTrace();
        displayAlert(
            AlertType.ERROR,
            "Unable to export files. The process requires movement outside the device boundaries.");
      }
      catch (Exception e)
      {
        e.printStackTrace();
        displayAlert(AlertType.ERROR, "Unable to export files.");
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
    Alert alert = new Alert(AlertType.INFORMATION);
    alert.setTitle("About " + title);
    alert.setHeaderText(title);
    alert
        .setContentText("Copyright (c) 2017 by "
            + "Sofia Bali, Michael Brumm, Chance T. Brunton, Alan Kato, Jonathan Navarro");

    Label label = new Label("Legal notices:");

    String legalText = "This software contains third party components subject to the following licenses.\n"
        + "\n"
        + "afterburner.fx\n"
        + "Copyright (C) 2013 - 2016 Adam Bien\n"
        + "\n"
        + "Licensed under the Apache License, Version 2.0 (the \"License\"); you may not use this file except in compliance with the License. You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0\n"
        + "\n"
        + "Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an \"AS IS\" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.\n"
        + "\n"
        + "\n"
        + "GCodeFXViewer\n"
        + "Copyright (c) 2010, 2016 Oracle and/or its affiliates.\n"
        + "All rights reserved. Use is subject to license terms.\n"
        + "\n"
        + "This file is available and licensed under the following license:\n"
        + "\n"
        + "Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:\n"
        + "\n"
        + " - Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.\n"
        + " - Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.\n"
        + " - Neither the name of Oracle Corporation nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.\n"
        + "\n"
        + "THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS \"AS IS\" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.";

    TextArea textArea = new TextArea(legalText);
    textArea.setEditable(false);
    textArea.setWrapText(true);
    textArea.setMaxWidth(Double.MAX_VALUE);
    textArea.setMaxHeight(Double.MAX_VALUE);
    GridPane.setVgrow(textArea, Priority.ALWAYS);
    GridPane.setHgrow(textArea, Priority.ALWAYS);

    GridPane contentPane = new GridPane();
    contentPane.setMaxWidth(Double.MAX_VALUE);
    contentPane.add(label, 0, 0);
    contentPane.add(textArea, 0, 1);

    alert.getDialogPane().setExpandableContent(contentPane);

    alert.showAndWait();
  }

  private boolean allowClose()
  {
    if (activeProcess.get().getDirty())
    {
      Alert alert = new Alert(AlertType.CONFIRMATION,
          "Do you want to save changes to "
              + activeProcess.get().getFileNameWithExtension() + "?",
          ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
      alert.setHeaderText(null);

      Optional<ButtonType> buttonPress = alert.showAndWait();

      if (!buttonPress.isPresent())
      {
        return false;
      }

      if (buttonPress.get() == ButtonType.YES)
      {
        return onSave();
      }
    }

    return true;
  }

  private void addDevice(Device device)
  {
    device.setLibrary(deviceLibrary);

    String deviceName = deviceLibrary.getAvailableName(Device.prefix);
    device.setName(deviceName);

    deviceLibrary.getItems().add(device);
    devicesTableView.getSelectionModel().select(device);
  }

  public void onAddRectangularDevice()
  {
    RectangularGCodeDevice device = new RectangularGCodeDevice();
    addDevice(device);
  }

  public void onAddCylindricalDevice()
  {
    CylindricalGCodeDevice device = new CylindricalGCodeDevice();
    addDevice(device);
  }

  public void onRemoveDevice()
  {
    Device device = devicesTableView.getSelectionModel().getSelectedItem();

    if (device != null)
    {
      deviceLibrary.getItems().remove(device);
    }
  }

  public void onRemoveLibraryContainer()
  {
    Container container = containerLibraryTableView.getSelectionModel()
        .getSelectedItem();

    if (container != null)
    {
      containerLibrary.getItems().remove(container);
    }
  }

  public void onCopyToProcessContainer()
  {
    Container container = activeLibraryContainer.get();

    if (container != null)
    {
      container = container.clone();

      String containerName = rootContainer
          .getAvailableSubcontainerName(container.getLocalName());
      container.setLocalName(containerName);

      rootContainer.getSubcontainers().add(container);
    }
  }

  private void addSubcontainer(Container container)
  {
    Container subcontainer = new Container();

    String subcontainerName = container
        .getAvailableSubcontainerName(Container.prefix);
    subcontainer.setLocalName(subcontainerName);

    container.getSubcontainers().add(subcontainer);
  }

  public void onAddContainer()
  {
    addSubcontainer(rootContainer);
  }

  public void onAddSubcontainer()
  {
    Container container = activeContainer.get();

    if (container != null)
    {
      addSubcontainer(container);
    }
  }

  public void onRemoveContainer()
  {
    Container container = activeContainer.get();

    if (container != null)
    {
      if (activeProcess.get().isContainerReferenced(container))
      {
        displayAlert(
            AlertType.INFORMATION,
            "This container cannot be removed because it is referenced by a procedure in the process.");
      }
      else
      {
        container.getParent().getSubcontainers().remove(container);
      }
    }
  }

  public void onCopyToLibraryContainer()
  {
    Container container = activeContainer.get();

    if (container != null)
    {
      container = container.clone();

      String containerName = containerLibrary.getAvailableName(container
          .getLocalName());
      container.setLocalName(containerName);

      containerLibrary.getItems().add(container);
    }
  }

  private void addProcedure(Procedure procedure)
  {
    int selectionIndex = proceduresTableView.getSelectionModel()
        .getSelectedIndex();
    selectionIndex++;

    activeProcess.get().getProcedures().add(selectionIndex, procedure);
    proceduresTableView.getSelectionModel().select(selectionIndex);
  }

  public void onAddDispenseProcedure()
  {
    DispenseProcedure procedure = new DispenseProcedure();
    addProcedure(procedure);
  }

  public void onAddMixProcedure()
  {
    MixProcedure procedure = new MixProcedure();
    addProcedure(procedure);
  }

  public void onAddChangeTipProcedure()
  {
    ChangeTipProcedure procedure = new ChangeTipProcedure();
    addProcedure(procedure);
  }

  public void onRemoveProcedure()
  {
    int selectionIndex = proceduresTableView.getSelectionModel()
        .getSelectedIndex();

    if (selectionIndex >= 0)
    {
      activeProcess.get().getProcedures().remove(selectionIndex);
    }
  }

  public void onMoveUpProcedure()
  {
    int selectionIndex = proceduresTableView.getSelectionModel()
        .getSelectedIndex();

    if (selectionIndex > 0)
    {
      ObservableList<Procedure> procedures = activeProcess.get()
          .getProcedures();
      Procedure procedure = procedures.get(selectionIndex);
      procedures.remove(selectionIndex);
      procedures.add(selectionIndex - 1, procedure);
      proceduresTableView.getSelectionModel().select(selectionIndex - 1);
    }
  }

  public void onMoveDownProcedure()
  {
    int selectionIndex = proceduresTableView.getSelectionModel()
        .getSelectedIndex();

    ObservableList<Procedure> procedures = activeProcess.get().getProcedures();

    if ((selectionIndex >= 0) && (selectionIndex < (procedures.size() - 1)))
    {
      Procedure procedure = procedures.get(selectionIndex);
      procedures.remove(selectionIndex);
      procedures.add(selectionIndex + 1, procedure);
      proceduresTableView.getSelectionModel().select(selectionIndex + 1);
    }
  }
}
