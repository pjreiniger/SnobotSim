
#pragma once

#include <string>

#include "SnobotSim/ExportHelper.h"

class EXPORT_ SimulatorConfigReaderV1
{
public:
    SimulatorConfigReaderV1();
    virtual ~SimulatorConfigReaderV1();

    bool LoadConfig(const std::string& aConfigFile);
};
