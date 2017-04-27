package pipettes.core;

import java.io.File;

import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class Library<T extends LibraryItem>
{
  @XmlTransient
  private File libraryFile;
  
  @XmlElement
  private ObservableMap<String, T> items = FXCollections.observableHashMap();
  
  public Library()
  {
  }
  
  public Library(String fileName)
  {
    Open(fileName);
  }
  
  @SuppressWarnings("unchecked")
  public void Open(String fileName)
  {
    libraryFile = new File(fileName);
    
    JAXBContext jaxbContext;

    try
    {
      jaxbContext = JAXBContext.newInstance(ObservableMap.class);
      Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
      items = (ObservableMap<String, T>) jaxbUnmarshaller.unmarshal(libraryFile);
    }
    catch (JAXBException e)
    {
      e.printStackTrace();
    }
  }
  
  public void Save(String fileName)
  {
    libraryFile = new File(fileName);
    Save();
  }
  
  public void Save()
  {
    JAXBContext jaxbContext;

    try
    {
      jaxbContext = JAXBContext.newInstance(ObservableMap.class);
      Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
      jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
      jaxbMarshaller.marshal(items, libraryFile);
    }
    catch (JAXBException e)
    {
      e.printStackTrace();
    }
  }
  
  public ObservableMap<String, T> getItems()
  {
    return items;
  }
  
  public void addLibraryItem(T item) throws NameConflictException
  {
    String libraryName = item.getLibraryName();
    
    // Prevent name collisions between subcontainers
    if (items.containsKey(libraryName))
    {
      throw new NameConflictException();
    }
    
    items.put(libraryName, item);
  }
}
