package com.snobot.simulator.robot_container;

/**
 * Interface that provides a wrapper around a robot class
 *
 * @author PJ
 *
 */
public interface IRobotClassContainer
{
    /**
     *
     * @throws ReflectiveOperationException
     */
    public void constructRobot()
            throws ReflectiveOperationException;

    /**
     *
     * @throws ReflectiveOperationException
     */
    public void startCompetition()
            throws ReflectiveOperationException;
}
