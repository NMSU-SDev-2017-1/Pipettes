package pipettes.core;

import javafx.geometry.Point3D;

public class Common
{
  @SuppressWarnings("rawtypes")
  public static final Class allClasses[] = { Process.class, Container.class,
      DispenseProcedure.class, CylindricalGCodeDevice.class,
      RectangularGCodeDevice.class, Library.class };

  // Point3D does not provide a clone method or a constructor that
  // copies a whole Point3D, so this is used
  public static Point3D newPoint3D(Point3D point)
  {
    return new Point3D(point.getX(), point.getY(), point.getZ());
  }

  public static String removeFileNameExtension(String fileName)
  {
    int endIndex = fileName.lastIndexOf('.');

    if (endIndex >= 0)
    {
      fileName = fileName.substring(0, endIndex);
    }

    return fileName;
  }

  public static String removeTrailingInteger(String text)
  {
    int offset = text.length();
    
    for (int i = offset - 1; i >= 0; i--)
    {
      char c = text.charAt(i);
      
      if (Character.isDigit(c))
      {
        offset = i;
      }
      else
      {
        break;
      }
    }
    
    return text.substring(0, offset);
  }
  
  public static String appendInteger(String prefix, int number)
  {
    if (number == 0)
    {
      return prefix;
    }
    else
    {
      return prefix + number;
    }
  }
}
