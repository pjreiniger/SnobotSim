
#include "SnobotSim/HalCallbacks/ThirdPartyCanCallbacks.h"

#undef EXPORT_
#include "CtreSimMocks/MockHooks.h"

#undef EXPORT_
#include "RevSimMocks/MockHooks.h"

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
    SnobotSim::SetPigeonCallback([](const char* name,
                                         uint32_t messageId,
                                         uint8_t* buffer,
                                         int length) {
        gCtreManager.handlePigeonMessage(name, messageId, buffer, length);
    });
    SnobotSim::SetCanifierCallback([](const char* name,
                                           uint32_t messageId,
                                           uint8_t* buffer,
                                           int length) {
        gCtreManager.handleCanifierMessage(name, messageId, buffer, length);
    });
    SnobotSim::SetBuffTrajPiontStreamCallback([](const char* name,
                                                      uint32_t messageId,
                                                      uint8_t* buffer,
                                                      int length) {
        gCtreManager.handleBuffTrajPointStreamMessage(name, messageId, buffer, length);
    });

    //////////////////////////////
    // REV
    //////////////////////////////
    SnobotSim::SetSparkMaxDriverCallback([](const char* name,
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
