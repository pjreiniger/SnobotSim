
#include "SnobotSim/SimulatorComponents/CtreWrappers/CtreManager.h"

#include <cstring>

#include "SnobotSim/GetSensorActuatorHelper.h"
#include "SnobotSim/Logging/SnobotLogger.h"
#include "SnobotSim/ModuleWrapper/Factories/FactoryContainer.h"
#include "SnobotSim/SimulatorComponents/CtreWrappers/CtreTalonSRXSpeedControllerSim.h"
#include "SnobotSim/SimulatorComponents/SmartSC/CanIdOffset.h"

namespace
{
std::shared_ptr<CtreTalonSRXSpeedControllerSim> getMotorControllerWrapper(int aCanPort)
{
    auto rawWrapper = SensorActuatorRegistry::Get().GetISpeedControllerWrapper(aCanPort + CAN_SC_OFFSET, true);
    auto wrapper = std::dynamic_pointer_cast<CtreTalonSRXSpeedControllerSim>(rawWrapper);
    if (!wrapper)
    {
        SNOBOT_LOG(SnobotLogging::LOG_LEVEL_CRITICAL, "Wrapper not found for port " << aCanPort);
    }
    return wrapper;
    // return (CtreTalonSrxSpeedControllerSim) SensorActuatorRegistry.get().getSpeedControllers().get(aCanPort + CtreTalonSrxSpeedControllerSim.sCAN_SC_OFFSET);
}

template <typename Type>
Type Extract(uint8_t* buffer, size_t& aBufferPos)
{
    Type output;
    std::memcpy(&output, &buffer[aBufferPos], sizeof(Type));
    aBufferPos += sizeof(Type);

    return output;
}

template <typename Type>
void Write(uint8_t* buffer, size_t& aBufferPos, const Type& value)
{
    std::memcpy(&buffer[aBufferPos], &value, sizeof(Type));
    aBufferPos += sizeof(Type);
}

// TODO make more elegant
template <typename T0>
void WriteData(const std::string& name, uint8_t* aBuffer, int aLength, const T0& v0)
{
    constexpr int expected_size = sizeof(T0);
    if (expected_size != aLength)
    {
        SNOBOT_LOG(SnobotLogging::LOG_LEVEL_CRITICAL, "Incorrect size, for '" << name << "' expected " << expected_size << ", got " << aLength);
    }

    size_t bufferPos = 0;
    Write(aBuffer, bufferPos, v0);
}

template <typename T0, typename T1>
void WriteData(const std::string& name, uint8_t* aBuffer, int aLength, const T0& v0, const T1& v1)
{
    constexpr int expected_size = sizeof(T0) + sizeof(T1);
    if (expected_size != aLength)
    {
        SNOBOT_LOG(SnobotLogging::LOG_LEVEL_CRITICAL, "Incorrect size, for '" << name << "' expected " << expected_size << ", got " << aLength);
    }

    size_t bufferPos = 0;
    Write(aBuffer, bufferPos, v0);
    Write(aBuffer, bufferPos, v1);
}

template <typename T0, typename T1, typename T2>
void WriteData(const std::string& name, uint8_t* aBuffer, int aLength, const T0& v0, const T1& v1, const T2& v2)
{
    constexpr int expected_size = sizeof(T0) + sizeof(T1) + sizeof(T2);
    if (expected_size != aLength)
    {
        SNOBOT_LOG(SnobotLogging::LOG_LEVEL_CRITICAL, "Incorrect size, for '" << name << "' expected " << expected_size << ", got " << aLength);
    }

    size_t bufferPos = 0;
    Write(aBuffer, bufferPos, v0);
    Write(aBuffer, bufferPos, v1);
    Write(aBuffer, bufferPos, v2);
}

// TODO make more elegant
template <typename T0>
std::tuple<T0> ExtractData(uint8_t* buffer, int aLength)
{
    constexpr int expected_size = sizeof(T0);
    if (expected_size != aLength)
    {
        SNOBOT_LOG(SnobotLogging::LOG_LEVEL_CRITICAL, "Incorrect size, expected " << expected_size << ", got " << aLength);
    }

    size_t bufferPos = 0;
    T0 t0 = Extract<T0>(buffer, bufferPos);

    return std::make_tuple(t0);
}

template <typename T0, typename T1>
std::tuple<T0, T1> ExtractData(uint8_t* buffer, int aLength)
{
    constexpr int expected_size = sizeof(T0) + sizeof(T1);
    if (expected_size != aLength)
    {
        SNOBOT_LOG(SnobotLogging::LOG_LEVEL_CRITICAL, "Incorrect size, expected " << expected_size << ", got " << aLength);
    }

    size_t bufferPos = 0;
    T0 t0 = Extract<T0>(buffer, bufferPos);
    T1 t1 = Extract<T1>(buffer, bufferPos);

    return std::make_tuple(t0, t1);
}

template <typename T0, typename T1, typename T2>
std::tuple<T0, T1, T2> ExtractData(uint8_t* buffer, int aLength)
{
    constexpr int expected_size = sizeof(T0) + sizeof(T1) + sizeof(T2);
    if (expected_size != aLength)
    {
        SNOBOT_LOG(SnobotLogging::LOG_LEVEL_CRITICAL, "Incorrect size, expected " << expected_size << ", got " << aLength);
    }

    size_t bufferPos = 0;
    T0 t0 = Extract<T0>(buffer, bufferPos);
    T1 t1 = Extract<T1>(buffer, bufferPos);
    T2 t2 = Extract<T2>(buffer, bufferPos);

    return std::make_tuple(t0, t1, t2);
}

template <typename T0, typename T1, typename T2, typename T3>
std::tuple<T0, T1, T2, T3> ExtractData(uint8_t* buffer, int aLength)
{
    constexpr int expected_size = sizeof(T0) + sizeof(T1) + sizeof(T2) + sizeof(T3);
    if (expected_size != aLength)
    {
        SNOBOT_LOG(SnobotLogging::LOG_LEVEL_CRITICAL, "Incorrect size, expected " << expected_size << ", got " << aLength);
    }

    size_t bufferPos = 0;
    T0 t0 = Extract<T0>(buffer, bufferPos);
    T1 t1 = Extract<T1>(buffer, bufferPos);
    T2 t2 = Extract<T2>(buffer, bufferPos);
    T3 t3 = Extract<T3>(buffer, bufferPos);

    return std::make_tuple(t0, t1, t2, t3);
}
} // namespace

