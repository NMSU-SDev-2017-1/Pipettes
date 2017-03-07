public abstract class Procedure
{
  public abstract Container[] getReferencedContainers();

  public abstract void perform(ProcessContext context, Device device)
      throws PositioningException;
}
