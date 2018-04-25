#pragma once

#include "ctre/phoenix/ErrorCode.h"
#include <string>

#include "MockHookUtilities.h"

extern "C" {
	EXPORT_ void c_Logger_Close();
	EXPORT_ void c_Logger_Open(int language, bool logDriverStation);
	EXPORT_ ctre::phoenix::ErrorCode c_Logger_Log(ctre::phoenix::ErrorCode code, const char* origin, int hierarchy, const char *stacktrace);
	EXPORT_ void c_Logger_Description(ctre::phoenix::ErrorCode code, std::string & shortDescripToFill, std::string & longDescripToFill);
}
