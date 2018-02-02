

[![Appveyor Build status](https://ci.appveyor.com/api/projects/status/wsc0xo7ix749ibuo?svg=true)](https://ci.appveyor.com/project/pjreiniger/snobotsim)
[![Travis Build Status](https://travis-ci.org/pjreiniger/SnobotSim.svg?branch=master)](https://travis-ci.org/pjreiniger/SnobotSim)
[![codecov](https://codecov.io/gh/pjreiniger/SnobotSim/branch/master/graph/badge.svg)](https://codecov.io/gh/pjreiniger/SnobotSim)

# SnobotSim

[![Join the chat at https://gitter.im/SnobotSim/Lobby](https://badges.gitter.im/SnobotSim/Lobby.svg)](https://gitter.im/SnobotSim/Lobby?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)
SnobotSim is a java-based simulator that can simulate FRC Robots written in Java, with incubating support for C++ and Python.

Check out the [Release Notes](ReleaseNotes.md) to keep up with all the updates and bug fixes going on througout the season

## Features
* Simple physics based motor simulations
* Feedback Sensor simulation (AnalogIO, DigitalIO, Encoders, Gyros)
* [NavX Simulation](https://www.kauailabs.com/store/index.php?route=product/product&product_id=56) for the 3-axis Gyro and 3-axis Accelerometers
* [CTRE CanTalon](http://www.ctr-electronics.com/talon-srx.html) and [Pigeon IMU](http://www.ctr-electronics.com/gadgeteer-imu-module-pigeon.html) simulations.  This is currently built against the 5.0.9.0 release with several functions unsupported in simulation.  It covers most of the control modes, but without something to test agains your miliage might vary.
* Open to extension for custom I2C, SPI, Potentiometer, Motors servo'd by limit switches, etc.  Hopefully anything you need!

## Contributing
If you need any help with simulator, check the [wiki](https://github.com/pjreiniger/SnobotSim/wiki).  If that doesn't help, DM me on ChiefDelphi, `pjreiniger`

If something doesn't seem like its working, create an issue here and I will try to make a patch for you

If you find a bug you can fix, don't like how I did something, or want to add an improvment, create a Pull Request and help make the project better.

## Documentation
Check out the [wiki](https://github.com/pjreiniger/SnobotSim/wiki) for how to set up and use the simulator.