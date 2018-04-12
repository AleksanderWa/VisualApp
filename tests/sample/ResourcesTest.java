package sample;

import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

public class ResourcesTest {
    private Resources testObject = new Resources();

    @Test
    public void convertToString() throws Exception {
        testObject.getInfo();
        boolean valueCheck = false;
        double cpuLoad = testObject.getCpuLoad() * 100;
        System.out.println(cpuLoad);
        if(cpuLoad < 0){
            valueCheck = false;
        }
        else if (cpuLoad >= 0)
            valueCheck = true;
        else
            valueCheck = false;

        assertEquals(true,valueCheck);
    }

    @Test
    public void convertToString1() throws Exception {
    }

    @Test
    public void string_convert_wrong_input() throws Exception{
        long wrong_value = -12312312;
        testObject.getInfo();
        boolean valueCheck = false;


        assertEquals(true,valueCheck);
    }

}