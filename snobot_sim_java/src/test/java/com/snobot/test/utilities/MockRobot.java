package com.snobot.test.utilities;

import edu.wpi.first.wpilibj.ADXL345_I2C;
import edu.wpi.first.wpilibj.ADXL345_SPI;
import edu.wpi.first.wpilibj.ADXL362;
import edu.wpi.first.wpilibj.AnalogAccelerometer;
import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.AnalogOutput;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.SD540;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.interfaces.Accelerometer.Range;

public class MockRobot extends IterativeRobot
{
    // Tank Drive simulator
    private Encoder mRightEncoder;
    private Encoder mLeftEncoder;
    private SpeedController mRightMotor;
    private SpeedController mLeftMotor;

    // Instantiate as many usefull classes as possible

    private final ADXL345_I2C mAdxl345I2cAccel;
    private final ADXL345_SPI mAdxl345SpiAccel;
    private final ADXL362 mAdxl362SpiAccel;
    // private final ADXRS450_Gyro mAdxr450SpiGyro0;
    // private final ADXRS450_Gyro mAdxr450SpiGyro1;
    private final AnalogAccelerometer mAnalogAccel;
    // private final AnalogInput mAnalogIn;
    private final AnalogGyro mAnalogGyro;
    private final AnalogOutput mAnalogOutput;
    private final AnalogPotentiometer mAnalogPot;
    private final DigitalInput mDigitalInput;
    private final DigitalOutput mDigitalOutput;
    private final DoubleSolenoid mDoubleSolenoid;
    private final Encoder mEncoder;
    private final Jaguar mJaguar;
    private final Joystick mJoystick;
    private final Relay mRelay;
    private final Servo mServo;
    private final SD540 mSd540;
    private final Solenoid mSolenoid;
    private final Talon mTalon;
    private final Timer mTimer;
    private final Ultrasonic mUltrasonic;
    private final Victor mVictor;
    private final VictorSP mVictorSp;

    public MockRobot()
    {
        // Tank Drive
        mRightEncoder = new Encoder(0, 1);
        mLeftEncoder = new Encoder(2, 3);

        mRightMotor = new Talon(0);
        mLeftMotor = new Jaguar(1);

        // Everything else
        mAdxl345I2cAccel = new ADXL345_I2C(I2C.Port.kOnboard, Range.k2G);
        // mAdxr450SpiGyro0 = new ADXRS450_Gyro();
        mAdxl345SpiAccel = new ADXL345_SPI(SPI.Port.kOnboardCS1, Range.k2G);
        mAdxl362SpiAccel = new ADXL362(SPI.Port.kOnboardCS2, Range.k2G);
        // mAdxr450SpiGyro1 = new ADXRS450_Gyro(SPI.Port.kOnboardCS3);
        mAnalogAccel = new AnalogAccelerometer(2);
        // mAnalogIn = new AnalogInput(0);
        mAnalogGyro = new AnalogGyro(1);
        mAnalogOutput = new AnalogOutput(1);
        mAnalogPot = new AnalogPotentiometer(0);
        mDigitalInput = new DigitalInput(4);
        mDigitalOutput = new DigitalOutput(5);
        mDoubleSolenoid = new DoubleSolenoid(0, 1);
        mEncoder = new Encoder(6, 7);
        mJaguar = new Jaguar(2);
        mJoystick = new Joystick(0);
        mRelay = new Relay(0);
        mServo = new Servo(3);
        mSd540 = new SD540(4);
        mSolenoid = new Solenoid(3);
        mTalon = new Talon(5);
        mTimer = new Timer();
        mUltrasonic = new Ultrasonic(8, 9);
        mVictor = new Victor(7);
        mVictorSp = new VictorSP(8);
    }
}
