
#include "SnobotSim/SimulatorComponents/CtreWrappers/CtrePigeonImuSim.h"

#include "SnobotSim/SensorActuatorRegistry.h"

namespace
{

class PigeonGyroWrapper : public AModuleWrapper, public IGyroWrapper
{
public:
    double mAngle{ 0 };
    double mAngleOffset{ 0 };

    static const std::string TYPE;
    std::string GetType() override
    {
        return TYPE;
    }

    explicit PigeonGyroWrapper(const std::string& aName) :
            AModuleWrapper(aName)
    {
    }

    // public void setDesiredYaw(double aOffset)
    // {
    //     mAngleOffset = aOffset - mAngle;
    // }

    double GetAngle() override
    {
        return mAngle + mAngleOffset;
    }

    void SetAngle(double aAngle) override
    {
        mAngle = aAngle;
    }
};

class PigeonAccelWrapper : public AModuleWrapper, public IAccelerometerWrapper
{
public:
    double mAccel{ 0 };

    explicit PigeonAccelWrapper(const std::string& aName) :
            AModuleWrapper(aName)
    {
    }

    double GetAcceleration() override
    {
        return mAccel;
    }

    void SetAcceleration(double aAccel) override
    {
        mAccel = aAccel;
    }
    static const std::string TYPE;
    std::string GetType() override
    {
        return TYPE;
    }
};

const std::string PigeonGyroWrapper::TYPE = "PigeonGyro";
const std::string PigeonAccelWrapper::TYPE = "PigeonAccel";

} // namespace

const std::string CtrePigeonImuSim::TYPE = "Pigeon";

CtrePigeonImuSim::CtrePigeonImuSim(int aBasePort) :
        AModuleWrapper("Pigeon IMU"),

        mYaw(new PigeonGyroWrapper("Pigeon Yaw")),
        mPitch(new PigeonGyroWrapper("Pigeon Pitch")),
        mRoll(new PigeonGyroWrapper("Pigeon Roll")),
        mX(new PigeonAccelWrapper("Pigeon X")),
        mY(new PigeonAccelWrapper("Pigeon Y")),
        mZ(new PigeonAccelWrapper("Pigeon Z"))

{
    SensorActuatorRegistry::Get().Register(aBasePort + 0, mX);
    SensorActuatorRegistry::Get().Register(aBasePort + 1, mY);
    SensorActuatorRegistry::Get().Register(aBasePort + 2, mZ);

    SensorActuatorRegistry::Get().Register(aBasePort + 0, mYaw);
    SensorActuatorRegistry::Get().Register(aBasePort + 1, mPitch);
    SensorActuatorRegistry::Get().Register(aBasePort + 2, mRoll);
}

void CtrePigeonImuSim::SetInitialized(bool aIsInitialized)
{
    AModuleWrapper::SetInitialized(aIsInitialized);

    mX->SetInitialized(aIsInitialized);
    mY->SetInitialized(aIsInitialized);
    mZ->SetInitialized(aIsInitialized);

    mYaw->SetInitialized(aIsInitialized);
    mPitch->SetInitialized(aIsInitialized);
    mRoll->SetInitialized(aIsInitialized);
}

std::shared_ptr<IGyroWrapper> CtrePigeonImuSim::getYawWrapper()
{
    return mYaw;
}
std::shared_ptr<IGyroWrapper> CtrePigeonImuSim::getPitchWrapper()
{
    return mPitch;
}
std::shared_ptr<IGyroWrapper> CtrePigeonImuSim::getRollWrapper()
{
    return mRoll;
}

std::shared_ptr<IAccelerometerWrapper> CtrePigeonImuSim::getXWrapper()
{
    return mX;
}
std::shared_ptr<IAccelerometerWrapper> CtrePigeonImuSim::getYWrapper()
{
    return mY;
}
std::shared_ptr<IAccelerometerWrapper> CtrePigeonImuSim::getZWrapper()
{
    return mZ;
}
