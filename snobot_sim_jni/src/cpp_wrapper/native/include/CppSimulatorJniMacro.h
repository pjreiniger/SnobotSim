/*
 * CppSimulatorJniMacro.h
 *
 *  Created on: Jul 20, 2018
 *      Author: PJ
 */

#ifndef SRC_CPP_WRAPPER_INCLUDE_CPPSIMULATORJNIMACRO_H_
#define SRC_CPP_WRAPPER_INCLUDE_CPPSIMULATORJNIMACRO_H_


#ifdef _MSC_VER
#define EXPORT_ __declspec(dllexport)
#else
#define EXPORT_
#endif

#include "com_snobot_simulator_cpp_wrapper_CppJniWrapper.h"

namespace SnobotSim
{
    template <typename RobotClass>
	class EXPORT_ CppJniWrapper
	{
	public:

    	RobotClass& GetRobot()
		{
			return mRobot;
		}
	protected:

		RobotClass mRobot;
	};
}

#define SIMULATOR_JNI_CODE(_RobotName_)                                                                        \
                                                                                                               \
static SnobotSim::CppJniWrapper<_RobotName_>* wrapper = NULL;                                                  \
                                                                                                               \
JNIEXPORT void JNICALL Java_com_snobot_simulator_cpp_1wrapper_CppJniWrapper_createRobot(JNIEnv *, jclass)      \
{                                                                                                              \
	if(wrapper != NULL)                                                                                        \
	{                                                                                                          \
		delete wrapper;                                                                                        \
	}                                                                                                          \
                                                                                                               \
	wrapper = new SnobotSim::CppJniWrapper<_RobotName_>();                                                     \
}                                                                                                              \
                                                                                                               \
JNIEXPORT void JNICALL Java_com_snobot_simulator_cpp_1wrapper_CppJniWrapper_startCompetition(JNIEnv *, jclass) \
{                                                                                                              \
	wrapper->GetRobot().StartCompetition();                                                                    \
}                                                                                                              \




#endif /* SRC_CPP_WRAPPER_INCLUDE_CPPSIMULATORJNIMACRO_H_ */
