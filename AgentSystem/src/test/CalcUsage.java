package test;
import java.io.*;
import java.text.*;

public class CalcUsage {
  private static long old_time;
  private static double old_use;

  public static double calc_usage() throws Exception {
    File file = new File("/proc/stat");
    BufferedReader reader = new BufferedReader(new FileReader(file));
    String line = reader.readLine().trim();
    String[] vals = line.split("\\s+");
    int usr  = Integer.parseInt(vals[1]);
    int nice = Integer.parseInt(vals[2]);
    int sys  = Integer.parseInt(vals[3]);
    reader.close();

    long now = System.currentTimeMillis() / 10;
    double usage = (usr + nice + sys - old_use) / (now - old_time);
    old_use = usr + nice + sys;
    old_time = now;
    return usage;
  }

  DecimalFormat format = new DecimalFormat("0.00");
  public String getUsage(){
	  try {
		Double usage = calc_usage();

		return format.format(usage);
	} catch (Exception e) {
		// TODO 自動生成された catch ブロック
		e.printStackTrace();
	}
	return null;
  }

  /**
  public static void main(String[] args) throws Exception {
	  DecimalFormat format = new DecimalFormat("0.00");
	  while (true) {
    	double usage = calc_usage();
    	long start = System.currentTimeMillis();

    	System.out.println(format.format(usage));

    	long stop = System.currentTimeMillis();

    	System.out.println(stop-start +" ms");
    	Thread.sleep(1000);
	  }
  }**/

}