package com.snobot.simulator.config;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.snobot.simulator.config.v1.SimulatorConfigReaderV1;
import com.snobot.simulator.motor_sim.SimpleMotorSimulationConfig;
import com.snobot.simulator.motor_sim.StaticLoadMotorSimulationConfig;
import com.snobot.simulator.wrapper_accessors.DataAccessorFactory;
import com.snobot.test.utilities.BaseSimulatorJavaTest;


public class TestReadConfig extends BaseSimulatorJavaTest
{
    public static final int sTEST_PARAMETER = 5;

    @Test
    public void testReadEmptyFile()
    {
        String file = "test_files/ConfigTest/ReadConfig/emptyFile.yml";
        SimulatorConfigReaderV1 reader = new SimulatorConfigReaderV1();
        Assertions.assertTrue(reader.loadConfig(file));
        Assertions.assertNotNull(reader.getConfig());
    }

    @Test
    public void testReadNullFile()
    {
        String file = null;
        SimulatorConfigReaderV1 reader = new SimulatorConfigReaderV1();
        Assertions.assertTrue(reader.loadConfig(file));
        Assertions.assertNull(reader.getConfig());
    }

    @Test
    public void testReadNonExistingFile()
    {
        String file = "does_not_exist.yml";
        SimulatorConfigReaderV1 reader = new SimulatorConfigReaderV1();
        Assertions.assertFalse(reader.loadConfig(file));
        Assertions.assertNull(reader.getConfig());
    }

