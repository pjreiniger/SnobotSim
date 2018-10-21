package edu.wpi.first.wpilibj.sim;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.snobot.simulator.JniLibraryResourceLoader;

import edu.wpi.first.wpilibj.ADXL345_SPI;
import edu.wpi.first.wpilibj.ADXL345_SPI.AllAxes;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.hal.HAL;
import edu.wpi.first.wpilibj.interfaces.Accelerometer;

public class TestLinking
{
    @Test
    public void testLinking()
    {
        JniLibraryResourceLoader.loadLibrary("wpiutil");
        JniLibraryResourceLoader.loadLibrary("wpiHal");
        JniLibraryResourceLoader.loadLibrary("halsim_adx_gyro_accelerometer");
        JniLibraryResourceLoader.loadLibrary("adx_family");
        HAL.initialize(0, 0);

        SPI.Port port = SPI.Port.kOnboardCS1;

        ADXL345_SpiSim simulator = new ADXL345_SpiSim(port.value);
        ADXL345_SPI accel = new ADXL345_SPI(port, Accelerometer.Range.k2G);

        AllAxes all = accel.getAccelerations();
        Assertions.assertEquals(0, all.XAxis);
        Assertions.assertEquals(0, accel.getX());
        Assertions.assertEquals(0, all.YAxis);
        Assertions.assertEquals(0, accel.getY());
        Assertions.assertEquals(0, all.YAxis);
        Assertions.assertEquals(0, accel.getY());

        simulator.setX(.25);
        simulator.setY(.85);
        simulator.setZ(.15);
        all = accel.getAccelerations();

        double EPSILON = 1 / 256.0;
        Assertions.assertEquals(.25, all.XAxis, EPSILON);
        Assertions.assertEquals(.25, accel.getX(), EPSILON);
        Assertions.assertEquals(.85, all.YAxis, EPSILON);
        Assertions.assertEquals(.85, accel.getY(), EPSILON);
        Assertions.assertEquals(.15, all.ZAxis, EPSILON);
        Assertions.assertEquals(.15, accel.getZ(), EPSILON);
    }

}
