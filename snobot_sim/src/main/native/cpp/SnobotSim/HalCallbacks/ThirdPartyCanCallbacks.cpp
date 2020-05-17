
#include "SnobotSim/HalCallbacks/ThirdPartyCanCallbacks.h"

#undef EXPORT_
#include "CtreSimUtils/MockHooks.h"

#undef EXPORT_
#include "RevSimUtils/MockHooks.h"

#include "SnobotSim/SimulatorComponents/CtreWrappers/CtreManager.h"
#include "SnobotSim/SimulatorComponents/RevWrappers/RevManager.h"

namespace SnobotSim
{

namespace
{
CtreManager gCtreManager;
RevManager gReveManager;
} // namespace

void InitializeThirdPartyCanCallbacks()
{
    //////////////////////////////
    // CTRE
    //////////////////////////////
    SnobotSim::SetMotControllerCallback([](const char* name,
                                                uint32_t messageId,
                                                uint8_t* buffer,
                                                int length) {
        gCtreManager.handleMotorControllerMessage(name, messageId, buffer, length);
    });
    SnobotSim::SetPigeonIMUCallback([](const char* name,
                                            uint32_t messageId,
                                            uint8_t* buffer,
                                            int length) {
        gCtreManager.handlePigeonMessage(name, messageId, buffer, length);
    });
    SnobotSim::SetCANifierCallback([](const char* name,
                                           uint32_t messageId,
                                           uint8_t* buffer,
                                           int length) {
        gCtreManager.handleCanifierMessage(name, messageId, buffer, length);
    });
    SnobotSim::SetCANCoderCallback([](const char* name,
                                           uint32_t messageId,
                                           uint8_t* buffer,
                                           int length) {
        gCtreManager.handleCanCoderMessage(name, messageId, buffer, length);
    });
    SnobotSim::SetBuffTrajPointStreamCallback([](const char* name,
                                                      uint32_t messageId,
                                                      uint8_t* buffer,
                                                      int length) {
        gCtreManager.handleBuffTrajPointStreamMessage(name, messageId, buffer, length);
    });

    //////////////////////////////
    // REV
    //////////////////////////////
    SnobotSim::SetCANSparkMaxDriverCallback([](const char* name,
                                                    uint32_t messageId,
                                                    uint8_t* buffer,
                                                    int length) {
        gReveManager.handleMessage(name, messageId, buffer, length);
    });
}

void ResetThirdPartyCanCallbacks()
{
    gCtreManager.Reset();
}

} // namespace SnobotSim
