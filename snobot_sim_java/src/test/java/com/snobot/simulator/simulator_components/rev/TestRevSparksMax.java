package com.snobot.simulator.simulator_components.rev;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.snobot.simulator.simulator_components.ctre.CtreTalonSrxSpeedControllerSim;
import com.snobot.simulator.wrapper_accessors.DataAccessorFactory;
import com.snobot.test.utilities.BaseSimulatorJavaTest;

@Tag("REV")
public class TestRevSparksMax extends BaseSimulatorJavaTest
{
    private static final double sDOUBLE_EPSILON = 1.0 / 1023;
    
    public static Collection<Integer> getData()
    {
        Collection<Integer> output = new ArrayList<>();

        output.add(2);

        return output;
    }

    @ParameterizedTest
    @MethodSource("getData")
    public void testSimpleSetters(int aCanHandle)
    {
        int rawHandle = aCanHandle + CtreTalonSrxSpeedControllerSim.sCTRE_OFFSET;

        Assertions.assertEquals(0, DataAccessorFactory.getInstance().getSpeedControllerAccessor().getPortList().size());

        CANSparkMax sparksMax = new CANSparkMax(aCanHandle, MotorType.kBrushless);
        Assertions.assertEquals(1, DataAccessorFactory.getInstance().getSpeedControllerAccessor().getPortList().size());
        Assertions.assertEquals("Rev SC " + aCanHandle,
                DataAccessorFactory.getInstance().getSpeedControllerAccessor().getName(rawHandle));
        
        sparksMax.set(-1.0);
        Assertions.assertEquals(-1.0, DataAccessorFactory.getInstance().getSpeedControllerAccessor().getVoltagePercentage(rawHandle), sDOUBLE_EPSILON);
        Assertions.assertEquals(-1.0, sparksMax.get(), sDOUBLE_EPSILON);

        sparksMax.set(0.5);
        Assertions.assertEquals(0.5, DataAccessorFactory.getInstance().getSpeedControllerAccessor().getVoltagePercentage(rawHandle), sDOUBLE_EPSILON);
        Assertions.assertEquals(0.5, sparksMax.get(), sDOUBLE_EPSILON);

        sparksMax.close();
    }

}
