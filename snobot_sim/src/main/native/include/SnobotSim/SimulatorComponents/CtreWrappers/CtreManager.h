
#pragma once

#include <map>
#include <memory>
#include <string>

#include "SnobotSim/SimulatorComponents/CtreWrappers/CtrePigeonImuSim.h"

class CtreManager
{
public:
    void Reset();

    void handleMotorControllerMessage(const std::string& aCallback, int aCanPort, uint8_t* aBuffer, int aLength);
    void handlePigeonMessage(const std::string& aCallback, int aCanPort, uint8_t* aBuffer, int aLength);
    void handleCanifierMessage(const std::string& aCallback, int aCanPort, uint8_t* aBuffer, int aLength);
    void handleCanCoderMessage(const std::string& aCallback, int aCanPort, uint8_t* aBuffer, int aLength);
    void handleBuffTrajPointStreamMessage(const std::string& aCallback, int aCanPort, uint8_t* aBuffer, int aLength);

    std::shared_ptr<CtrePigeonImuSim> createPigeon(int aPort);
    void createMotorController(int aCanPort);

protected:
    std::shared_ptr<CtrePigeonImuSim> getPigeonWrapper(int aCanPort);

    std::map<int, std::shared_ptr<CtrePigeonImuSim>> mPigeonMap;
};
