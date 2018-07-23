package com.snobot.simulator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class JniLibraryResourceLoader
{
    private static final Logger sLOGGER = LogManager.getLogger(JniLibraryResourceLoader.class);

    private JniLibraryResourceLoader()
    {

    }

    public static boolean copyResourceFromJar(String aResourceName, File aResourceFile) throws IOException
    {
        return copyResourceFromJar(aResourceName, aResourceFile, true);
    }

    public static boolean copyResourceFromJar(String aResourceName, File aResourceFile, boolean aDeleteOnExit) throws IOException
    {
        boolean success = false;

        try (InputStream is = JniLibraryResourceLoader.class.getResourceAsStream(aResourceName))
        {
            if (is == null)
            {
                sLOGGER.log(Level.FATAL, "Could not find resource at " + aResourceName);
            }
            else
            {

                // flag for delete on exit
                if (aDeleteOnExit)
                {
                    aResourceFile.deleteOnExit();
                }
                OutputStream os = new FileOutputStream(aResourceFile);

                byte[] buffer = new byte[1024];
                int readBytes;
                try
                {
                    while ((readBytes = is.read(buffer)) != -1)
                    {
                        os.write(buffer, 0, readBytes);
                    }

                    success = true;
                }
                finally
                {
                    os.close();
                    is.close();
                }

                sLOGGER.log(Level.DEBUG, "Copied resource to " + aResourceFile.getAbsolutePath() + " from resource " + aResourceName);
            }
        }

        return success;
    }


    public static boolean loadLibrary(String aLibraryname)
    {
        try
        {
            sLOGGER.log(Level.DEBUG, "Loading native library" + aLibraryname);
            System.loadLibrary(aLibraryname);
            return true;
        }
        catch (Exception | UnsatisfiedLinkError ex)
        {
            sLOGGER.log(Level.ERROR, ex);
        }
        return false;
    }
}
