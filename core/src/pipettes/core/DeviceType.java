package pipettes.core;

public enum DeviceType
{
  RectangularGCode("Rectangular G-Code 3D Printer"), CylindricalGCode("Cylindrical G-Code 3D Printer");
  
  private final String description;

  private DeviceType(String description)
  {
    this.description = description;
  }
    
  @Override
  public String toString()
  {
    return description;
  }
}
