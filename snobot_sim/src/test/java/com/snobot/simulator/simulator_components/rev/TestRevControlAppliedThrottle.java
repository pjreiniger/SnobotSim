package com.snobot.simulator.simulator_components.rev;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.snobot.simulator.module_wrapper.interfaces.IPwmWrapper;
import com.snobot.simulator.wrapper_accessors.DataAccessorFactory;
import com.snobot.simulator.wrapper_accessors.SpeedControllerWrapperAccessor;
import com.snobot.simulator.wrapper_accessors.jni.JniSpeedControllerWrapperAccessor;
import com.snobot.test.utilities.BaseSimulatorJniTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

public class TestRevControlAppliedThrottle extends BaseSimulatorJniTest
{
    private static final double sDOUBLE_EPSILON = 1.0 / 1023;

    @ParameterizedTest
    @ArgumentsSource(GetRevTestIds.class)
    public void testSimpleSetters(int aCanHandle)
    {
        int rawHandle = aCanHandle + JniSpeedControllerWrapperAccessor.sCAN_SC_OFFSET;

        SpeedControllerWrapperAccessor scAccessor = DataAccessorFactory.getInstance().getSpeedControllerAccessor();

        Assertions.assertEquals(0, scAccessor.getPortList().size());

        CANSparkMax sparksMax = new CANSparkMax(aCanHandle, CANSparkMaxLowLevel.MotorType.kBrushless);
        IPwmWrapper wrapper =  DataAccessorFactory.getInstance().getSpeedControllerAccessor().getWrapper(rawHandle);
        Assertions.assertEquals(1, scAccessor.getPortList().size());
        Assertions.assertEquals("Rev SC " + aCanHandle, wrapper.getName());

        sparksMax.set(-1.0);
        Assertions.assertEquals(-1.0, wrapper.getVoltagePercentage(), sDOUBLE_EPSILON);
        Assertions.assertEquals(-1.0, sparksMax.get(), sDOUBLE_EPSILON);
        Assertions.assertEquals(-1.0, sparksMax.getAppliedOutput(), sDOUBLE_EPSILON);

        sparksMax.set(0.5);
        Assertions.assertEquals(0.5, wrapper.getVoltagePercentage(), sDOUBLE_EPSILON);
        Assertions.assertEquals(0.5, sparksMax.get(), sDOUBLE_EPSILON);
        Assertions.assertEquals(0.5, sparksMax.getAppliedOutput(), sDOUBLE_EPSILON);

        sparksMax.close();
    }
}
