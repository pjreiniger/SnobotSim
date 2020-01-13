
#pragma once

#include <map>
#include <string>
#include <vector>

struct BasicModuleConfig
{
    int mHandle = 0;
    std::string mName = "";
    std::string mType = "";

    virtual void Print(std::ostream& aStream, const std::string& aIndent = "");
};

struct EncoderConfig : public BasicModuleConfig
{
    int mConnectedSpeedControllerHandle = -1;

    void Print(std::ostream& aStream, const std::string& aIndent = "") override;
};

struct SimpleMotorSimulationConfig
{
    double mMaxSpeed;
};

struct StaticLoadMotorSimulationConfig
{
    double mLoad;
    double mConversionFactor;
};

struct GravityLoadMotorSimulationConfig
{
    double mLoad;
};

struct DcMotorModelConfigConfig
{
    struct FactoryParams
    {
        std::string mMotorType;
        int mNumMotors;
        double mGearReduction;
        double mGearboxEfficiency;

        bool mInverted;
        bool mHasBrake;
    };

    struct MotorParams
    {
        double mNominalVoltage;
        double mFreeSpeedRpm;
        double mFreeCurrent;
        double mStallTorque;
        double mStallCurrent;
        double mMotorInertia;
    };

    FactoryParams mFactoryParams;
    MotorParams mMotorParams;
};

struct RotationalLoadMotorSimulationConfig
{
    double mArmCenterOfMass;
    double mArmMass;
    double mConstantAssistTorque;
    double mOverCenterAssistTorque;
};

struct PwmConfig : public BasicModuleConfig
{
    union MotorSimConfig {
        SimpleMotorSimulationConfig mSimple;
        StaticLoadMotorSimulationConfig mStatic;
        GravityLoadMotorSimulationConfig mGravity;
        RotationalLoadMotorSimulationConfig mRotational;
    };

    enum MotorSimConfigType
    {
        None,
        Simple,
        Static,
        Gravity,
        Rotational,
    };

    MotorSimConfig mMotorSimConfig;
    MotorSimConfigType mMotorSimConfigType = None;
    DcMotorModelConfigConfig mMotorModelConfig;

    void Print(std::ostream& aStream, const std::string& aIndent = "") override;
};

struct SimulatorConfigV1
{
    std::vector<BasicModuleConfig> mAccelerometers;
    std::vector<BasicModuleConfig> mAnalogIn;
    std::vector<BasicModuleConfig> mAnalogOut;
    std::vector<BasicModuleConfig> mDigitalIO;
    std::vector<BasicModuleConfig> mGyros;
    std::vector<BasicModuleConfig> mRelays;
    std::vector<BasicModuleConfig> mSolenoids;
    std::vector<EncoderConfig> mEncoders;
    std::vector<PwmConfig> mPwm;
    std::map<int, std::string> mDefaultI2CWrappers;
    std::map<int, std::string> mDefaultSpiWrappers;

    virtual void Print(std::ostream& aStream);
};
