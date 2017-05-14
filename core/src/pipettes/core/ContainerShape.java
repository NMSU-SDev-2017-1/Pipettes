package pipettes.core;

public enum ContainerShape
{
  Rectangular("Rectangular"), Cylindrical("Cylindrical");

  private final String description;

  private ContainerShape(String description)
  {
    this.description = description;
  }

  @Override
  public String toString()
  {
    return description;
  }
}
