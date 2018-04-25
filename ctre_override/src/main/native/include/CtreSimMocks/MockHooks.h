
#ifndef SNOBOTSIM_CTRE_OVERRIDE_MOCK_HOOKS_INCLUDE_MOCKHOOKS_H_
#define SNOBOTSIM_CTRE_OVERRIDE_MOCK_HOOKS_INCLUDE_MOCKHOOKS_H_


#include <stdint.h>

#include "MockHookUtilities.h"

namespace SnobotSim
{

typedef void (*CTRE_CallbackFunc)(const char* name,
                                  uint32_t messageId,
                                  uint8_t* buffer);


void EXPORT_ SetMotControllerCallback(CTRE_CallbackFunc callback);
void EXPORT_ SetPigeonCallback(CTRE_CallbackFunc callback);

//class BaseCtreWrapper
//{
//public:
//
//    uint32_t mDeviceId;
//
//    BaseCtreWrapper(int arbId) :
//        mDeviceId(arbId & 0x3F)
//    {
//        std::cout << "Created wrapper" << std::endl;
//        Send("Create");
//    }
//
//    void Send(const std::string& aName)
//    {
//        uint8_t buffer[1];
//        Send(aName, buffer);
//    }
//
//    template<typename T0>
//    void Send(const std::string& aName, T0& param0)
//    {
//        int size = sizeof(T0);
//
//        uint8_t* buffer = new uint8_t[size];
//        std::memset(&buffer[0], 0, size);
//
//        uint32_t offset = 0;
//        PushValue(buffer, param0, offset);
//        Send(aName, buffer);
//
//        delete buffer;
//
//    }
//
//    template<typename T0, typename T1>
//    void Send(const std::string& aName, T0& param0, T1& param1)
//    {
//        int size = sizeof(T0) + sizeof(T1);
//
//        uint8_t* buffer = new uint8_t[size];
//        std::memset(&buffer[0], 0, size);
//
//        uint32_t offset = 0;
//        PushValue(buffer, param0, offset);
//        PushValue(buffer, param1, offset);
//        Send(aName, buffer);
//
//        delete buffer;
//    }
//
//    template<typename T0, typename T1, typename T2>
//    void Send(const std::string& aName, T0& param0, T1& param1, T2& param2)
//    {
//        int size = sizeof(T0) + sizeof(T1) + sizeof(T2);
//
//        uint8_t* buffer = new uint8_t[size];
//        std::memset(&buffer[0], 0, size);
//
//        uint32_t offset = 0;
//        PushValue(buffer, param0, offset);
//        PushValue(buffer, param1, offset);
//        PushValue(buffer, param2, offset);
//        Send(aName, buffer);
//
//        delete buffer;
//    }
//
//    virtual void Send(const std::string& aName, uint8_t* aBuffer) = 0;
//
//    template <typename T>
//    void PushValue(uint8_t* buffer, T& value, uint32_t& offset)
//    {
//        std::memcpy(&buffer[offset], &value, sizeof(value));
//        offset += sizeof(value);
//    }
//};

} // namespace SnobotSim

#endif  // SNOBOTSIM_CTRE_OVERRIDE_MOCK_HOOKS_INCLUDE_MOCKHOOKS_H_
