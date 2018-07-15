package com.snobot.simulator.simulator_components.adx_family;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import com.snobot.simulator.wrapper_accessors.DataAccessorFactory;
import com.snobot.test.utilities.BaseSimulatorJniTest;

import edu.wpi.first.wpilibj.ADXL345_SPI;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.interfaces.Accelerometer.Range;

public class TestADXL345SPIAccelerometer extends BaseSimulatorJniTest
{
    private static final double sDOUBLE_EPSILON = 1 / 256.0; // Resoultion isn't as good as normal sensors

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
        DataAccessorFactory.getInstance().getSpiAccessor().createSpiSimulator(aPort.value, "ADXL345");

        ADXL345_SPI accel = new ADXL345_SPI(aPort, aRange);

        int xHandle = 100 + aPort.value * 3;
        int yHandle = 101 + aPort.value * 3;
        int zHandle = 102 + aPort.value * 3;

        Assertions.assertEquals(3, DataAccessorFactory.getInstance().getAccelerometerAccessor().getPortList().size());
        Assertions.assertTrue(DataAccessorFactory.getInstance().getAccelerometerAccessor().getPortList().contains(xHandle));
        Assertions.assertTrue(DataAccessorFactory.getInstance().getAccelerometerAccessor().getPortList().contains(yHandle));
        Assertions.assertTrue(DataAccessorFactory.getInstance().getAccelerometerAccessor().getPortList().contains(zHandle));

        // Initial State
        Assertions.assertEquals(0, DataAccessorFactory.getInstance().getAccelerometerAccessor().getAcceleration(xHandle), sDOUBLE_EPSILON);
        Assertions.assertEquals(0, DataAccessorFactory.getInstance().getAccelerometerAccessor().getAcceleration(yHandle), sDOUBLE_EPSILON);
        Assertions.assertEquals(0, DataAccessorFactory.getInstance().getAccelerometerAccessor().getAcceleration(zHandle), sDOUBLE_EPSILON);
        Assertions.assertEquals(0, accel.getX(), sDOUBLE_EPSILON);
        Assertions.assertEquals(0, accel.getY(), sDOUBLE_EPSILON);
        Assertions.assertEquals(0, accel.getZ(), sDOUBLE_EPSILON);

        // Set positive accelerations
        DataAccessorFactory.getInstance().getAccelerometerAccessor().setAcceleration(xHandle, 0);
        DataAccessorFactory.getInstance().getAccelerometerAccessor().setAcceleration(yHandle, 1);
        DataAccessorFactory.getInstance().getAccelerometerAccessor().setAcceleration(zHandle, 2);
        ADXL345_SPI.AllAxes axes = accel.getAccelerations();
        Assertions.assertEquals(0, DataAccessorFactory.getInstance().getAccelerometerAccessor().getAcceleration(xHandle), sDOUBLE_EPSILON);
        Assertions.assertEquals(1, DataAccessorFactory.getInstance().getAccelerometerAccessor().getAcceleration(yHandle), sDOUBLE_EPSILON);
        Assertions.assertEquals(2, DataAccessorFactory.getInstance().getAccelerometerAccessor().getAcceleration(zHandle), sDOUBLE_EPSILON);
        Assertions.assertEquals(0, accel.getX(), sDOUBLE_EPSILON);
        Assertions.assertEquals(1, accel.getY(), sDOUBLE_EPSILON);
        Assertions.assertEquals(2, accel.getZ(), sDOUBLE_EPSILON);
        Assertions.assertEquals(0, axes.XAxis, sDOUBLE_EPSILON);
        Assertions.assertEquals(1, axes.YAxis, sDOUBLE_EPSILON);
        Assertions.assertEquals(2, axes.ZAxis, sDOUBLE_EPSILON);

        // Set Negative accelerations
        DataAccessorFactory.getInstance().getAccelerometerAccessor().setAcceleration(xHandle, -0.3);
        DataAccessorFactory.getInstance().getAccelerometerAccessor().setAcceleration(yHandle, -1.3);
        DataAccessorFactory.getInstance().getAccelerometerAccessor().setAcceleration(zHandle, -2.0);
        axes = accel.getAccelerations();
        Assertions.assertEquals(-0.3, DataAccessorFactory.getInstance().getAccelerometerAccessor().getAcceleration(xHandle), sDOUBLE_EPSILON);
        Assertions.assertEquals(-1.3, DataAccessorFactory.getInstance().getAccelerometerAccessor().getAcceleration(yHandle), sDOUBLE_EPSILON);
        Assertions.assertEquals(-2.0, DataAccessorFactory.getInstance().getAccelerometerAccessor().getAcceleration(zHandle), sDOUBLE_EPSILON);
        Assertions.assertEquals(-0.3, accel.getX(), sDOUBLE_EPSILON);
        Assertions.assertEquals(-1.3, accel.getY(), sDOUBLE_EPSILON);
        Assertions.assertEquals(-2.0, accel.getZ(), sDOUBLE_EPSILON);
        Assertions.assertEquals(-0.3, axes.XAxis, sDOUBLE_EPSILON);
        Assertions.assertEquals(-1.3, axes.YAxis, sDOUBLE_EPSILON);
        Assertions.assertEquals(-2.0, axes.ZAxis, sDOUBLE_EPSILON);
    }
}
