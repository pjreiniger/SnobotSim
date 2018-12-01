/*
 * RobotStateSingleton.hpp
 *
 *  Created on: May 5, 2017
 *      Author: preiniger
 */

#pragma once
#include <chrono>
#include <condition_variable>
#include <mutex>
#include <thread>

#include "SnobotSim/ExportHelper.h"

class EXPORT_ RobotStateSingleton
{
private:
    RobotStateSingleton();
    RobotStateSingleton(const RobotStateSingleton& that) = delete;
    virtual ~RobotStateSingleton();
    static RobotStateSingleton sINSTANCE;

public:
    static RobotStateSingleton& Get();

    void Reset();

    void UpdateLoop();

    double GetMatchTime() const;

    void WaitForNextControlLoop(double aWaitTime);

protected:
    std::chrono::time_point<std::chrono::system_clock> mTimeEnabled;
};
