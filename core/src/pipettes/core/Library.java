package pipettes.core;

import java.io.File;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
  private String prefix;
  
  @XmlTransient
  private File libraryFile;
  
  @XmlElement
  private ObservableList<T> items = FXCollections.observableArrayList();
  
  public Library(String prefix)
  {
    this.prefix = prefix;
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
      items = (ObservableList<T>) jaxbUnmarshaller.unmarshal(libraryFile);
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
  
  public ObservableList<T> getItems()
  {
    return items;
  }
  
  public String getAvailableName()
  {
    boolean collision;
    int itemNumber = 1;
    
    do {
      collision = false;
      
      for (LibraryItem item : items)
      {
        if (item.getLibraryName().equals(prefix + itemNumber))
        {
          itemNumber++;
          collision = true;
          break;
        }
      }
    } while (collision);
    
    return prefix + itemNumber;
  }
}
