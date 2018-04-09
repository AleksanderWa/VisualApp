package sample;

import java.lang.management.ManagementFactory;

public class Resources {
    private com.sun.management.OperatingSystemMXBean os;
    private static long maxRam;
    private long ramLoad;
    private double cpuLoad;
    private static Resources instance = null;

     Resources(){
        if (instance != null) {
            System.out.println("Singleton error, more than 1 instance created!!");
        }
        instance = this;
        os = (com.sun.management.OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        maxRam = os.getTotalPhysicalMemorySize();
    }

     void getInfo(){
        os = (com.sun.management.OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        cpuLoad = os.getSystemCpuLoad();
        ramLoad = os.getFreePhysicalMemorySize();
    }

    long getMaxRam() {
        return maxRam;
    }

    long getRamLoad() {
        return ramLoad;
    }

    double getCpuLoad() {
        return cpuLoad;
    }
}
