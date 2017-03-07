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
    source.setLocalPosition(new Point3D(-70.0f, -50.0f, 0.0f));
    source.setSize(new Point3D(10.0f, 10.0f, 80.0f));
    source.setShape(ContainerShape.Cylindrical);
    source.setDrawHeightAboveBottom(4.0f);
    source.setDispenseHeightAboveTop(5.0f);
    source.setClearanceHeightAboveTop(5.0f);
    
    BasicContainer destination = new BasicContainer();
    destination.setLocalName("Beaker 2");
    destination.setLocalPosition(new Point3D(60.0f, 70.0f, 0.0f));
    destination.setSize(new Point3D(10.0f, 10.0f, 50.0f));
    destination.setShape(ContainerShape.Rectangular);
    destination.setDrawHeightAboveBottom(5.0f);
    destination.setDispenseHeightAboveTop(10.0f);
    destination.setClearanceHeightAboveTop(10.0f);
    
    Process process = new Process();
    
    DispenseProcedure procedure1 = new DispenseProcedure();
    procedure1.setSource(source);
    procedure1.setDestination(destination);
    procedure1.setVolume(10.0f);
    process.addProcedure(procedure1);
    
    DispenseProcedure procedure2 = new DispenseProcedure();
    procedure2.setSource(destination);
    procedure2.setDestination(source);
    procedure2.setVolume(2.0f);
    process.addProcedure(procedure2);

    CylindricalGCodeDevice deviceCylindrical = new CylindricalGCodeDevice();
    RectangularGCodeDevice deviceRectangular = new RectangularGCodeDevice();
    
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
