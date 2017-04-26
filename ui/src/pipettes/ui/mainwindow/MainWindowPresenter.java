package pipettes.ui.mainwindow;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.ObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;
import javafx.scene.layout.GridPane;

import javax.inject.Inject;

import pipettes.core.Container;
import pipettes.core.ContainerShape;
import pipettes.core.Device;
import pipettes.core.Library;
import pipettes.core.Procedure;
import pipettes.ui.ContainerListItem;
import pipettes.ui.ContainerTreeItem;
import pipettes.ui.DeviceListItem;
import pipettes.ui.ProcedureListItem;

public class MainWindowPresenter implements Initializable
{
  @FXML
  private ListView<DeviceListItem> deviceLibraryListView;

  @FXML
  private ListView<ContainerListItem> containerLibraryListView;

  @FXML
  private TreeView<ContainerTreeItem> containersTreeView;

  @FXML
  private ListView<ProcedureListItem> proceduresListView;
  
  @FXML
  private MenuItem fileNewMenuItem;
  
  @FXML
  private MenuItem fileOpenMenuItem;
  
  @FXML
  private MenuItem fileSaveMenuItem;
  
  @FXML
  private MenuItem fileSaveAsMenuItem;
  
  @FXML
  private MenuItem fileExportMenuItem;
  
  @FXML
  private MenuItem fileExitMenuItem;

  @FXML
  private MenuItem deviceAddRectangularMenuItem;
  
  @FXML
  private MenuItem deviceAddCylindricalMenuItem;
  
  @FXML
  private Button deviceRemoveButton;
  
  @FXML
  private TextField containerNameTextField;

  @FXML
  private TextField containerDrawHeightTextField;

  @FXML
  private TextField deviceRectangularMinimumYTextField;

  @FXML
  private GridPane procedureMixGridPane;

  @FXML
  private GridPane procedureChangeTipGridPane;

  @FXML
  private TextField deviceRectangularMaximumZTextField;

  @FXML
  private TextField containerPositionYTextField;

  @FXML
  private Button containersRemoveFromLibraryButton;

  @FXML
  private TextField procedureMixVolumeTextField;

  @FXML
  private TextField deviceRectangularHomeZTextField;

  @FXML
  private Button containersRemoveButton;

  @FXML
  private TextField deviceCylindricalRadiusTextField;

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
  private MenuButton procedureDispenseMixContainerMenuButton;

  @FXML
  private TextField deviceRectangularNameTextField;

  @FXML
  private TextField deviceRectangularMaximumXTextField;

  @FXML
  private TextField deviceRectangularHomeYTextField;

  @FXML
  private MenuButton procedureTipChangeNewContainerMenuButton;

  @FXML
  private MenuItem procedureAddChangeTipMenuItem;

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
  private TextField deviceCylindricalNameTextField;

  @FXML
  private TextField deviceRectangularMinimumZTextField;

  @FXML
  private GridPane deviceCylindricalGridPane;

  @FXML
  private TextField containerClearanceHeightTextField;

  @FXML
  private Button procedureMoveDownButton;

  @FXML
  private GridPane procedureDispenseGridPane;

  @FXML
  private ChoiceBox<ContainerShape> containerShapeChoiceBox;

  @FXML
  private Button containersCopyToProcessButton;

  @FXML
  private MenuItem procedureAddDispenseMenuItem;

  @FXML
  private TextField deviceCylindricalMaximumZTextField;

  @FXML
  private MenuItem helpAboutMenuItem;

  @FXML
  private TextField containerPositionXTextField;

  @Inject
  Library<Device> deviceLibrary;

  @Inject
  Library<Container> containerLibrary;

  @Inject
  ObjectProperty<Process> activeProcess;

  @Inject
  ObjectProperty<Device> activeDevice;

  @Inject
  ObjectProperty<Container> activeContainer;

  @Inject
  ObjectProperty<Procedure> activeProcedure;

  @Override
  public void initialize(URL arg0, ResourceBundle arg1)
  {
    for (Device device : deviceLibrary.getItems().values())
    {
      deviceLibraryListView.getItems().add(new DeviceListItem(device));
    }
    
    for (Container container : containerLibrary.getItems().values())
    {
      containerLibraryListView.getItems().add(new ContainerListItem(container));
    }
  }
}
