package com.snobot.simulator.simulator_components.ctre;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.snobot.simulator.wrapper_accessors.DataAccessorFactory;
import com.snobot.test.utilities.BaseSimulatorJavaTest;

@Tag("CTRE")
public class TestCtreCanTalon extends BaseSimulatorJavaTest
{
    public static Collection<Integer> getData()
    {
        Collection<Integer> output = new ArrayList<>();

        for (int i = 0; i < 64; ++i)
        {
            output.add(i);
        }

        return output;
    }

    @ParameterizedTest
    @MethodSource("getData")
    public void testSimpleSetters(int aCanHandle)
    {
        Assertions.assertEquals(0, DataAccessorFactory.getInstance().getSpeedControllerAccessor().getPortList().size());

        TalonSRX talon = new TalonSRX(aCanHandle);
        Assertions.assertEquals(1, DataAccessorFactory.getInstance().getSpeedControllerAccessor().getPortList().size());

        Assertions.assertEquals("CAN SC " + aCanHandle,
                DataAccessorFactory.getInstance().getSpeedControllerAccessor().getName(aCanHandle + CtreTalonSrxSpeedControllerSim.sCTRE_OFFSET));

        talon.config_kP(0, 0, 0);
//        //////////////////
//        // PID
//        /////////////////
//        Assertions.assertEquals(0, talon.configGetParameter(ParamEnum.f, arg1, arg2)(), DOUBLE_EPSILON);
//        Assertions.assertEquals(0, talon.getI(), DOUBLE_EPSILON);
//        Assertions.assertEquals(0, talon.getD(), DOUBLE_EPSILON);
//        Assertions.assertEquals(0, talon.getF(), DOUBLE_EPSILON);
//        Assertions.assertEquals(0, talon.getIZone(), DOUBLE_EPSILON);
//
//        talon.setProfile(0);
//        talon.setP(.5);
//        talon.setI(1.5);
//        talon.setD(100.7890);
//        talon.setF(-9.485);
//        talon.setIZone(40);
//
//        Assertions.assertEquals(.5, talon.getP(), DOUBLE_EPSILON);
//        Assertions.assertEquals(1.5, talon.getI(), DOUBLE_EPSILON);
//        Assertions.assertEquals(100.7890, talon.getD(), DOUBLE_EPSILON);
//        Assertions.assertEquals(-9.485, talon.getF(), DOUBLE_EPSILON);
//        Assertions.assertEquals(40, talon.getIZone(), DOUBLE_EPSILON);
//
//        talon.setProfile(1);
//        talon.setP(.231);
//        talon.setI(1.634);
//        talon.setD(90.7890);
//        talon.setF(-8.485);
//        talon.setIZone(20);
//
//        Assertions.assertEquals(.231, talon.getP(), DOUBLE_EPSILON);
//        Assertions.assertEquals(1.634, talon.getI(), DOUBLE_EPSILON);
//        Assertions.assertEquals(90.7890, talon.getD(), DOUBLE_EPSILON);
//        Assertions.assertEquals(-8.485, talon.getF(), DOUBLE_EPSILON);
//        Assertions.assertEquals(20, talon.getIZone(), DOUBLE_EPSILON);
//
//        Assertions.assertEquals(30, talon.getTemperature(), DOUBLE_EPSILON);
//        Assertions.assertEquals(12, talon.getBusVoltage(), DOUBLE_EPSILON);
    }
}