void CtreManager::Reset()
{
    mPigeonMap.clear();
}

void CtreManager::handleMotorControllerMessage(const std::string& aCallback, int aCanPort, uint8_t* aBuffer, int aLength)
{
    SNOBOT_LOG(SnobotLogging::LOG_LEVEL_DEBUG, "Getting Motor Controller Message " << aCallback << " on port " << aCanPort << "(" << aLength << " bytes)");

    if ("Create" == aCallback)
    {
        createMotorController(aCanPort);
    }
    else if ("SetDemand" == aCallback)
    {
        auto wrapper = getMotorControllerWrapper(aCanPort);

        auto [mode, param0, param1] = ExtractData<int, int, int>(aBuffer, aLength);
        // sLOGGER.log(Level.DEBUG, "Setting demand " + mode + ", " + param0 + ", " + param1);

        switch (mode)
        {
        case 0:
            wrapper->SetVoltagePercentage(param0 / 1023.0);
            break;
        case 1:
            wrapper->setPositionGoal(param0);
            break;
        case 2:
            wrapper->setSpeedGoal(param0);
            break;
        case 5:
        {
            int followerPort = param0 & 0xFF;
            auto leadTalon = getMotorControllerWrapper(followerPort);
            leadTalon->addFollower(wrapper);
            break;
        }
        case 6:
            wrapper->setMotionProfilingCommand(param0);
            break;
        case 7:
            wrapper->setMotionMagicGoal(param0);
            break;
        case 15:
            wrapper->SetVoltagePercentage(0);
            break;
        default:
            SNOBOT_LOG(SnobotLogging::LOG_LEVEL_CRITICAL, "Unknown demand mode " << mode);
            break;
        }
    }
    else if ("Set_4" == aCallback)
    {
        auto wrapper = getMotorControllerWrapper(aCanPort);

        auto [mode, demand0, demand1, demand1Type] = ExtractData<int, double, double, int>(aBuffer, aLength);
        SNOBOT_LOG(SnobotLogging::LOG_LEVEL_DEBUG, "Setting_4 " << mode << ", " << demand0 << ", " << demand1 << ", " << demand1Type);

        switch (mode)
        {
        case 0:
            wrapper->setRawGoal(demand0);
            break;
        case 1:
            wrapper->setPositionGoal(demand0);
            break;
        case 2:
            wrapper->setSpeedGoal(demand0);
            break;
        case 5:
        {
            int followerPort = (static_cast<int>(demand0)) & 0xFF;
            auto leadTalon = getMotorControllerWrapper(followerPort);
            leadTalon->addFollower(wrapper);
            break;
        }
        case 6:
            wrapper->setMotionProfilingCommand(demand0);
            break;
        case 7:
            wrapper->setMotionMagicGoal(demand0);
            break;
        case 15:
            wrapper->SetVoltagePercentage(0);
            break;
        default:
            SNOBOT_LOG(SnobotLogging::LOG_LEVEL_CRITICAL, "Unknown demand mode " << mode);
            break;
        }
    }
    else if ("ConfigSelectedFeedbackSensor" == aCallback)
    {
        auto wrapper = getMotorControllerWrapper(aCanPort);

        auto [feedbackDevice, _] = ExtractData<int, int>(aBuffer, aLength);
        wrapper->setCanFeedbackDevice(static_cast<char>(feedbackDevice));
    }
    else if ("Config_kP" == aCallback)
    {
        auto wrapper = getMotorControllerWrapper(aCanPort);

        auto [slot, value] = ExtractData<int, double>(aBuffer, aLength);
        wrapper->setPGain(slot, value);
    }
    else if ("Config_kI" == aCallback)
    {
        auto wrapper = getMotorControllerWrapper(aCanPort);

        auto [slot, value] = ExtractData<int, double>(aBuffer, aLength);
        wrapper->setIGain(slot, value);
    }
    else if ("Config_kD" == aCallback)
    {
        auto wrapper = getMotorControllerWrapper(aCanPort);

        auto [slot, value] = ExtractData<int, double>(aBuffer, aLength);
        wrapper->setDGain(slot, value);
    }
    else if ("Config_kF" == aCallback)
    {
        auto wrapper = getMotorControllerWrapper(aCanPort);

        auto [slot, value] = ExtractData<int, double>(aBuffer, aLength);
        wrapper->setFGain(slot, value);
    }
    else if ("Config_IntegralZone" == aCallback)
    {
        auto wrapper = getMotorControllerWrapper(aCanPort);

        auto [slot, value] = ExtractData<int, double>(aBuffer, aLength);
        wrapper->setIZone(slot, value);
    }
    else if ("SetSelectedSensorPosition" == aCallback)
    {
        auto wrapper = getMotorControllerWrapper(aCanPort);
        auto [slot, value] = ExtractData<int, double>(aBuffer, aLength);
        if (value != 0)
        {
            SNOBOT_LOG(SnobotLogging::LOG_LEVEL_WARN, "Only setting position to 0 is supported");
        }
        wrapper->Reset();
    }
    /*
        else if ("ConfigMotionCruiseVelocity" == aCallback)
        {
            auto wrapper = getMotorControllerWrapper(aCanPort);

            int sensorUnitsPer100ms = aData.getInt();

            wrapper->setMotionMagicMaxVelocity(sensorUnitsPer100ms);
        }
        else if ("ConfigMotionAcceleration" == aCallback)
        {
            auto wrapper = getMotorControllerWrapper(aCanPort);

            int sensorUnitsPer100msPerSec = aData.getInt();

            wrapper->setMotionMagicMaxAcceleration(sensorUnitsPer100msPerSec);
        }
        else if ("PushMotionProfileTrajectory" == aCallback)
        {
            double position = aData.getDouble();
            double velocity = aData.getDouble();

            auto wrapper = getMotorControllerWrapper(aCanPort);
            MotionProfilePoint point = new MotionProfilePoint(wrapper->getMotionProfileSize() + 1, position, velocity);
            wrapper->addMotionProfilePoint(point);
        }
        else if ("PushMotionProfileTrajectory_2" == aCallback)
        {
            double position = aData.getDouble();
            double velocity = aData.getDouble();

            auto wrapper = getMotorControllerWrapper(aCanPort);
            MotionProfilePoint point = new MotionProfilePoint(wrapper->getMotionProfileSize() + 1, position, velocity);
            wrapper->addMotionProfilePoint(point);
        }
        else if ("ProcessMotionProfileBuffer" == aCallback)
        { // NOPMD
            // Nothing to do
        }

        ////////////////////////
        //
        ////////////////////////
        */
    else if ("GetBaseID" == aCallback)
    {
        WriteData(aCallback, aBuffer, aLength, aCanPort);
    }
    else if ("GetMotorOutputPercent" == aCallback)
    {
        auto wrapper = getMotorControllerWrapper(aCanPort);

        double speed = wrapper->GetVoltagePercentage();
        WriteData(aCallback, aBuffer, aLength, speed);
    }
    else if ("GetSelectedSensorPosition" == aCallback)
    {
        auto wrapper = getMotorControllerWrapper(aCanPort);
        auto [_, pidSlot] = ExtractData<int, int>(aBuffer, aLength);

        int position = wrapper->getBinnedPosition();
        WriteData(aCallback, aBuffer, aLength, position, pidSlot);
    }
    else if ("GetSelectedSensorVelocity" == aCallback)
    {
        auto wrapper = getMotorControllerWrapper(aCanPort);

        auto [_, pidSlot] = ExtractData<int, int>(aBuffer, aLength);

        int speed = wrapper->getBinnedVelocity();
        WriteData(aCallback, aBuffer, aLength, speed, pidSlot);
    }
    /*
        else if ("GetClosedLoopError" == aCallback)
        {
            auto wrapper = getMotorControllerWrapper(aCanPort);

            int speed = (int) Math.ceil(wrapper->getLastClosedLoopError());
            aData.putInt(0, speed);

        }
        else if ("GetMotionProfileStatus" == aCallback)
        {
            auto wrapper = getMotorControllerWrapper(aCanPort);

            aData.putInt(4550);
            aData.putInt(wrapper->getMotionProfileSize());
            aData.putInt(0);
            aData.put((byte) 3);
            aData.put((byte) 4);
            aData.put((byte) 5);
            aData.put((byte) 0);
            aData.putInt(4444);
            aData.putInt(3333);
        }
        else if ("GetActiveTrajectoryPosition" == aCallback)
        {
            auto wrapper = getMotorControllerWrapper(aCanPort);
            MotionProfilePoint point = wrapper->getMotionProfilePoint();
            aData.putInt(point == null ? 0 : (int) point.mPosition);
        }
        else if ("GetActiveTrajectoryVelocity" == aCallback)
        {
            auto wrapper = getMotorControllerWrapper(aCanPort);
            MotionProfilePoint point = wrapper->getMotionProfilePoint();
            aData.putInt(point == null ? 0 : (int) point.mVelocity);
        }
        else if ("GetQuadraturePosition" == aCallback)
        {
            auto wrapper = getMotorControllerWrapper(aCanPort);

            int speed = wrapper->getBinnedPosition();
            aData.putInt(0, speed);
        }
        else if ("GetQuadratureVelocity" == aCallback)
        {
            auto wrapper = getMotorControllerWrapper(aCanPort);

            int speed = wrapper->getBinnedVelocity();
            aData.putInt(0, speed);
        }
        else if ("SetInverted_2" == aCallback)
        {
            auto wrapper = getMotorControllerWrapper(aCanPort);

            int inverted = aData.getInt();
            sLOGGER.log(Level.DEBUG, "SetInverted_2 " + inverted);
            wrapper->setInverted(inverted != 0);
        }

        ///////////////////////////////////
        // Unsupported, but not important
        ///////////////////////////////////
        else if (unsupportedFunctions.contains(aCallback))
        {
            sLOGGER.log(Level.DEBUG, aCallback + " not supported");
        }
        */
    else
    {
        SNOBOT_LOG(SnobotLogging::LOG_LEVEL_CRITICAL, "Unknown motor callback: " << aCallback);
    }
}

