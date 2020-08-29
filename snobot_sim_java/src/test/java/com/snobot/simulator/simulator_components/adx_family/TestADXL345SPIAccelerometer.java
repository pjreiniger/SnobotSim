package com.snobot.simulator.simulator_components.adx_family;

import java.util.ArrayList;
import java.util.Collection;

import com.snobot.simulator.module_wrapper.interfaces.IAccelerometerWrapper;
import com.snobot.simulator.wrapper_accessors.AccelerometerWrapperAccessor;
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
        AccelerometerWrapperAccessor accelAccessor = DataAccessorFactory.getInstance().getAccelerometerAccessor();

        final double DOUBLE_EPSILON = 1 / 256.0; // Resoultion isn't as good as
                                                 // normal sensors
        DataAccessorFactory.getInstance().getSpiAccessor().createSpiSimulator(aPort.value, "ADXL345");

        ADXL345_SPI accel = new ADXL345_SPI(aPort, aRange);

        int xHandle = 100 + aPort.value * 3;
        int yHandle = 101 + aPort.value * 3;
        int zHandle = 102 + aPort.value * 3;

        Assertions.assertEquals(3, accelAccessor.getPortList().size());
        Assertions.assertTrue(accelAccessor.getPortList().contains(xHandle));
        Assertions.assertTrue(accelAccessor.getPortList().contains(yHandle));
        Assertions.assertTrue(accelAccessor.getPortList().contains(zHandle));

        IAccelerometerWrapper xWrapper = accelAccessor.getWrapper(xHandle);
        IAccelerometerWrapper yWrapper = accelAccessor.getWrapper(yHandle);
        IAccelerometerWrapper zWrapper = accelAccessor.getWrapper(zHandle);

        // Initial State
        Assertions.assertEquals(0, xWrapper.getAcceleration(), DOUBLE_EPSILON);
        Assertions.assertEquals(0, yWrapper.getAcceleration(), DOUBLE_EPSILON);
        Assertions.assertEquals(0, zWrapper.getAcceleration(), DOUBLE_EPSILON);
        Assertions.assertEquals(0, accel.getX(), DOUBLE_EPSILON);
        Assertions.assertEquals(0, accel.getY(), DOUBLE_EPSILON);
        Assertions.assertEquals(0, accel.getZ(), DOUBLE_EPSILON);

        // Set positive accelerations
        xWrapper.setAcceleration(0);
        yWrapper.setAcceleration(1);
        zWrapper.setAcceleration(2);
        ADXL345_SPI.AllAxes axes = accel.getAccelerations();
        Assertions.assertEquals(0, xWrapper.getAcceleration(), DOUBLE_EPSILON);
        Assertions.assertEquals(1, yWrapper.getAcceleration(), DOUBLE_EPSILON);
        Assertions.assertEquals(2, zWrapper.getAcceleration(), DOUBLE_EPSILON);
        Assertions.assertEquals(0, accel.getX(), DOUBLE_EPSILON);
        Assertions.assertEquals(1, accel.getY(), DOUBLE_EPSILON);
        Assertions.assertEquals(2, accel.getZ(), DOUBLE_EPSILON);
        Assertions.assertEquals(0, axes.XAxis, DOUBLE_EPSILON);
        Assertions.assertEquals(1, axes.YAxis, DOUBLE_EPSILON);
        Assertions.assertEquals(2, axes.ZAxis, DOUBLE_EPSILON);

        // Set Negative accelerations
        xWrapper.setAcceleration(-0.3);
        yWrapper.setAcceleration(-1.3);
        zWrapper.setAcceleration(-2.0);
        axes = accel.getAccelerations();
        Assertions.assertEquals(-0.3, xWrapper.getAcceleration(), DOUBLE_EPSILON);
        Assertions.assertEquals(-1.3, yWrapper.getAcceleration(), DOUBLE_EPSILON);
        Assertions.assertEquals(-2.0, zWrapper.getAcceleration(), DOUBLE_EPSILON);
        Assertions.assertEquals(-0.3, accel.getX(), DOUBLE_EPSILON);
        Assertions.assertEquals(-1.3, accel.getY(), DOUBLE_EPSILON);
        Assertions.assertEquals(-2.0, accel.getZ(), DOUBLE_EPSILON);
        Assertions.assertEquals(-0.3, axes.XAxis, DOUBLE_EPSILON);
        Assertions.assertEquals(-1.3, axes.YAxis, DOUBLE_EPSILON);
        Assertions.assertEquals(-2.0, axes.ZAxis, DOUBLE_EPSILON);
    }
}
