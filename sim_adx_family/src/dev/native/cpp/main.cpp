/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

#include <hal/HAL.h>

#include <iostream>

#include "ADXRS450_SpiGyroWrapperData.h"

int main()
{
    HAL_Initialize(500, 0);
    hal::ADXRS450_SpiGyroWrapper sim{ 1 };
    std::cout << "Hello" << std::endl;
    return 0;
}
