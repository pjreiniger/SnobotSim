package com.snobot.simulator.module_wrapper;

import com.snobot.simulator.module_wrapper.interfaces.IDigitalIoWrapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.snobot.simulator.module_wrapper.wpi.WpiDigitalIoWrapper;
import com.snobot.simulator.wrapper_accessors.DataAccessorFactory;
import com.snobot.test.utilities.BaseSimulatorJavaTest;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DigitalOutput;

public class TestDigitalIOJni extends BaseSimulatorJavaTest
{
    @Test
    public void testCreateDigitalIn()
    {
        Assertions.assertEquals(0, DataAccessorFactory.getInstance().getDigitalAccessor().getWrappers().size());

        new DigitalInput(0);
        IDigitalIoWrapper wrapper0 = DataAccessorFactory.getInstance().getDigitalAccessor().getWrapper(0);
        Assertions.assertEquals(1, DataAccessorFactory.getInstance().getDigitalAccessor().getWrappers().size());
        Assertions.assertEquals("Digital IO 0", wrapper0.getName());
        Assertions.assertFalse(wrapper0.getWantsHidden());

        new DigitalInput(3);
        IDigitalIoWrapper wrapper3 = DataAccessorFactory.getInstance().getDigitalAccessor().getWrapper(3);
        Assertions.assertEquals(2, DataAccessorFactory.getInstance().getDigitalAccessor().getWrappers().size());
        Assertions.assertEquals("Digital IO 3", wrapper3.getName());
        Assertions.assertFalse(wrapper3.getWantsHidden());

        wrapper3.setName("NewNameFor3");
        Assertions.assertEquals("NewNameFor3", wrapper3.getName());
    }

    @Test
    public void testCreateDigitalInWithSetup()
    {
        IDigitalIoWrapper wrapper = DataAccessorFactory.getInstance().getDigitalAccessor().createSimulator(3, WpiDigitalIoWrapper.class.getName());
        Assertions.assertFalse(wrapper.isInitialized());

        new DigitalInput(3);
        Assertions.assertTrue(wrapper.isInitialized());
    }

    @Test
    public void testCreateDigitalOut()
    {
        Assertions.assertEquals(0, DataAccessorFactory.getInstance().getDigitalAccessor().getWrappers().size());

        new DigitalOutput(0);
        Assertions.assertEquals(1, DataAccessorFactory.getInstance().getDigitalAccessor().getWrappers().size());

        new DigitalOutput(3);
        Assertions.assertEquals(2, DataAccessorFactory.getInstance().getDigitalAccessor().getWrappers().size());
    }

    @Test
    public void testDigitalIn()
    {
        DigitalInput input = new DigitalInput(0);
        IDigitalIoWrapper wrapper = DataAccessorFactory.getInstance().getDigitalAccessor().getWrapper(0);

        Assertions.assertTrue(input.get());
        Assertions.assertTrue(wrapper.get());

        wrapper.set(false);
        Assertions.assertFalse(input.get());
        Assertions.assertFalse(wrapper.get());
    }

}
