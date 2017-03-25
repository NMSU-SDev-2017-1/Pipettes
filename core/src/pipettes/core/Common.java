package pipettes.core;

import javafx.geometry.Point3D;

public class Common
{
  @SuppressWarnings("rawtypes")
  public static final Class allClasses[] = {Process.class, Container.class, DispenseProcedure.class, CylindricalGCodeDevice.class, RectangularGCodeDevice.class};
  
  @SuppressWarnings("rawtypes")
  public static final Class processClasses[] = {Process.class, Container.class, DispenseProcedure.class};
  
  @SuppressWarnings("rawtypes")
  public static final Class deviceClasses[] = {CylindricalGCodeDevice.class, RectangularGCodeDevice.class};  
  
  // Point3D does not provide a clone method or a constructor that
  // copies a whole Point3D, so this is used
  public static Point3D newPoint3D(Point3D point)
  {
    return new Point3D(point.getX(), point.getY(), point.getZ()); 
  }
}
