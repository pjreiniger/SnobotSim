package com.snobot.simulator.simulator_components.ctre;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.snobot.simulator.motor_sim.DcMotorModelConfig;
import com.snobot.simulator.motor_sim.StaticLoadMotorSimulationConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.snobot.simulator.simulator_components.smart_sc.SmartScAnalogIn;
import com.snobot.simulator.simulator_components.smart_sc.SmartScEncoder;
import com.snobot.simulator.wrapper_accessors.DataAccessorFactory;
import com.snobot.test.utilities.BaseSimulatorJavaTest;

@Tag("CTRE")
public class TestCtreCanTalon extends BaseSimulatorJavaTest
{

    @Test
    public void testSetup()
    {
        Assertions.assertEquals(0, DataAccessorFactory.getInstance().getSpeedControllerAccessor().getPortList().size());
        Assertions.assertEquals(0, DataAccessorFactory.getInstance().getEncoderAccessor().getPortList().size());

        TalonSRX talon1 = new TalonSRX(1);
        talon1.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
        Assertions.assertEquals(1, DataAccessorFactory.getInstance().getSpeedControllerAccessor().getPortList().size());
        Assertions.assertEquals(1, DataAccessorFactory.getInstance().getEncoderAccessor().getPortList().size());
        Assertions.assertTrue(DataAccessorFactory.getInstance().getSpeedControllerAccessor().isInitialized(101));
        Assertions.assertTrue(DataAccessorFactory.getInstance().getEncoderAccessor().isInitialized(101));

        DataAccessorFactory.getInstance().getSpeedControllerAccessor().createSimulator(102, CtreTalonSrxSpeedControllerSim.class.getName());
        DataAccessorFactory.getInstance().getEncoderAccessor().createSimulator(102, SmartScEncoder.class.getName());
        Assertions.assertFalse(DataAccessorFactory.getInstance().getSpeedControllerAccessor().isInitialized(102));
        TalonSRX talon2 = new TalonSRX(2);
        talon2.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
        Assertions.assertTrue(DataAccessorFactory.getInstance().getSpeedControllerAccessor().isInitialized(102));
        Assertions.assertTrue(DataAccessorFactory.getInstance().getEncoderAccessor().isInitialized(102));
    }

    @ParameterizedTest
    @ArgumentsSource(GetCtreTestIds.GetCtreTestIdsFeedbackDevice.class)
    public void testSimpleSetters(int aCanHandle)
    {
        Assertions.assertEquals(0, DataAccessorFactory.getInstance().getSpeedControllerAccessor().getPortList().size());

        TalonSRX talon = new TalonSRX(aCanHandle);
        Assertions.assertEquals(1, DataAccessorFactory.getInstance().getSpeedControllerAccessor().getPortList().size());

        Assertions.assertEquals("CTRE SC " + aCanHandle,
                DataAccessorFactory.getInstance().getSpeedControllerAccessor().getName(aCanHandle + CtreTalonSrxSpeedControllerSim.sCAN_SC_OFFSET));

        talon.config_kP(0, 0, 0);
//        //////////////////
//        // PID
//        /////////////////
//        Assertions.assertEquals(0, talon.configGetParameter(ParamEnum.f, arg1, arg2)(), DOUBLE_EPSILON);
//        Assertions.assertEquals(0, talon.getI(), DOUBLE_EPSILON);
//        Assertions.assertEquals(0, talon.getD(), DOUBLE_EPSILON);
//        Assertions.assertEquals(0, talon.getF(), DOUBLE_EPSILON);
//        Assertions.assertEquals(0, talon.getIZone(), DOUBLE_EPSILON);
//
//        talon.setProfile(0);
//        talon.setP(.5);
//        talon.setI(1.5);
//        talon.setD(100.7890);
//        talon.setF(-9.485);
//        talon.setIZone(40);
//
//        Assertions.assertEquals(.5, talon.getP(), DOUBLE_EPSILON);
//        Assertions.assertEquals(1.5, talon.getI(), DOUBLE_EPSILON);
//        Assertions.assertEquals(100.7890, talon.getD(), DOUBLE_EPSILON);
//        Assertions.assertEquals(-9.485, talon.getF(), DOUBLE_EPSILON);
//        Assertions.assertEquals(40, talon.getIZone(), DOUBLE_EPSILON);
//
//        talon.setProfile(1);
//        talon.setP(.231);
//        talon.setI(1.634);
//        talon.setD(90.7890);
//        talon.setF(-8.485);
//        talon.setIZone(20);
//
//        Assertions.assertEquals(.231, talon.getP(), DOUBLE_EPSILON);
//        Assertions.assertEquals(1.634, talon.getI(), DOUBLE_EPSILON);
//        Assertions.assertEquals(90.7890, talon.getD(), DOUBLE_EPSILON);
//        Assertions.assertEquals(-8.485, talon.getF(), DOUBLE_EPSILON);
//        Assertions.assertEquals(20, talon.getIZone(), DOUBLE_EPSILON);
//
//        Assertions.assertEquals(30, talon.getTemperature(), DOUBLE_EPSILON);
//        Assertions.assertEquals(12, talon.getBusVoltage(), DOUBLE_EPSILON);
    }

