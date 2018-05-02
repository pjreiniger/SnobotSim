
#include <jni.h>

#include <cassert>

#include "com_ctre_phoenix_CTRLoggerJNI.h"

/*
 * Class:     com_ctre_phoenix_CTRLoggerJNI
 * Method:    JNI_Logger_Close
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_com_ctre_phoenix_CTRLoggerJNI_JNI_1Logger_1Close(JNIEnv*, jclass)
{
}

/*
 * Class:     com_ctre_phoenix_CTRLoggerJNI
 * Method:    JNI_Logger_Log
 * Signature: (ILjava/lang/String;Ljava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_com_ctre_phoenix_CTRLoggerJNI_JNI_1Logger_1Log(JNIEnv*, jclass, jint, jstring, jstring)
{
    return 0;
}

/*
 * Class:     com_ctre_phoenix_CTRLoggerJNI
 * Method:    JNI_Logger_Open
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_com_ctre_phoenix_CTRLoggerJNI_JNI_1Logger_1Open(JNIEnv*, jclass, jint)
{
}

/*
 * Class:     com_ctre_phoenix_CTRLoggerJNI
 * Method:    JNI_Logger_GetShort
 * Signature: (I)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_com_ctre_phoenix_CTRLoggerJNI_JNI_1Logger_1GetShort(JNIEnv*, jclass, jint)
{
    return NULL;
}

/*
 * Class:     com_ctre_phoenix_CTRLoggerJNI
 * Method:    JNI_Logger_GetLong
 * Signature: (I)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_com_ctre_phoenix_CTRLoggerJNI_JNI_1Logger_1GetLong(JNIEnv*, jclass, jint)
{
    return NULL;
}
