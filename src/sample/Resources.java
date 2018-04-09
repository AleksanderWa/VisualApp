package sample;

import java.lang.management.ManagementFactory;

public class Resources {
    private com.sun.management.OperatingSystemMXBean os;
    private static long maxRam;
    private static long ramLoad;
    private static double cpuLoad;
    private static Resources instance = null;

    public Resources(){
        if (instance != null) {
            System.out.println("Singleton error, more than 1 instance created!!");
        }
        instance = this;
        os = (com.sun.management.OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        maxRam = os.getTotalPhysicalMemorySize();
    }

    private void getInfo(){
        os = (com.sun.management.OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        cpuLoad = os.getSystemCpuLoad();
        ramLoad = os.getFreePhysicalMemorySize();
    }

    public static long getMaxRam() {
        return maxRam;
    }

    public static long getRamLoad() {
        return ramLoad;
    }

    public static double getCpuLoad() {
        return cpuLoad;
    }
}
