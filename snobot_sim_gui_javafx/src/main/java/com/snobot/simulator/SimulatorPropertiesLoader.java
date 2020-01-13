package com.snobot.simulator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SimulatorPropertiesLoader
{
    private static final Logger LOGGER = LogManager.getLogger(SimulatorPropertiesLoader.class);

    private String mRobotClassName;
    private String mRobotType;
    private BaseSimulator mSimulator;

    public void loadProperties(File aConfigDirectory)
    {
        File propertiesFile = new File(aConfigDirectory, "simulator_config.properties");

        try
        {
            if (!propertiesFile.exists())
            {
                createDefaultConfig(aConfigDirectory, propertiesFile);
            }

            Properties p = new Properties();

            try (FileInputStream fis = new FileInputStream(propertiesFile))
            {
                p.load(fis);
            }

            mRobotClassName = p.getProperty("robot_class");
            mRobotType = p.getProperty("robot_type");

            String simulatorClassName = p.getProperty("simulator_class");

            createSimulator(simulatorClassName, p.getProperty("simulator_config"));
        }
        catch (IOException ex)
        {
            LOGGER.log(Level.WARN, "Could not read properties file", ex);
        }
    }

    private void createDefaultConfig(File aConfigDirectory, File aPropertiesFile) throws IOException
    {
        LOGGER.log(Level.WARN, "Could not read properties file, will use defaults and will overwrite the file if it exists");

        if (!aConfigDirectory.exists() && !aConfigDirectory.mkdirs())
        {
            throw new IllegalStateException();
        }

        String defaultSimConfig = aConfigDirectory + "/simulator_config.yml";
        Properties defaults = new Properties();

        defaults.putIfAbsent("robot_class", "com.snobot.simulator.example_robot.ExampleRobot");
        defaults.putIfAbsent("robot_type", "java");
        defaults.putIfAbsent("simulator_config", defaultSimConfig);

        File defaultConfigFile = new File(defaultSimConfig);
        if (!defaultConfigFile.exists() && !defaultConfigFile.createNewFile())
        {
            LOGGER.log(Level.WARN, "Could not create default config file at " + defaultConfigFile);
        }

        try (OutputStreamWriter fw = new OutputStreamWriter(new FileOutputStream(aPropertiesFile), StandardCharsets.UTF_8))
        {
            defaults.store(fw, "");
        }
    }

    private void createSimulator(String aSimulatorClassName, String aSimulatorConfig)
    {
        try
        {
            if (aSimulatorClassName == null || aSimulatorClassName.isEmpty())
            {
                mSimulator = new BaseSimulator();
                mSimulator.loadConfig(aSimulatorConfig);

                LOGGER.log(Level.DEBUG, "Created default simulator");
            }
            else
            {
                mSimulator = (BaseSimulator) Class.forName(aSimulatorClassName).getDeclaredConstructor().newInstance();
                mSimulator.loadConfig(aSimulatorConfig);
                LOGGER.log(Level.INFO, aSimulatorClassName);
            }
        }
        catch (ReflectiveOperationException | IllegalArgumentException | SecurityException e)
        {
            LOGGER.log(Level.FATAL, "Could not find simulator class " + aSimulatorClassName, e);
        }
    }

    public String getRobotType()
    {
        return mRobotType;
    }

    public String getRobotClassName()
    {
        return mRobotClassName;
    }

    public BaseSimulator getSimulator()
    {
        return mSimulator;
    }
}
