package pipettes.core;

import java.io.File;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class Library<T extends LibraryItem>
{
  private File libraryFile;

  @XmlElementWrapper(name = "items")
  @XmlAnyElement(lax = true)
  private ObservableList<T> items = FXCollections.observableArrayList();

  public Library()
  {
  }
  
  public Library(String fileName)
  {
    open(fileName);
  }
  
  @SuppressWarnings("unchecked")
  public void open(String fileName)
  {
    libraryFile = new File(fileName);

    JAXBContext jaxbContext;

    try
    {
      jaxbContext = JAXBContext.newInstance(Common.allClasses);
      Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
      Library<T> copy = (Library<T>) jaxbUnmarshaller.unmarshal(libraryFile);
      this.items = copy.items;
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

  public void save()
  {
    JAXBContext jaxbContext;

    try
    {
      jaxbContext = JAXBContext.newInstance(Common.allClasses);
      Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
      jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
      jaxbMarshaller.marshal(this, libraryFile);
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
