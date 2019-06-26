package com.snobot.simulator.robot_container;

import java.lang.reflect.InvocationTargetException;

import javax.management.ReflectionException;

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
     * @throws ReflectionException
     * @throws ReflectiveOperationException
     */
    public void constructRobot()
            throws ReflectionException, ReflectiveOperationException;

    /**
     * 
     * @throws NoSuchMethodException
     * @throws SecurityException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     */
    public void startCompetition()
            throws ReflectionException, ReflectiveOperationException;
}
