package com.snobot.simulator.module_wrapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.snobot.simulator.wrapper_accessors.DataAccessorFactory;
import com.snobot.test.utilities.BaseSimulatorJniTest;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DigitalOutput;

public class TestDigitalIOJni extends BaseSimulatorJniTest
{
    @Test
    public void testCreateDigitalIn()
    {
        Assertions.assertEquals(0, DataAccessorFactory.getInstance().getDigitalAccessor().getPortList().size());

        new DigitalInput(0);
        Assertions.assertEquals(1, DataAccessorFactory.getInstance().getDigitalAccessor().getPortList().size());
        Assertions.assertEquals("Digital IO 0", DataAccessorFactory.getInstance().getDigitalAccessor().getName(0));
        Assertions.assertFalse(DataAccessorFactory.getInstance().getDigitalAccessor().getWantsHidden(0));

        new DigitalInput(3);
        Assertions.assertEquals(2, DataAccessorFactory.getInstance().getDigitalAccessor().getPortList().size());
        Assertions.assertEquals("Digital IO 3", DataAccessorFactory.getInstance().getDigitalAccessor().getName(3));
        Assertions.assertFalse(DataAccessorFactory.getInstance().getDigitalAccessor().getWantsHidden(3));

        DataAccessorFactory.getInstance().getDigitalAccessor().setName(3, "NewNameFor3");
        Assertions.assertEquals("NewNameFor3", DataAccessorFactory.getInstance().getDigitalAccessor().getName(3));
    }

    @Test
    public void testCreateDigitalInWithSetup()
    {
        DataAccessorFactory.getInstance().getDigitalAccessor().createSimulator(3, "WpiDigitalIoWrapper");
        Assertions.assertFalse(DataAccessorFactory.getInstance().getDigitalAccessor().isInitialized(3));

        new DigitalInput(3);
        Assertions.assertTrue(DataAccessorFactory.getInstance().getDigitalAccessor().isInitialized(3));
    }

    @Test
    public void testCreateDigitalOut()
    {
        Assertions.assertEquals(0, DataAccessorFactory.getInstance().getDigitalAccessor().getPortList().size());

        new DigitalOutput(0);
        Assertions.assertEquals(1, DataAccessorFactory.getInstance().getDigitalAccessor().getPortList().size());

        new DigitalOutput(3);
        Assertions.assertEquals(2, DataAccessorFactory.getInstance().getDigitalAccessor().getPortList().size());
    }

    @Test
    public void testDigitalIn()
    {
        DigitalInput input = new DigitalInput(0);

        Assertions.assertTrue(input.get());
        Assertions.assertTrue(DataAccessorFactory.getInstance().getDigitalAccessor().getState(0));

        DataAccessorFactory.getInstance().getDigitalAccessor().setState(0, false);
        Assertions.assertFalse(input.get());
        Assertions.assertFalse(DataAccessorFactory.getInstance().getDigitalAccessor().getState(0));
    }

}
