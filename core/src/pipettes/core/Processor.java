package pipettes.core;

import java.io.PrintStream;

public class Processor
{
  public static void run(Process process, Device device, PrintStream output) throws PositioningException
  {
    device.beginProcess(output);
    process.run(device);
    device.endProcess();
  }
}
