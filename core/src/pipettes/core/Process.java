package pipettes.core;

import java.util.Iterator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class Process
{
  @XmlElement
  private ObservableMap<String, Container> baseContainers = FXCollections.observableHashMap();

  @XmlElementWrapper(name = "procedures")
  @XmlAnyElement(lax = true)
  private ObservableList<Procedure> procedures = FXCollections.observableArrayList();
  
  private void addSubcontainersToContext(ProcessContext context, Container container)
  {
    context.addContainer(container);
    
    if (container.hasSubcontainers())
    {
      Iterator<Container> subcontainers = container.getSubcontainerIterator();
      while (subcontainers.hasNext())
      {
        addSubcontainersToContext(context, subcontainers.next());
      }
    }
  }
  
  // TODO: Initialize with information necessary for procedures to perform
  // their operation
  public ProcessContext createContext(Device device, ProcessLogger logger)
  {
    ProcessContext context = new ProcessContext(device, logger);
    
    for (Container container : baseContainers.values())
    {
      addSubcontainersToContext(context, container);
    }
    
    return context;
  }

  private Container getBaseContainer(Container container)
  {
    while (container.getParent() != null)
    {
      container = container.getParent();
    }
    
    return container;
  }  
  
  private void verifyContainer(Container container)
  {
    container = getBaseContainer(container);
    
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

  private void verifyProcedures()
  {
    for (Object procedure : procedures)
    {
      // This can happen if a procedure is incorrectly defined in XML
      if (!Procedure.class.isAssignableFrom(procedure.getClass()))
      {
        // TODO: Throw an exception
      }
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

  public void run(Device device, ProcessLogger logger) throws PositioningException
  {
    verifyProcedures();
    verifyContainers();

    ProcessContext context = createContext(device, logger);

    for (Procedure procedure : procedures)
    {
      procedure.perform(context);
    }
  }
  
  public ObservableMap<String, Container> getBaseContainers()
  {
    return baseContainers;
  }

  public ObservableList<Procedure> getProcedures()
  {
    return procedures;
  }
  
  public void addContainer(Container container)
  {
    container = getBaseContainer(container);
    
    baseContainers.put(container.getName(), container);
  }

  public void addProcedure(Procedure procedure)
  {
    for (Container container : procedure.getReferencedContainers())
    {
      addContainer(container);
    }
    
    procedures.add(procedure);
  }
}