    @Test
    public void testEncoderSensorSetup()
    {
        int canId = 6;
        int simId = canId + 100;
        TalonSRX talon = new TalonSRX(canId);

        Assertions.assertEquals(0, DataAccessorFactory.getInstance().getEncoderAccessor().getPortList().size());
        Assertions.assertTrue(DataAccessorFactory.getInstance().getEncoderAccessor().createSimulator(simId, SmartScEncoder.class.getName()));
        Assertions.assertFalse(DataAccessorFactory.getInstance().getEncoderAccessor().isInitialized(simId));

        talon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);
        Assertions.assertEquals(1, DataAccessorFactory.getInstance().getEncoderAccessor().getPortList().size());
        Assertions.assertTrue(DataAccessorFactory.getInstance().getEncoderAccessor().isInitialized(simId));
    }

    @Test
    public void testAnalogSensorSetup()
    {
        int canId = 6;
        int simId = canId + 100;
        TalonSRX talon = new TalonSRX(canId);

        Assertions.assertEquals(0, DataAccessorFactory.getInstance().getAnalogInAccessor().getPortList().size());
        Assertions.assertTrue(DataAccessorFactory.getInstance().getAnalogInAccessor().createSimulator(simId, SmartScAnalogIn.class.getName()));
        Assertions.assertFalse(DataAccessorFactory.getInstance().getAnalogInAccessor().isInitialized(simId));

        talon.configSelectedFeedbackSensor(FeedbackDevice.Analog);
        Assertions.assertEquals(1, DataAccessorFactory.getInstance().getAnalogInAccessor().getPortList().size());
        Assertions.assertTrue(DataAccessorFactory.getInstance().getAnalogInAccessor().isInitialized(simId));
    }

    @Test
    public void testSwitchControlModes()
    {
        int canId = 6;
        int simId = canId + 100;
        TalonSRX talon = new TalonSRX(canId);

        // Simulate CIM drivetrain
        DcMotorModelConfig motorConfig = DataAccessorFactory.getInstance().getSimulatorDataAccessor().createMotor("CIM", 1, 10, 1);
        Assertions.assertTrue(DataAccessorFactory.getInstance().getSimulatorDataAccessor().setSpeedControllerModel_Static(simId, motorConfig,
            new StaticLoadMotorSimulationConfig(.2)));

        talon.config_kP(0, .045, 5);
        talon.config_kF(0, .018, 5);
        talon.config_IntegralZone(0, 1, 5);

        talon.set(ControlMode.Velocity, 40);

        simulateForTime(1, () ->
        {
        });

        Assertions.assertEquals(40, DataAccessorFactory.getInstance().getSpeedControllerAccessor().getVelocity(simId), 1);
        Assertions.assertEquals(40, talon.getSelectedSensorVelocity(0) / 600.0, 1);
        Assertions.assertEquals(0.720454097, DataAccessorFactory.getInstance().getSpeedControllerAccessor().getVoltagePercentage(simId), .0001);
        Assertions.assertEquals(0.720454097, talon.getMotorOutputPercent(), .0001);

        talon.set(ControlMode.PercentOutput, .5);
        simulateForTime(1, () ->
        {
        });
        Assertions.assertEquals(.5, DataAccessorFactory.getInstance().getSpeedControllerAccessor().getVoltagePercentage(simId), .0001);
        Assertions.assertEquals(.5, talon.getMotorOutputPercent(), .0001);

    }
}
