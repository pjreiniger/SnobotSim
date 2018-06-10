package com.snobot.simulator.module_wrapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.snobot.simulator.SensorActuatorRegistry;
import com.snobot.simulator.wrapper_accessors.DataAccessorFactory;
import com.snobot.test.utilities.BaseSimulatorJavaTest;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.AnalogOutput;

public class TestAnalogIOJni extends BaseSimulatorJavaTest
{
    @Test
    public void testCreateAnalogIn()
    {
        Assertions.assertEquals(0, DataAccessorFactory.getInstance().getAnalogAccessor().getPortList().size());

        new AnalogInput(0);
        Assertions.assertEquals(1, DataAccessorFactory.getInstance().getAnalogAccessor().getPortList().size());
        Assertions.assertEquals("Analog 0", DataAccessorFactory.getInstance().getAnalogAccessor().getName(0));
        Assertions.assertFalse(DataAccessorFactory.getInstance().getAnalogAccessor().getWantsHidden(0));

        new AnalogInput(3);
        Assertions.assertEquals(2, DataAccessorFactory.getInstance().getAnalogAccessor().getPortList().size());
        Assertions.assertEquals("Analog 3", DataAccessorFactory.getInstance().getAnalogAccessor().getName(3));
        Assertions.assertFalse(DataAccessorFactory.getInstance().getAnalogAccessor().getWantsHidden(3));

        DataAccessorFactory.getInstance().getAnalogAccessor().setName(3, "NewNameFor3");
        Assertions.assertEquals("NewNameFor3", DataAccessorFactory.getInstance().getAnalogAccessor().getName(3));

        // Set name for non-existing sensor
        DataAccessorFactory.getInstance().getAnalogAccessor().setName(4, "NewNameFor4");
    }

    @Test
    public void testCreateAnalogOut()
    {
        Assertions.assertEquals(0, DataAccessorFactory.getInstance().getAnalogAccessor().getPortList().size());

        new AnalogOutput(0);
        Assertions.assertEquals(1, DataAccessorFactory.getInstance().getAnalogAccessor().getPortList().size());

        new AnalogOutput(1);
        Assertions.assertEquals(2, DataAccessorFactory.getInstance().getAnalogAccessor().getPortList().size());
    }

    public void testReuseInPort()
    {
        Assertions.assertEquals(0, DataAccessorFactory.getInstance().getAnalogAccessor().getPortList().size());

        new AnalogInput(0);
        Assertions.assertEquals(1, DataAccessorFactory.getInstance().getAnalogAccessor().getPortList().size());

        Assertions.assertThrows(RuntimeException.class, () ->
        {
            new AnalogInput(0);
        });
    }

    public void testReuseOutPort()
    {
        Assertions.assertEquals(0, DataAccessorFactory.getInstance().getAnalogAccessor().getPortList().size());

        new AnalogOutput(0);
        Assertions.assertEquals(1, DataAccessorFactory.getInstance().getAnalogAccessor().getPortList().size());

        Assertions.assertThrows(RuntimeException.class, () ->
        {
            new AnalogOutput(0);
        });
    }

    @Test
    public void testAnalogIn()
    {
        Assertions.assertEquals(0, DataAccessorFactory.getInstance().getAnalogAccessor().getPortList().size());
        AnalogInput input = new AnalogInput(0);
        Assertions.assertEquals(1, DataAccessorFactory.getInstance().getAnalogAccessor().getPortList().size());

        Assertions.assertEquals(0, input.getVoltage(), DOUBLE_EPSILON);
        Assertions.assertEquals(0, DataAccessorFactory.getInstance().getAnalogAccessor().getVoltage(0), DOUBLE_EPSILON);
    }

    @Test
    public void testSimulatorFeedbackNoUpdate()
    {
        new AnalogInput(5);
        AnalogWrapper wrapper = SensorActuatorRegistry.get().getAnalog().get(5);
        wrapper.setVoltage(5);
        wrapper.setVoltage(5);
    }
}
