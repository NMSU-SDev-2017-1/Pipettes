package pipettes.ui.preview;

import java.util.ArrayList;

import pipettes.ui.Common;
import pipettes.core.Container;
import pipettes.core.ContainerShape;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Shape3D;

public class ContainerNode extends Group
{
  private final double containerSaturation = 0.3;
  private final double containerBrightness = 0.3;
  private final double containerOpacity = 0.05;

  private Container container;

  private boolean draw;
  private PhongMaterial containerMaterial;
  private Box containerBox;
  private Cylinder containerCylinder;

  public Container getContainer()
  {
    return container;
  }

  public ContainerNode(Container container, boolean draw)
  {
    this.container = container;
    this.draw = draw;

    containerMaterial = getRandomMaterial();
    
    container.shapeProperty().addListener((observable, oldValue, newValue) ->
    {
      onShape();
    });

    addChildrenListener(container);

    onShape();
  }

  private PhongMaterial getRandomMaterial()
  {
    double hue = Math.random() * 360;
    Color color = Color.hsb(hue, containerSaturation, containerBrightness, containerOpacity);
    
    return Common.createMaterial(color);
  }
  
  private void bindShape(Shape3D shape)
  {
    shape.translateXProperty().bind(container.positionXProperty());
    shape.translateYProperty().bind(container.positionYProperty());
    shape.translateZProperty().bind(container.positionZProperty());
    
    shape.scaleXProperty().bind(container.sizeXProperty());
    shape.scaleYProperty().bind(container.sizeYProperty());
    shape.scaleZProperty().bind(container.sizeZProperty());
  }

  private void unbindShape(Shape3D shape)
  {
    shape.translateXProperty().unbind();
    shape.translateYProperty().unbind();
    shape.translateZProperty().unbind();
    
    shape.scaleXProperty().unbind();
    shape.scaleYProperty().unbind();
    shape.scaleZProperty().unbind();
  }
  
  private void onShape()
  {
    if (container.getShape() == ContainerShape.Rectangular)
    {
      if (draw && (containerBox == null))
      {
        containerBox = new Box(1, 1, 1);
        containerBox.setMaterial(containerMaterial);
        containerBox.getTransforms().add(Common.translateZ0_5);
        getChildren().add(containerBox);

        bindShape(containerBox);
      }

      if (containerCylinder != null)
      {
        unbindShape(containerCylinder);

        getChildren().remove(containerCylinder);
        containerCylinder = null;
      }
    }
    else
    {
      if (draw && (containerCylinder == null))
      {
        containerCylinder = new Cylinder(0.5, 1);
        containerCylinder.setMaterial(containerMaterial);
        containerCylinder.getTransforms().addAll(Common.translateZ0_5,
            Common.rotateX90);
        getChildren().add(containerCylinder);
        
        bindShape(containerCylinder);
      }

      if (containerBox != null)
      {
        unbindShape(containerBox);

        getChildren().remove(containerBox);
        containerBox = null;
      }
    }
  }

  private void addChildrenListener(Container value)
  {
    final ObservableList<Container> subcontainers = value.getSubcontainers();

    for (Container subcontainer : subcontainers)
    {
      getChildren().add(new ContainerNode(subcontainer, true));
    }

    subcontainers.addListener(new ListChangeListener<Container>()
    {
      @Override
      public void onChanged(
          ListChangeListener.Change<? extends Container> change)
      {
        while (change.next())
        {
          if (change.wasAdded())
          {
            for (Container subcontainer : change.getAddedSubList())
            {
              getChildren().add(new ContainerNode(subcontainer, true));
            }
          }

          if (change.wasRemoved())
          {
            ArrayList<Node> nodesToRemove = new ArrayList<Node>();
            
            for (Container subcontainer : change.getRemoved())
            {
              for (Node node : getChildren())
              {
                if ((node instanceof ContainerNode)
                    && ((ContainerNode) node).getContainer().equals(
                        subcontainer))
                {
                  nodesToRemove.add(node);
                }
              }
            }
            
            getChildren().removeAll(nodesToRemove);
          }
        }
      }
    });
  }
}
