package pipettes.ui;

import pipettes.core.Procedure;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class ProcedureListItem extends HBox
{
  private Procedure precedure;
  private Label label = new Label();

  public ProcedureListItem(Procedure procedure)
  {
    super();

    this.precedure = procedure;

    label.textProperty().set(procedure.getName());
    
    this.getChildren().add(label);
    this.setAlignment(Pos.CENTER_LEFT);
  }
  
  public Procedure getProcedure()
  {
    return precedure;
  }
}
