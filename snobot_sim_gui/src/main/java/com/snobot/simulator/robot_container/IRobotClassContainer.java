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
     * Constructs the robot class.
     *
     * @throws ReflectiveOperationException
     *             Thrown if the robot cannot be constructed reflectively
     */
    public void constructRobot()
            throws ReflectiveOperationException;

    /**
     * Starts the robot competition loop.
     *
     * @throws ReflectiveOperationException
     *             Thrown if the robot cannot be constructed reflectively
     */
    public void startCompetition()
            throws ReflectiveOperationException;
}
