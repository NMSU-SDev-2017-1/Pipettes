package pipettes.core;

import javafx.beans.property.StringProperty;

public abstract class LibraryItem
{
  public abstract void setLibrary(Library<? extends LibraryItem> library);
  
  public abstract String getLibraryName();
  
  public abstract void setLibraryName(String name);
  
  public abstract StringProperty libraryNameProperty();
}
