
#include "edu_wpi_first_hal_sim_mockdata_DriverStationDataJNI.h"
#include "MockData/DriverStationData.h"
#include <jni.h>

/*
 * Class:     edu_wpi_first_hal_sim_mockdata_DriverStationDataJNI
 * Method:    setEnabled
 * Signature: (Z)V
 */
JNIEXPORT void JNICALL Java_edu_wpi_first_hal_sim_mockdata_DriverStationDataJNI_setEnabled
(JNIEnv*, jclass, jboolean value)
{
	  HALSIM_SetDriverStationEnabled(value);
}


/*
 * Class:     edu_wpi_first_hal_sim_mockdata_DriverStationDataJNI
 * Method:    setDsAttached
 * Signature: (Z)V
 */
JNIEXPORT void JNICALL Java_edu_wpi_first_hal_sim_mockdata_DriverStationDataJNI_setDsAttached
(JNIEnv*, jclass, jboolean value)
{
	  HALSIM_SetDriverStationDsAttached(value);
}


/*
 * Class:     edu_wpi_first_hal_sim_mockdata_DriverStationDataJNI
 * Method:    setAutonomous
 * Signature: (Z)V
 */
JNIEXPORT void JNICALL Java_edu_wpi_first_hal_sim_mockdata_DriverStationDataJNI_setAutonomous
(JNIEnv*, jclass, jboolean value)
{
	  HALSIM_SetDriverStationAutonomous(value);
}


/*
 * Class:     edu_wpi_first_hal_sim_mockdata_DriverStationDataJNI
 * Method:    notifyNewData
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_edu_wpi_first_hal_sim_mockdata_DriverStationDataJNI_notifyNewData
  (JNIEnv *, jclass)
{
	  HALSIM_NotifyDriverStationNewData();
}


/*
 * Class:     edu_wpi_first_hal_sim_mockdata_DriverStationDataJNI
 * Method:    setJoystickAxes
 * Signature: (B[F)V
 */
JNIEXPORT void JNICALL Java_edu_wpi_first_hal_sim_mockdata_DriverStationDataJNI_setJoystickAxes
(JNIEnv* env, jclass, jbyte joystickNum, jfloatArray axesArray)
{
//	  HAL_JoystickAxes axes;
//	  {
//	    wpi::java::JFloatArrayRef jArrayRef(env, axesArray);
//	    auto arrayRef = jArrayRef.array();
//	    auto arraySize = arrayRef.size();
//	    int maxCount =
//	        arraySize < HAL_kMaxJoystickAxes ? arraySize : HAL_kMaxJoystickAxes;
//	    axes.count = maxCount;
//	    for (int i = 0; i < maxCount; i++) {
//	      axes.axes[i] = arrayRef[i];
//	    }
//	  }
//	  HALSIM_SetJoystickAxes(joystickNum, &axes);
//	  return;
}


/*
 * Class:     edu_wpi_first_hal_sim_mockdata_DriverStationDataJNI
 * Method:    setJoystickPOVs
 * Signature: (B[S)V
 */
JNIEXPORT void JNICALL Java_edu_wpi_first_hal_sim_mockdata_DriverStationDataJNI_setJoystickPOVs
(JNIEnv* env, jclass, jbyte joystickNum, jshortArray povsArray)
{
//	  HAL_JoystickPOVs povs;
//	  {
//	    wpi::java::JShortArrayRef jArrayRef(env, povsArray);
//	    auto arrayRef = jArrayRef.array();
//	    auto arraySize = arrayRef.size();
//	    int maxCount =
//	        arraySize < HAL_kMaxJoystickPOVs ? arraySize : HAL_kMaxJoystickPOVs;
//	    povs.count = maxCount;
//	    for (int i = 0; i < maxCount; i++) {
//	      povs.povs[i] = arrayRef[i];
//	    }
//	  }
//	  HALSIM_SetJoystickPOVs(joystickNum, &povs);
//	  return;
}


/*
 * Class:     edu_wpi_first_hal_sim_mockdata_DriverStationDataJNI
 * Method:    setJoystickButtons
 * Signature: (BII)V
 */
JNIEXPORT void JNICALL Java_edu_wpi_first_hal_sim_mockdata_DriverStationDataJNI_setJoystickButtons
(JNIEnv* env, jclass, jbyte joystickNum, jint buttons, jint count)
{
	  if (count > 32) {
	    count = 32;
	  }
	  HAL_JoystickButtons joystickButtons;
	  joystickButtons.count = count;
	  joystickButtons.buttons = buttons;
	  HALSIM_SetJoystickButtons(joystickNum, &joystickButtons);
}
