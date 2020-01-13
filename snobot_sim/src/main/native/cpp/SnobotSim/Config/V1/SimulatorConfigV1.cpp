
#include "SnobotSim/Config/SimulatorConfigV1.h"

#include <iostream>

template <typename T>
void PrintVector(std::ostream& aStream, const std::string& aName, std::vector<T>& aVector)
{
    aStream << aName << "\n";
    for (unsigned int i = 0; i < aVector.size(); ++i)
    {
        aVector[i].Print(aStream, "  ");
    }
}

void BasicModuleConfig::Print(std::ostream& aStream, const std::string& aIndent)
{
    aStream << aIndent << "Handle: " << mHandle << "\n"
            << aIndent << "Name  : " << mName << "\n"
            << aIndent << "Type  : " << mType << "\n";
}

void EncoderConfig::Print(std::ostream& aStream, const std::string& aIndent)
{
    BasicModuleConfig::Print(aStream, aIndent);
}

void PwmConfig::Print(std::ostream& aStream, const std::string& aIndent)
{
    BasicModuleConfig::Print(aStream, aIndent);
}

void SimulatorConfigV1::Print(std::ostream& aStream)
{
    PrintVector(aStream, "Accel", mAccelerometers);
    PrintVector(aStream, "Analog In", mAnalogIn);
    PrintVector(aStream, "Analog Out", mAnalogOut);
    PrintVector(aStream, "Digital IO", mDigitalIO);
    PrintVector(aStream, "Gyro", mGyros);
    PrintVector(aStream, "Relay", mRelays);
    PrintVector(aStream, "Solenoid", mSolenoids);
    PrintVector(aStream, "Encoders", mEncoders);
    PrintVector(aStream, "PWM", mPwm);
}