void CtreManager::handlePigeonMessage(const std::string& aCallback, int aCanPort, uint8_t* aBuffer, int aLength)
{
    SNOBOT_LOG(SnobotLogging::LOG_LEVEL_CRITICAL, "Getting Pigeon Message " << aCallback << " on port " << aCanPort << "(" << aLength << " bytes)");

    if ("Create" == aCallback)
    {
        if (mPigeonMap.find(aCanPort) == mPigeonMap.end())
        {
            SNOBOT_LOG(SnobotLogging::LOG_LEVEL_CRITICAL, "CTRE Pigeon is being created dynamically instead of in the config file for port " << aCanPort);
            createPigeon(aCanPort);
        }

        mPigeonMap[aCanPort]->SetInitialized(true);
        // CtrePigeonImuSim pigeon = mPigeonMap.get(aPort);
        // if (pigeon == null)
        // {
        //     sLOGGER.log(Level.WARN, "CTRE Pigeon is being created dynamically instead of in the config file for port " + aPort);
        //     pigeon = createPigeon(aPort);
        // }

        // pigeon.setInitialized(true);
    }
    else if ("SetYaw" == aCallback)
    {
        // double desiredYaw = aData.getDouble();
        // CtrePigeonImuSim wrapper = getPigeonWrapper(aPort);
        // ((CtrePigeonImuSim.PigeonGyroWrapper) wrapper.getYawWrapper()).setDesiredYaw(desiredYaw);
    }

    //////////////////////////
    //
    //////////////////////////
    else if ("GetRawGyro" == aCallback)
    {
        auto wrapper = getPigeonWrapper(aCanPort);

        WriteData(aCallback, aBuffer, aLength,
                wrapper->getYawWrapper()->GetAngle(),
                wrapper->getPitchWrapper()->GetAngle(),
                wrapper->getRollWrapper()->GetAngle());
    }
    else if ("GetYawPitchRoll" == aCallback)
    {
        auto wrapper = getPigeonWrapper(aCanPort);

        WriteData(aCallback, aBuffer, aLength,
                wrapper->getYawWrapper()->GetAngle(),
                wrapper->getPitchWrapper()->GetAngle(),
                wrapper->getRollWrapper()->GetAngle());
    }
    else if ("GetFusedHeading" == aCallback || "GetFusedHeading1" == aCallback)
    {
        auto wrapper = getPigeonWrapper(aCanPort);

        WriteData(aCallback, aBuffer, aLength, wrapper->getYawWrapper()->GetAngle());
    }
    else
    {
        SNOBOT_LOG(SnobotLogging::LOG_LEVEL_CRITICAL, "Unknown pigeon callback: " << aCallback);
    }
}

