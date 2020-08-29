package com.snobot.simulator.module_wrapper;

import com.snobot.simulator.module_wrapper.interfaces.IRelayWrapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.snobot.simulator.module_wrapper.wpi.WpiRelayWrapper;
import com.snobot.simulator.wrapper_accessors.DataAccessorFactory;
import com.snobot.test.utilities.BaseSimulatorJavaTest;

import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Relay.Value;

public class TestRelayJni extends BaseSimulatorJavaTest
{
    @Test
    public void testCreateRelays()
    {
        Assertions.assertEquals(0, DataAccessorFactory.getInstance().getRelayAccessor().getPortList().size());

        new Relay(0);
        Assertions.assertEquals(1, DataAccessorFactory.getInstance().getRelayAccessor().getPortList().size());
        Assertions.assertEquals("Relay 0", DataAccessorFactory.getInstance().getRelayAccessor().getWrapper(0).getName());
        Assertions.assertFalse(DataAccessorFactory.getInstance().getRelayAccessor().getWrapper(0).getWantsHidden());

        new Relay(1);
        Assertions.assertEquals(2, DataAccessorFactory.getInstance().getRelayAccessor().getPortList().size());
        Assertions.assertEquals("Relay 1", DataAccessorFactory.getInstance().getRelayAccessor().getWrapper(1).getName());
        Assertions.assertFalse(DataAccessorFactory.getInstance().getRelayAccessor().getWrapper(1).getWantsHidden());

        DataAccessorFactory.getInstance().getRelayAccessor().getWrapper(0).setName("NewNameFor0");
        Assertions.assertEquals("NewNameFor0", DataAccessorFactory.getInstance().getRelayAccessor().getWrapper(0).getName());
    }

    @Test
    public void testCreateRelaysWithSetup()
    {
        DataAccessorFactory.getInstance().getRelayAccessor().createSimulator(3, WpiRelayWrapper.class.getName());
        Assertions.assertFalse(DataAccessorFactory.getInstance().getRelayAccessor().getWrapper(3).isInitialized());

        new Relay(3);
        Assertions.assertTrue(DataAccessorFactory.getInstance().getRelayAccessor().getWrapper(3).isInitialized());
    }

    @Test
    public void testReusePort()
    {
        Assertions.assertEquals(0, DataAccessorFactory.getInstance().getRelayAccessor().getPortList().size());

        new Relay(2);
        Assertions.assertEquals(1, DataAccessorFactory.getInstance().getRelayAccessor().getPortList().size());

        Assertions.assertThrows(RuntimeException.class, () ->
        {
            new Relay(2);
        });
    }

    @Test
    public void testSetRelay()
    {
        Assertions.assertEquals(0, DataAccessorFactory.getInstance().getRelayAccessor().getPortList().size());


        Relay relay = new Relay(1);
        IRelayWrapper relayWrapper = DataAccessorFactory.getInstance().getRelayAccessor().getWrapper(1);

        Assertions.assertFalse(relayWrapper.getRelayForwards());
        Assertions.assertFalse(relayWrapper.getRelayReverse());

        relay.set(Value.kOn);
        Assertions.assertTrue(relayWrapper.getRelayForwards());
        Assertions.assertTrue(relayWrapper.getRelayReverse());

        relay.set(Value.kForward);
        Assertions.assertTrue(relayWrapper.getRelayForwards());
        Assertions.assertFalse(relayWrapper.getRelayReverse());

        relay.set(Value.kReverse);
        Assertions.assertFalse(relayWrapper.getRelayForwards());
        Assertions.assertTrue(relayWrapper.getRelayReverse());

        relay.set(Value.kOff);
        Assertions.assertFalse(relayWrapper.getRelayForwards());
        Assertions.assertFalse(relayWrapper.getRelayReverse());
    }
}
