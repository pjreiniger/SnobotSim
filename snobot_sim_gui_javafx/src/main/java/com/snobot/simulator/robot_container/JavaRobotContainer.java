package com.snobot.simulator.robot_container;

import edu.wpi.first.wpilibj.RobotBase;

/**
 * Wrapper class around a Java robot
 *
 * @author PJ
 *
 */
public class JavaRobotContainer implements IRobotClassContainer
{
    private final String mRobotClassName;
    private RobotBase mRobot;

    public JavaRobotContainer(String aRobotClassName)
    {
        mRobotClassName = aRobotClassName;
    }

    @Override
    public void constructRobot() throws ReflectiveOperationException
    {
        mRobot = (RobotBase) Class.forName(mRobotClassName).newInstance();
    }

    @Override
    public void startCompetition()
    {
        mRobot.startCompetition();
    }

    public RobotBase getJavaRobot()
    {
        return mRobot;
    }
}
