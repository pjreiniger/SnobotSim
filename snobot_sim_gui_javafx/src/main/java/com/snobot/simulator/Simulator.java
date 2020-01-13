package com.snobot.simulator;

import java.io.File;
import java.util.function.Consumer;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.snobot.simulator.SimulatorPreloader.StateNotification.StateNotificationType;
import com.snobot.simulator.gui.SimulatorFrameController;
import com.snobot.simulator.joysticks.IJoystickInterface;
import com.snobot.simulator.joysticks.NullJoystickInterface;
import com.snobot.simulator.joysticks.SnobotSimJoystickInterface;
import com.snobot.simulator.robot_container.IRobotClassContainer;
import com.snobot.simulator.wrapper_accessors.DataAccessorFactory;
import com.snobot.simulator.wrapper_accessors.SimulatorDataAccessor.SnobotLogLevel;

import edu.wpi.first.wpilibj.DriverStation;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class Simulator
{
    private static final Logger LOGGER = LogManager.getLogger(Simulator.class);

    private IRobotClassContainer mRobot; // The robot code to run
    private BaseSimulator mSimulator; // The simulator for the robot
    private final File mUserConfigDirectory;
    private final SimulatorFrameController mController;
    private final IJoystickInterface mJoystickInterface;

    protected Thread mRobotThread;
    protected Thread mSimulatorThread;
    protected boolean mRunningSimulator;
    protected final boolean mUseSnobotSimDriverStation;

    /**
     * Constructor
     *
     * @param aLogLevel
     *            The log level to set up the simulator with
     * @param aUserConfigDir
     *            The config directory where settings are saved
     * @throws Exception
     *             Throws an exception if the plugin loading failed
     */
    public Simulator(SimulatorFrameController aController, SnobotLogLevel aLogLevel, String aUserConfigDir, boolean aUseSnobotSimDriverstation)
            throws Exception
    {
        mUserConfigDirectory = new File(aUserConfigDir);
        mController = aController;
        mUseSnobotSimDriverStation = aUseSnobotSimDriverstation;

        if (mUseSnobotSimDriverStation)
        {
            mJoystickInterface = new SnobotSimJoystickInterface();
        }
        else
        {
            mJoystickInterface = new NullJoystickInterface();
        }
    }

    protected void showInitializationMessage()
    {
        String message = DataAccessorFactory.getInstance().getInitializationErrors();
        if (message != null && !message.isEmpty())
        {
            System.out.println("Initialization warning");
//            String message = "<html>Some simulator components were specified in the config file, but not in the robot.<br>"
//                    + "They will be <b>removed</b> from the simulator registery, to make it easier to fix your config file.<ul>" + aMessage
//                    + "</ul></html>";
//            JLabel label = new JLabel(message);
//            label.setFont(new Font("serif", Font.PLAIN, 14));
//
//            JOptionPane.showMessageDialog(null, label, "Config file mismatch", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void setupSimulator(Consumer<SimulatorPreloader.StateNotification> aProgressCallback) throws ReflectiveOperationException
    {
        aProgressCallback.accept(new SimulatorPreloader.StateNotification(StateNotificationType.LoadingSimulatorProperties));
        SimulatorPropertiesLoader propertyLoader = new SimulatorPropertiesLoader();
        propertyLoader.loadProperties(mUserConfigDirectory);

        // Force the jinput libraries to load
        mJoystickInterface.sendJoystickUpdate();

        mSimulator = propertyLoader.getSimulator();

        aProgressCallback.accept(new SimulatorPreloader.StateNotification(StateNotificationType.CreatingRobot));
        mRobot = RobotContainerFactory.createRobotContainer(mUserConfigDirectory, propertyLoader.getRobotType(), propertyLoader.getRobotClassName());

        if (mRobot == null)
        {
            throw new IllegalArgumentException("Cannot start, could not create robot class");
        }
        else if (mSimulator == null)
        {
            throw new IllegalArgumentException("Cannot start, could not create simulator class");
        }
        else
        {
            mRobotThread = new Thread(createRobotThread(), "RobotThread");

            mRunningSimulator = true;
            LOGGER.log(Level.INFO, "Starting simulator");

            aProgressCallback.accept(new SimulatorPreloader.StateNotification(StateNotificationType.StartingRobotThread));
            mRobotThread.start();

            aProgressCallback.accept(new SimulatorPreloader.StateNotification(StateNotificationType.WaitingForProgramToStart));
            DataAccessorFactory.getInstance().getDriverStationAccessor().waitForProgramToStart();

            showInitializationMessage();
            mSimulator.setRobot(mRobot);

            mController.initialize(mUserConfigDirectory + "/simulator_config.yml", mUseSnobotSimDriverStation);

            aProgressCallback.accept(new SimulatorPreloader.StateNotification(StateNotificationType.Finished));
        }
    }

    public void startSimulation()
    {
        createSimulatorBackgroundService().start();
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
                    mJoystickInterface.waitForLoop();
                    mRobot.startCompetition();
                }
                catch (UnsatisfiedLinkError e)
                {
                    throw new RuntimeException(
                            "Unsatisfied link error.  This likely means that there is a native "
                                    + "call in WpiLib or the NetworkTables libraries.  Please tell PJ so he can mock it out.\n\nError Message: " + e,
                            e);
                }
                catch (Exception e)
                {
                    throw new RuntimeException("Unexpected exception, shutting down simulator", e);
                }
            }
        };
    }

    private Service<Void> createSimulatorBackgroundService()
    {
        return new Service<Void>()
        {
            @Override
            protected Task<Void> createTask()
            {
                return new Task<Void>()
                {
                    @Override
                    protected Void call() throws Exception
                    {
                        DataAccessorFactory.getInstance().getDriverStationAccessor().setDisabled(false);

                        while (mRunningSimulator)
                        {
                            mJoystickInterface.waitForLoop();
                            DataAccessorFactory.getInstance().getSimulatorDataAccessor().updateSimulatorComponents();
                            mSimulator.update();
                            mJoystickInterface.sendJoystickUpdate();

                            Platform.runLater(() -> mController.updateLoop());
                        }

                        return null;
                    }
                };
            }
        };
    }

    public void stop()
    {
        mRunningSimulator = false;
    }

}
