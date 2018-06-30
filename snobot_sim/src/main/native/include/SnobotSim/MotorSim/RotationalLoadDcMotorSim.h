/*
 * RotationalLoadDcMotorSim.h
 *
 *  Created on: May 9, 2017
 *      Author: preiniger
 */

#ifndef SNOBOTSIM_SNOBOT_SIM_SRC_MAIN_NATIVE_INCLUDE_SNOBOTSIM_MOTORSIM_ROTATIONALLOADDCMOTORSIM_H_
#define SNOBOTSIM_SNOBOT_SIM_SRC_MAIN_NATIVE_INCLUDE_SNOBOTSIM_MOTORSIM_ROTATIONALLOADDCMOTORSIM_H_

#include <memory>

#include "SnobotSim/ModuleWrapper/Interfaces/ISpeedControllerWrapper.h"
#include "SnobotSim/MotorSim/BaseDcMotorSimulator.h"

class EXPORT_ RotationalLoadDcMotorSim : public BaseDcMotorSimulator
{
public:
    RotationalLoadDcMotorSim(const DcMotorModel& aMotorModel, const std::shared_ptr<ISpeedControllerWrapper>& aSpeedController, double aArmCenterOfMass,
            double aArmMass,
            double aConstantAssistTorque, double aOverCenterAssistTorque);
    virtual ~RotationalLoadDcMotorSim();

    void Update(double cycleTime) override;

    double GetArmCenterOfMass();
    double GetArmMass();

protected:
    static const double sGRAVITY;

    const std::shared_ptr<ISpeedControllerWrapper> mSpeedController;
    const double mArmInertia;
    const double mGravityBasedTorqueFactor;

    // Helper springs
    const double mConstantAssistTorque;
    const double mOverCenterAssistTorque;

    const double mArmCenterOfMass;
    const double mArmMass;
};

#endif // SNOBOTSIM_SNOBOT_SIM_SRC_MAIN_NATIVE_INCLUDE_SNOBOTSIM_MOTORSIM_ROTATIONALLOADDCMOTORSIM_H_
