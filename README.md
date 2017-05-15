# Pipettes

This project produces a Pipette Process Editor application, which allows users to define pipetting processes using containers and procedures in a graphical user interface. The software can export G-Code which allows most 3D printers to become a pipette robot and perform automated repetitive transport and dispensing of liquids in medical, chemical, and biochemical laboratories.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites

- Java Runtime Environment version 1.8.0_51 or later
- Eclipse IDE version 4.4 (Luna) or later

Newer versions may work, but adjustments may be necessary.

### Optional Tools

- Git Components for Eclipse
- JavaFX Features in Eclipse
- Scene Builder version 2.0 or later

#### Install Git Components in Eclipse

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
  - The "Git Repositories" and "Git Staging" interfaces are really all you probably need to manage all our interactions with GitHub.

#### Install JavaFX Components in Eclipse

  - Run Eclipse.
  - Select "Install New Software" from the "Help" menu in Eclipse.
  - In the "Available Software" dialog that appears, type the following URI into the drop down for the "Work with" field:
http://download.eclipse.org/efxclipse/updates-released/1.2.0/site/
  - You may have to wait a few seconds while the list of software components is populated.
  - After the list of components displays, check boxes next to the "e(fx)clipse - IDE" item and click the "Next" button to install it. It may tell you that some or all of these components are already installed. And, you may have to agree to terms and conditions and click "Finish" before the installation process begins. Then, Eclipse itself will restart to begin using the new components.
  - These JavaFX features are not as obvious, but they will assist in working with JavaFX projects, and editing FXML and CSS files used with JavaFX.

#### Install JavaFX Components in Eclipse

#### Configure Eclipse to Open FXML in Scene Builder

- Run Eclipse.
- Select "Preferences" from the "Window" menu in Eclipse.
- Select "JavaFX" in the tree on the left of the "Preferences" dialog that appears.
- Enter the installed location for the Scene Builder executable.

Now, when you right click on an FXML file in Eclipse, you have the option to select "Open with Scene Builder", which will allow you to visually edit the layout in Scene Builder, instead of just opening the it as XML text in the Eclipse FXML editor.

Depending on what you plan to change about an FXML file, it may be better to open it as XML text within Eclipse (if you need to do a bunch of search and replace, for instance), or it may be better to open it in Scene Builder if you want to move around controls and immediately see how the layout looks.
