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
        IRelayWrapper wrapper0 = DataAccessorFactory.getInstance().getRelayAccessor().getWrapper(0);
        Assertions.assertEquals(1, DataAccessorFactory.getInstance().getRelayAccessor().getPortList().size());
        Assertions.assertEquals("Relay 0", wrapper0.getName());
        Assertions.assertFalse(wrapper0.getWantsHidden());

        new Relay(1);
        IRelayWrapper wrapper1 = DataAccessorFactory.getInstance().getRelayAccessor().getWrapper(1);
        Assertions.assertEquals(2, DataAccessorFactory.getInstance().getRelayAccessor().getPortList().size());
        Assertions.assertEquals("Relay 1", wrapper1.getName());
        Assertions.assertFalse(wrapper1.getWantsHidden());

        wrapper0.setName("NewNameFor0");
        Assertions.assertEquals("NewNameFor0", wrapper0.getName());
    }

    @Test
    public void testCreateRelaysWithSetup()
    {
        IRelayWrapper wrapper = DataAccessorFactory.getInstance().getRelayAccessor().createSimulator(3, WpiRelayWrapper.class.getName());
        Assertions.assertFalse(wrapper.isInitialized());

        new Relay(3);
        Assertions.assertTrue(wrapper.isInitialized());
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
