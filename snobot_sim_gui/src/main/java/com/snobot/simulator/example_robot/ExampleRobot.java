package com.snobot.simulator.example_robot;

import edu.wpi.first.wpilibj.ADXL345_I2C;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.interfaces.Accelerometer;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Example robot used in the event that no robot to simulate is specified. Does
 * a simple simulation by hooking up a few commonly used components
 *
 * @author PJ
 *
 */
public class ExampleRobot extends TimedRobot
{
    private XboxController mJoystick;
    private Solenoid mSolenoid;
    private SpeedController mLeftDrive;
    private SpeedController mRightDrive;
    private Encoder mLeftDriveEncoder;
    private Encoder mRightDriveEncoder;
    private Gyro mAnalogGyro;
    private Gyro mSpiGyro;
    private ADXL345_I2C mAdxAccelerometer;

    private Timer mAutoTimer;

    @Override
    public void robotInit()
    {
        mJoystick = new XboxController(0);

        mSolenoid = new Solenoid(0);
        mLeftDrive = new VictorSP(0);
        mRightDrive = new VictorSP(1);
        mLeftDriveEncoder = new Encoder(0, 1);
        mRightDriveEncoder = new Encoder(2, 3);

        mLeftDriveEncoder.setDistancePerPulse(.01);
        mRightDriveEncoder.setDistancePerPulse(.01);

        mAnalogGyro = new AnalogGyro(0);
        mSpiGyro = new ADXRS450_Gyro();
        mAdxAccelerometer = new ADXL345_I2C(I2C.Port.kMXP, Accelerometer.Range.k2G);

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
        mLeftDrive.set(mJoystick.getY(GenericHID.Hand.kLeft));
        mRightDrive.set(-mJoystick.getY(GenericHID.Hand.kRight));

        mSolenoid.set(mJoystick.getRawButton(1));

        SmartDashboard.putNumber("Left Enc", mLeftDriveEncoder.getDistance());
        SmartDashboard.putNumber("Right Enc", mRightDriveEncoder.getDistance());
        SmartDashboard.putNumber("Analog Gyro", mAnalogGyro.getAngle());
        SmartDashboard.putNumber("SPI Gyro", mSpiGyro.getAngle());
        SmartDashboard.putNumber("I2C Accelerometer", mAdxAccelerometer.getX());
    }

    @Override
    public void disabledPeriodic()
    {
        // Nothing to do
    }

}
