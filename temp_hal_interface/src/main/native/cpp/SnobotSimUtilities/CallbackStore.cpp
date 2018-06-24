#include "SnobotSimUtilities/CallbackStore.h"
#include <iostream>

using namespace wpi::java;

namespace SnobotSim {

static int handlePtrs[1000];

static std::vector<std::shared_ptr<CallbackStore> > callbackHandles;

static JavaVM* gJvm = NULL;
static JClass notifyCallbackCls;
static jmethodID notifyCallbackCallback;

void ResetCallbacks(JNIEnv* env) {
	env->GetJavaVM(&gJvm);
	for (int i = 0; i < 1000; ++i) {
		handlePtrs[i] = i;
	}

	notifyCallbackCls = JClass(env, "edu/wpi/first/wpilibj/sim/NotifyCallback");
	notifyCallbackCallback = env->GetMethodID(notifyCallbackCls,
			"callbackNative", "(Ljava/lang/String;IJD)V");

	callbackHandles.clear();
}

void AllocateCallback(JNIEnv* env, jint index, jobject callback,
		jboolean initialNotify, RegisterCallbackFunc createCallback) {
	auto callbackStore = std::make_shared<CallbackStore>();

	int* handleAsPtr = &handlePtrs[callbackHandles.size()];
	callbackHandles.push_back(callbackStore);

	void* handleAsVoidPtr = reinterpret_cast<void*>(handleAsPtr);

	callbackStore->create(env, callback);

	auto callbackFunc = [](const char* name, void* param, const HAL_Value* value) {
		int* handleTmp = reinterpret_cast<int*>(param);
		auto data = callbackHandles[*handleTmp];
		data->performCallback(name, value);
	};

	auto id = createCallback(index, callbackFunc, handleAsVoidPtr,
			initialNotify);

	callbackStore->setCallbackId(id);
}

void AllocateChannelCallback(
    JNIEnv* env, jint index, jint channel, jobject callback,
    jboolean initialNotify, RegisterChannelCallbackFunc createCallback) {

	auto callbackStore = std::make_shared<CallbackStore>();

	int* handleAsPtr = &handlePtrs[callbackHandles.size()];
	callbackHandles.push_back(callbackStore);

	void* handleAsVoidPtr = reinterpret_cast<void*>(handleAsPtr);

	callbackStore->create(env, callback);

	auto callbackFunc = [](const char* name, void* param,
						 const HAL_Value* value) {
		int* handleTmp = reinterpret_cast<int*>(param);
		auto data = callbackHandles[*handleTmp];

		data->performCallback(name, value);
	};

	auto id = createCallback(index, channel, callbackFunc, handleAsVoidPtr,
						   initialNotify);

	callbackStore->setCallbackId(id);
}

void CallbackStore::create(JNIEnv* env, jobject obj) {
	m_call = JGlobal<jobject>(env, obj);
}

void CallbackStore::performCallback(const char* name, const HAL_Value* value) {

	JavaVMAttachArgs args = { JNI_VERSION_1_2, 0, 0 };
	JNIEnv* env;
	gJvm->AttachCurrentThread(reinterpret_cast<void**>(&env), &args);

	env->CallVoidMethod(m_call, notifyCallbackCallback, MakeJString(env, name),
			(jint) value->type, (jlong) value->data.v_long,
			(jdouble) value->data.v_double);
}

}
