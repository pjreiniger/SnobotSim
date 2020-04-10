package com.snobot.simulator.simulator_components.ctre;
//package com.snobot.simulator.simulator_components.ctre;
//
//import java.util.ArrayList;
//import java.util.Collection;
//
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.junit.runner.RunWith;
//import org.junit.runners.Parameterized;
//import org.junit.runners.Parameterized.Parameters;
//
//import com.ctre.phoenix.MotorControl.ControlMode;
//import com.ctre.phoenix.MotorControl.CAN.TalonSRX;
//import com.snobot.simulator.wrapper_accessors.DataAccessorFactory;
//import com.snobot.test.utilities.BaseSimulatorTest;
//
//@RunWith(value = Parameterized.class)
//public class TestCtreCanTalon_ControlVoltage extends BaseSimulatorTest
//{
//    @Parameters(name = "Test: {index} CanPort={0}")
//    public static Collection<Integer> data()
//    {
//        Collection<Integer> output = new ArrayList<>();
//
//        for (int i = 0; i < 64; ++i)
//        {
//            output.add(i);
//        }
//
//        return output;
//    }
//
//    private final int mCanHandle;
//    private final int mRawHandle;
//
//    public TestCtreCanTalon_ControlVoltage(int aCanHandle)
//    {
//        mCanHandle = aCanHandle;
//        mRawHandle = mCanHandle + 100;
//    }
//
//    @Test
//    public void testSetWithVoltage()
//    {
//        double DOUBLE_EPSILON = .01;
//
//        Assertions.assertEquals(0, DataAccessorFactory.getInstance().getSpeedControllerAccessor().getPortList().size());
//        TalonSRX talon = new TalonSRX(mCanHandle);
//        Assertions.assertEquals(1, DataAccessorFactory.getInstance().getSpeedControllerAccessor().getPortList().size());
//
//        talon.set(ControlMode.10.8);
//        Assertions.assertEquals(0.9, DataAccessorFactory.getInstance().getSpeedControllerAccessor().getVoltagePercentage(mRawHandle), DOUBLE_EPSILON);
//        Assertions.assertEquals(10.8, talon.get(), DOUBLE_EPSILON);
//
//        talon.set(-6.0);
//        Assertions.assertEquals(-0.5, DataAccessorFactory.getInstance().getSpeedControllerAccessor().getVoltagePercentage(mRawHandle), DOUBLE_EPSILON);
//        Assertions.assertEquals(-6.0, talon.get(), DOUBLE_EPSILON);
//
//        talon.set(3.0);
//        Assertions.assertEquals(0.25, DataAccessorFactory.getInstance().getSpeedControllerAccessor().getVoltagePercentage(mRawHandle), DOUBLE_EPSILON);
//        Assertions.assertEquals(3.0, talon.get(), DOUBLE_EPSILON);
//    }
//}
