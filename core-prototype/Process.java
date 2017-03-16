import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Process
{
  // TODO: HashMap based on name possibly not the best collection
  private HashMap<String, Container> baseContainers = new HashMap<String, Container>();

  @XmlElementWrapper(name = "procedures")
  @XmlAnyElement(lax = true)
  private ArrayList<Procedure> procedures = new ArrayList<Procedure>();

  
  private void addContainerToContext(ProcessContext context, Container container)
  {
    context.addContainer(container);
    
    if (container.hasSubcontainers())
    {
      Iterator<Container> subcontainers = container.getSubcontainerIterator();
      while (subcontainers.hasNext())
      {
        addContainerToContext(context, subcontainers.next());
      }
    }
  }
  
  // TODO: Initialize with information necessary for procedures to perform
  // their operation
  public ProcessContext createContext(Device device)
  {
    ProcessContext context = new ProcessContext();
    
    for (Container container : baseContainers.values())
    {
      addContainerToContext(context, container);
    }
    
    return context;
  }

  private void verifyContainer(Container container)
  {
    while (container.getParent() != null)
    {
      container = container.getParent();
    }
    
    Container mappedContainer = baseContainers.get(container.getName());

    if (mappedContainer == null)
    {
      // TODO: Container not included in mapping
    }
    else if (mappedContainer != container)
    {
      // TODO: Container names conflict
    }
  }

  private void verifyContainers()
  {
    for (Procedure procedure : procedures)
    {
      for (Container container : procedure.getReferencedContainers())
      {
        verifyContainer(container);
      }
    }
  }

  public void run(Device device) throws PositioningException
  {
    verifyContainers();

    ProcessContext context = createContext(device);

    for (Procedure procedure : procedures)
    {
      procedure.perform(context, device);
    }
  }

  public void addContainer(Container container)
  {
    baseContainers.put(container.getName(), container);
  }

  public void addProcedure(Procedure procedure)
  {
    procedures.add(procedure);

    for (Container container : procedure.getReferencedContainers())
    {
      addContainer(container);
    }
  }
}
