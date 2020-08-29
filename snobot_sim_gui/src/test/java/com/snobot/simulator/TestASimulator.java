package com.snobot.simulator;

import com.snobot.simulator.wrapper_accessors.AccelerometerWrapperAccessor;
import com.snobot.simulator.wrapper_accessors.AnalogInWrapperAccessor;
import com.snobot.simulator.wrapper_accessors.DataAccessorFactory;
import com.snobot.simulator.wrapper_accessors.DigitalSourceWrapperAccessor;
import com.snobot.simulator.wrapper_accessors.EncoderWrapperAccessor;
import com.snobot.simulator.wrapper_accessors.GyroWrapperAccessor;
import com.snobot.simulator.wrapper_accessors.RelayWrapperAccessor;
import com.snobot.simulator.wrapper_accessors.SolenoidWrapperAccessor;
import com.snobot.simulator.wrapper_accessors.SpeedControllerWrapperAccessor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import com.snobot.simulator.motor_sim.SimpleMotorSimulationConfig;
import com.snobot.simulator.motor_sim.StaticLoadMotorSimulationConfig;
import com.snobot.test.utilities.BaseSimulatorTest;
import com.snobot.test.utilities.MockRobot;

public class TestASimulator extends BaseSimulatorTest
{
    public String extractFiles(String aFilename)
    {
        Path filepath = Path.of("asimultor_test/read_test", aFilename);
        try (InputStream fileStream = getClass().getResource(aFilename).openStream())
        {
            if (fileStream == null || filepath.getParent() == null)
            {
                Assertions.fail();
                return null;
            }
            if (!filepath.getParent().toFile().exists() && !filepath.getParent().toFile().mkdirs())
            {
                Assertions.fail();
                return null;
            }

            Files.copy(fileStream, filepath, StandardCopyOption.REPLACE_EXISTING);
        }
        catch (IOException ex)
        {
            Assertions.fail(ex);
        }

        return filepath.toAbsolutePath().toString();
    }

