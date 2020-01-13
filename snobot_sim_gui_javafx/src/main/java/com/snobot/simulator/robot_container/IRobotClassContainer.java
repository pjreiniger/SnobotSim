package com.snobot.simulator.robot_container;

/**
 * Interface that provides a wrapper around a robot class
 *
 * @author PJ
 *
 */
public interface IRobotClassContainer
{
    public void constructRobot()
            throws ReflectiveOperationException;

    public void startCompetition()
            throws ReflectiveOperationException;
}
