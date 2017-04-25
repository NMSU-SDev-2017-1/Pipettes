package pipettes.ui;

import javafx.beans.property.StringProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import pipettes.core.Container;

public class ContainerTreeItem extends HBox
{
  private Container container;
  private Label label = new Label();

  public ContainerTreeItem(StringProperty name, Container container)
  {
    super();

    this.container = container;

    if (name != null)
    {
      label.textProperty().bindBidirectional(name);
    }
    else
    {
      label.textProperty().bindBidirectional(container.localNameProperty());
    }
    
    this.getChildren().add(label);
    this.setAlignment(Pos.CENTER_LEFT);
  }
}
