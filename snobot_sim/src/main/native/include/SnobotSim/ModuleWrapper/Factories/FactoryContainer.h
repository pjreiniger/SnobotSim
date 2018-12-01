/*
 * FactoryContainer.h
 *
 *  Created on: Jul 18, 2018
 *      Author: PJ
 */

#pragma once

#include <memory>

#include "SnobotSim/ModuleWrapper/Factories/AccelerometerFactory.h"
#include "SnobotSim/ModuleWrapper/Factories/AnalogInFactory.h"
#include "SnobotSim/ModuleWrapper/Factories/AnalogOutFactory.h"
#include "SnobotSim/ModuleWrapper/Factories/DigitalIoFactory.h"
#include "SnobotSim/ModuleWrapper/Factories/EncoderFactory.h"
#include "SnobotSim/ModuleWrapper/Factories/GyroFactory.h"
#include "SnobotSim/ModuleWrapper/Factories/I2CWrapperFactory.h"
#include "SnobotSim/ModuleWrapper/Factories/RelayFactory.h"
#include "SnobotSim/ModuleWrapper/Factories/SolenoidFactory.h"
#include "SnobotSim/ModuleWrapper/Factories/SpeedControllerFactory.h"
#include "SnobotSim/ModuleWrapper/Factories/SpiWrapperFactory.h"

class EXPORT_ FactoryContainer
{
private:
    FactoryContainer();
    FactoryContainer(const FactoryContainer& that) = delete;
    virtual ~FactoryContainer();
    static FactoryContainer sINSTANCE;

public:
    static FactoryContainer& Get();

    std::shared_ptr<AccelerometerFactory> GetAccelerometerFactory();
    std::shared_ptr<AnalogInFactory> GetAnalogInFactory();
    std::shared_ptr<AnalogOutFactory> GetAnalogOutFactory();
    std::shared_ptr<DigitalIoFactory> GetDigitalIoFactory();
    std::shared_ptr<EncoderFactory> GetEncoderFactory();
    std::shared_ptr<GyroFactory> GetGyroFactory();
    std::shared_ptr<I2CWrapperFactory> GetI2CWrapperFactory();
    std::shared_ptr<RelayFactory> GetRelayFactory();
    std::shared_ptr<SolenoidFactory> GetSolenoidFactory();
    std::shared_ptr<SpeedControllerFactory> GetSpeedControllerFactory();
    std::shared_ptr<SpiWrapperFactory> GetSpiWrapperFactory();

protected:
    std::shared_ptr<AccelerometerFactory> mAccelerometerFactory;
    std::shared_ptr<AnalogInFactory> mAnalogInFactory;
    std::shared_ptr<AnalogOutFactory> mAnalogOutFactory;
    std::shared_ptr<DigitalIoFactory> mDigitalIoFactory;
    std::shared_ptr<EncoderFactory> mEncoderFactory;
    std::shared_ptr<GyroFactory> mGyroFactory;
    std::shared_ptr<I2CWrapperFactory> mI2CWrapperFactory;
    std::shared_ptr<RelayFactory> mRelayFactory;
    std::shared_ptr<SolenoidFactory> mSolenoidFactory;
    std::shared_ptr<SpeedControllerFactory> mSpeedControllerFactory;
    std::shared_ptr<SpiWrapperFactory> mSpiWrapperFactory;
};
