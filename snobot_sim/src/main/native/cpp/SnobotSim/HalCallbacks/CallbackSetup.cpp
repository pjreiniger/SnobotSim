
#include "SnobotSim/HalCallbacks/CallbackSetup.h"

#include "SnobotSim/HalCallbacks/AnalogGyroCallbacks.h"
#include "SnobotSim/HalCallbacks/AnalogIOCallbacks.h"
#include "SnobotSim/HalCallbacks/DigitalIOCallbacks.h"
#include "SnobotSim/HalCallbacks/EncoderCallbacks.h"
#include "SnobotSim/HalCallbacks/I2CCallbacks.h"
#include "SnobotSim/HalCallbacks/PwmCallbacks.h"
#include "SnobotSim/HalCallbacks/RelayCallbacks.h"
#include "SnobotSim/HalCallbacks/SolenoidCallbacks.h"
#include "SnobotSim/HalCallbacks/SpiCallbacks.h"
#include "SnobotSim/HalCallbacks/ThirdPartyCanCallbacks.h"
#include "SnobotSim/Logging/SnobotLogger.h"
#include "simulation/SimDeviceSim.h"

void SnobotSim::InitializeSnobotCallbacks()
{
    InitializeAnalogGyroCallbacks();
    InitializeAnalogIOCallbacks();
    InitializeDigitalIOCallbacks();
    InitializeEncoderCallbacks();
    InitializeI2CCallbacks();
    InitializePwmCallbacks();
    InitializeRelayCallbacks();
    InitializeSolenoidCallbacks();
    InitializeSpiCallbacks();

    InitializeThirdPartyCanCallbacks();
}

void SnobotSim::ResetSnobotCallbacks()
{
    frc::sim::SimDeviceSim::ResetData();
    ResetAnalogGyroCallbacks();
    ResetAnalogIOCallbacks();
    ResetDigitalIOCallbacks();
    ResetEncoderCallbacks();
    ResetI2CCallbacks();
    ResetPwmCallbacks();
    ResetRelayCallbacks();
    ResetSolenoidCallbacks();
    ResetSpiCallbacks();

    ResetThirdPartyCanCallbacks();

    // Re-initialize after you are done
    InitializeSnobotCallbacks();
}
