package pipettes.ui;

import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

public class Common
{
  public static final Rotate rotateX180 = new Rotate(180, 0, 0, 0,
      Rotate.X_AXIS);
  public static final Rotate rotateX90 = new Rotate(90, 0, 0, 0, Rotate.X_AXIS);
  public static final Translate translateZ0_5 = new Translate(0, 0, 0.5);

  public static Color createColor(Color color, double opacity)
  {
    return new Color(color.getRed(), color.getGreen(), color.getBlue(), opacity);
  }

  public static PhongMaterial createMaterial(Color diffuse)
  {
    final PhongMaterial material = new PhongMaterial();
    material.setDiffuseColor(diffuse);
    return material;
  }

  public static PhongMaterial createMaterial(Color diffuse, Color specular)
  {
    final PhongMaterial material = new PhongMaterial();
    material.setDiffuseColor(diffuse);
    material.setSpecularColor(specular);
    return material;
  }

  public static PhongMaterial createMaterial(Color diffuse, Color specular,
      Double specularPower)
  {
    PhongMaterial material = createMaterial(diffuse, specular);
    material.setSpecularPower(specularPower);
    return material;
  }

}
