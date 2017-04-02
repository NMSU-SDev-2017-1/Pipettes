package pipettes.core;

import java.io.PrintStream;

public class ProcessLogger
{
  private PrintStream log; 
  
  public ProcessLogger(PrintStream log)
  {
    this.log = log;
  }
  
  public void begin()
  {
    if (log != null)
    {
      log.printf("Container,Operation,Volume\n");
    }
  }

  public void end()
  {
    if (log != null)
    {
      log.flush();
      log = null;
    }
  }
  
  private String escape(String s)
  {
    if (s.contains(",") || s.contains("\""))
    {
      return "\"" + s.replaceAll("\"", "\"\"") + "\"";
    }
    else
    {
      return s;
    }
  }
  
  private void logOperation(String containerName, String operation, double volume)
  {
    if (log != null)
    {
      log.printf("%s,%s,%.3f\n", escape(containerName), operation, volume);
    }
  }
  
  public void logDraw(String containerName, double volume)
  {
    logOperation(containerName, "Draw", volume);
  }
  
  public void logDispense(String containerName, double volume)
  {
    logOperation(containerName, "Dispense", volume);
  }
}