void CtreManager::handleCanifierMessage(const std::string& aCallback, int aCanPort, uint8_t* aBuffer, int aLength)
{
    SNOBOT_LOG(SnobotLogging::LOG_LEVEL_CRITICAL, "Getting Canifier Message " << aCallback << " on port " << aCanPort << "(" << aLength << " bytes)");
}

void CtreManager::handleCanCoderMessage(const std::string& aCallback, int aCanPort, uint8_t* aBuffer, int aLength)
{
    SNOBOT_LOG(SnobotLogging::LOG_LEVEL_CRITICAL, "Getting CanCoder Message " << aCallback << " on port " << aCanPort << "(" << aLength << " bytes)");
}

void CtreManager::handleBuffTrajPointStreamMessage(const std::string& aCallback, int aCanPort, uint8_t* aBuffer, int aLength)
{
    SNOBOT_LOG(SnobotLogging::LOG_LEVEL_CRITICAL, "Getting Trajectory Point Message " << aCallback << " on port " << aCanPort << "(" << aLength << " bytes)");
}

std::shared_ptr<CtrePigeonImuSim> CtreManager::createPigeon(int aPort)
{
    if (mPigeonMap.find(aPort) != mPigeonMap.end())
    {
        SNOBOT_LOG(SnobotLogging::LOG_LEVEL_CRITICAL, "Pigeon alrady registered on " << aPort);
    }
    else
    {
        std::shared_ptr<CtrePigeonImuSim> sim(new CtrePigeonImuSim(CtrePigeonImuSim::CTRE_OFFSET + aPort * 3));
        mPigeonMap[aPort] = sim;
    }

    return mPigeonMap[aPort];
}

