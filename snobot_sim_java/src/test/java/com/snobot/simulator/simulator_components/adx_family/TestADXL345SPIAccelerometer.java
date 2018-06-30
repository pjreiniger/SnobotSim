package com.snobot.simulator.simulator_components.adx_family;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import com.snobot.simulator.wrapper_accessors.DataAccessorFactory;
import com.snobot.test.utilities.BaseSimulatorJavaTest;

import edu.wpi.first.wpilibj.ADXL345_SPI;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.interfaces.Accelerometer.Range;

@Tag("AdxFamily")
public class TestADXL345SPIAccelerometer extends BaseSimulatorJavaTest
{
    public static Collection<Object[]> getData()
    {
        Collection<Object[]> output = new ArrayList<>();

        for (SPI.Port port : SPI.Port.values())
        {
            for (Range range : Range.values())
            {
                output.add(new Object[]{port, range});
            }
        }

        return output;
    }

    @ParameterizedTest
    @MethodSource("getData")
    public void testADXL345_SPI(SPI.Port aPort, Range aRange)
    {
        final double DOUBLE_EPSILON = 1 / 256.0; // Resoultion isn't as good as
                                                 // normal sensors
        DataAccessorFactory.getInstance().getSimulatorDataAccessor().createSpiSimulator(aPort.value, "ADXL345");

        ADXL345_SPI accel = new ADXL345_SPI(aPort, aRange);

        int xHandle = 100 + aPort.value * 3;
        int yHandle = 101 + aPort.value * 3;
        int zHandle = 102 + aPort.value * 3;

        Assertions.assertEquals(3, DataAccessorFactory.getInstance().getAccelerometerAccessor().getPortList().size());
        Assertions.assertTrue(DataAccessorFactory.getInstance().getAccelerometerAccessor().getPortList().contains(xHandle));
        Assertions.assertTrue(DataAccessorFactory.getInstance().getAccelerometerAccessor().getPortList().contains(yHandle));
        Assertions.assertTrue(DataAccessorFactory.getInstance().getAccelerometerAccessor().getPortList().contains(zHandle));

        // Initial State
        Assertions.assertEquals(0, DataAccessorFactory.getInstance().getAccelerometerAccessor().getAcceleration(xHandle), DOUBLE_EPSILON);
        Assertions.assertEquals(0, DataAccessorFactory.getInstance().getAccelerometerAccessor().getAcceleration(yHandle), DOUBLE_EPSILON);
        Assertions.assertEquals(0, DataAccessorFactory.getInstance().getAccelerometerAccessor().getAcceleration(zHandle), DOUBLE_EPSILON);
        Assertions.assertEquals(0, accel.getX(), DOUBLE_EPSILON);
        Assertions.assertEquals(0, accel.getY(), DOUBLE_EPSILON);
        Assertions.assertEquals(0, accel.getZ(), DOUBLE_EPSILON);

        // Set positive accelerations
        DataAccessorFactory.getInstance().getAccelerometerAccessor().setAcceleration(xHandle, 0);
        DataAccessorFactory.getInstance().getAccelerometerAccessor().setAcceleration(yHandle, 1);
        DataAccessorFactory.getInstance().getAccelerometerAccessor().setAcceleration(zHandle, 2);
        ADXL345_SPI.AllAxes axes = accel.getAccelerations();
        Assertions.assertEquals(0, DataAccessorFactory.getInstance().getAccelerometerAccessor().getAcceleration(xHandle), DOUBLE_EPSILON);
        Assertions.assertEquals(1, DataAccessorFactory.getInstance().getAccelerometerAccessor().getAcceleration(yHandle), DOUBLE_EPSILON);
        Assertions.assertEquals(2, DataAccessorFactory.getInstance().getAccelerometerAccessor().getAcceleration(zHandle), DOUBLE_EPSILON);
        Assertions.assertEquals(0, accel.getX(), DOUBLE_EPSILON);
        Assertions.assertEquals(1, accel.getY(), DOUBLE_EPSILON);
        Assertions.assertEquals(2, accel.getZ(), DOUBLE_EPSILON);
        Assertions.assertEquals(0, axes.XAxis, DOUBLE_EPSILON);
        Assertions.assertEquals(1, axes.YAxis, DOUBLE_EPSILON);
        Assertions.assertEquals(2, axes.ZAxis, DOUBLE_EPSILON);

        // Set Negative accelerations
        DataAccessorFactory.getInstance().getAccelerometerAccessor().setAcceleration(xHandle, -0.3);
        DataAccessorFactory.getInstance().getAccelerometerAccessor().setAcceleration(yHandle, -1.3);
        DataAccessorFactory.getInstance().getAccelerometerAccessor().setAcceleration(zHandle, -2.0);
        axes = accel.getAccelerations();
        Assertions.assertEquals(-0.3, DataAccessorFactory.getInstance().getAccelerometerAccessor().getAcceleration(xHandle), DOUBLE_EPSILON);
        Assertions.assertEquals(-1.3, DataAccessorFactory.getInstance().getAccelerometerAccessor().getAcceleration(yHandle), DOUBLE_EPSILON);
        Assertions.assertEquals(-2.0, DataAccessorFactory.getInstance().getAccelerometerAccessor().getAcceleration(zHandle), DOUBLE_EPSILON);
        Assertions.assertEquals(-0.3, accel.getX(), DOUBLE_EPSILON);
        Assertions.assertEquals(-1.3, accel.getY(), DOUBLE_EPSILON);
        Assertions.assertEquals(-2.0, accel.getZ(), DOUBLE_EPSILON);
        Assertions.assertEquals(-0.3, axes.XAxis, DOUBLE_EPSILON);
        Assertions.assertEquals(-1.3, axes.YAxis, DOUBLE_EPSILON);
        Assertions.assertEquals(-2.0, axes.ZAxis, DOUBLE_EPSILON);
    }
}
