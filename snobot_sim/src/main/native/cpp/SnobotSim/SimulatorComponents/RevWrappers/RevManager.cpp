
#include "SnobotSim/SimulatorComponents/RevWrappers/RevManager.h"

#include <cstring>

#include "SnobotSim/GetSensorActuatorHelper.h"
#include "SnobotSim/Logging/SnobotLogger.h"
#include "SnobotSim/ModuleWrapper/Factories/FactoryContainer.h"
#include "SnobotSim/SimulatorComponents/RevWrappers/RevSpeedControllerSimWrapper.h"
#include "SnobotSim/SimulatorComponents/SmartSC/CanIdOffset.h"

namespace
{

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

template <typename T0, typename T1, typename T2, typename T3, typename T4>
std::tuple<T0, T1, T2, T3, T4> ExtractData(uint8_t* buffer, int aLength)
{
    constexpr int expected_size = sizeof(T0) + sizeof(T1) + sizeof(T2) + sizeof(T3) + sizeof(T4);
    if (expected_size != aLength)
    {
        SNOBOT_LOG(SnobotLogging::LOG_LEVEL_CRITICAL, "Incorrect size, expected " << expected_size << ", got " << aLength);
    }

    size_t bufferPos = 0;
    T0 t0 = Extract<T0>(buffer, bufferPos);
    T1 t1 = Extract<T1>(buffer, bufferPos);
    T2 t2 = Extract<T2>(buffer, bufferPos);
    T3 t3 = Extract<T3>(buffer, bufferPos);
    T4 t4 = Extract<T4>(buffer, bufferPos);

    return std::make_tuple(t0, t1, t2, t3, t4);
}

std::shared_ptr<RevpeedControllerSim> getMotorControllerWrapper(int aCanPort)
{
    auto rawWrapper = SensorActuatorRegistry::Get().GetISpeedControllerWrapper(aCanPort + CAN_SC_OFFSET, true);
    auto wrapper = std::dynamic_pointer_cast<RevpeedControllerSim>(rawWrapper);
    if (!wrapper)
    {
        SNOBOT_LOG(SnobotLogging::LOG_LEVEL_CRITICAL, "Wrapper not found for port " << aCanPort);
    }
    return wrapper;
    // return (CtreTalonSrxSpeedControllerSim) SensorActuatorRegistry.get().getSpeedControllers().get(aCanPort + CtreTalonSrxSpeedControllerSim.sCAN_SC_OFFSET);
}

void createSim(int aCanPort)
{
    int simPort = aCanPort + CAN_SC_OFFSET;

    auto wrapper = SensorActuatorRegistry::Get().GetISpeedControllerWrapper(aCanPort, false);
    if (!wrapper)
    {
        SNOBOT_LOG(SnobotLogging::LOG_LEVEL_WARN, "Rev Motor Controller is being created dynamically instead of in the config file for port " << aCanPort);

        FactoryContainer::Get().GetSpeedControllerFactory()->Create(simPort, RevpeedControllerSim::TYPE);
        // SensorActuatorRegistry::Get().Register(simPort, std::shared_ptr<ISpeedControllerWrapper>(new CtreTalonSRXSpeedControllerSim(aCanPort)));
    }
    else if (!std::dynamic_pointer_cast<RevpeedControllerSim>(wrapper))
    {
        SNOBOT_LOG(SnobotLogging::LOG_LEVEL_CRITICAL, "Wrong motor type set up on " << aCanPort);
        return;
    }

    SensorActuatorRegistry::Get().GetISpeedControllerWrapper(simPort, true)->SetInitialized(true);
}

} // namespace

void RevManager::Reset()
{
}

