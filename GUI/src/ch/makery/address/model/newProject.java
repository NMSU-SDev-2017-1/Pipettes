package ch.makery.address.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class newProject {
	
	private final StringProperty projectName;
	//private final StringProperty projectLocation;
	
	public newProject()  {
		this(null);
	}
	
	public newProject(String projectName)  {
		this.projectName = new SimpleStringProperty(projectName);
		//this.projectLocation = new SimpleStringProperty("");
	}
	
	public String getProjectName()  {
		return projectName.get();
	}
	
	public void setProjectName(String projectName)  {
		this.projectName.set(projectName);
	}
	
	public StringProperty projectNameProperty()  {
		return projectName;
	}

}

