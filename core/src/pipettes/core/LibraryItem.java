package pipettes.core;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import javafx.beans.property.StringProperty;

@XmlAccessorType(XmlAccessType.NONE)
public abstract class LibraryItem
{
  public abstract void setLibrary(Library<? extends LibraryItem> library);
  
  public abstract String getLibraryName();
  
  public abstract void setLibraryName(String name);
  
  public abstract StringProperty libraryNameProperty();
}
