package com.snobot.simulator.simulator_components.ctre;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.ctre.CANTalon;
import com.snobot.simulator.wrapper_accessors.DataAccessorFactory;
import com.snobot.test.utilities.BaseSimulatorTest;

@RunWith(value = Parameterized.class)
public class TestCtreCanTalon extends BaseSimulatorTest
{
    @Parameters(name = "Test: {index} CanPort={0}")
    public static Collection<Integer> data()
    {
        Collection<Integer> output = new ArrayList<>();

        for (int i = 0; i < 64; ++i)
        {
            output.add(i);
        }

        return output;
    }

    private final int mCanHandle;

    public TestCtreCanTalon(int aCanHandle)
    {
        mCanHandle = aCanHandle;
    }

    @Test
    public void testSimpleSetters()
    {
        Assert.assertEquals(0, DataAccessorFactory.getInstance().getSpeedControllerAccessor().getPortList().size());

        CANTalon talon = new CANTalon(mCanHandle);
        Assert.assertEquals(1, DataAccessorFactory.getInstance().getSpeedControllerAccessor().getPortList().size());

        //////////////////
        // PID
        /////////////////
        Assert.assertEquals(0, talon.getP(), DOUBLE_EPSILON);
        Assert.assertEquals(0, talon.getI(), DOUBLE_EPSILON);
        Assert.assertEquals(0, talon.getD(), DOUBLE_EPSILON);
        Assert.assertEquals(0, talon.getF(), DOUBLE_EPSILON);
        Assert.assertEquals(0, talon.getIZone(), DOUBLE_EPSILON);

        talon.setP(.5);
        talon.setI(1.5);
        talon.setD(100.7890);
        talon.setF(-9.485);
        talon.setIZone(40);

        Assert.assertEquals(.5, talon.getP(), DOUBLE_EPSILON);
        Assert.assertEquals(1.5, talon.getI(), DOUBLE_EPSILON);
        Assert.assertEquals(100.7890, talon.getD(), DOUBLE_EPSILON);
        Assert.assertEquals(-9.485, talon.getF(), DOUBLE_EPSILON);
        Assert.assertEquals(40, talon.getIZone(), DOUBLE_EPSILON);

        Assert.assertEquals(30, talon.getTemperature(), DOUBLE_EPSILON);
        Assert.assertEquals(12, talon.getBusVoltage(), DOUBLE_EPSILON);
    }
}