void RevManager::handleMessage(const std::string& aCallback, int aCanPort, uint8_t* aBuffer, int aLength)
{
    SNOBOT_LOG(SnobotLogging::LOG_LEVEL_DEBUG, "Getting Motor Controller Message " << aCallback << " on port " << aCanPort << "(" << aLength << " bytes)");

    if ("Create" == aCallback)
    {
        createSim(aCanPort);
    }
    else if ("SetpointCommand" == aCallback)
    {
        auto wrapper = getMotorControllerWrapper(aCanPort);

        auto [value, ctrl, pidSlot, arbFeedForward, arbFFUnits] = ExtractData<float, int, int, float, int>(aBuffer, aLength);
        SNOBOT_LOG(SnobotLogging::LOG_LEVEL_DEBUG, "SetpointCommand " << value << ", " << ctrl);

        /*
            int pidSlot = aData.getInt();
            float arbFeedforward = aData.getFloat();
            int arbFFUnits = aData.getInt();
            */
        switch (ctrl)
        {
        // Throttle
        case 0:
            wrapper->setRawGoal(value);
            break;
        // Velocity
        case 1:
            wrapper->setSpeedGoal(value);
            break;
        // Position
        case 3:
            wrapper->setPositionGoal(value);
            break;
        // SmartMotion
        case 4:
            wrapper->setMotionMagicGoal(value);
            break;
        default:
            SNOBOT_LOG(SnobotLogging::LOG_LEVEL_CRITICAL, "Unknown demand mode " << ctrl);
            break;
        }
    }
    else if ("SetFollow" == aCallback)
    {
        auto [followerID] = ExtractData<int>(aBuffer, aLength);
        int leadId = followerID & 0x3F;

        auto leadWrapper = getMotorControllerWrapper(leadId);
        auto follower = getMotorControllerWrapper(aCanPort);

        SNOBOT_LOG(SnobotLogging::LOG_LEVEL_INFO, "Setting SparkMax " << aCanPort << " to follow " << leadId);

        leadWrapper->addFollower(follower);
    }
    else if ("SetSensorType" == aCallback)
    {
        auto wrapper = getMotorControllerWrapper(aCanPort);
        auto [type] = ExtractData<int>(aBuffer, aLength);
        wrapper->setCanFeedbackDevice(type);
    }
    else if ("SetFeedbackDevice" == aCallback)
    {
        auto wrapper = getMotorControllerWrapper(aCanPort);
        auto [type] = ExtractData<int>(aBuffer, aLength);
        wrapper->setCanFeedbackDevice(type);
    }
    else if ("SetP" == aCallback)
    {
        auto wrapper = getMotorControllerWrapper(aCanPort);
        auto [slot, value] = ExtractData<int, float>(aBuffer, aLength);
        wrapper->setPGain(slot, value);
    }
    else if ("SetI" == aCallback)
    {
        auto wrapper = getMotorControllerWrapper(aCanPort);
        auto [slot, value] = ExtractData<int, float>(aBuffer, aLength);
        wrapper->setIGain(slot, value);
    }
    else if ("SetFF" == aCallback)
    {
        auto wrapper = getMotorControllerWrapper(aCanPort);
        auto [slot, value] = ExtractData<int, float>(aBuffer, aLength);
        wrapper->setFGain(slot, value);
    }
    else if ("SetSmartMotionMaxVelocity" == aCallback)
    {
        auto wrapper = getMotorControllerWrapper(aCanPort);
        auto [slot, value] = ExtractData<int, float>(aBuffer, aLength);
        wrapper->setMotionMagicMaxVelocity(static_cast<int>(value));
    }
    else if ("SetSmartMotionMaxAccel" == aCallback)
    {
        auto wrapper = getMotorControllerWrapper(aCanPort);
        auto [slot, value] = ExtractData<int, float>(aBuffer, aLength);
        wrapper->setMotionMagicMaxAcceleration(static_cast<int>(value));
    }
    else if ("SetEncoderPosition" == aCallback)
    {
        auto [position] = ExtractData<int>(aBuffer, aLength);
        auto wrapper = getMotorControllerWrapper(aCanPort);
        wrapper->Reset(position, wrapper->GetVelocity(), wrapper->GetCurrent());
    }

    ////////////////////////
    // Getters
    ////////////////////////
    else if ("GetAppliedOutput" == aCallback)
    {
        auto wrapper = getMotorControllerWrapper(aCanPort);

        float speed = static_cast<float>(wrapper->GetVoltagePercentage());
        WriteData(aCallback, aBuffer, aLength, speed);
    }
    else if ("GetEncoderPosition" == aCallback)
    {
        auto wrapper = getMotorControllerWrapper(aCanPort);

        float position = static_cast<float>(wrapper->GetPosition());
        WriteData(aCallback, aBuffer, aLength, position);
    }
    else if ("GetEncoderVelocity" == aCallback)
    {
        auto wrapper = getMotorControllerWrapper(aCanPort);

        float velocity = static_cast<float>(wrapper->GetVelocity());
        WriteData(aCallback, aBuffer, aLength, velocity);
    }
    else
    {
        SNOBOT_LOG(SnobotLogging::LOG_LEVEL_CRITICAL, "Unknown motor callback: " << aCallback);
    }
}
