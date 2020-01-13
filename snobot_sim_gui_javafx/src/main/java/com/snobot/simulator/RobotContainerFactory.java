package com.snobot.simulator;

import java.io.File;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;

import com.snobot.simulator.robot_container.CppRobotContainer;
import com.snobot.simulator.robot_container.IRobotClassContainer;
import com.snobot.simulator.robot_container.JavaRobotContainer;

import edu.wpi.first.networktables.NetworkTableInstance;

public final class RobotContainerFactory
{
    private RobotContainerFactory()
    {

    }

    public static IRobotClassContainer createRobotContainer(File aConfigDirectory, String aRobotType, String aRobotClassName)
            throws ReflectiveOperationException
    {
        LogManager.getLogger(RobotContainerFactory.class).log(Level.INFO, "Starting Robot Code");

        IRobotClassContainer mRobot;

        if (aRobotType == null || "java".equals(aRobotType))
        {
            mRobot = new JavaRobotContainer(aRobotClassName);
        }
        else if ("cpp".equals(aRobotType))
        {
            mRobot = new CppRobotContainer(aRobotClassName);
        }
        else
        {
            throw new RuntimeException("Unsupported robot type " + aRobotType);
        }

        mRobot.constructRobot();

        // Change the network table preferences path. Need to start
        // the robot, stop the server and restart it
        NetworkTableInstance inst = NetworkTableInstance.getDefault();
        inst.stopServer();
        inst.startServer(aConfigDirectory.toString() + "/networktables.ini");

        return mRobot;
    }
}
