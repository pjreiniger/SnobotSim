package com.snobot.simulator.simulator_components.adx_family;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import com.snobot.simulator.module_wrapper.interfaces.IGyroWrapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import com.snobot.simulator.wrapper_accessors.DataAccessorFactory;
import com.snobot.test.utilities.BaseSimulatorJniTest;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.SPI;

public class TestSpiGyro extends BaseSimulatorJniTest
{
    public static Collection<SPI.Port> getData()
    {
        Collection<SPI.Port> output = new ArrayList<>();

        output.addAll(Arrays.asList(SPI.Port.values()));

        return output;
    }

    @ParameterizedTest
    @MethodSource("getData")
    public void testSpiGyro(SPI.Port aPort)
    {
        DataAccessorFactory.getInstance().getSpiAccessor().createSpiSimulator(aPort.value, "ADXRS450");
        ADXRS450_Gyro gyro = new ADXRS450_Gyro(aPort);

        int gyroHandle = 100 + aPort.value;

        IGyroWrapper gyroWrapper = DataAccessorFactory.getInstance().getGyroAccessor().getWrapper(gyroHandle);
        Assertions.assertNotNull(gyroWrapper);

        Assertions.assertEquals(0, gyroWrapper.getAngle(), DOUBLE_EPSILON);
        Assertions.assertEquals(0, gyro.getAngle(), DOUBLE_EPSILON);

        gyroWrapper.setAngle(90);
        Assertions.assertEquals(90, gyroWrapper.getAngle(), DOUBLE_EPSILON);
        Assertions.assertEquals(90, gyro.getAngle(), DOUBLE_EPSILON);

        gyroWrapper.setAngle(192.1234);
        Assertions.assertEquals(192.1234, gyroWrapper.getAngle(), DOUBLE_EPSILON);
        Assertions.assertEquals(192.1234, gyro.getAngle(), DOUBLE_EPSILON);

        gyroWrapper.setAngle(359.9999);
        Assertions.assertEquals(359.9999, gyroWrapper.getAngle(), DOUBLE_EPSILON);
        Assertions.assertEquals(359.9999, gyro.getAngle(), DOUBLE_EPSILON);

        gyroWrapper.setAngle(-421.3358);
        Assertions.assertEquals(-421.3358, gyroWrapper.getAngle(), DOUBLE_EPSILON);
        Assertions.assertEquals(-421.3358, gyro.getAngle(), DOUBLE_EPSILON);

        // Reset
        gyro.reset();
        Assertions.assertEquals(0, gyro.getAngle(), DOUBLE_EPSILON);
    }
}
