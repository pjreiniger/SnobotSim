/*
 * NavxSimulator.h
 *
 *  Created on: Jul 8, 2017
 *      Author: PJ
 */

#ifndef SNOBOTSIM_SIM_EXTENSION_NAVX_SRC_MAIN_NATIVE_INCLUDE_NAVXSIM_NAVXSIMULATOR_H_
#define SNOBOTSIM_SIM_EXTENSION_NAVX_SRC_MAIN_NATIVE_INCLUDE_NAVXSIM_NAVXSIMULATOR_H_

#include <atomic>
#include <cstring>
#include <memory>

#include "wpi/mutex.h"

#ifdef _MSC_VER
#define EXPORT_ __declspec(dllexport)
#else
#define EXPORT_
#endif

class EXPORT_ NavxSimulator
{
public:
    NavxSimulator();
    virtual ~NavxSimulator();

    virtual void ResetData();

    double GetX();
    void SetX(double x);

    double GetY();
    void SetY(double y);

    double GetZ();
    void SetZ(double z);

    double GetYaw();
    void SetYaw(double yaw);

    double GetPitch();
    void SetPitch(double pitch);

    double GetRoll();
    void SetRoll(double roll);

protected:
    template <typename Value>
    void PutTheValue(uint8_t* aBuffer, int aPosition, const Value& value, int aSize)
    {
        std::memcpy(&aBuffer[aPosition], &value, aSize);
    }

    template <typename Type>
    Type BoundBetweenNeg180Pos180(Type input)
    {
        Type output = input;
        while (output > 180)
        {
            output -= 360;
        }
        while (output < -180)
        {
            output += 360;
        }

        return output;
    }

    void GetCurrentData(uint8_t* aBuffer, int aFirstAddress);
    void GetWriteConfig(uint8_t* aBuffer);

    std::mutex m_registerMutex;

    std::atomic<double> mX{ 0.0 };
    std::atomic<double> mY{ 0.0 };
    std::atomic<double> mZ{ 0.0 };

    std::atomic<double> mYaw{ 0.0 };
    std::atomic<double> mPitch{ 0.0 };
    std::atomic<double> mRoll{ 0.0 };
};

#endif // SNOBOTSIM_SIM_EXTENSION_NAVX_SRC_MAIN_NATIVE_INCLUDE_NAVXSIM_NAVXSIMULATOR_H_
