
#ifndef TEMP_SUPPORT_H_
#define TEMP_SUPPORT_H_


#include <jni.h>


namespace wpi {
namespace java {

template<typename T>
class JGlobal {
 public:
  JGlobal() = default;

  JGlobal(JNIEnv* env, T obj) {
    m_cls = static_cast<T>(env->NewGlobalRef(obj));
  }

  void free(JNIEnv* env) {
    if (m_cls) env->DeleteGlobalRef(m_cls);
    m_cls = nullptr;
  }

  explicit operator bool() const { return m_cls; }

  operator T() const { return m_cls; }

 protected:
  T m_cls = nullptr;
};


}  // namespace java
}  // namespace wpi

#endif