    @Test
    public void testReadConfig()
    {
        String file = "test_files/ConfigTest/ReadConfig/testReadFile.yml";
        SimulatorConfigReaderV1 reader = new SimulatorConfigReaderV1();
        Assertions.assertTrue(reader.loadConfig(file));
        Assertions.assertNotNull(reader.getConfig());

        Assertions.assertEquals("I2C ADXL345 X Accel", DataAccessorFactory.getInstance().getAccelerometerAccessor().getName(50));
        Assertions.assertEquals("I2C ADXL345 Y Accel", DataAccessorFactory.getInstance().getAccelerometerAccessor().getName(51));
        Assertions.assertEquals("I2C ADXL345 Z Accel", DataAccessorFactory.getInstance().getAccelerometerAccessor().getName(52));
        Assertions.assertEquals("SPI ADXL345 X Accel", DataAccessorFactory.getInstance().getAccelerometerAccessor().getName(103));
        Assertions.assertEquals("SPI ADXL345 Y Accel", DataAccessorFactory.getInstance().getAccelerometerAccessor().getName(104));
        Assertions.assertEquals("SPI ADXL345 Z Accel", DataAccessorFactory.getInstance().getAccelerometerAccessor().getName(105));
        Assertions.assertEquals("SPI ADXL362 X Accel", DataAccessorFactory.getInstance().getAccelerometerAccessor().getName(156));
        Assertions.assertEquals("SPI ADXL362 Y Accel", DataAccessorFactory.getInstance().getAccelerometerAccessor().getName(157));
        Assertions.assertEquals("SPI ADXL362 Z Accel", DataAccessorFactory.getInstance().getAccelerometerAccessor().getName(158));

        Assertions.assertEquals("Analog In 0", DataAccessorFactory.getInstance().getAnalogInAccessor().getName(0));
        Assertions.assertEquals("Analog In 1", DataAccessorFactory.getInstance().getAnalogInAccessor().getName(1));
        Assertions.assertEquals("Analog In 2", DataAccessorFactory.getInstance().getAnalogInAccessor().getName(2));

        Assertions.assertEquals("Digital IO 0", DataAccessorFactory.getInstance().getDigitalAccessor().getName(0));
        Assertions.assertEquals("Digital IO 1", DataAccessorFactory.getInstance().getDigitalAccessor().getName(1));
        Assertions.assertEquals("Digital IO 2", DataAccessorFactory.getInstance().getDigitalAccessor().getName(2));
        Assertions.assertEquals("Digital IO 3", DataAccessorFactory.getInstance().getDigitalAccessor().getName(3));
        Assertions.assertEquals("Digital IO 4", DataAccessorFactory.getInstance().getDigitalAccessor().getName(4));
        Assertions.assertEquals("Digital IO 5", DataAccessorFactory.getInstance().getDigitalAccessor().getName(5));
        Assertions.assertEquals("Digital IO 6", DataAccessorFactory.getInstance().getDigitalAccessor().getName(6));
        Assertions.assertEquals("Digital IO 7", DataAccessorFactory.getInstance().getDigitalAccessor().getName(7));
        Assertions.assertEquals("Digital IO 8", DataAccessorFactory.getInstance().getDigitalAccessor().getName(8));
        Assertions.assertEquals("Digital IO 9", DataAccessorFactory.getInstance().getDigitalAccessor().getName(9));

        Assertions.assertEquals("Encoder 0", DataAccessorFactory.getInstance().getEncoderAccessor().getName(0));
        Assertions.assertEquals("Encoder 1", DataAccessorFactory.getInstance().getEncoderAccessor().getName(1));
        Assertions.assertEquals("Encoder 2", DataAccessorFactory.getInstance().getEncoderAccessor().getName(2));
        Assertions.assertTrue(DataAccessorFactory.getInstance().getEncoderAccessor().isHookedUp(0));
        Assertions.assertFalse(DataAccessorFactory.getInstance().getEncoderAccessor().isHookedUp(1));
        Assertions.assertFalse(DataAccessorFactory.getInstance().getEncoderAccessor().isHookedUp(2));
        Assertions.assertEquals(1, DataAccessorFactory.getInstance().getEncoderAccessor().getHookedUpId(0));

        Assertions.assertEquals("Analog Gyro", DataAccessorFactory.getInstance().getGyroAccessor().getName(1));

        Assertions.assertEquals("Speed Controller 0", DataAccessorFactory.getInstance().getSpeedControllerAccessor().getName(0));
        Assertions.assertEquals("Speed Controller 1", DataAccessorFactory.getInstance().getSpeedControllerAccessor().getName(1));
        Assertions.assertEquals("Speed Controller 2", DataAccessorFactory.getInstance().getSpeedControllerAccessor().getName(2));
        Assertions.assertEquals("Speed Controller 3", DataAccessorFactory.getInstance().getSpeedControllerAccessor().getName(3));
        Assertions.assertEquals("Speed Controller 4", DataAccessorFactory.getInstance().getSpeedControllerAccessor().getName(4));
        Assertions.assertEquals("Speed Controller 5", DataAccessorFactory.getInstance().getSpeedControllerAccessor().getName(5));
        // Assertions.assertEquals("Speed Controller 6",
        // DataAccessorFactory.getInstance().getSpeedControllerAccessor().getName(6));
        Assertions.assertEquals("Speed Controller 7", DataAccessorFactory.getInstance().getSpeedControllerAccessor().getName(7));
        Assertions.assertEquals("Speed Controller 8", DataAccessorFactory.getInstance().getSpeedControllerAccessor().getName(8));

        Assertions.assertEquals("Relay 0", DataAccessorFactory.getInstance().getRelayAccessor().getName(0));

        Assertions.assertEquals("Solenoid 0", DataAccessorFactory.getInstance().getSolenoidAccessor().getName(0));
        Assertions.assertEquals("Solenoid 1", DataAccessorFactory.getInstance().getSolenoidAccessor().getName(1));
        Assertions.assertEquals("Solenoid 3", DataAccessorFactory.getInstance().getSolenoidAccessor().getName(3));

        {
            SimpleMotorSimulationConfig config = DataAccessorFactory.getInstance().getSpeedControllerAccessor().getMotorSimSimpleModelConfig(0);
            Assertions.assertEquals(12, config.mMaxSpeed, DOUBLE_EPSILON);
        }
        {
            StaticLoadMotorSimulationConfig config = DataAccessorFactory.getInstance().getSpeedControllerAccessor().getMotorSimStaticModelConfig(1);
            Assertions.assertEquals(.5, config.mLoad, DOUBLE_EPSILON);
            Assertions.assertEquals(6.28, config.mConversionFactor, DOUBLE_EPSILON);
        }

        Assertions.assertEquals(3, DataAccessorFactory.getInstance().getSimulatorDataAccessor().getSimulatorComponentConfigs().size());
    }

