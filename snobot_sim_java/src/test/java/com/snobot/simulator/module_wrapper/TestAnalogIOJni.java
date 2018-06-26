package com.snobot.simulator.module_wrapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.snobot.simulator.SensorActuatorRegistry;
import com.snobot.simulator.module_wrapper.interfaces.IAnalogInWrapper;
import com.snobot.simulator.module_wrapper.wpi.WpiAnalogInWrapper;
import com.snobot.simulator.module_wrapper.wpi.WpiAnalogOutWrapper;
import com.snobot.simulator.wrapper_accessors.DataAccessorFactory;
import com.snobot.test.utilities.BaseSimulatorJavaTest;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.AnalogOutput;

public class TestAnalogIOJni extends BaseSimulatorJavaTest
{
    @Test
    public void testCreateAnalogIn()
    {
        Assertions.assertEquals(0, DataAccessorFactory.getInstance().getAnalogInAccessor().getPortList().size());

        new AnalogInput(0);
        Assertions.assertEquals(1, DataAccessorFactory.getInstance().getAnalogInAccessor().getPortList().size());
        Assertions.assertEquals("Analog In 0", DataAccessorFactory.getInstance().getAnalogInAccessor().getName(0));
        Assertions.assertFalse(DataAccessorFactory.getInstance().getAnalogInAccessor().getWantsHidden(0));

        new AnalogInput(3);
        Assertions.assertEquals(2, DataAccessorFactory.getInstance().getAnalogInAccessor().getPortList().size());
        Assertions.assertEquals("Analog In 3", DataAccessorFactory.getInstance().getAnalogInAccessor().getName(3));
        Assertions.assertFalse(DataAccessorFactory.getInstance().getAnalogInAccessor().getWantsHidden(3));

        DataAccessorFactory.getInstance().getAnalogInAccessor().setName(3, "NewNameFor3");
        Assertions.assertEquals("NewNameFor3", DataAccessorFactory.getInstance().getAnalogInAccessor().getName(3));

        // Set name for non-existing sensor
        DataAccessorFactory.getInstance().getAnalogInAccessor().setName(4, "NewNameFor4");
    }

    @Test
    public void testCreateAnalogInWithSetup()
    {
        DataAccessorFactory.getInstance().getAnalogInAccessor().createSimulator(3, WpiAnalogInWrapper.class.getName());
        Assertions.assertFalse(DataAccessorFactory.getInstance().getAnalogInAccessor().isInitialized(3));

        new AnalogInput(3);
        Assertions.assertTrue(DataAccessorFactory.getInstance().getAnalogInAccessor().isInitialized(3));
    }

    @Test
    public void testCreateAnalogOut()
    {
        Assertions.assertEquals(0, DataAccessorFactory.getInstance().getAnalogOutAccessor().getPortList().size());

        new AnalogOutput(0);
        Assertions.assertEquals(1, DataAccessorFactory.getInstance().getAnalogOutAccessor().getPortList().size());

        new AnalogOutput(1);
        Assertions.assertEquals(2, DataAccessorFactory.getInstance().getAnalogOutAccessor().getPortList().size());
    }

    @Test
    public void testCreateAnalogOutWithSetup()
    {
        DataAccessorFactory.getInstance().getAnalogOutAccessor().createSimulator(1, WpiAnalogOutWrapper.class.getName());
        Assertions.assertFalse(DataAccessorFactory.getInstance().getAnalogOutAccessor().isInitialized(1));

        new AnalogOutput(1);
        Assertions.assertTrue(DataAccessorFactory.getInstance().getAnalogOutAccessor().isInitialized(1));
    }

    public void testReuseInPort()
    {
        Assertions.assertEquals(0, DataAccessorFactory.getInstance().getAnalogInAccessor().getPortList().size());

        new AnalogInput(0);
        Assertions.assertEquals(1, DataAccessorFactory.getInstance().getAnalogInAccessor().getPortList().size());

        Assertions.assertThrows(RuntimeException.class, () ->
        {
            new AnalogInput(0);
        });
    }

    public void testReuseOutPort()
    {
        Assertions.assertEquals(0, DataAccessorFactory.getInstance().getAnalogOutAccessor().getPortList().size());

        new AnalogOutput(0);
        Assertions.assertEquals(1, DataAccessorFactory.getInstance().getAnalogOutAccessor().getPortList().size());

        Assertions.assertThrows(RuntimeException.class, () ->
        {
            new AnalogOutput(0);
        });
    }

    @Test
    public void testAnalogIn()
    {
        Assertions.assertEquals(0, DataAccessorFactory.getInstance().getAnalogInAccessor().getPortList().size());
        AnalogInput input = new AnalogInput(0);
        Assertions.assertEquals(1, DataAccessorFactory.getInstance().getAnalogInAccessor().getPortList().size());

        Assertions.assertEquals(0, input.getVoltage(), DOUBLE_EPSILON);
        Assertions.assertEquals(0, DataAccessorFactory.getInstance().getAnalogInAccessor().getVoltage(0), DOUBLE_EPSILON);
    }

    @Test
    public void testSimulatorFeedbackNoUpdate()
    {
        new AnalogInput(5);
        IAnalogInWrapper wrapper = SensorActuatorRegistry.get().getAnalogIn().get(5);
        wrapper.setVoltage(5);
        wrapper.setVoltage(5);
    }
}
