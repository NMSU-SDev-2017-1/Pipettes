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

@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class Library<T extends LibraryItem>
{
  private File libraryFile;

  @XmlElement
  private ObservableList<T> items = FXCollections.observableArrayList();

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
      jaxbContext = JAXBContext.newInstance(Library.class);
      Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
      items = (ObservableList<T>) jaxbUnmarshaller.unmarshal(libraryFile);
    }
    catch (JAXBException e)
    {
      e.printStackTrace();
    }

    for (LibraryItem item : items)
    {
      item.setLibrary(this);
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
      jaxbContext = JAXBContext.newInstance(Library.class);
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

  public String getAvailableName(String prefix)
  {
    String result = Common.removeTrailingInteger(prefix);
    
    if (result.length() == 0)
    {
      result = prefix;
    }
    
    boolean collision;
    int number = 0;

    do
    {
      collision = false;

      for (LibraryItem item : items)
      {
        if (item.getLibraryName().equals(Common.appendInteger(result, number)))
        {
          number++;
          collision = true;
          break;
        }
      }
    } while (collision);

    return Common.appendInteger(result, number);
  }

  public boolean isValidNameChange(String name)
  {
    if (name.length() == 0)
    {
      return false;
    }

    int count = 0;
    for (LibraryItem item : items)
    {
      if (item.getLibraryName().equals(name))
      {
        count++;

        if (count > 1)
        {
          return false;
        }
      }
    }

    return true;
  }
}
