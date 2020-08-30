package com.snobot.simulator.joysticks;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.snobot.simulator.joysticks.joystick_specializations.KeyboardJoystick;
import com.snobot.simulator.joysticks.joystick_specializations.NullJoystick;
import com.snobot.simulator.joysticks.joystick_specializations.Ps4Joystick;
import com.snobot.simulator.joysticks.joystick_specializations.XboxJoystick;
import com.snobot.test.utilities.BaseSimulatorTest;
import com.snobot.test.utilities.MockController;

import edu.wpi.first.wpilibj.DriverStation;

public class TestJoystickFactory extends BaseSimulatorTest
{
    @Test
    public void testJoystickFactory()
    {
        File configFile = new File(JoystickFactory.sJOYSTICK_CONFIG_FILE);
        if (configFile.exists())
        {
            Assertions.assertTrue(configFile.delete());
        }

        if (!configFile.getParentFile().exists())
        {
            Assertions.assertTrue(configFile.getParentFile().mkdirs());
        }

        JoystickFactory factory = JoystickFactory.getInstance();

        Map<String, ControllerConfiguration> config = factory.getControllerConfiguration();
        Assertions.assertEquals(0, config.size());

        for (int i = 0; i < DriverStation.kJoystickPorts; ++i)
        {
            Assertions.assertTrue(factory.get(i) instanceof NullJoystick);
        }

        config.put("X", new ControllerConfiguration(new MockController(), XboxJoystick.class));
        config.put("Y", new ControllerConfiguration(new MockController(), Ps4Joystick.class));
        config.put("Z", new ControllerConfiguration(new MockController(), KeyboardJoystick.class));

        factory.setJoysticks(0, "X");
        factory.setJoysticks(3, "Y");
        factory.setJoysticks(5, "Z");

        Assertions.assertTrue(factory.get(0) instanceof XboxJoystick);
        Assertions.assertTrue(factory.get(1) instanceof NullJoystick);
        Assertions.assertTrue(factory.get(2) instanceof NullJoystick);
        Assertions.assertTrue(factory.get(3) instanceof Ps4Joystick);
        Assertions.assertTrue(factory.get(4) instanceof NullJoystick);
        Assertions.assertTrue(factory.get(5) instanceof KeyboardJoystick);

        Properties properties = new Properties();
        try (InputStream inputStream = new FileInputStream(JoystickFactory.sJOYSTICK_CONFIG_FILE))
        {
            properties.load(inputStream);
        }
        catch (IOException e)
        {
            Assertions.fail(e.getMessage());
        }


        Assertions.assertEquals("X---com.snobot.simulator.joysticks.joystick_specializations.XboxJoystick", properties.getProperty("Joystick_0"));
        Assertions.assertEquals("Null Joystick---null", properties.getProperty("Joystick_1"));
        Assertions.assertEquals("Null Joystick---null", properties.getProperty("Joystick_2"));
        Assertions.assertEquals("Y---com.snobot.simulator.joysticks.joystick_specializations.Ps4Joystick", properties.getProperty("Joystick_3"));
        Assertions.assertEquals("Null Joystick---null", properties.getProperty("Joystick_4"));
        Assertions.assertEquals("Z---com.snobot.simulator.joysticks.joystick_specializations.KeyboardJoystick", properties.getProperty("Joystick_5"));
    }

}
