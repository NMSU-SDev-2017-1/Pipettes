package pipettes.core;

import java.util.ArrayList;
import java.util.HashMap;
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
  private HashMap<String, Container> baseContainers = new HashMap<String, Container>();
  
  private ObservableMap<String, Container> observableBaseContainers = FXCollections.observableMap(baseContainers);

  @XmlElementWrapper(name = "procedures")
  @XmlAnyElement(lax = true)
  private ArrayList<Procedure> procedures = new ArrayList<Procedure>();

  private ObservableList<Procedure> observableProcedures = FXCollections.observableArrayList(procedures);
  
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
  public ProcessContext createContext(Device device)
  {
    ProcessContext context = new ProcessContext();
    
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

  public void run(Device device) throws PositioningException
  {
    verifyProcedures();
    verifyContainers();

    ProcessContext context = createContext(device);

    for (Procedure procedure : procedures)
    {
      procedure.perform(context, device);
    }
  }
  
  public ObservableMap<String, Container> getBaseContainers()
  {
    return observableBaseContainers;
  }

  public ObservableList<Procedure> getProcedures()
  {
    return observableProcedures;
  }
  
  public void addContainer(Container container)
  {
    container = getBaseContainer(container);
    
    observableBaseContainers.put(container.getName(), container);
  }

  public void addProcedure(Procedure procedure)
  {
    for (Container container : procedure.getReferencedContainers())
    {
      addContainer(container);
    }
    
    observableProcedures.add(procedure);
  }
}
