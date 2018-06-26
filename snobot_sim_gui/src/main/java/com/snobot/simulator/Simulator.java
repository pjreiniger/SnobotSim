package com.snobot.simulator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.snobot.simulator.gui.SimulatorFrame;
import com.snobot.simulator.joysticks.IMockJoystick;
import com.snobot.simulator.joysticks.JoystickFactory;
import com.snobot.simulator.robot_container.CppRobotContainer;
import com.snobot.simulator.robot_container.IRobotClassContainer;
import com.snobot.simulator.robot_container.JavaRobotContainer;
import com.snobot.simulator.robot_container.PythonRobotContainer;
import com.snobot.simulator.wrapper_accessors.DataAccessorFactory;
import com.snobot.simulator.wrapper_accessors.SimulatorDataAccessor.SnobotLogLevel;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DriverStation;

/**
 * The actual simulator. Runs the robot and the GUI threads. The GUI will appear
 * after receiving the signal that the robot code has started
 *
 * @author PJ
 *
 */
public class Simulator
{
    private static final Logger sLOGGER = LogManager.getLogger(Simulator.class);

    private final String mUserConfigDirectory;
    private final String mPropertiesFile;

    // private String mSimulatorClassName; // The name of the class that
    // represents the simulator
    private String mSimulatorConfig;

    private IRobotClassContainer mRobot; // The robot code to run
    private ASimulator mSimulator; // The robot code to run

    protected Thread mRobotThread;
    protected Thread mSimulatorThread;
    protected boolean mRunningSimulator;

    /**
     * Constructor
     *
     * @param aLogLevel
     *            The log level to set up the simulator with
     * @param aPluginDirectory
     *            The directory to search for plugins
     * @param aUserConfigDir
     *            The config directory where settings are saved
     * @throws Exception
     *             Throws an exception if the plugin loading failed
     */
    public Simulator(SnobotLogLevel aLogLevel, File aPluginDirectory, String aUserConfigDir) throws Exception
    {
        DataAccessorFactory.getInstance().getSimulatorDataAccessor().setLogLevel(aLogLevel);

        PluginSniffer sniffer = new PluginSniffer();
        sniffer.loadPlugins(aPluginDirectory);

        mUserConfigDirectory = aUserConfigDir;
        mPropertiesFile = mUserConfigDirectory + "simulator_config.properties";

        File configDir = new File(aUserConfigDir);
        if (!Files.exists(configDir.toPath()) && !configDir.mkdir())
        {
            sLOGGER.log(Level.ERROR, "Could not create config directory at " + configDir);
        }
    }

    private void loadConfig(String aFile)
    {

        try
        {
            if (!Files.exists(Paths.get(aFile)))
            {
                sLOGGER.log(Level.WARN,
                        "Could not read properties file, will use defaults and will overwrite the file if it exists");

                String defaultSimConfig = mUserConfigDirectory + "simulator_config.yml";
                Properties defaults = new Properties();

                try (InputStream stream = Simulator.class.getResourceAsStream("/com/snobot/simulator/config/default_properties.properties"))
                {
                    defaults.load(stream);
                }
                defaults.putIfAbsent("simulator_config", defaultSimConfig);

                File defaultConfigFile = new File(defaultSimConfig);
                if (!defaultConfigFile.exists() && !defaultConfigFile.createNewFile())
                {
                    sLOGGER.log(Level.WARN, "Could not create default config file at " + defaultConfigFile);
                }

                try (OutputStreamWriter fw = new OutputStreamWriter(new FileOutputStream(aFile), "UTF-8"))
                {
                    defaults.store(fw, "");
                }

            }

            Properties p = new Properties();

            try (FileInputStream fis = new FileInputStream(new File(aFile)))
            {
                p.load(fis);
            }

            String robotClassName = p.getProperty("robot_class");
            String robotType = p.getProperty("robot_type");

            String simulatorClassName = p.getProperty("simulator_class");
            mSimulatorConfig = p.getProperty("simulator_config");

            createSimulator(simulatorClassName, mSimulatorConfig);
            createRobot(robotType, robotClassName);

            // Change the network table preferences path. Need to start
            // the robot, stop the server and restart it
            NetworkTableInstance inst = NetworkTableInstance.getDefault();
            inst.stopServer();
            inst.startServer(mUserConfigDirectory + "networktables.ini");
        }
        catch (IOException | InstantiationException | IllegalAccessException | ClassNotFoundException | NoSuchMethodException | SecurityException
                | IllegalArgumentException | InvocationTargetException ex)
        {
            sLOGGER.log(Level.WARN, "Could not read properties file", ex);
        }
    }

    private void createRobot(String aRobotType, String aRobotClassName)
            throws InstantiationException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException,
            SecurityException, IllegalArgumentException, InvocationTargetException
    {
        sLOGGER.log(Level.INFO, "Starting Robot Code");

        if (aRobotType == null || "java".equals(aRobotType))
        {
            mRobot = new JavaRobotContainer(aRobotClassName);
        }
        else if ("cpp".equals(aRobotType))
        {
            mRobot = new CppRobotContainer(aRobotClassName);
        }
        else if ("python".equals(aRobotType))
        {
            mRobot = new PythonRobotContainer(aRobotClassName);
        }
        else
        {
            throw new RuntimeException("Unsuppored robot type " + aRobotType);
        }

        mRobot.constructRobot();
    }