    @Test
    public void testReadLegacyConfigWithoutCan()
    {
        String file = "test_files/ConfigTest/ReadConfig/testLegacyConfigFileWithoutCan.yml";
        SimulatorConfigReaderV1 reader = new SimulatorConfigReaderV1();
        Assertions.assertTrue(reader.loadConfig(file));
        Assertions.assertNotNull(reader.getConfig());

        Assertions.assertEquals("Digital Source6", DataAccessorFactory.getInstance().getDigitalAccessor().getName(6));
        Assertions.assertEquals("Digital Source7", DataAccessorFactory.getInstance().getDigitalAccessor().getName(7));
        Assertions.assertEquals("Digital Source8", DataAccessorFactory.getInstance().getDigitalAccessor().getName(8));
        Assertions.assertEquals("Digital Source9", DataAccessorFactory.getInstance().getDigitalAccessor().getName(9));

        Assertions.assertEquals("drive right", DataAccessorFactory.getInstance().getEncoderAccessor().getName(0));
        Assertions.assertEquals("drive left", DataAccessorFactory.getInstance().getEncoderAccessor().getName(1));
        Assertions.assertEquals("Elevator", DataAccessorFactory.getInstance().getEncoderAccessor().getName(2));

        Assertions.assertEquals("Robot Gyro", DataAccessorFactory.getInstance().getGyroAccessor().getName(100));

        Assertions.assertEquals("Drive Left", DataAccessorFactory.getInstance().getSpeedControllerAccessor().getName(0));
        Assertions.assertEquals("Drive Right", DataAccessorFactory.getInstance().getSpeedControllerAccessor().getName(1));
        Assertions.assertEquals("Elevator", DataAccessorFactory.getInstance().getSpeedControllerAccessor().getName(2));

        Assertions.assertEquals("Claw (A)", DataAccessorFactory.getInstance().getSolenoidAccessor().getName(1));
        Assertions.assertEquals("Claw (B)", DataAccessorFactory.getInstance().getSolenoidAccessor().getName(2));
    }

    @Test
    public void testReadLegacyConfigWithCan()
    {
        String file = "test_files/ConfigTest/ReadConfig/testLegacyConfigFileWithCan.yml";
        SimulatorConfigReaderV1 reader = new SimulatorConfigReaderV1();
        Assertions.assertTrue(reader.loadConfig(file));
        Assertions.assertNotNull(reader.getConfig());

        Assertions.assertEquals("Winch", DataAccessorFactory.getInstance().getSpeedControllerAccessor().getName(3));
        Assertions.assertEquals("Drive Right (A)", DataAccessorFactory.getInstance().getSpeedControllerAccessor().getName(103));
        Assertions.assertEquals("Drive Right (B)", DataAccessorFactory.getInstance().getSpeedControllerAccessor().getName(104));
        Assertions.assertEquals("Drive Left (A)", DataAccessorFactory.getInstance().getSpeedControllerAccessor().getName(101));
        Assertions.assertEquals("Drive Left (B)", DataAccessorFactory.getInstance().getSpeedControllerAccessor().getName(106));
        Assertions.assertEquals("Elevator (A)", DataAccessorFactory.getInstance().getSpeedControllerAccessor().getName(105));
        Assertions.assertEquals("Elevator (B)", DataAccessorFactory.getInstance().getSpeedControllerAccessor().getName(102));

        Assertions.assertEquals("Drive Right", DataAccessorFactory.getInstance().getEncoderAccessor().getName(103));
        Assertions.assertEquals("Drive Left", DataAccessorFactory.getInstance().getEncoderAccessor().getName(101));
        Assertions.assertEquals("Elevator", DataAccessorFactory.getInstance().getEncoderAccessor().getName(105));

        Assertions.assertEquals("Robot Gyro", DataAccessorFactory.getInstance().getGyroAccessor().getName(100));

        Assertions.assertEquals("Claw (A)", DataAccessorFactory.getInstance().getSolenoidAccessor().getName(1));
        Assertions.assertEquals("Claw (B)", DataAccessorFactory.getInstance().getSolenoidAccessor().getName(2));
    }
}
