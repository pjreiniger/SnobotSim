
#include "SnobotSim/Config/SimulatorConfigReaderV1.h"

#include <iostream>

#include <filesystem>

#include "SnobotSim/Config/SimulatorConfigV1.h"
#include "SnobotSim/GetSensorActuatorHelper.h"
#include "SnobotSim/Logging/SnobotLogger.h"
#include "SnobotSim/ModuleWrapper/Factories/FactoryContainer.h"
#include "SnobotSim/MotorSim/GravityLoadDcMotorSim.h"
#include "SnobotSim/MotorSim/RotationalLoadDcMotorSim.h"
#include "SnobotSim/MotorSim/SimpleMotorSimulator.h"
#include "SnobotSim/MotorSim/StaticLoadDcMotorSim.h"
#include "SnobotSim/SensorActuatorRegistry.h"
#include "yaml-cpp/yaml.h"

void ParseMap(const YAML::Node& aNode, std::map<int, std::string>& aMap)
{
    for (YAML::const_iterator it = aNode.begin(); it != aNode.end(); ++it)
    {
        aMap[it->first.as<int>()] = it->second.as<std::string>();
    }
}

template <typename T>
void ParseVector(const YAML::Node& aNode, std::vector<T>& aVector)
{
    for (YAML::const_iterator it = aNode.begin(); it != aNode.end(); ++it)
    {
        T value;
        (*it) >> value;
        aVector.push_back(value);
    }
}

void LoadBasicConfig(const YAML::Node& aNode, BasicModuleConfig& aOutput)
{
    aOutput.mHandle = aNode["mHandle"].as<int>();
    aOutput.mName = aNode["mName"].as<std::string>();
    aOutput.mType = aNode["mType"].as<std::string>();
}

const YAML::Node& operator>>(const YAML::Node& aNode, EncoderConfig& aOutput)
{
    LoadBasicConfig(aNode, aOutput);
    aOutput.mConnectedSpeedControllerHandle = aNode["mConnectedSpeedControllerHandle"].as<int>();
    return aNode;
}

const YAML::Node& operator>>(const YAML::Node& aNode, PwmConfig& aOutput)
{
    LoadBasicConfig(aNode, aOutput);

    if (aNode["mMotorSimConfig"])
    {
        const YAML::Node& motorSimConfig = aNode["mMotorSimConfig"];
        const std::string& motorSimConfigTag = motorSimConfig.Tag();

        if (motorSimConfigTag == "tag:yaml.org,2002:com.snobot.simulator.motor_sim.SimpleMotorSimulationConfig")
        {
            aOutput.mMotorSimConfigType = PwmConfig::Simple;
            aOutput.mMotorSimConfig.mSimple.mMaxSpeed = motorSimConfig["mMaxSpeed"].as<double>();
        }
        else if (motorSimConfigTag == "tag:yaml.org,2002:com.snobot.simulator.motor_sim.StaticLoadMotorSimulationConfig")
        {
            aOutput.mMotorSimConfigType = PwmConfig::Static;
            aOutput.mMotorSimConfig.mStatic.mLoad = motorSimConfig["mLoad"].as<double>();
            aOutput.mMotorSimConfig.mStatic.mConversionFactor = motorSimConfig["mConversionFactor"].as<double>();
        }
        else if (motorSimConfigTag == "tag:yaml.org,2002:com.snobot.simulator.motor_sim.GravityLoadMotorSimulationConfig")
        {
            aOutput.mMotorSimConfigType = PwmConfig::Gravity;
            aOutput.mMotorSimConfig.mGravity.mLoad = motorSimConfig["mLoad"].as<double>();
        }
        else if (motorSimConfigTag == "tag:yaml.org,2002:com.snobot.simulator.motor_sim.RotationalLoadMotorSimulationConfig")
        {
            aOutput.mMotorSimConfigType = PwmConfig::Rotational;
            aOutput.mMotorSimConfig.mRotational.mArmCenterOfMass = motorSimConfig["mArmCenterOfMass"].as<double>();
            aOutput.mMotorSimConfig.mRotational.mArmMass = motorSimConfig["mArmMass"].as<double>();
            aOutput.mMotorSimConfig.mRotational.mConstantAssistTorque = motorSimConfig["mConstantAssistTorque"].as<double>();
            aOutput.mMotorSimConfig.mRotational.mOverCenterAssistTorque = motorSimConfig["mOverCenterAssistTorque"].as<double>();
        }
    }
    else
    {
        SNOBOT_LOG(SnobotLogging::LOG_LEVEL_CRITICAL, "Thing is null");
    }

    return aNode;
}

const YAML::Node& operator>>(const YAML::Node& aNode, BasicModuleConfig& aOutput)
{
    LoadBasicConfig(aNode, aOutput);
    return aNode;
}

