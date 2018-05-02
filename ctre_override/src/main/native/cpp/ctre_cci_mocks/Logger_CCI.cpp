
#include "ctre/phoenix/CCI/Logger_CCI.h"

#include <cstring>
#include <iostream>
#include <string>
#include <vector>

#include "MockHooks.h"



extern "C" {

void c_Logger_Close()
{
    LOG_UNSUPPORTED_CAN_FUNC("");
}

void c_Logger_Open(int language, bool logDriverStation)
{
    LOG_UNSUPPORTED_CAN_FUNC("");
}

ctre::phoenix::ErrorCode c_Logger_Log(ctre::phoenix::ErrorCode code, const char* origin, int hierarchy, const char *stacktrace)
{
    LOG_UNSUPPORTED_CAN_FUNC("");
    return (ctre::phoenix::ErrorCode) 0;
}

void c_Logger_Description(ctre::phoenix::ErrorCode code, std::string & shortDescripToFill, std::string & longDescripToFill)
{
    LOG_UNSUPPORTED_CAN_FUNC("");
}

}
