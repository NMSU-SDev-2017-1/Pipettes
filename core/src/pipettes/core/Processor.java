package pipettes.core;

import java.io.PrintStream;

public class Processor
{
  public static void run(Process process, Device device, PrintStream output,
      PrintStream log) throws PositioningException
  {
    ProcessLogger logger = new ProcessLogger(log);

    device.beginProcess(output);
    logger.begin();
    process.run(device, logger);
    logger.end();
    device.endProcess();
  }
}