const YAML::Node& operator>>(const YAML::Node& configNode, SimulatorConfigV1& config)
{
    ParseMap(configNode["mDefaultI2CWrappers"], config.mDefaultI2CWrappers);
    ParseMap(configNode["mDefaultSpiWrappers"], config.mDefaultSpiWrappers);
    ParseVector(configNode["mAccelerometers"], config.mAccelerometers);
    ParseVector(configNode["mAnalogIn"], config.mAnalogIn);
    ParseVector(configNode["mAnalogOut"], config.mAnalogOut);
    ParseVector(configNode["mDigitalIO"], config.mDigitalIO);
    ParseVector(configNode["mGyros"], config.mGyros);
    ParseVector(configNode["mRelays"], config.mRelays);
    ParseVector(configNode["mSolenoids"], config.mSolenoids);
    ParseVector(configNode["mEncoders"], config.mEncoders);
    ParseVector(configNode["mPwm"], config.mPwm);

    return configNode;
}

SimulatorConfigReaderV1::SimulatorConfigReaderV1()
{
}

SimulatorConfigReaderV1::~SimulatorConfigReaderV1()
{
}

template <typename FactoryType, typename WrapperType>
void CreateBasicComponent(std::shared_ptr<FactoryType> aFactory, const std::map<int, WrapperType>& wrapperMap, const BasicModuleConfig& aConfig)
{
    aFactory->Create(aConfig.mHandle, aConfig.mType);
    auto findIter = wrapperMap.find(aConfig.mHandle);
    if (findIter != wrapperMap.end())
    {
        findIter->second->SetName(aConfig.mName);
    }
    else
    {
        SNOBOT_LOG(SnobotLogging::LOG_LEVEL_CRITICAL, "Could not set name for handle " << aConfig.mHandle);
    }
}

template <typename FactoryType, typename WrapperType>
void CreateBasicComponents(std::shared_ptr<FactoryType> aFactory, const std::map<int, WrapperType>& wrapperMap, const std::vector<BasicModuleConfig>& aConfigs)
{
    for (auto it : aConfigs)
    {
        CreateBasicComponent(aFactory, wrapperMap, it);
    }
}

void CreatePwmComponents(std::shared_ptr<SpeedControllerFactory> aFactory, const std::map<int, std::shared_ptr<ISpeedControllerWrapper>>& wrapperMap, const std::vector<PwmConfig>& aConfigs)
{
    for (auto it : aConfigs)
    {
        CreateBasicComponent(aFactory, wrapperMap, it);

        std::shared_ptr<ISpeedControllerWrapper> speedController = GetSensorActuatorHelper::GetISpeedControllerWrapper(it.mHandle);
        if (!speedController)
        {
            SNOBOT_LOG(SnobotLogging::LOG_LEVEL_CRITICAL, "Invalid Speed Controller " << it.mHandle);
            continue;
        }

        DcMotorModelConfig::FactoryParams factoryParams(
                it.mMotorModelConfig.mFactoryParams.mMotorType,
                it.mMotorModelConfig.mFactoryParams.mNumMotors,
                it.mMotorModelConfig.mFactoryParams.mGearReduction,
                it.mMotorModelConfig.mFactoryParams.mGearboxEfficiency);

        DcMotorModelConfig motorModelConfig(
                factoryParams,
                it.mMotorModelConfig.mMotorParams.mNominalVoltage,
                it.mMotorModelConfig.mMotorParams.mFreeSpeedRpm,
                it.mMotorModelConfig.mMotorParams.mFreeCurrent,
                it.mMotorModelConfig.mMotorParams.mStallTorque,
                it.mMotorModelConfig.mMotorParams.mStallCurrent,
                it.mMotorModelConfig.mMotorParams.mMotorInertia,
                it.mMotorModelConfig.mFactoryParams.mHasBrake,
                it.mMotorModelConfig.mFactoryParams.mInverted);

        DcMotorModel motorModel(motorModelConfig);

        switch (it.mMotorSimConfigType)
        {
        case PwmConfig::Simple:
        {
            speedController->SetMotorSimulator(std::shared_ptr<IMotorSimulator>(new SimpleMotorSimulator(
                    it.mMotorSimConfig.mSimple.mMaxSpeed)));
            break;
        }
        case PwmConfig::Static:
        {
            speedController->SetMotorSimulator(std::shared_ptr<IMotorSimulator>(new StaticLoadDcMotorSim(
                    motorModel,
                    it.mMotorSimConfig.mStatic.mLoad,
                    it.mMotorSimConfig.mStatic.mConversionFactor)));
            break;
        }
        case PwmConfig::Gravity:
        {
            speedController->SetMotorSimulator(std::shared_ptr<IMotorSimulator>(new GravityLoadDcMotorSim(
                    motorModel,
                    it.mMotorSimConfig.mGravity.mLoad)));
            break;
        }
        case PwmConfig::Rotational:
        {
            speedController->SetMotorSimulator(std::shared_ptr<IMotorSimulator>(new RotationalLoadDcMotorSim(
                    motorModel,
                    speedController,
                    it.mMotorSimConfig.mRotational.mArmCenterOfMass,
                    it.mMotorSimConfig.mRotational.mArmMass,
                    it.mMotorSimConfig.mRotational.mConstantAssistTorque,
                    it.mMotorSimConfig.mRotational.mOverCenterAssistTorque)));
            break;
        }
        case PwmConfig::None:
        default:
            break;
        }
    }
}

