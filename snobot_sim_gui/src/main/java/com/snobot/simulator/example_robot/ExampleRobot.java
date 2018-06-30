package com.snobot.simulator.example_robot;

import com.ctre.phoenix.sensors.PigeonIMU;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.ADXL345_I2C;
import edu.wpi.first.wpilibj.ADXL345_SPI;
import edu.wpi.first.wpilibj.ADXL362;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Example robot used in the event that no robot to simulate is specified. Does
 * a simple simulation by hooking up a few commonly used components
 *
 * @author PJ
 *
 */
public class ExampleRobot extends IterativeRobot
{
    public Joystick mJoystick;
    public Solenoid mSolenoid;
    public SpeedController mLeftDrive;
    public SpeedController mRightDrive;
    public Encoder mLeftDriveEncoder;
    public Encoder mRightDriveEncoder;
    public Gyro mAnalogGyro;
    public AnalogInput mAnalogInput;

    public ADXL345_I2C mAdx345I2CAccelerometer;
    public ADXL345_SPI mAdx345SpiAccelerometer;
    public ADXL362 mAdx362SpiAccelerometer;
    public ADXRS450_Gyro mAdxSpiGyro;
    public AHRS mNavx;
    public PigeonIMU mPigeon;

    public Timer mAutoTimer;

    @Override
    public void robotInit()
    {
        mJoystick = new Joystick(0);

        mSolenoid = new Solenoid(0);
        mLeftDrive = new VictorSP(0);
        mRightDrive = new VictorSP(1);
        mLeftDriveEncoder = new Encoder(0, 1);
        mRightDriveEncoder = new Encoder(2, 3);

        mLeftDriveEncoder.setDistancePerPulse(.01);
        mRightDriveEncoder.setDistancePerPulse(.01);

        mAnalogGyro = new AnalogGyro(0);
        mAnalogInput = new AnalogInput(1);

//        mAdx345I2CAccelerometer = new ADXL345_I2C(I2C.Port.kMXP, Accelerometer.Range.k2G);
//        mAdx345SpiAccelerometer = new ADXL345_SPI(SPI.Port.kOnboardCS1, Accelerometer.Range.k2G);
//        mAdx362SpiAccelerometer = new ADXL362(SPI.Port.kOnboardCS2, Accelerometer.Range.k2G);
//        mAdxSpiGyro = new ADXRS450_Gyro();
//
//        mNavx = new AHRS(SPI.Port.kOnboardCS3);
//        mPigeon = new PigeonIMU(10);

        mAutoTimer = new Timer();

        String errorMessage = "Warning, this is the example robot bundled with the simulator!\n";
        errorMessage += "To configure this for your robot, change <project_dir>/simulator_config/simulator_config.properties, and update the robot_class field"; // NOPMD

        System.err.println(errorMessage); // NOPMD
    }

    @Override
    public void autonomousInit()
    {
        mLeftDriveEncoder.reset();
        mRightDriveEncoder.reset();
        mAutoTimer.start();

        System.out.println("Game Information: "); // NOPMD
        System.out.println("  Match Number : " + DriverStation.getInstance().getMatchNumber()); // NOPMD
        System.out.println("  Match Replay : " + DriverStation.getInstance().getReplayNumber()); // NOPMD
        System.out.println("  Match Type   : " + DriverStation.getInstance().getMatchType()); // NOPMD
        System.out.println("  Event Name   : " + DriverStation.getInstance().getEventName()); // NOPMD
        System.out.println("  Game Info    : " + DriverStation.getInstance().getGameSpecificMessage()); // NOPMD
    }

    @Override
    public void autonomousPeriodic()
    {
        if (mAutoTimer.get() < 2)
        {
            mLeftDrive.set(1);
            mRightDrive.set(-1);
        }
        else
        {
            mLeftDrive.set(0);
            mRightDrive.set(0);
        }
    }

    @Override
    public void teleopPeriodic()
    {
        mLeftDrive.set(mJoystick.getRawAxis(0));
        mRightDrive.set(-mJoystick.getRawAxis(0));

        mSolenoid.set(mJoystick.getRawButton(1));

        SmartDashboard.putNumber("Left Enc", mLeftDriveEncoder.getDistance());
        SmartDashboard.putNumber("Right Enc", mRightDriveEncoder.getDistance());
        SmartDashboard.putNumber("Gyro", mAnalogGyro.getAngle());
    }

}
