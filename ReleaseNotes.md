
# Current Release

## [2019-0.0.1](https://github.com/pjreiniger/SnobotSim/releases/tag/v2019-0.0.1)
### First release of the 2019 build season
* Updated to work with wpilib 2019.1.1
* Updated to work with CTRE-Phoenix 5.12.0
* Updated [examples](https://github.com/pjreiniger/SnobotSimExamples) to work with the kickoff release of VsCode/wpilib/CTRE/NavX
* Requires [2019-0.2.0 Plugin](https://github.com/pjreiniger/SnobotSimPlugin/releases/tag/2019-0.2.0) to use downstream

### Known Issues
* C++ simulations with a gui broke during GradleRIO alpha testing. C++ teams, I know I've said it for a year, but you should be able to use this before seasons end
* CTRE simulation is hot off the presses. Unit tests work, but new features and functions are probably not supported.



# Old releases

## [2019-0.0.0](https://github.com/pjreiniger/SnobotSim/releases/tag/2019-0.0.0)
### 2019 Beta Version
* Updated to work with wpilib 2019
* Added some [example](https://github.com/pjreiniger/SnobotSimExamples) on how to use the simulator. Note: these are a work in progress.

### Important notes
* Requires [2019-0.0.0 Plugin](https://github.com/pjreiniger/SnobotSimPlugin/releases/tag/v2019_0.0.0) to use downstream
* wpilib is still in beta as well. This means that there might be issues in their software which can cause confusion when using the simulator. Also, since the real "full" beta hasn't been released, I don't think you will be able to run this version of wpilib on a real robot. This is meant for simulation purposes only.
* 3rd party libraries (like CTRE and NavX) have not released their 2019 libraries. There might be a lot of things that have to change to work with those once they get released.

## [2018-2.0.0](https://github.com/pjreiniger/SnobotSim/releases/tag/2018-2.0.0)
* Modified the native library loading scheme to help support [HAL Extensions](https://github.com/wpilibsuite/allwpilib/tree/master/simulation/halsim_ds_nt).  Also means that mac builds now work
* Added command line option to disable the driver station simulation.
  * Opens the door to being able to use an extension to talk to the real driver station instead.  This extension is in the works and should be part of the 2019 wpilib release
* Note: Requires the [2018-2.0.0 Plugin](https://github.com/pjreiniger/SnobotSimPlugin/releases/tag/v2018_2.0.0) to use downstream

### Known Issues
#### CTRE
* Voltage and AppliedThrottle control modes are in beta support.  This means that that they *should* work as expected
* MotionMagic, Position, and Velocity control modes are in alpha support.  This means that they *may, sort of, sometimes* work as expected.  They are tested against, but may not anywhere near the same as the real world
* Everything else is unsupported.  Attempting to use other functions will simply log a warning.  For the average team, these features will not hinder the majority of your pre-robot testing

## [2018-1.0.1](https://github.com/pjreiniger/SnobotSim/releases/tag/2018-1.0.1)
* Changed to Year-Sem.Ver.2 versioning system
* Removed the eclipse boilerplate.  All Hail GradlRIO.
* Moved internal sources around to fit into the standard gradle format (src/main/java vs src)
* Externalized CTRE simulator core code.  This should make it easier to pull in different version of the library, and avoid unsatisfied link errors
* Updated the [SnobotSimulatorPlugin](https://plugins.gradle.org/plugin/com.snobot.simulator.plugin.SnobotSimulatorPlugin) to work with the code refactoring.  To use this release, you must use a plugin version of 2018-1.0.0
  * This plugin upgrade allows you to run the simulator from the command line with `gradlew runSnobotSim`
* Updated the configuration file format to know more about the simulator objects.  Should make it easier to create custom overrides.
  * There is a utility that will load the "v0" config file, but it will pop up with a warning.  It is recommended that you re-save your config to force it into the "v1" state
* Simulator components get loaded on startup, rather than dynamically as the robot starts.
  * Note: You might get a popup warning now when you start the simulator.  It loads things first, then checks to make sure they were initialized.  If there is a mismatch, expected things might not show up
* Started preperation to migrate to WPI's 2019 library (while maintaining 2018 support)

### Known Issues
#### CTRE
* Voltage and AppliedThrottle control modes are in beta support.  This means that that they *should* work as expected
* MotionMagic, Position, and Velocity control modes are in alpha support.  This means that they *may, sort of, sometimes* work as expected.  They are tested against, but may not anywhere near the same as the real world
* Everything else is unsupported.  Attempting to use other functions will simply log a warning.  For the average team, these features will not hinder the majority of your pre-robot testing


## [0.8.0](https://github.com/pjreiniger/SnobotSim/releases/tag/0.8.0) (improvments also include 7.1, 7.2, 7.3 because I forgot)
* Fixed issues with match time and the frequency loops were being called
* Added Linux version of the Eclipse Boilerplate.  Due to compilation flags being different on build machines vs. your machine, your miliage might vary
* CTRE 5.2.1.1
* Added static analysis tools to the build process (PMD, checkstyle, fixbugs)

## [0.7.0](https://github.com/pjreiniger/SnobotSim/releases/tag/0.7.0)
First release of the 2018 Season.  Anything releases before this were experimental and not recommended for usage.

### Known Issues
#### CTRE
* Voltage and AppliedThrottle control modes are in beta support.  This means that that they *should* work as expected
* MotionMagic, Position, and Velocity control modes are in alpha support.  This means that they *may, sort of, sometimes* work as expected.  They are tested against, but may not anywhere near the same as the real world
* Everything else is unsupported.  Attempting to use other functions will simply log a warning.  For the average team, these features will not hinder the majority of your pre-robot testing

#### Simulator
* Tank drive simulator, I2C, and SPI built in simulations do not have GUI support, so you must update the config file manually
