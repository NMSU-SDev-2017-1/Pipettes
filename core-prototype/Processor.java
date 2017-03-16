import java.io.File;
import java.io.FileNotFoundException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

public class Processor
{
  public static void main(String[] args)
  {
    String processFileName = "TestProcess.xml"; // args[0];
    String deviceFileName = "TestDeviceCylindrical.xml"; // args[1];
    String outputFileName = "output.gcode"; // args[2];

    File processFile = new File(processFileName);
    File deviceFile = new File(deviceFileName);
    File outputFile = new File(outputFileName);

    Process process = null;
    Device device = null;

    JAXBContext jaxbContext = null;
    Unmarshaller jaxbUnmarshaller = null;

    try
    {
      jaxbContext = JAXBContext.newInstance(Common.xmlClasses);
      jaxbUnmarshaller = jaxbContext.createUnmarshaller();
    }
    catch (JAXBException e1)
    {
      // TODO Auto-generated catch block
      e1.printStackTrace();
    }

    if (jaxbUnmarshaller != null)
    {
      try
      {
        process = (Process) jaxbUnmarshaller.unmarshal(processFile);
      }
      catch (JAXBException e)
      {
        e.printStackTrace();
      }

      try
      {
        device = (Device) jaxbUnmarshaller.unmarshal(deviceFile);
      }
      catch (JAXBException e)
      {
        e.printStackTrace();
      }
    }

    if ((process != null) && (device != null))
    {
      try
      {
        device.beginProcess(outputFile);
      }
      catch (FileNotFoundException e)
      {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      try
      {
        process.run(device);
      }
      catch (PositioningException e)
      {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      device.endProcess();
    }
  }
}
