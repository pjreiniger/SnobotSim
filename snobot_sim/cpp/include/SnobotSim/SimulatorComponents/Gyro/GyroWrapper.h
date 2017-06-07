/*
 * IGyroWrapper.hpp
 *
 *  Created on: May 5, 2017
 *      Author: PJ
 */

#ifndef GYROWRAPPER_HPP_
#define GYROWRAPPER_HPP_

#include "SnobotSim/ModuleWrapper/AModuleWrapper.h"

class EXPORT_ GyroWrapper: public AModuleWrapper
{
public:
    GyroWrapper(const std::string& aName);
    virtual ~GyroWrapper();
    
    void SetAngle(double aAngle);
    
    double GetAngle();
    
protected:
    double mAngle;
};



#endif /* GYROWRAPPER_HPP_ */