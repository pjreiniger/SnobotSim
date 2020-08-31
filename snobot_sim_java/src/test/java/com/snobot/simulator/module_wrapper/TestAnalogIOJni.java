package com.snobot.simulator.module_wrapper;

import com.snobot.simulator.module_wrapper.interfaces.IAnalogOutWrapper;
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
        Assertions.assertEquals(0, DataAccessorFactory.getInstance().getAnalogInAccessor().getWrappers().size());

        new AnalogInput(0);
        IAnalogInWrapper wrapper0 = DataAccessorFactory.getInstance().getAnalogInAccessor().getWrapper(0);
        Assertions.assertEquals(1, DataAccessorFactory.getInstance().getAnalogInAccessor().getWrappers().size());
        Assertions.assertEquals("Analog In 0", wrapper0.getName());
        Assertions.assertFalse(wrapper0.getWantsHidden());

        new AnalogInput(3);
        IAnalogInWrapper wrapper3 = DataAccessorFactory.getInstance().getAnalogInAccessor().getWrapper(3);
        Assertions.assertEquals(2, DataAccessorFactory.getInstance().getAnalogInAccessor().getWrappers().size());
        Assertions.assertEquals("Analog In 3", wrapper3.getName());
        Assertions.assertFalse(wrapper3.getWantsHidden());

        wrapper3.setName("NewNameFor3");
        Assertions.assertEquals("NewNameFor3", wrapper3.getName());
    }

    @Test
    public void testCreateAnalogInWithSetup()
    {
        IAnalogInWrapper wrapper = DataAccessorFactory.getInstance().getAnalogInAccessor().createSimulator(3, WpiAnalogInWrapper.class.getName());
        Assertions.assertFalse(wrapper.isInitialized());

        new AnalogInput(3);
        Assertions.assertTrue(wrapper.isInitialized());
    }

    @Test
    public void testCreateAnalogOut()
    {
        Assertions.assertEquals(0, DataAccessorFactory.getInstance().getAnalogOutAccessor().getWrappers().size());

        new AnalogOutput(0);
        Assertions.assertEquals(1, DataAccessorFactory.getInstance().getAnalogOutAccessor().getWrappers().size());

        new AnalogOutput(1);
        Assertions.assertEquals(2, DataAccessorFactory.getInstance().getAnalogOutAccessor().getWrappers().size());
    }

    @Test
    public void testCreateAnalogOutWithSetup()
    {
        IAnalogOutWrapper wrapper = DataAccessorFactory.getInstance().getAnalogOutAccessor().createSimulator(1, WpiAnalogOutWrapper.class.getName());
        Assertions.assertFalse(wrapper.isInitialized());

        new AnalogOutput(1);
        Assertions.assertTrue(wrapper.isInitialized());
    }

    @Test
    public void testReuseInPort()
    {
        Assertions.assertEquals(0, DataAccessorFactory.getInstance().getAnalogInAccessor().getWrappers().size());

        new AnalogInput(0);
        Assertions.assertEquals(1, DataAccessorFactory.getInstance().getAnalogInAccessor().getWrappers().size());

        Assertions.assertThrows(RuntimeException.class, () ->
        {
            new AnalogInput(0);
        });
    }

    @Test
    public void testReuseOutPort()
    {
        Assertions.assertEquals(0, DataAccessorFactory.getInstance().getAnalogOutAccessor().getWrappers().size());

        new AnalogOutput(0);
        Assertions.assertEquals(1, DataAccessorFactory.getInstance().getAnalogOutAccessor().getWrappers().size());

        Assertions.assertThrows(RuntimeException.class, () ->
        {
            new AnalogOutput(0);
        });
    }

    @Test
    public void testAnalogIn()
    {
        Assertions.assertEquals(0, DataAccessorFactory.getInstance().getAnalogInAccessor().getWrappers().size());
        AnalogInput input = new AnalogInput(0);
        Assertions.assertEquals(1, DataAccessorFactory.getInstance().getAnalogInAccessor().getWrappers().size());

        Assertions.assertEquals(0, input.getVoltage(), DOUBLE_EPSILON);
        Assertions.assertEquals(0, DataAccessorFactory.getInstance().getAnalogInAccessor().getWrapper(0).getVoltage(), DOUBLE_EPSILON);
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