void CreateEncoderComponents(std::shared_ptr<EncoderFactory> aFactory, const std::map<int, std::shared_ptr<IEncoderWrapper>>& wrapperMap, const std::vector<EncoderConfig>& aConfigs)
{
    for (auto it : aConfigs)
    {
        CreateBasicComponent(aFactory, wrapperMap, it);
        if (it.mHandle != -1 && it.mConnectedSpeedControllerHandle != -1)
        {
            std::shared_ptr<IEncoderWrapper> encoder = GetSensorActuatorHelper::GetIEncoderWrapper(it.mHandle);
            std::shared_ptr<ISpeedControllerWrapper> speedController = GetSensorActuatorHelper::GetISpeedControllerWrapper(it.mConnectedSpeedControllerHandle);
            if (encoder && speedController)
            {
                encoder->SetSpeedController(speedController);
            }
            else
            {
                SNOBOT_LOG(SnobotLogging::LOG_LEVEL_CRITICAL, "Necessary components not set (" << encoder << ", " << speedController << ")");
            }
        }
    }
}

void SetupSimulator(const SimulatorConfigV1& aConfig)
{
    for (auto it : aConfig.mDefaultI2CWrappers)
    {
        FactoryContainer::Get().GetI2CWrapperFactory()->RegisterDefaultWrapperType(it.first, it.second);
        FactoryContainer::Get().GetI2CWrapperFactory()->GetI2CWrapper(it.first);
    }
    for (auto it : aConfig.mDefaultSpiWrappers)
    {
        FactoryContainer::Get().GetSpiWrapperFactory()->RegisterDefaultWrapperType(it.first, it.second);
        FactoryContainer::Get().GetSpiWrapperFactory()->GetSpiWrapper(it.first);
    }

    CreateBasicComponents(FactoryContainer::Get().GetAccelerometerFactory(), SensorActuatorRegistry::Get().GetIAccelerometerWrapperMap(), aConfig.mAccelerometers);
    CreateBasicComponents(FactoryContainer::Get().GetAnalogInFactory(), SensorActuatorRegistry::Get().GetIAnalogInWrapperMap(), aConfig.mAnalogIn);
    CreateBasicComponents(FactoryContainer::Get().GetAnalogOutFactory(), SensorActuatorRegistry::Get().GetIAnalogOutWrapperMap(), aConfig.mAnalogOut);
    CreateBasicComponents(FactoryContainer::Get().GetDigitalIoFactory(), SensorActuatorRegistry::Get().GetIDigitalIoWrapperMap(), aConfig.mDigitalIO);
    CreateBasicComponents(FactoryContainer::Get().GetGyroFactory(), SensorActuatorRegistry::Get().GetIGyroWrapperMap(), aConfig.mGyros);
    CreateBasicComponents(FactoryContainer::Get().GetRelayFactory(), SensorActuatorRegistry::Get().GetIRelayWrapperMap(), aConfig.mRelays);
    CreateBasicComponents(FactoryContainer::Get().GetSolenoidFactory(), SensorActuatorRegistry::Get().GetISolenoidWrapperMap(), aConfig.mSolenoids);

    CreatePwmComponents(FactoryContainer::Get().GetSpeedControllerFactory(), SensorActuatorRegistry::Get().GetISpeedControllerWrapperMap(), aConfig.mPwm);
    CreateEncoderComponents(FactoryContainer::Get().GetEncoderFactory(), SensorActuatorRegistry::Get().GetIEncoderWrapperMap(), aConfig.mEncoders);
}

bool SimulatorConfigReaderV1::LoadConfig(const std::string& aConfigFile)
{
    namespace fs = std::experimental::filesystem;
    fs::path configFile = aConfigFile;

    bool success = true;
    SNOBOT_LOG(SnobotLogging::LOG_LEVEL_INFO, "Loading config file '" << fs::canonical(configFile) << "'");

    try
    {
        YAML::Node configNode = YAML::LoadFile(aConfigFile);

        SimulatorConfigV1 config;
        configNode >> config;

        SetupSimulator(config);
    }
    catch (std::exception& ex)
    {
        SNOBOT_LOG(SnobotLogging::LOG_LEVEL_CRITICAL, "Could not parse config file... " << ex.what());
        success = false;
    }

    return success;
}
