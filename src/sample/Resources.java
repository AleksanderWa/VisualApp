package sample;

import java.io.File;
import java.lang.management.ManagementFactory;


public class Resources implements Runnable {
    private com.sun.management.OperatingSystemMXBean os;
    private static Long maxRam;
    private Long ramLoad;
    private double cpuLoad;
    private static Resources instance = null;
    private long freeDisk;


    Resources() {
        if (instance != null) {
            System.out.println("Singleton error, more than 1 instance created!!");
        }
        instance = this;
        os = (com.sun.management.OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        maxRam = os.getTotalPhysicalMemorySize();
    }



    synchronized void getInfo() {

                os = (com.sun.management.OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
                cpuLoad = os.getSystemCpuLoad();
                ramLoad = maxRam - os.getFreePhysicalMemorySize();
                readIO();

    }

    double getRamPercent() {
        return (ramLoad.doubleValue() / maxRam.doubleValue()) * 100;
    }

    String convertToString(double usage, int constValue) {

        double temp = usage * constValue;
        return String.format("%1.2f", temp);
    }

    String convertToString(long usage, int devided) {
        if(usage < 0){}
        Long temp = usage / devided;
        return temp.toString();
    }

    long getMaxRam() {
        return maxRam;
    }

    long getRamLoad() {
        return ramLoad;
    }

    long getFreeDisk() {
        return freeDisk;
    }

    double getCpuLoad() {
        return cpuLoad;
    }

    double getRamLoadDouble(){return ramLoad.doubleValue();}
    double getMaxRamDouble(){return maxRam.doubleValue();}
    @Override
    public void run() {
        System.out.println("Thread from runnable " + Thread.currentThread().getId());
    }

    private void readIO() {
        File root = new File("C:\\");
        try {
            freeDisk = root.getUsableSpace();
           // root.delete();
        } catch (Exception e) {
            System.out.println("catched exception " + e);
        }
    }
}

