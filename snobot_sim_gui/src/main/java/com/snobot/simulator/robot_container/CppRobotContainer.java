package com.snobot.simulator.robot_container;

import java.lang.reflect.Method;

import com.snobot.simulator.JniLibraryResourceLoader;

/**
 * Wrapper class around a C++ robot's code
 *
 * @author PJ
 *
 */
public class CppRobotContainer implements IRobotClassContainer
{
    private final String mRobotClassName;
    private Class<?> mJniClass;

    public CppRobotContainer(String aRobotClassName)
    {
        mRobotClassName = aRobotClassName;
    }

    @Override
    public void constructRobot()
            throws ReflectiveOperationException
    {
        mJniClass = Class.forName(mRobotClassName);

        String libraryName = (String) mJniClass.getMethod("getLibraryName").invoke(null);

        String openCvVersion = "343";

        JniLibraryResourceLoader.loadLibrary("ntcore");
        JniLibraryResourceLoader.loadLibrary("opencv_core" + openCvVersion);
        JniLibraryResourceLoader.loadLibrary("opencv_imgproc" + openCvVersion);
        JniLibraryResourceLoader.loadLibrary("opencv_imgcodecs" + openCvVersion);
        JniLibraryResourceLoader.loadLibrary("cscore");
        JniLibraryResourceLoader.loadLibrary("wpiutil");
        JniLibraryResourceLoader.loadLibrary("cameraserver");
        JniLibraryResourceLoader.loadLibrary("wpilibc");
        JniLibraryResourceLoader.loadLibrary("snobot_sim");
        JniLibraryResourceLoader.loadLibrary(libraryName);

        Method method = mJniClass.getMethod("createRobot");
        method.invoke(null);
    }

    @Override
    public void startCompetition()
            throws ReflectiveOperationException
    {
        Method method = mJniClass.getMethod("startCompetition");
        method.invoke(null);
    }
}
