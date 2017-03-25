package pipettes.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

public class ProcessorTest
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
    PrintStream output = null;
    
    JAXBContext jaxbContext;

    try
    {
      jaxbContext = JAXBContext.newInstance(Common.allClasses);
      Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
      process = (Process) jaxbUnmarshaller.unmarshal(processFile);
    }
    catch (JAXBException e)
    {
      e.printStackTrace();
    }

    try
    {
      jaxbContext = JAXBContext.newInstance(Common.deviceClasses);
      Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
      device = (Device) jaxbUnmarshaller.unmarshal(deviceFile);
    }
    catch (JAXBException e)
    {
      e.printStackTrace();
    }

    try
    {
      output = new PrintStream(outputFile);
    }
    catch (FileNotFoundException e)
    {
      e.printStackTrace();
    }
    
    if ((process != null) && (device != null) && (output != null))
    {
      try
      {
        Processor.run(process, device, output);
      }
      catch (PositioningException e)
      {
        e.printStackTrace();
      }
    }
  }
}
