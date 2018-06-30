package com.snobot.simulator;

import java.io.File;
import java.io.FilenameFilter;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import edu.wpi.first.wpilibj.RobotBase;

/**
 * Class that loads any of the jar files in the plugin directory. Also can
 * search for C++/Java robot classes
 *
 * @author PJ
 *
 */
public class PluginSniffer
{
    private static final Logger sLOGGER = LogManager.getLogger(PluginSniffer.class);

    private File[] mDiscoveredJars;
    private final List<Class<?>> mCppRobots;
    private final List<Class<?>> mJavaRobots;

    public PluginSniffer()
    {
        mCppRobots = new ArrayList<>();
        mJavaRobots = new ArrayList<>();
    }

    /**
     * Gets the list of C++ robots discovered. Note: Must call
     * {@link findRobots} before this list gets populated
     *
     * @return The C++ robots
     */
    public List<Class<?>> getCppRobots()
    {
        return mCppRobots;
    }

    /**
     * Gets the list of Java robots discovered. Note: Must call
     * {@link findRobots} before this list gets populated
     *
     * @return The Java robots
     */
    public List<Class<?>> getJavaRobots()
    {
        return mJavaRobots;
    }

    /**
     * Loads all the plugins in the plugin directory. Does not search recursivly
     *
     * @param aPluginDir
     *            The directory to search.
     * @throws Exception
     *             Thrown if the plugin loading fails
     */
    public void loadPlugins(File aPluginDir) throws Exception
    {
        sLOGGER.log(Level.INFO, "Searching for robot plugins in " + aPluginDir.getAbsolutePath());
        mDiscoveredJars = aPluginDir.listFiles(new FilenameFilter()
        {

            public boolean accept(File aDir, String aName)
            {
                return aName.endsWith(".jar");
            }
        });

        URLClassLoader classLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();
        Method method = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
        method.setAccessible(true);

        if (mDiscoveredJars != null)
        {
            for (File file : mDiscoveredJars)
            {
                method.invoke(classLoader, file.toURI().toURL());
                sLOGGER.log(Level.INFO, "  Added " + file.getAbsolutePath() + " to classpath");
            }
        }
    }

    /**
     * Finds any robot classes available in the plugin directory
     *
     * @throws Exception
     *             Thrown if the plugin loading fails
     */
    public void findRobots() throws Exception
    {
        if (mDiscoveredJars != null)
        {
            for (File file : mDiscoveredJars)
            {
                findRobots(file);
            }
        }
    }

    private void findRobots(File aFile) throws Exception
    {
        JarFile jar = new JarFile(aFile);

        Enumeration<JarEntry> entries = jar.entries();
        while (entries.hasMoreElements())
        {
            JarEntry entry = entries.nextElement();
            String name = entry.getName();

            if (name.endsWith(".class"))
            {
                name = name.substring(0, name.length() - ".class".length());
                name = name.replaceAll("/", ".");

                Class<?> clazz = Class.forName(name);
                if (name.startsWith("com.snobot.simulator.cpp_wrapper."))
                {
                    tryToAddCppRobot(aFile, clazz, name);
                }
                else if (RobotBase.class.isAssignableFrom(clazz))
                {
                    mJavaRobots.add(clazz);
                }

            }
        }

        jar.close();

    }

    private void tryToAddCppRobot(File aFile, Class<?> aClazz, String aWName) throws ClassNotFoundException

    {

        try
        {
            Method createRobotMethod = aClazz.getMethod("createRobot");
            Method getLibraryNameMethod = aClazz.getMethod("getLibraryName");
            Method startCompetitionMethod = aClazz.getMethod("startCompetition");

            if (createRobotMethod != null && getLibraryNameMethod != null && startCompetitionMethod != null)
            {
                mCppRobots.add(aClazz);
            }
        }
        catch (Exception e)
        {
            sLOGGER.log(Level.WARN,
                    "Thought we had a C++ robot in " + aFile + ", but couldn't find required items: " + e.getMessage(), e);
        }
    }
}
