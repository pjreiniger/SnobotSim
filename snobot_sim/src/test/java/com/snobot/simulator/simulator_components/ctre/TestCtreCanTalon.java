package com.snobot.simulator.simulator_components.ctre;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.snobot.simulator.module_wrapper.interfaces.IAnalogInWrapper;
import com.snobot.simulator.module_wrapper.interfaces.IEncoderWrapper;
import com.snobot.simulator.module_wrapper.interfaces.IPwmWrapper;
import com.snobot.simulator.motor_sim.DcMotorModelConfig;
import com.snobot.simulator.motor_sim.StaticLoadMotorSimulationConfig;
import com.snobot.simulator.wrapper_accessors.AnalogInWrapperAccessor;
import com.snobot.simulator.wrapper_accessors.EncoderWrapperAccessor;
import com.snobot.simulator.wrapper_accessors.SpeedControllerWrapperAccessor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.snobot.simulator.wrapper_accessors.DataAccessorFactory;
import com.snobot.simulator.wrapper_accessors.jni.JniSpeedControllerWrapperAccessor;
import com.snobot.test.utilities.BaseSimulatorJniTest;

@Tag("CTRE")
public class TestCtreCanTalon extends BaseSimulatorJniTest
{
    @Test
    public void testSetup()
    {
        SpeedControllerWrapperAccessor scAccessor = DataAccessorFactory.getInstance().getSpeedControllerAccessor();
        EncoderWrapperAccessor encoderAccessor = DataAccessorFactory.getInstance().getEncoderAccessor();

        Assertions.assertEquals(0, scAccessor.getWrappers().size());
        Assertions.assertEquals(0, encoderAccessor.getWrappers().size());

        TalonSRX talon1 = new TalonSRX(1);
        talon1.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
        Assertions.assertEquals(1, scAccessor.getWrappers().size());
        Assertions.assertEquals(1, encoderAccessor.getWrappers().size());
        Assertions.assertTrue(scAccessor.getWrapper(101).isInitialized());
        Assertions.assertTrue(encoderAccessor.getWrapper(101).isInitialized());

        IPwmWrapper scWrapper = scAccessor.createSimulator(102, "com.snobot.simulator.simulator_components.ctre.CtreTalonSrxSpeedControllerSim");
        IEncoderWrapper encoderWrapper = encoderAccessor.createSimulator(102, "com.snobot.simulator.simulator_components.smart_sc.SmartScEncoder");
        Assertions.assertFalse(scWrapper.isInitialized());
        TalonSRX talon2 = new TalonSRX(2);
        talon2.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
        Assertions.assertTrue(scWrapper.isInitialized());
        Assertions.assertTrue(encoderWrapper.isInitialized());
    }

    @ParameterizedTest
    @ArgumentsSource(GetCtreTestIds.GetCtreTestIdsFeedbackDevice.class)
    public void testSimpleSetters(int aCanHandle)
    {
        SpeedControllerWrapperAccessor scAccessor = DataAccessorFactory.getInstance().getSpeedControllerAccessor();

        Assertions.assertEquals(0, scAccessor.getWrappers().size());

        TalonSRX talon = new TalonSRX(aCanHandle);
        Assertions.assertEquals(1, scAccessor.getWrappers().size());

        IPwmWrapper scWrapper = DataAccessorFactory.getInstance().getSpeedControllerAccessor().getWrapper(aCanHandle + JniSpeedControllerWrapperAccessor.sCAN_SC_OFFSET);
        Assertions.assertEquals("CTRE SC " + aCanHandle, scWrapper.getName());

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
        EncoderWrapperAccessor encoderAccessor = DataAccessorFactory.getInstance().getEncoderAccessor();

        int canId = 6;
        int simId = canId + 100;
        TalonSRX talon = new TalonSRX(canId);

        Assertions.assertEquals(0, encoderAccessor.getWrappers().size());
        IEncoderWrapper encoderWrapper = encoderAccessor.createSimulator(simId, "com.snobot.simulator.simulator_components.smart_sc.SmartScEncoder");
        Assertions.assertNotNull(encoderWrapper);
        Assertions.assertFalse(encoderWrapper.isInitialized());

        talon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);
        Assertions.assertEquals(1, encoderAccessor.getWrappers().size());
        Assertions.assertTrue(encoderWrapper.isInitialized());
    }

    @Test
    public void testAnalogSensorSetup()
    {

        AnalogInWrapperAccessor analogAccessor = DataAccessorFactory.getInstance().getAnalogInAccessor();

        int canId = 6;
        int simId = canId + 100;
        TalonSRX talon = new TalonSRX(canId);

        Assertions.assertEquals(0, analogAccessor.getWrappers().size());
        IAnalogInWrapper wrapper = analogAccessor.createSimulator(simId, "com.snobot.simulator.simulator_components.smart_sc.SmartScAnalogIn");
        Assertions.assertNotNull(wrapper);
        Assertions.assertFalse(wrapper.isInitialized());

        talon.configSelectedFeedbackSensor(FeedbackDevice.Analog);
        Assertions.assertEquals(1, analogAccessor.getWrappers().size());
        Assertions.assertTrue(wrapper.isInitialized());
    }

    @Test
    public void testSwitchControlModes()
    {
        int canId = 6;
        int simId = canId + 100;
        TalonSRX talon = new TalonSRX(canId);
        IPwmWrapper wrapper = DataAccessorFactory.getInstance().getSpeedControllerAccessor().getWrapper(simId);

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

        Assertions.assertEquals(40, wrapper.getVelocity(), 1);
        Assertions.assertEquals(40, talon.getSelectedSensorVelocity(0) / 600.0, 1);
        Assertions.assertEquals(0.720454097, wrapper.getVoltagePercentage(), .0001);
        Assertions.assertEquals(0.720454097, talon.getMotorOutputPercent(), .0001);

        talon.set(ControlMode.PercentOutput, .5);
        simulateForTime(1, () ->
        {
        });
        Assertions.assertEquals(.5, wrapper.getVoltagePercentage(), .0001);
        Assertions.assertEquals(.5, talon.getMotorOutputPercent(), .0001);

    }
}
