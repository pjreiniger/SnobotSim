#pragma once

#include "jni.h"
#include "support/jni_util.h"
#include "HAL/Types.h"
#include "MockData/HAL_Value.h"

typedef HAL_Handle SIM_JniHandle;

namespace sim {
JavaVM* GetJVM();

jmethodID GetNotifyCallback();
jmethodID GetBufferCallback();
jmethodID GetConstBufferCallback();
jmethodID GetSpiReadAutoReceiveBufferCallback();
}
