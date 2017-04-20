package pipettes.core;

import java.io.File;

import javafx.geometry.Point3D;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

public class XmlSerializationTest
{
  public static void main(String[] args)
  {
    String processFileName = args[0];
    String deviceCylindricalFileName = args[1];
    String deviceRectangularFileName = args[2];

    File processFile = new File(processFileName);
    File deviceCylindricalFile = new File(deviceCylindricalFileName);
    File deviceRectangularFile = new File(deviceRectangularFileName);

    Container source = new Container();
    source.setLocalName("Beaker 1");
    source.setLocalPosition(new Point3D(-70.0, -50.0, 0.0));
    source.setSize(new Point3D(10.0, 10.0, 60.0));
    source.setShape(ContainerShape.Cylindrical);
    source.setDrawHeightAboveBottom(4.0);
    source.setDispenseHeightAboveTop(5.0);
    source.setClearanceHeightAboveTop(5.0);
    
    Container destination = new Container();
    destination.setLocalName("Beaker 2");
    destination.setLocalPosition(new Point3D(60.0, 40.0, 0.0));
    destination.setSize(new Point3D(10.0, 10.0, 40.0));
    destination.setShape(ContainerShape.Cylindrical);
    destination.setDrawHeightAboveBottom(5.0);
    destination.setDispenseHeightAboveTop(10.0);
    destination.setClearanceHeightAboveTop(10.0);

    Container sample = new Container();
    sample.setLocalName("Beaker 3");
    sample.setLocalPosition(new Point3D(-90.0, 90.0, 0.0));
    sample.setSize(new Point3D(10.0, 10.0, 40.0));
    sample.setShape(ContainerShape.Rectangular);
    sample.setDrawHeightAboveBottom(6.0);
    sample.setDispenseHeightAboveTop(7.0);
    sample.setClearanceHeightAboveTop(7.0);

    Container sampleRack = new Container();
    sampleRack.setLocalName("Tips container");
    sampleRack.setLocalPosition(new Point3D(-90.0, 90.0, 0.0));
    sampleRack.setSize(new Point3D(8.85,2.625,1.125));
    sampleRack.setShape(ContainerShape.Rectangular);
    sampleRack.setDrawHeightAboveBottom(0.125);
    sampleRack.setClearanceHeightAboveTop(2.0);
    
    double positionX = sampleRack.getSizeX()/32;
    double positionY = sampleRack.getSizeY()/10;
    for(int i = 1; i<=80;i++){
      Container subcontainer = new Container();
      subcontainer.setLocalName(Integer.toString(i));
      subcontainer.setLocalPosition(new Point3D(positionX,positionY,0.0));
      subcontainer.setSize(new Point3D(6.0, 2.0, 0.0));
      subcontainer.setShape(ContainerShape.Cylindrical);
      subcontainer.setDrawHeightAboveBottom(0.125);
      subcontainer.setClearanceHeightAboveTop(2.5);
      sampleRack.addSubcontainer(subcontainer);
      positionX = positionX + 2*positionX;
      if (i%16==0){
        positionX = sampleRack.getSizeX()/32;
        positionY = positionY + 2*positionY;
      }
    }

    
    Process process = new Process();
    
    DispenseProcedure procedure1 = new DispenseProcedure();
    procedure1.setSource(source);
    procedure1.setDestination(destination);
    procedure1.setVolume(10.0);
    process.addProcedure(procedure1);
    
    DispenseProcedure procedure2 = new DispenseProcedure();
    procedure2.setSource(source);
    procedure2.setDestination(destination);
    procedure2.setVolume(10.0);
    process.addProcedure(procedure2);

    DispenseProcedure procedure3 = new DispenseProcedure();
    procedure3.setSource(source);
    procedure3.setDestination(destination);
    procedure3.setVolume(10.0);
    process.addProcedure(procedure3);
    
    DispenseProcedure procedure4 = new DispenseProcedure();
    procedure4.setSource(destination);
    procedure4.setDestination(sample);
    procedure4.setVolume(5.0);
    process.addProcedure(procedure4);
    
    CylindricalGCodeDevice deviceCylindrical = new CylindricalGCodeDevice();
    deviceCylindrical.setName("SeeMeCNC Rostock MAX v2");
    deviceCylindrical.setExtrudePerVolume(20.0);
    deviceCylindrical.setDispenseExtrudeRatio(1.01);
    deviceCylindrical.setRadius(140.0);
    deviceCylindrical.setMinimumZ(0.0);
    deviceCylindrical.setMaximumZ(375.0);
    
    RectangularGCodeDevice deviceRectangular = new RectangularGCodeDevice();
    deviceRectangular.setName("MakerBot Replicator");
    deviceRectangular.setExtrudePerVolume(20.0);
    deviceRectangular.setDispenseExtrudeRatio(1.01);
    deviceRectangular.setHomePosition(new Point3D(50.0, 75.0, 130.0));
    deviceRectangular.setMinimumExtent(new Point3D(-110.0, -75.0, 0.0));
    deviceRectangular.setMaximumExtent(new Point3D(110.0, 75.0, 130.0));
    
    JAXBContext jaxbContext;

    try
    {
      jaxbContext = JAXBContext.newInstance(Common.allClasses);
      Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

      jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

      jaxbMarshaller.marshal(process, processFile);
      jaxbMarshaller.marshal(deviceCylindrical, deviceCylindricalFile);
      jaxbMarshaller.marshal(deviceRectangular, deviceRectangularFile);
    }
    catch (JAXBException e)
    {
      e.printStackTrace();
    }
  }
}
