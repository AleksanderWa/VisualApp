package sample;

import java.lang.management.ManagementFactory;

public class Resources {

    public String getInfo(){
        com.sun.management.OperatingSystemMXBean os = (com.sun.management.OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        Long usage = os.getTotalPhysicalMemorySize();
        return usage.toString();
    }
}
