/*
 * Copyright (c) 2010, 2016 Oracle and/or its affiliates.
 * All rights reserved. Use is subject to license terms.
 *
 * This file is available and licensed under the following license:
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  - Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *  - Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the distribution.
 *  - Neither the name of Oracle Corporation nor the names of its
 *    contributors may be used to endorse or promote products derived
 *    from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package pipettes.ui.preview;

import javafx.animation.Timeline;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.AmbientLight;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.PerspectiveCamera;
import javafx.scene.PointLight;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.input.ZoomEvent;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Shape3D;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

public class PreviewModel
{
  private final double CAMERA_CLIP_NEAR = 1;
  private final double CAMERA_CLIP_FAR = 10000;
  private final double CAMERA_ANGLE = 45;
  private final double CAMERA_DISTANCE = 2000;

  private final double AXIS_LENGTH = 1000;
  private final double AXIS_WIDTH = 0.2;
  private final double AXIS_RADIUS = 2.0;

  private final double PLATE_THICKNESS = 1;
  private final double PRINT_HEAD_RADIUS = 5;
  private final double PRINT_HEAD_HEIGHT = 50;

  private final PhongMaterial redMaterial = createMaterial(Color.DARKRED,
      Color.RED);
  private final PhongMaterial greenMaterial = createMaterial(Color.DARKGREEN,
      Color.GREEN);
  private final PhongMaterial blueMaterial = createMaterial(Color.DARKBLUE,
      Color.BLUE);
  private final PhongMaterial plateMaterial = createMaterial(Color.WHEAT,
      Color.WHEAT, 10d);
  private final PhongMaterial printHeadMaterial = createMaterial(Color.YELLOW,
      Color.YELLOW);

  private final SimpleObjectProperty<SubScene> subScene = new SimpleObjectProperty<>();
  private final Group sceneGroup = new Group();

  private final PerspectiveCamera camera = new PerspectiveCamera(true);
  private final Translate cameraPosition = new Translate(0, 0, 0);
  private final Translate contentTranslate = new Translate(0, 0, 0);
  private final Xform cameraRotate = new Xform();
  private final Xform cameraTranslate = new Xform();

  private ObjectProperty<Node> content = new SimpleObjectProperty<>();
  private ObjectProperty<Node> highlite = new SimpleObjectProperty<>();
  private AutoScalingGroup autoScalingGroup = new AutoScalingGroup(2);
  private Group axisGroup, plateView;
  private Group printHeadView;
  private Sphere xSphere, ySphere, zSphere;
  private Box xAxis, yAxis, zAxis, plate;
  private Cylinder printHead;
  private AmbientLight ambientLight = new AmbientLight(Color.DARKGREY);
  private PointLight pointLight = new PointLight(Color.LIGHTGRAY);
  private SimpleObjectProperty<Timeline> timeline = new SimpleObjectProperty<>();

  private double mousePosX;
  private double mousePosY;
  private double mouseOldX;
  private double mouseOldY;
  private double mouseDeltaX;
  private double mouseDeltaY;
  Node picked = null;

  public Timeline getTimeline()
  {
    return timeline.get();
  }

  public SimpleObjectProperty<Timeline> timelineProperty()
  {
    return timeline;
  }

  public void setTimeline(Timeline timeline)
  {
    this.timeline.set(timeline);
  }

  public AutoScalingGroup getAutoScalingGroup()
  {
    return autoScalingGroup;
  }

  public ObjectProperty<Node> contentProperty()
  {
    return content;
  }

  public Node getContent()
  {
    return content.get();
  }

  public void setContent(Node content)
  {
    this.content.set(content);
  }

  public ObjectProperty<Node> highlightProperty()
  {
    return highlite;
  }

  public Node getHighlight()
  {
    return highlite.get();
  }

  public void setHighlight(Node content)
  {
    this.highlite.set(content);
  }

  public SubScene getSubScene()
  {
    return subScene.get();
  }

  public SimpleObjectProperty<SubScene> subSceneProperty()
  {
    return subScene;
  }

  public PerspectiveCamera getCamera()
  {
    return camera;
  }

  public Translate getCameraPosition()
  {
    return cameraPosition;
  }

  public Xform getCameraRotate()
  {
    return cameraRotate;
  }

  public Xform getCameraTranslate()
  {
    return cameraTranslate;
  }

  public Group getPrintHeadView()
  {
    return printHeadView;
  }

  public PreviewModel()
  {
    content.addListener((ObservableValue<? extends Node> ov, Node oldContent,
        Node newContent) ->
    {
      autoScalingGroup.getChildren().remove(oldContent);
      autoScalingGroup.getChildren().add(newContent);
      newContent.getTransforms().add(contentTranslate);
    });

    initializeCamera();

    sceneGroup.getChildren().add(autoScalingGroup);

    sceneGroup.getChildren().add(ambientLight);
    sceneGroup.getChildren().add(pointLight);

    createFeatures();

    rebuildSubScene();
  }

  private final EventHandler<MouseEvent> mouseEventHandler = event ->
  {
    double yFlip = 1.0;

    if (event.getEventType() == MouseEvent.MOUSE_PRESSED)
    {
      picked = event.getPickResult().getIntersectedNode();
      if (!(picked instanceof Shape3D))
        picked = null;
      mousePosX = event.getSceneX();
      mousePosY = event.getSceneY();
      mouseOldX = event.getSceneX();
      mouseOldY = event.getSceneY();
      sceneGroup.requestFocus();
    }
    else if (event.getEventType() == MouseEvent.MOUSE_DRAGGED)
    {
      double modifier = 1.0;
      double modifierFactor = 0.3;
      picked = null;

      if (event.isAltDown())
      {
        modifier = 10.0;
      }

      mouseOldX = mousePosX;
      mouseOldY = mousePosY;
      mousePosX = event.getSceneX();
      mousePosY = event.getSceneY();
      mouseDeltaX = (mousePosX - mouseOldX);
      mouseDeltaY = (mousePosY - mouseOldY);

      double flip = -1.0;

      if (event.isPrimaryButtonDown())
      {
        if (event.isShiftDown() && event.isControlDown())
        {
          cameraRotate.ry.setAngle(cameraRotate.ry.getAngle() - yFlip
              * mouseDeltaX * modifierFactor * modifier * 2.0);
          contentTranslate.setZ(contentTranslate.getZ() - yFlip * mouseDeltaY
              * modifierFactor * modifier * 0.3); // -
        }
        else if (event.isControlDown())
        {
          contentTranslate.setX(contentTranslate.getX() - flip * mouseDeltaX
              * modifierFactor * modifier * 0.3); // -
          contentTranslate.setY(contentTranslate.getY() - yFlip * mouseDeltaY
              * modifierFactor * modifier * 0.3); // -
        }
        else if (event.isShiftDown())
        {
          cameraTranslate.t.setX(cameraTranslate.t.getX() + flip * mouseDeltaX
              * modifierFactor * modifier * 0.3); // -
          cameraTranslate.t.setY(cameraTranslate.t.getY() + yFlip * mouseDeltaY
              * modifierFactor * modifier * 0.3); // -
        }
        else
        {
          cameraRotate.rz.setAngle(cameraRotate.rz.getAngle() + flip
              * mouseDeltaX * modifierFactor * modifier * 2.0); // -
          cameraRotate.rx.setAngle(cameraRotate.rx.getAngle() + flip
              * mouseDeltaY * modifierFactor * modifier * 2.0); // -
        }
      }
    }
    else if (event.getEventType() == MouseEvent.MOUSE_RELEASED
        && picked != null)
    {
      // TODO: Allow picking
    }
  };

  private final EventHandler<ScrollEvent> scrollEventHandler = event ->
  {
    // Touch pad
    if (event.getTouchCount() > 0)
    {
      cameraTranslate.t.setX(cameraTranslate.t.getX()
          - (0.01 * event.getDeltaX())); // -
      cameraTranslate.t.setY(cameraTranslate.t.getY()
          + (0.01 * event.getDeltaY())); // -
    }
    else
    {
      double z = cameraPosition.getZ() + (event.getDeltaY() * 0.2);
      z = Math.max(z, -1000);
      z = Math.min(z, 0);
      cameraPosition.setZ(z);
    }
  };

  private final EventHandler<ZoomEvent> zoomEventHandler = event ->
  {
    if (!Double.isNaN(event.getZoomFactor()) && event.getZoomFactor() > 0.8
        && event.getZoomFactor() < 1.2)
    {
      double z = cameraPosition.getZ() / event.getZoomFactor();
      z = Math.max(z, -1000);
      z = Math.min(z, 0);
      cameraPosition.setZ(z);
    }
  };

  private final EventHandler<KeyEvent> keyEventHandler = event ->
  {
    // TODO: Add back timeline control
    /*
     * Timeline timeline = getTimeline(); Duration currentTime;
     */
    double CONTROL_MULTIPLIER = 0.1;
    double SHIFT_MULTIPLIER = 0.1;
    double ALT_MULTIPLIER = 0.5;

    switch (event.getCode())
    {
    case Z:
      resetCamera();
      break;
    case UP:
      if (event.isControlDown() && event.isShiftDown())
      {
        cameraTranslate.t.setY(cameraTranslate.t.getY() - 10.0
            * CONTROL_MULTIPLIER);
      }
      else if (event.isAltDown() && event.isShiftDown())
      {
        cameraRotate.rx.setAngle(cameraRotate.rx.getAngle() - 10.0
            * ALT_MULTIPLIER);
      }
      else if (event.isControlDown())
      {
        cameraTranslate.t.setY(cameraTranslate.t.getY() - 1.0
            * CONTROL_MULTIPLIER);
      }
      else if (event.isAltDown())
      {
        cameraRotate.rx.setAngle(cameraRotate.rx.getAngle() - 2.0
            * ALT_MULTIPLIER);
      }
      else if (event.isShiftDown())
      {
        double z = camera.getTranslateZ();
        double newZ = z + 5.0 * SHIFT_MULTIPLIER;
        camera.setTranslateZ(newZ);
      }
      break;
    case DOWN:
      if (event.isControlDown() && event.isShiftDown())
      {
        cameraTranslate.t.setY(cameraTranslate.t.getY() + 10.0
            * CONTROL_MULTIPLIER);
      }
      else if (event.isAltDown() && event.isShiftDown())
      {
        cameraRotate.rx.setAngle(cameraRotate.rx.getAngle() + 10.0
            * ALT_MULTIPLIER);
      }
      else if (event.isControlDown())
      {
        cameraTranslate.t.setY(cameraTranslate.t.getY() + 1.0
            * CONTROL_MULTIPLIER);
      }
      else if (event.isAltDown())
      {
        cameraRotate.rx.setAngle(cameraRotate.rx.getAngle() + 2.0
            * ALT_MULTIPLIER);
      }
      else if (event.isShiftDown())
      {
        double z = camera.getTranslateZ();
        double newZ = z - 5.0 * SHIFT_MULTIPLIER;
        camera.setTranslateZ(newZ);
      }
      break;
    case RIGHT:
      if (event.isControlDown() && event.isShiftDown())
      {
        cameraTranslate.t.setX(cameraTranslate.t.getX() + 10.0
            * CONTROL_MULTIPLIER);
      }
      else if (event.isAltDown() && event.isShiftDown())
      {
        cameraRotate.ry.setAngle(cameraRotate.ry.getAngle() - 10.0
            * ALT_MULTIPLIER);
      }
      else if (event.isControlDown())
      {
        cameraTranslate.t.setX(cameraTranslate.t.getX() + 1.0
            * CONTROL_MULTIPLIER);
      }
      /*
       * else if (event.isShiftDown()) { currentTime =
       * timeline.getCurrentTime();
       * timeline.jumpTo(Frame.frame(Math.round(Frame.
       * toFrame(currentTime)/10.0)*10 + 10)); //
       * timeline.jumpTo(Duration.seconds(currentTime.toSeconds() + ONE_FRAME));
       * }
       */else if (event.isAltDown())
      {
        cameraRotate.ry.setAngle(cameraRotate.ry.getAngle() - 2.0
            * ALT_MULTIPLIER);
      }
      /*
       * else { currentTime = timeline.getCurrentTime();
       * timeline.jumpTo(Frame.frame(Frame.toFrame(currentTime) + 1)); //
       * timeline.jumpTo(Duration.seconds(currentTime.toSeconds() + ONE_FRAME));
       * }
       */break;
    case LEFT:
      if (event.isControlDown() && event.isShiftDown())
      {
        cameraTranslate.t.setX(cameraTranslate.t.getX() - 10.0
            * CONTROL_MULTIPLIER);
      }
      else if (event.isAltDown() && event.isShiftDown())
      {
        cameraRotate.ry.setAngle(cameraRotate.ry.getAngle() + 10.0
            * ALT_MULTIPLIER); // -
      }
      else if (event.isControlDown())
      {
        cameraTranslate.t.setX(cameraTranslate.t.getX() - 1.0
            * CONTROL_MULTIPLIER);
      }
      /*
       * else if (event.isShiftDown()) { currentTime =
       * timeline.getCurrentTime();
       * timeline.jumpTo(Frame.frame(Math.round(Frame.
       * toFrame(currentTime)/10.0)*10 - 10)); //
       * timeline.jumpTo(Duration.seconds(currentTime.toSeconds() - ONE_FRAME));
       * }
       */else if (event.isAltDown())
      {
        cameraRotate.ry.setAngle(cameraRotate.ry.getAngle() + 2.0
            * ALT_MULTIPLIER); // -
      }
      /*
       * else { currentTime = timeline.getCurrentTime();
       * timeline.jumpTo(Frame.frame(Frame.toFrame(currentTime) - 1)); //
       * timeline.jumpTo(Duration.seconds(currentTime.toSeconds() - ONE_FRAME));
       * }
       */break;
    default:
      break;
    }
  };

  public void initializeCamera()
  {
    camera.setNearClip(CAMERA_CLIP_NEAR);
    camera.setFarClip(CAMERA_CLIP_FAR);

    Rotate yUpRotate = new Rotate(180, 0, 0, 0, Rotate.X_AXIS);

    camera.getTransforms().addAll(yUpRotate, cameraPosition);

    cameraRotate.getChildren().add(cameraTranslate);
    cameraTranslate.getChildren().add(camera);

    sceneGroup.getChildren().add(cameraRotate);

    resetCamera();
  }

  public void resetCamera()
  {
    cameraRotate.rx.setAngle(CAMERA_ANGLE);
    cameraRotate.ry.setAngle(0);
    cameraRotate.rz.setAngle(0);

    cameraPosition.setZ(-CAMERA_DISTANCE);

    cameraTranslate.t.setX(0);
    cameraTranslate.t.setY(0);

    setViewPoint(0d, 0d, 0d);
  }

  private void rebuildSubScene()
  {
    SubScene oldSubScene = this.subScene.get();

    if (oldSubScene != null)
    {
      oldSubScene.setRoot(new Region());
      oldSubScene.setCamera(null);
      oldSubScene.removeEventHandler(MouseEvent.ANY, mouseEventHandler);
      oldSubScene.removeEventHandler(KeyEvent.ANY, keyEventHandler);
      oldSubScene.removeEventHandler(ScrollEvent.ANY, scrollEventHandler);
    }

    SubScene subScene = new SubScene(sceneGroup, 400, 400, true,
        SceneAntialiasing.DISABLED);

    this.subScene.set(subScene);

    subScene.setFill(Color.TRANSPARENT);
    subScene.setCamera(camera);

    subScene.addEventHandler(MouseEvent.ANY, mouseEventHandler);
    subScene.addEventHandler(KeyEvent.ANY, keyEventHandler);
    subScene.addEventHandler(ZoomEvent.ANY, zoomEventHandler);
    subScene.addEventHandler(ScrollEvent.ANY, scrollEventHandler);
  }

  public void setViewPoint(Double x, Double y, Double z)
  {
    if (x != null)
    {
      contentTranslate.setX(x);
    }

    if (y != null)
    {
      contentTranslate.setY(y);
    }

    if (z != null)
    {
      contentTranslate.setZ(z);
    }
  }

  private PhongMaterial createMaterial(Color diffuse, Color specular)
  {
    final PhongMaterial material = new PhongMaterial();
    material.setDiffuseColor(diffuse);
    material.setSpecularColor(specular);
    return material;
  }

  private PhongMaterial createMaterial(Color diffuse, Color specular,
      Double specularPower)
  {
    PhongMaterial material = createMaterial(diffuse, specular);
    material.setSpecularPower(specularPower);
    return material;
  }

  private void createFeatures()
  {
    xSphere = new Sphere(AXIS_RADIUS);
    ySphere = new Sphere(AXIS_RADIUS);
    zSphere = new Sphere(AXIS_RADIUS);

    xSphere.setTranslateX(AXIS_LENGTH);
    xSphere.setMaterial(redMaterial);

    ySphere.setTranslateY(AXIS_LENGTH);
    ySphere.setMaterial(greenMaterial);

    zSphere.setTranslateZ(AXIS_LENGTH);
    zSphere.setMaterial(blueMaterial);

    xAxis = new Box(1, AXIS_WIDTH, AXIS_WIDTH);
    yAxis = new Box(AXIS_WIDTH, 1, AXIS_WIDTH);
    zAxis = new Box(AXIS_WIDTH, AXIS_WIDTH, 1);

    xAxis.setScaleX(AXIS_LENGTH);
    xAxis.setTranslateX(AXIS_LENGTH * 0.5);
    xAxis.setMaterial(redMaterial);

    yAxis.setScaleY(AXIS_LENGTH);
    yAxis.setTranslateY(AXIS_LENGTH * 0.5);
    yAxis.setMaterial(greenMaterial);

    zAxis.setScaleZ(AXIS_LENGTH);
    zAxis.setTranslateZ(AXIS_LENGTH * 0.5);
    zAxis.setMaterial(blueMaterial);

    plate = new Box(1, 1, PLATE_THICKNESS);
    plate.setMaterial(plateMaterial);
    plate.setTranslateZ(-PLATE_THICKNESS * 0.5);
    plate.setScaleX(AXIS_LENGTH);
    plate.setScaleY(AXIS_LENGTH);
    plate.setTranslateX(AXIS_LENGTH * 0.5);
    plate.setTranslateY(AXIS_LENGTH * 0.5);

    printHead = new Cylinder(PRINT_HEAD_RADIUS, PRINT_HEAD_HEIGHT);
    printHead.setMaterial(printHeadMaterial);
    printHead.setTranslateZ(-PRINT_HEAD_HEIGHT / 2);

    axisGroup = new Group();
    axisGroup.getChildren().addAll(xSphere, xAxis, ySphere, yAxis, zSphere,
        zAxis);
    axisGroup.getTransforms().add(contentTranslate);

    plateView = new Group();
    plateView.getChildren().add(plate);
    plateView.getTransforms().add(contentTranslate);

    printHeadView = new Group();
    printHeadView.getChildren().add(printHead);
    printHeadView.getTransforms().add(contentTranslate);

    autoScalingGroup.getChildren().addAll(axisGroup, plateView, printHeadView);
  }
}