void CtreManager::createMotorController(int aCanPort)
{
    int simPort = aCanPort + CAN_SC_OFFSET;

    auto wrapper = SensorActuatorRegistry::Get().GetISpeedControllerWrapper(aCanPort, false);
    if (!wrapper)
    {
        SNOBOT_LOG(SnobotLogging::LOG_LEVEL_WARN, "CTRE Motor Controller is being created dynamically instead of in the config file for port " << aCanPort);

        FactoryContainer::Get().GetSpeedControllerFactory()->Create(simPort, CtreTalonSRXSpeedControllerSim::TYPE);
        // SensorActuatorRegistry::Get().Register(simPort, std::shared_ptr<ISpeedControllerWrapper>(new CtreTalonSRXSpeedControllerSim(aCanPort)));
    }
    else if (!std::dynamic_pointer_cast<CtreTalonSRXSpeedControllerSim>(wrapper))
    {
        SNOBOT_LOG(SnobotLogging::LOG_LEVEL_CRITICAL, "Wrong motor type set up on " << aCanPort);
        return;
    }

    SensorActuatorRegistry::Get().GetISpeedControllerWrapper(simPort, true)->SetInitialized(true);
}

std::shared_ptr<CtrePigeonImuSim> CtreManager::getPigeonWrapper(int aCanPort)
{
    if (mPigeonMap.find(aCanPort) == mPigeonMap.end())
    {
        SNOBOT_LOG(SnobotLogging::LOG_LEVEL_CRITICAL, "No pigeon on " << aCanPort);
        StackTraceHelper::PrintStackTracker();
    }
    return mPigeonMap[aCanPort];
}
