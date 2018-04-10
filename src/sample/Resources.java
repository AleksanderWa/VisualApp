package sample;

        import java.lang.management.ManagementFactory;

public class Resources {
    private com.sun.management.OperatingSystemMXBean os;
    private static Long maxRam;
    private Long ramLoad;
    private double cpuLoad;
    private static Resources instance = null;

    Resources() {
        if (instance != null) {
            System.out.println("Singleton error, more than 1 instance created!!");
        }
        instance = this;
        os = (com.sun.management.OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        maxRam = os.getTotalPhysicalMemorySize();
    }

    void getInfo() {
        os = (com.sun.management.OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        cpuLoad = os.getSystemCpuLoad();
        ramLoad = maxRam - os.getFreePhysicalMemorySize();
    }

    double getRamPercent() {
        return (ramLoad.doubleValue() / maxRam.doubleValue()) * 100;
    }

    String convertToString(double usage) {
        double temp = usage * 100;
        return String.format("%1.2f", temp);
    }

    String convertToString(long usage) {
        Long temp = usage / 1048576;
        return temp.toString();
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
