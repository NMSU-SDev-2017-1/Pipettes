package pipettes.ui;

import pipettes.core.DispenseProcedure;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.util.converter.NumberStringConverter;

public class DispenseProcedureListItem extends HBox
{
  private DispenseProcedure procedure;
  private Label nameLabel = new Label();
  private Button destinationButton = new Button();
  private Button sourceButton = new Button();
  private TextField volumeTextField = new TextField();

  public DispenseProcedureListItem(DispenseProcedure procedure)
  {
    super();

    this.procedure = procedure;
    nameLabel.textProperty().set(procedure.getName());
    volumeTextField.textProperty().bindBidirectional(procedure.volumeProperty(), new NumberStringConverter());
    
    destinationButton.textProperty().set(procedure.getDestination().getName());
    sourceButton.textProperty().set(procedure.getSource().getName());

    this.getChildren().addAll(nameLabel, destinationButton, sourceButton, volumeTextField);
    this.setAlignment(Pos.CENTER_LEFT);
  }
}
