package com.snobot.simulator.module_wrapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.snobot.simulator.wrapper_accessors.DataAccessorFactory;
import com.snobot.test.utilities.BaseSimulatorJniTest;

import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Relay.Value;

public class TestRelayJni extends BaseSimulatorJniTest
{
    @Test
    public void testCreateRelays()
    {
        Assertions.assertEquals(0, DataAccessorFactory.getInstance().getRelayAccessor().getPortList().size());

        new Relay(0);
        Assertions.assertEquals(1, DataAccessorFactory.getInstance().getRelayAccessor().getPortList().size());
        Assertions.assertEquals("Relay 0", DataAccessorFactory.getInstance().getRelayAccessor().getName(0));
        Assertions.assertFalse(DataAccessorFactory.getInstance().getRelayAccessor().getWantsHidden(0));

        new Relay(1);
        Assertions.assertEquals(2, DataAccessorFactory.getInstance().getRelayAccessor().getPortList().size());
        Assertions.assertEquals("Relay 1", DataAccessorFactory.getInstance().getRelayAccessor().getName(1));
        Assertions.assertFalse(DataAccessorFactory.getInstance().getRelayAccessor().getWantsHidden(1));

        DataAccessorFactory.getInstance().getRelayAccessor().setName(0, "NewNameFor0");
        Assertions.assertEquals("NewNameFor0", DataAccessorFactory.getInstance().getRelayAccessor().getName(0));
    }

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
        Assertions.assertFalse(DataAccessorFactory.getInstance().getRelayAccessor().getFowardValue(1));
        Assertions.assertFalse(DataAccessorFactory.getInstance().getRelayAccessor().getReverseValue(1));

        relay.set(Value.kOn);
        Assertions.assertTrue(DataAccessorFactory.getInstance().getRelayAccessor().getFowardValue(1));
        Assertions.assertTrue(DataAccessorFactory.getInstance().getRelayAccessor().getReverseValue(1));

        relay.set(Value.kForward);
        Assertions.assertTrue(DataAccessorFactory.getInstance().getRelayAccessor().getFowardValue(1));
        Assertions.assertFalse(DataAccessorFactory.getInstance().getRelayAccessor().getReverseValue(1));

        relay.set(Value.kReverse);
        Assertions.assertFalse(DataAccessorFactory.getInstance().getRelayAccessor().getFowardValue(1));
        Assertions.assertTrue(DataAccessorFactory.getInstance().getRelayAccessor().getReverseValue(1));

        relay.set(Value.kOff);
        Assertions.assertFalse(DataAccessorFactory.getInstance().getRelayAccessor().getFowardValue(1));
        Assertions.assertFalse(DataAccessorFactory.getInstance().getRelayAccessor().getReverseValue(1));
    }
}
