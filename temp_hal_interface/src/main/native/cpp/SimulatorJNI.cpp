

#include "HAL/HAL.h"
#include "HAL/handles/HandlesInternal.h"
#include "MockData/MockHooks.h"
#include "SnobotSimUtilities/CallbackStore.h"
#include "edu_wpi_first_hal_sim_mockdata_SimulatorJNI.h"

/*
 * Class:     edu_wpi_first_hal_sim_mockdata_SimulatorJNI
 * Method:    waitForProgramStart
 * Signature: ()V
 */
JNIEXPORT void JNICALL
Java_edu_wpi_first_hal_sim_mockdata_SimulatorJNI_waitForProgramStart
  (JNIEnv*, jclass)
{
    HALSIM_WaitForProgramStart();
}

/*
 * Class:     edu_wpi_first_hal_sim_mockdata_SimulatorJNI
 * Method:    resetHandles
 * Signature: ()V
 */
JNIEXPORT void JNICALL
Java_edu_wpi_first_hal_sim_mockdata_SimulatorJNI_resetHandles
  (JNIEnv* env, jclass)
{
    static bool initialized = false;

    if (!initialized)
    {
        HAL_Initialize(0, 0);
    }

    SnobotSim::ResetCallbacks(env);
    hal::HandleBase::ResetGlobalHandles();
}
