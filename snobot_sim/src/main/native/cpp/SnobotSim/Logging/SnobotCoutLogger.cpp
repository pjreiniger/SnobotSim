/*
 * SnobotCoutLogger.cpp
 *
 *  Created on: Jun 21, 2017
 *      Author: PJ
 */

#include "SnobotSim/Logging/SnobotCoutLogger.h"

#include <iostream>

using namespace SnobotLogging;

SnobotCoutLogger::SnobotCoutLogger()
{
    mDirectorySubstring = "";

    std::string thisFile = FixWindowsSlashes(__FILE__);
    int filenamePos = thisFile.find("snobot_sim/cpp/lib/SnobotSim/Logging/SnobotCoutLogger.cpp");

    if (filenamePos != -1)
    {
        mDirectorySubstring = thisFile.substr(0, filenamePos);
    }
}

SnobotCoutLogger::~SnobotCoutLogger()
{
}

std::string SnobotCoutLogger::FixWindowsSlashes(const std::string& aInput)
{
    std::string output = aInput;

    // Stupid windows
    // https://stackoverflow.com/questions/20406744/how-to-find-and-replace-all-occurrences-of-a-substring-in-a-string
    std::string::size_type n = 0;
    while ((n = output.find("\\", n)) != std::string::npos)
    {
        output.replace(n, 1, "/");
        n += 1;
    }

    return output;
}

void SnobotCoutLogger::Log(
        LogLevel aLogLevel,
        int aLineNumber,
        const std::string& aFileName,
        const std::string& aMessage)
{
    if (aLogLevel < mLogLevel)
    {
        return;
    }

    std::stringstream logLevelStr;

    switch (aLogLevel)
    {
    case LOG_LEVEL_DEBUG:
        logLevelStr << "Debug  ";
        break;
    case LOG_LEVEL_INFO:
        logLevelStr << "Info   ";
        break;
    case LOG_LEVEL_WARN:
        logLevelStr << "Warn   ";
        break;
    case LOG_LEVEL_CRITICAL:
        logLevelStr << "Error  ";
        break;
    case LOG_LEVEL_NONE:
        logLevelStr << "Invalid";
        break;
    }

    std::string shortenedFileName = FixWindowsSlashes(aFileName);

    int filenamePos = shortenedFileName.find(mDirectorySubstring);
    if (filenamePos != -1)
    {
        shortenedFileName = shortenedFileName.substr(mDirectorySubstring.size());
    }

    if (aLogLevel <= LOG_LEVEL_INFO)
    {
        std::cout << logLevelStr.str() << " " << shortenedFileName << ":" << aLineNumber << " - " << aMessage << std::endl;
    }
    else
    {
        std::cerr << logLevelStr.str() << " " << shortenedFileName << ":" << aLineNumber << " - " << aMessage << std::endl;
    }
}
