
#ifndef SNOBOTSIM_CTRE_OVERRIDE_MOCK_HOOKS_INCLUDE_MOCKHOOKUTILITIES_H_
#define SNOBOTSIM_CTRE_OVERRIDE_MOCK_HOOKS_INCLUDE_MOCKHOOKUTILITIES_H_



#include <iostream>

#ifndef __FUNCTION_NAME__
    #ifdef WIN32   //WINDOWS
        #define __FUNCTION_NAME__   __FUNCTION__
    #else          //*NIX
        #define __FUNCTION_NAME__   __func__
    #endif
#endif

#define LOG_UNSUPPORTED_CAN_FUNC(x) std::cerr << __FUNCTION_NAME__ << " Unsupported" << std::endl;


#ifdef _MSC_VER
#define EXPORT_ __declspec(dllexport)
#else
#define EXPORT_
#endif

#endif  // SNOBOTSIM_CTRE_OVERRIDE_MOCK_HOOKS_INCLUDE_MOCKHOOKUTILITIES_H_