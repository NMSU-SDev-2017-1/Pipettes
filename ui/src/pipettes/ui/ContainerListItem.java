package pipettes.ui;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import pipettes.core.Container;

public class ContainerListItem extends HBox
{
  private Container container;
  private Label label = new Label();

  public ContainerListItem(Container container)
  {
    super();

    this.container = container;
    
    label.textProperty().bindBidirectional(container.localNameProperty());

    this.getChildren().add(label);
    this.setAlignment(Pos.CENTER_LEFT);
  }
}
