import java.io.File;

import javafx.geometry.Point3D;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

public class XmlSerializationTest
{
  public static void main(String[] args)
  {
    File processFile = new File("TestProcess.xml");
    File deviceCylindricalFile = new File("TestDeviceCylindrical.xml");
    File deviceRectangularFile = new File("TestDeviceRectangular.xml");

    BasicContainer source = new BasicContainer();
    source.setLocalName("Beaker 1");
    source.setLocalPosition(new Point3D(-70.0, -50.0, 0.0));
    source.setSize(new Point3D(10.0, 10.0, 60.0));
    source.setShape(ContainerShape.Cylindrical);
    source.setDrawHeightAboveBottom(4.0);
    source.setDispenseHeightAboveTop(5.0);
    source.setClearanceHeightAboveTop(5.0);
    
    BasicContainer destination = new BasicContainer();
    destination.setLocalName("Beaker 2");
    destination.setLocalPosition(new Point3D(60.0, 40.0, 0.0));
    destination.setSize(new Point3D(10.0, 10.0, 40.0));
    destination.setShape(ContainerShape.Cylindrical);
    destination.setDrawHeightAboveBottom(5.0);
    destination.setDispenseHeightAboveTop(10.0);
    destination.setClearanceHeightAboveTop(10.0);

    BasicContainer sample = new BasicContainer();
    sample.setLocalName("Beaker 3");
    sample.setLocalPosition(new Point3D(-90.0, 90.0, 0.0));
    sample.setSize(new Point3D(10.0, 10.0, 40.0));
    sample.setShape(ContainerShape.Rectangular);
    sample.setDrawHeightAboveBottom(6.0);
    sample.setDispenseHeightAboveTop(7.0);
    sample.setClearanceHeightAboveTop(7.0);
    
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
    deviceCylindrical.setExtrudePerVolumeRatio(20.0);
    deviceCylindrical.setDispenseExtrudeRatio(1.01);
    deviceCylindrical.setRadius(140.0);
    deviceCylindrical.setMinimumZ(0.0);
    deviceCylindrical.setMaximumZ(375.0);
    
    RectangularGCodeDevice deviceRectangular = new RectangularGCodeDevice();
    deviceCylindrical.setExtrudePerVolumeRatio(20.0);
    deviceCylindrical.setDispenseExtrudeRatio(1.01);
    deviceRectangular.setHomePosition(new Point3D(50.0, 75.0, 130.0));
    deviceRectangular.setMinimumExtent(new Point3D(-110.0, -75.0, 0.0));
    deviceRectangular.setMaximumExtent(new Point3D(110.0, 75.0, 130.0));
    
    JAXBContext jaxbContext;

    try
    {
      jaxbContext = JAXBContext.newInstance(Common.xmlClasses);
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
