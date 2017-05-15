# Pipettes

These projects produce a Pipette Process Editor application, which allows users to define pipetting processes using containers and procedures in a graphical user interface. The software can export G-Code which allows most 3D printers to become a pipette robot and perform automated repetitive transport and dispensing of liquids in medical, chemical, and biochemical laboratories.

## Getting Started

These instructions will get you a copy of the projects up and running on your local machine for development and testing purposes. See the "Deployment" section for notes on how to deploy the application.

### Prerequisites

- Java Runtime Environment (JRE) version 1.8.0_51 or later
- Eclipse IDE for Java Developers version 4.4 (Luna) or later

Newer versions of these tools may work, but some adjustments may be necessary.

### Optional Tools

- Git Components for Eclipse
- JavaFX Features in Eclipse
- Scene Builder version 2.0 or later

#### How to Install Git Components in Eclipse

- Run Eclipse.
- Select "Install New Software" from the "Help" menu in Eclipse.
- In the "Available Software" dialog that appears, select "All Available Sites" in the drop down for the "Work with" field.
- Type "git" in the "type filter text" text box immediately below the drop down.
- You may have to wait a few seconds while the list of software components is populated.
- After the list of components displays, check boxes next to the following five components:

  - Eclipse Git Team Provider
  - Eclipse GitHub integration with task focused interface
  - Java implementation of Git
  - Mylyn Versions Connector: Git
  - Task focused interface for Eclipse Git Team Provider

- Click the "Next" button to begin installing these components. It may tell you that some or all of these components are already installed. And, you may have to agree to terms and conditions and click "Finish" before the installation process begins. Then, Eclipse itself will restart to begin using the new components.
- If you do not see a tab in the Eclipse workspace called "Git Repositories", then select the menu item "Window", select "Show View", and select "Other..." A "Show View" dialog will appear. Scroll down and open the "Git" section of the tree, select "Git Repositories", and click the "OK" button. Do the same for the "Git Staging" view.
- The "Git Repositories" and "Git Staging" interfaces are really all you probably need to manage all interactions with Git repositories.

#### How to Install JavaFX Components in Eclipse

- Run Eclipse.
- Select "Install New Software" from the "Help" menu in Eclipse.
- In the "Available Software" dialog that appears, type the following URI into the drop down for the "Work with" field:
http://download.eclipse.org/efxclipse/updates-released/1.2.0/site/
- You may have to wait a few seconds while the list of software components is populated.
- After the list of components displays, check boxes next to the "e(fx)clipse - IDE" item and click the "Next" button to install it. It may tell you that some or all of these components are already installed. And, you may have to agree to terms and conditions and click "Finish" before the installation process begins. Then, Eclipse itself will restart to begin using the new components.

These JavaFX features are not as obvious, but they will assist in working with JavaFX projects, such as editing FXML and CSS files used with JavaFX.

#### How to Install Scene Builder 2.0

Binary downloads and installation packages of Scene Builder are available from Oracle:
http://www.oracle.com/technetwork/java/javase/downloads/javafxscenebuilder-info-2157684.html

#### Configure Eclipse to Open FXML in Scene Builder

- Run Eclipse.
- Select "Preferences" from the "Window" menu in Eclipse.
- Select "JavaFX" in the tree on the left of the "Preferences" dialog that appears.
- Enter the installed location for the Scene Builder executable (tar file).

Now, when you right click on an FXML file in Eclipse, you have the option to select "Open with Scene Builder", which will allow you to visually edit the layout in Scene Builder, instead of just opening the it as XML text in the Eclipse FXML editor.

Depending on what you plan to change in an FXML file, it may be better to open it as XML text within Eclipse (if you need to do a search and replace, for instance), or it may be better to open it in Scene Builder if you want to move or modify controls and immediately see how the layout looks.

## Development



### Importing a Downloaded Copy of the Projects

- Select "Existing Projects into Workspace" from the "General" tree, and click "Next".
- Click the "Browse" button to select the location of the downloaded projects.
- Click "Finish".

### Java Runtime Environment (JRE)

If you are having problems building the projects, you may be referencing an old JRE library in Eclipse. This will be indicated by errors at JavaFX imports for alerts and dialog boxes ("import javafx.scene.control.Alert"). This can be fixed by referencing a newer JRE.

#### Configure Eclipse to Use a Newer JRE

- Run Eclipse.
- Select "Preferences" from the "Window" menu in Eclipse.
- Select "Java \ Installed JREs" in the tree on the left of the "Preferences" dialog that appears.
- Click the "Add.." button to the right of the list of installed JREs.
- Select "Standard VM" in the dialog box and click the "Next" button.
- Enter the following location for the newer JRE. For example:
/usr/jdk1.8.0_51
- Click the "Finish" button.
- Ensure that the "jdk1_8.0_51" or newer is checked and selected as the default.
- Click the "OK" button to close the "Preferences" dialog.

## Organization

The software is separated into five different Eclipse Java projects:

- afterburner.fx

This project contains the Afterburner.fx dependency injection framework, which reduces coupling between components of the user interface by allowing dependencies to be discovered without complicating the construction and interaction between user interface components and their underlying dependencies.

The Afterburner.fx model-view-presenter framework ensures that the view is almost completely defined by FXML files, and the presenter as an interface between the view and the data model, with the model completely decoupled from the user interface. This allows improved testing of the individual components, since the data model can be tested without the presenter or view, and the presenter can be tested against the data model without the view.

- Pipettes Core

This core project contains the major data model classes (containers, devices, procedures, processes, etc...). These classes are JAXB compatible JavaBeans with JavaFX properties to allow data binding to the user interface components, which will allow the user to edit properties and request actions to be performed by these objects. JAXB compatibility will allow data properties of the objects to be written as XML files so that they can be read and used again later.

- Pipettes UI

This project contains the "Pipette Process Editor" application. The visual layout of the user interface is defined in FXML (an XML-based user interface markup language) files which are loaded and bound at runtime to interface with the properties of the objects defined by the core classes. The core classes have been constructed to include bindable properties (along with standard JavaBean getters and setters) to facilitate this process.

- Pipettes XML Test

This project performs a simple test of the JAXB functionality of the core classes to serialize as XML. It is not necessary for building the application.

- Pipettes Processor Test

This project performs a simple test of the core classes to generate G-Code from data models defined as XML. It is not necessary for building the application.

## Deployment

- Run Eclipse.
- Close all projects except for the "afterburner.fx", "Pipettes Core", and "Pipettes UI" projects.
- Select the "Pipettes UI" project in the "Package Explorer" tree.
- Select "Export" from the "File" menu.
- Select "Runnable JAR file" from the "Java" tree, and click "Next".
- Click the "Browse" button to select a name and location to be used for the JAR file. It is suggested that the name of the JAR file be "Pipette Process Editor.jar".
- Click "Finish".

The JAR (Java Archive) file that is created will contain the application and should be executable. On many platforms, you can double-click on the JAR file and the application will run.