    private void createSimulator(String aSimulatorClassName, String aSimulatorConfig)
    {
        try
        {
            if (aSimulatorClassName != null && !aSimulatorClassName.isEmpty()) // NOPMD
            {
                mSimulator = (ASimulator) Class.forName(aSimulatorClassName).newInstance();
                mSimulator.loadConfig(aSimulatorConfig);
                sLOGGER.log(Level.INFO, aSimulatorClassName);
            }
            else
            {
                mSimulator = new ASimulator();
                mSimulator.loadConfig(aSimulatorConfig);
                sLOGGER.log(Level.DEBUG, "Created default simulator");
            }

        }
        catch (ClassNotFoundException | InstantiationException | IllegalAccessError | IllegalAccessException e)
        {
            sLOGGER.log(Level.FATAL, "Could not find simulator class " + aSimulatorClassName, e);
        }
    }

    /**
     * Starts the simulation by starting the robot and the GUI
     *
     * @throws InstantiationException
     *             Thrown the robot class could not be started with reflection
     * @throws IllegalAccessException
     *             Thrown the robot class could not be started with reflection
     * @throws ClassNotFoundException
     *             Thrown the robot class could not be started with reflection
     * @throws NoSuchMethodException
     *             Thrown the robot class could not be started with reflection
     * @throws SecurityException
     *             Thrown the robot class could not be started with reflection
     * @throws IllegalArgumentException
     *             Thrown the robot class could not be started with reflection
     * @throws InvocationTargetException
     *             Thrown the robot class could not be started with reflection
     */
    public void startSimulation()
            throws InstantiationException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException, SecurityException,
            IllegalArgumentException, InvocationTargetException
    {
        loadConfig(mPropertiesFile);

        sendJoystickUpdate();

        if (mSimulator != null && mRobot != null) // NOPMD
        {
            mRobotThread = new Thread(createRobotThread(), "RobotThread");
            mSimulatorThread = new Thread(createSimulationThread(), "SimulatorThread");

            mRunningSimulator = true;
            sLOGGER.log(Level.INFO, "Starting simulator");

            mSimulatorThread.start();
            mRobotThread.start();
        }
        else
        {
            if (mSimulator != null)
            {
                sLOGGER.log(Level.FATAL, "Could not start simulator, no simulator was created");
            }
            if (mRobot != null)
            {
                sLOGGER.log(Level.FATAL, "Could not start simulator, robot was created");
            }
            exitWithError();
        }
    }

    protected void setFrameVisible(SimulatorFrame aFrame)
    {
        aFrame.pack();
        aFrame.setVisible(true);
        aFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    protected void showInitializationMessage(String aMessage)
    {
        if (aMessage != null && !aMessage.isEmpty())
        {
            JOptionPane.showMessageDialog(null, "Some simulator components were specified in the config file, but not in the robot:\n" + aMessage,
                    "Config file mismatch", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void sendJoystickUpdate()
    {
        IMockJoystick[] joysticks = JoystickFactory.getInstance().getAll();
        for (int i = 0; i < joysticks.length; ++i)
        {
            IMockJoystick joystick = joysticks[i];
            DataAccessorFactory.getInstance().getSimulatorDataAccessor().setJoystickInformation(i, joystick.getAxisValues(), joystick.getPovValues(),
                    joystick.getButtonCount(),
                    joystick.getButtonMask());
        }
    }

    private Runnable createSimulationThread()
    {
        return new Runnable()
        {

            @Override
            public void run()
            {
                try
                {
                    DataAccessorFactory.getInstance().getSimulatorDataAccessor().waitForProgramToStart();

                    String errors = DataAccessorFactory.getInstance().getInitializationErrors();
                    showInitializationMessage(errors);
                    mSimulator.setRobot(mRobot);

                    SimulatorFrame frame = new SimulatorFrame(mSimulatorConfig);
                    setFrameVisible(frame);

                    while (mRunningSimulator)
                    {
                        DataAccessorFactory.getInstance().getSimulatorDataAccessor().waitForNextUpdateLoop();
                        DataAccessorFactory.getInstance().getSimulatorDataAccessor().updateSimulatorComponents();

                        mSimulator.update();
                        frame.updateLoop();
                        sendJoystickUpdate();
                    }
                }
                catch (Throwable e)
                {
                    sLOGGER.log(Level.FATAL, "Encountered fatal error, will exit.  Error: " + e.getMessage(), e);
                    exitWithError();
                }
            }
        };
    }

    private Runnable createRobotThread()
    {
        return new Runnable()
        {

            @Override
            public void run()
            {

                try
                {
                    DriverStation.getInstance();
                    DataAccessorFactory.getInstance().getSimulatorDataAccessor().waitForNextUpdateLoop();
                    mRobot.startCompetition();
                }
                catch (UnsatisfiedLinkError e)
                {
                    sLOGGER.log(Level.FATAL,
                            "Unsatisfied link error.  This likely means that there is a native "
                                    + "call in WpiLib or the NetworkTables libraries.  Please tell PJ so he can mock it out.\n\nError Message: " + e,
                            e);
                    exitWithError();
                }
                catch (Exception e)
                {
                    sLOGGER.log(Level.FATAL, "Unexpected exception, shutting down simulator", e);
                    exitWithError();
                }
            }
        };
    }

    /**
     * Stops the all the simulator threads (that we can stop)
     */
    protected void stop()
    {
        sLOGGER.log(Level.INFO, "Stopping simulator");

        if (mSimulatorThread != null)
        {
            try
            {
                mRunningSimulator = false;
                mSimulatorThread.join();
                mSimulatorThread = null;
            }
            catch (InterruptedException e)
            {
                sLOGGER.log(Level.FATAL, e);
            }
        }

        if (mRobotThread != null)
        {
            mRobotThread.interrupt();
            mRobotThread.stop();
            mRobotThread = null;
        }
    }

    /**
     * Shuts down the simulator when an error has occurred
     */
    protected void exitWithError()
    {
        stop();
        System.exit(-1);
    }
}
