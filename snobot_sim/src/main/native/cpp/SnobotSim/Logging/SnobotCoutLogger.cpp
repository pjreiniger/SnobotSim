/*
 * SnobotCoutLogger.cpp
 *
 *  Created on: Jun 21, 2017
 *      Author: PJ
 */

#include "SnobotSim/Logging/SnobotCoutLogger.h"

#include <iostream>
#include <map>

#ifdef _WIN32
#include <Windows.h>
#endif

using namespace SnobotLogging;

namespace
{

std::map<LogLevel, std::string> gLOG_LEVEL_LOOKUP = {
    { LOG_LEVEL_DEBUG, "Debug   " },
    { LOG_LEVEL_INFO, "Info    " },
    { LOG_LEVEL_WARN, "Warn    " },
    { LOG_LEVEL_CRITICAL, "Critical" },
    { LOG_LEVEL_NONE, "" },
};

#ifdef _WIN32

WORD gCurrentConsoleAttr;

std::map<LogLevel, WORD> gColorLookup = {
    { LOG_LEVEL_DEBUG, FOREGROUND_BLUE },
    { LOG_LEVEL_INFO, FOREGROUND_GREEN },
    { LOG_LEVEL_WARN, FOREGROUND_BLUE | FOREGROUND_GREEN },
    { LOG_LEVEL_CRITICAL, FOREGROUND_RED },
    { LOG_LEVEL_NONE, 0 },
};

void StartColor(std::ostream& stream, LogLevel aLogLevel)
{
    const HANDLE stdout_handle = GetStdHandle(STD_OUTPUT_HANDLE);
    CONSOLE_SCREEN_BUFFER_INFO buffer_info;
    if (GetConsoleScreenBufferInfo(stdout_handle, &buffer_info))
    {
        gCurrentConsoleAttr = buffer_info.wAttributes;
    }

    SetConsoleTextAttribute(stdout_handle, gColorLookup[aLogLevel]);
}

void EndColor(std::ostream& stream, LogLevel aLogLevel)
{
    SetConsoleTextAttribute(GetStdHandle(STD_OUTPUT_HANDLE), gCurrentConsoleAttr);
}

#else

std::map<LogLevel, std::string> gColorLookup = {
    { LOG_LEVEL_DEBUG, "\033[1;36m" },
    { LOG_LEVEL_INFO, "\033[1;32m" },
    { LOG_LEVEL_WARN, "\033[1;33m" },
    { LOG_LEVEL_CRITICAL, "\033[1;31m" },
    { LOG_LEVEL_NONE, "\033[1;31m" },
};


void StartColor(std::ostream& stream, LogLevel aLogLevel)
{
    stream << gColorLookup[aLogLevel];
}

void EndColor(std::ostream& stream, LogLevel aLogLevel)
{
    stream << "\033[0m";
}

#endif

} // namespace

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
    logLevelStr << gLOG_LEVEL_LOOKUP[aLogLevel];

    std::string shortenedFileName = FixWindowsSlashes(aFileName);

    int filenamePos = shortenedFileName.find(mDirectorySubstring);
    if (filenamePos != -1)
    {
        shortenedFileName = shortenedFileName.substr(mDirectorySubstring.size());
    }

    StartColor(std::cout, aLogLevel);
    std::cout << logLevelStr.str() << " " << shortenedFileName << ":" << aLineNumber << " - " << aMessage << std::endl;
    EndColor(std::cout, aLogLevel);
}