    @Test
    public void testASimulator()
    {
        String file = extractFiles("testReadFile.yml");

        ASimulator baseSimulator = new ASimulator();
        Assertions.assertTrue(baseSimulator.loadConfig(file));

        // Used to create some components
        new MockRobot();

        AccelerometerWrapperAccessor accelerometerAccessor = DataAccessorFactory.getInstance().getAccelerometerAccessor();
        AnalogInWrapperAccessor analogInAccessor = DataAccessorFactory.getInstance().getAnalogInAccessor();
        DigitalSourceWrapperAccessor digitalAccessor = DataAccessorFactory.getInstance().getDigitalAccessor();
        EncoderWrapperAccessor encoderAccessor = DataAccessorFactory.getInstance().getEncoderAccessor();
        SpeedControllerWrapperAccessor scAccessor =  DataAccessorFactory.getInstance().getSpeedControllerAccessor();
        SolenoidWrapperAccessor solenoidAccessor =  DataAccessorFactory.getInstance().getSolenoidAccessor();
        RelayWrapperAccessor relayAccessor =  DataAccessorFactory.getInstance().getRelayAccessor();
        GyroWrapperAccessor gyroAccessor =  DataAccessorFactory.getInstance().getGyroAccessor();

        Assertions.assertEquals("I2C ADXL345 X Accel", accelerometerAccessor.getWrapper(50).getName());
        Assertions.assertEquals("I2C ADXL345 Y Accel", accelerometerAccessor.getWrapper(51).getName());
        Assertions.assertEquals("I2C ADXL345 Z Accel", accelerometerAccessor.getWrapper(52).getName());
        Assertions.assertEquals("SPI ADXL345 X Accel", accelerometerAccessor.getWrapper(103).getName());
        Assertions.assertEquals("SPI ADXL345 Y Accel", accelerometerAccessor.getWrapper(104).getName());
        Assertions.assertEquals("SPI ADXL345 Z Accel", accelerometerAccessor.getWrapper(105).getName());
        Assertions.assertEquals("SPI ADXL362 X Accel", accelerometerAccessor.getWrapper(156).getName());
        Assertions.assertEquals("SPI ADXL362 Y Accel", accelerometerAccessor.getWrapper(157).getName());
        Assertions.assertEquals("SPI ADXL362 Z Accel", accelerometerAccessor.getWrapper(158).getName());

        Assertions.assertEquals("Analog In 0", analogInAccessor.getWrapper(0).getName());
        Assertions.assertEquals("Analog In 1", analogInAccessor.getWrapper(1).getName());
        Assertions.assertEquals("Analog In 2", analogInAccessor.getWrapper(2).getName());

        Assertions.assertEquals("Digital IO 0", digitalAccessor.getWrapper(0).getName());
        Assertions.assertEquals("Digital IO 1", digitalAccessor.getWrapper(1).getName());
        Assertions.assertEquals("Digital IO 2", digitalAccessor.getWrapper(2).getName());
        Assertions.assertEquals("Digital IO 3", digitalAccessor.getWrapper(3).getName());
        Assertions.assertEquals("Digital IO 4", digitalAccessor.getWrapper(4).getName());
        Assertions.assertEquals("Digital IO 5", digitalAccessor.getWrapper(5).getName());
        Assertions.assertEquals("Digital IO 6", digitalAccessor.getWrapper(6).getName());
        Assertions.assertEquals("Digital IO 7", digitalAccessor.getWrapper(7).getName());
        Assertions.assertEquals("Digital IO 8", digitalAccessor.getWrapper(8).getName());
        Assertions.assertEquals("Digital IO 9", digitalAccessor.getWrapper(9).getName());

        Assertions.assertEquals("Encoder 0", encoderAccessor.getWrapper(0).getName());
        Assertions.assertEquals("Encoder 1", encoderAccessor.getWrapper(1).getName());
        Assertions.assertEquals("Encoder 2", encoderAccessor.getWrapper(2).getName());
        Assertions.assertTrue(encoderAccessor.getWrapper(0).isHookedUp());
        Assertions.assertFalse(encoderAccessor.getWrapper(1).isHookedUp());
        Assertions.assertFalse(encoderAccessor.getWrapper(2).isHookedUp());
        Assertions.assertEquals(1, encoderAccessor.getWrapper(0).getHookedUpId());

        Assertions.assertEquals("Analog Gyro", gyroAccessor.getWrapper(1).getName());

        Assertions.assertEquals("Speed Controller 0", scAccessor.getWrapper(0).getName());
        Assertions.assertEquals("Speed Controller 1", scAccessor.getWrapper(1).getName());
        Assertions.assertEquals("Speed Controller 2", scAccessor.getWrapper(2).getName());
        Assertions.assertEquals("Speed Controller 3", scAccessor.getWrapper(3).getName());
        Assertions.assertEquals("Speed Controller 4", scAccessor.getWrapper(4).getName());
        Assertions.assertEquals("Speed Controller 5", scAccessor.getWrapper(5).getName());
        // Assertions.assertEquals("Speed Controller 6",
        // DataAccessorFactory.getInstance().getSpeedControllerAccessor().getWrapper(6).getName());
        Assertions.assertEquals("Speed Controller 7", scAccessor.getWrapper(7).getName());
        Assertions.assertEquals("Speed Controller 8", scAccessor.getWrapper(8).getName());

        Assertions.assertEquals("Relay 0", relayAccessor.getWrapper(0).getName());

        Assertions.assertEquals("Solenoid 0", solenoidAccessor.getWrapper(0).getName());
        Assertions.assertEquals("Solenoid 1", solenoidAccessor.getWrapper(1).getName());
        Assertions.assertEquals("Solenoid 3", solenoidAccessor.getWrapper(3).getName());

        {
            SimpleMotorSimulationConfig config = scAccessor.getMotorSimSimpleModelConfig(0);
            Assertions.assertEquals(12, config.mMaxSpeed, DOUBLE_EPSILON);
        }
        {
            StaticLoadMotorSimulationConfig config = scAccessor.getMotorSimStaticModelConfig(1);
            Assertions.assertEquals(.5, config.mLoad, DOUBLE_EPSILON);
            Assertions.assertEquals(6.28, config.mConversionFactor, DOUBLE_EPSILON);
        }

        Assertions.assertEquals(3, DataAccessorFactory.getInstance().getSimulatorDataAccessor().getSimulatorComponentConfigs().size());
    }
}
