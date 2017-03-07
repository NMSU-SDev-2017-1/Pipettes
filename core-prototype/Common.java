import javafx.geometry.Point3D;

public class Common
{
  @SuppressWarnings("rawtypes")
  public static final Class xmlClasses[] = {Process.class, BasicContainer.class, DispenseProcedure.class, CylindricalGCodeDevice.class, RectangularGCodeDevice.class};  
  
  public static Point3D newPoint3D(Point3D point)
  {
    return new Point3D(point.getX(), point.getY(), point.getZ()); 
  }
}
