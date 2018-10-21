/*
 * NavxSimulator.cpp
 *
 *  Created on: Jul 8, 2017
 *      Author: PJ
 */

#include "NavxSim/NavxSimulator.h"

NavxSimulator::NavxSimulator()
{
}

NavxSimulator::~NavxSimulator()
{
}

void NavxSimulator::ResetData()
{
    mX = 0.0;
    mY = 0.0;
    mZ = 0.0;

    mYaw = 0.0;
    mPitch = 0.0;
    mRoll = 0.0;
}

void NavxSimulator::GetWriteConfig(uint8_t* aBuffer)
{
    aBuffer[0] = 0x01; // Type
    aBuffer[1] = 0x02; // HW rev
    aBuffer[2] = 0x03; // FW Major
    aBuffer[3] = 0x04; // FW Minor
    aBuffer[4] = 0x05; // Update Rate
    aBuffer[5] = 0x06; // Accel g
    aBuffer[6] = 0x07; // Gyro DPS
    aBuffer[7] = 0x08; // Gyro DPS
    aBuffer[8] = 0x09; // Op Status
    aBuffer[9] = 0x10; // Cal Status
    aBuffer[10] = 0x11; // Self Test Status
    aBuffer[11] = 0x12; // Capability Flags
    aBuffer[12] = 0x13; // Capability Flags
    aBuffer[16] = 0x14; // Sensor Status
    aBuffer[17] = 0x15; // Sensor Status
}

void NavxSimulator::GetCurrentData(uint8_t* aBuffer, int aFirstAddress)
{
    static uint32_t timestamp = 1;

    PutTheValue(aBuffer, 0x12 - aFirstAddress, timestamp, 4); // Op Status
    ++timestamp;

    PutTheValue(aBuffer, 0x08 - aFirstAddress, 1, 1); // Op Status
    PutTheValue(aBuffer, 0x0A - aFirstAddress, 2, 1); // Self Test
    PutTheValue(aBuffer, 0x09 - aFirstAddress, 3, 1); // Cal Status
    PutTheValue(aBuffer, 0x10 - aFirstAddress, 4, 1); // Sensor Status

    double yaw = mYaw;
    double pitch = mPitch;
    double roll = mRoll;

    yaw = BoundBetweenNeg180Pos180(yaw);
    pitch = BoundBetweenNeg180Pos180(pitch);
    roll = BoundBetweenNeg180Pos180(roll);

    PutTheValue(aBuffer, 0x16 - aFirstAddress, int16_t(yaw * 100), 2); // Yaw
    PutTheValue(aBuffer, 0x1A - aFirstAddress, int16_t(pitch * 100), 2); // Pitch
    PutTheValue(aBuffer, 0x18 - aFirstAddress, int16_t(roll * 100), 2); // Roll
    //    PutTheValue(aBuffer, 0x1C - aFirstAddress, uint16_t(0 * 100), 2); // Heading
    //    PutTheValue(aBuffer, 0x32 - aFirstAddress, int16_t(0 * 100), 2); // Temperature
    //    PutTheValue(aBuffer, 0x24 - aFirstAddress, int16_t(mX * 1000), 2); // Linear Accel X
    //    PutTheValue(aBuffer, 0x26 - aFirstAddress, int16_t(mY * 1000), 2); // Linear Accel Y
    //    PutTheValue(aBuffer, 0x28 - aFirstAddress, int16_t(mZ * 1000), 2); // Linear Accel Z
}

double NavxSimulator::GetX()
{
    return mX;
}

void NavxSimulator::SetX(double x)
{
    mX.exchange(x);
}

double NavxSimulator::GetY()
{
    return mY;
}

void NavxSimulator::SetY(double y)
{
    mY.exchange(y);
}

double NavxSimulator::GetZ()
{
    return mZ;
}

void NavxSimulator::SetZ(double z)
{
    mZ.exchange(z);
}

double NavxSimulator::GetYaw()
{
    return mYaw;
}

void NavxSimulator::SetYaw(double yaw)
{
    mYaw.exchange(yaw);
}

double NavxSimulator::GetPitch()
{
    return mPitch;
}

void NavxSimulator::SetPitch(double pitch)
{
    mPitch.exchange(pitch);
}

double NavxSimulator::GetRoll()
{
    return mRoll;
}

void NavxSimulator::SetRoll(double roll)
{
    mRoll.exchange(roll);
}
