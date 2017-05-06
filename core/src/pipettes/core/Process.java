package pipettes.core;

import java.io.File;
import java.util.Iterator;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
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
  private boolean setFileName = false;

  private ObjectProperty<File> file = new SimpleObjectProperty<File>();

  private BooleanProperty dirty = new SimpleBooleanProperty();

  @XmlElement
  private ObservableMap<String, Container> baseContainers = FXCollections
      .observableHashMap();

  @XmlElementWrapper(name = "procedures")
  @XmlAnyElement(lax = true)
  private ObservableList<Procedure> procedures = FXCollections
      .observableArrayList();

  public File getFile()
  {
    return file.get();
  }

  public void setFile(File file)
  {
    this.file.set(file);
    setFileName = true;
  }

  public ObjectProperty<File> fileProperty()
  {
    return file;
  }

  public boolean hasSetFileName()
  {
    return setFileName;
  }

  public String getFileName()
  {
    File file = getFile();

    if (file == null)
    {
      return "Untitled";
    }
    else
    {
      return file.getName();
    }
  }

  public String getFileNameWithExtension()
  {
    return Common.removeFileNameExtension(getFileName());
  }

  public boolean getDirty()
  {
    return dirty.get();
  }

  public void setDirty(boolean dirty)
  {
    this.dirty.set(dirty);
  }
  
  public BooleanProperty dirtyProperty()
  {
    return dirty;
  }

  // TODO: Complete change listener
  public Process()
  {
    procedures.addListener(new ListChangeListener<Procedure>()
    {
      @Override
      public void onChanged(
          ListChangeListener.Change<? extends Procedure> change)
      {
        setDirty(true);
        
        while (change.next())
        {
          if (change.wasUpdated())
          {
            // System.out.println("Update detected");
          }
          else if (change.wasPermutated())
          {
          }
          else
          {
            for (Procedure remitem : change.getRemoved())
            {
              // do things
            }
            for (Procedure additem : change.getAddedSubList())
            {
              // do things
            }
          }
        }
      }
    });
  }

  private void addSubcontainersToContext(ProcessContext context,
      Container container)
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

  public void run(Device device, ProcessLogger logger)
      throws PositioningException
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

  public static Process open(File file) throws JAXBException
  {
    Process process = null;

    JAXBContext jaxbContext = JAXBContext.newInstance(Process.class);
    Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

    process = (Process) jaxbUnmarshaller.unmarshal(file);

    process.setFile(file);

    return process;
  }

  public void save() throws JAXBException
  {
    saveAs(file.get());
  }

  public void saveAs(File file) throws JAXBException
  {
    JAXBContext jaxbContext = JAXBContext.newInstance(Process.class);
    Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

    jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

    jaxbMarshaller.marshal(this, file);

    setFile(file);
    setDirty(false);
  }
}
