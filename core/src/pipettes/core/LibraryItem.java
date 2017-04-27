package pipettes.core;

import javafx.beans.property.StringProperty;

public interface LibraryItem
{
  String getLibraryName();
  
  void setLibraryName(String name) throws NameConflictException;
  
  StringProperty libraryNameProperty();
}
