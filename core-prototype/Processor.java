import java.io.File;
import java.io.FileNotFoundException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

public class Processor
{
  public static void main(String[] args)
  {
    String processFileName = args[0];
    String deviceFileName = args[1];
    String outputFileName = args[2];

    File processFile = new File(processFileName);
    File deviceFile = new File(deviceFileName);
    File outputFile = new File(outputFileName);

    Process process = null;
    Device device = null;

    JAXBContext jaxbContext;

    try
    {
      jaxbContext = JAXBContext.newInstance(Process.class);
      Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
      process = (Process) jaxbUnmarshaller.unmarshal(processFile);
    }
    catch (JAXBException e)
    {
      e.printStackTrace();
    }

    try
    {
      jaxbContext = JAXBContext.newInstance(Device.class);
      Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
      device = (Device) jaxbUnmarshaller.unmarshal(deviceFile);
    }
    catch (JAXBException e)
    {
      e.printStackTrace();
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
