package pipettes.core;

public abstract class Procedure
{
  public abstract String getName();
  
  public abstract Container[] getReferencedContainers();

  public abstract void perform(ProcessContext context)
      throws PositioningException;
}
