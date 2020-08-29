package com.snobot.simulator.module_wrapper;

import com.snobot.simulator.wrapper_accessors.DataAccessorFactory;
import com.snobot.test.utilities.BaseSimulatorJavaTest;
import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class TestAddressableLedJni extends BaseSimulatorJavaTest
{
    @Test
    public void testAddressableLed()
    {
        int port = 4;

        Assertions.assertEquals(0, DataAccessorFactory.getInstance().getAddressableLedAccessor().getPortList().size());

        AddressableLED leds = new AddressableLED(port);
        AddressableLEDBuffer buffer = new AddressableLEDBuffer(60);
        leds.setLength(buffer.getLength());
        leds.setData(buffer);
        leds.start();

        Assertions.assertEquals(1, DataAccessorFactory.getInstance().getAddressableLedAccessor().getPortList().size());
        Assertions.assertEquals("Addressable LED 0", DataAccessorFactory.getInstance().getAddressableLedAccessor().getWrapper(0).getName());


        byte[] data;

        // Initial state
        data = DataAccessorFactory.getInstance().getAddressableLedAccessor().getWrapper(0).getData();
        Assertions.assertEquals(240, data.length);
        System.out.println(Arrays.toString(data));

        // Set Color
        for (int i = 0; i < buffer.getLength(); ++i)
        {
            buffer.setRGB(i, 100, 200, 254);
        }
        leds.setData(buffer);

        data = DataAccessorFactory.getInstance().getAddressableLedAccessor().getWrapper(0).getData();
        Assertions.assertEquals(240, data.length);
        for (int i = 0; i < buffer.getLength(); i += 4)
        {
            Assertions.assertEquals((byte) 254, data[i]); // Blue
            Assertions.assertEquals((byte) 200, data[i + 1]); // Green
            Assertions.assertEquals((byte) 100, data[i + 2]); // Red
            Assertions.assertEquals((byte) 0, data[i + 3]);
        }
    }
}
