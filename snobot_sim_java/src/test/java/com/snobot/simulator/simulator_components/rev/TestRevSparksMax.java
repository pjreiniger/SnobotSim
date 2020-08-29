package com.snobot.simulator.simulator_components.rev;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.ControlType;
import com.snobot.simulator.module_wrapper.interfaces.IPwmWrapper;
import com.snobot.simulator.motor_sim.DcMotorModelConfig;
import com.snobot.simulator.motor_sim.StaticLoadMotorSimulationConfig;
import com.snobot.simulator.wrapper_accessors.SpeedControllerWrapperAccessor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.snobot.simulator.simulator_components.ctre.CtreTalonSrxSpeedControllerSim;
import com.snobot.simulator.wrapper_accessors.DataAccessorFactory;
import com.snobot.test.utilities.BaseSimulatorJavaTest;

@Tag("REV")
public class TestRevSparksMax extends BaseSimulatorJavaTest
{
    private static final double sDOUBLE_EPSILON = 1.0 / 1023;
    private static final int sFOLLOWER_ID = 11;

    @ParameterizedTest
    @ArgumentsSource(GetRevTestIds.class)
    public void testAppliedThrottle(int aCanHandle)
    {
        SpeedControllerWrapperAccessor accessor = DataAccessorFactory.getInstance().getSpeedControllerAccessor();
        int rawHandle = aCanHandle + CtreTalonSrxSpeedControllerSim.sCAN_SC_OFFSET;

        Assertions.assertEquals(0, accessor.getPortList().size());

        CANSparkMax sparksMax = new CANSparkMax(aCanHandle, MotorType.kBrushless);
        IPwmWrapper wrapper = accessor.getWrapper(rawHandle);
        Assertions.assertEquals(1, accessor.getPortList().size());
        Assertions.assertEquals("Rev SC " + aCanHandle, wrapper.getName());

        sparksMax.set(-1.0);
        Assertions.assertEquals(-1.0, wrapper.getVoltagePercentage(), sDOUBLE_EPSILON);
        Assertions.assertEquals(-1.0, sparksMax.get(), sDOUBLE_EPSILON);
        Assertions.assertEquals(-1.0, sparksMax.getAppliedOutput(), sDOUBLE_EPSILON);

        sparksMax.set(0.5);
        Assertions.assertEquals(0.5, wrapper.getVoltagePercentage(), sDOUBLE_EPSILON);
        Assertions.assertEquals(0.5, sparksMax.get(), sDOUBLE_EPSILON);
        Assertions.assertEquals(0.5, sparksMax.getAppliedOutput(), sDOUBLE_EPSILON);

        sparksMax.close();
    }

    @ParameterizedTest
    @ArgumentsSource(GetRevTestIds.class)
    public void testFollower(int aCanHandle)
    {
        if (aCanHandle == sFOLLOWER_ID)
        {
            return;
        }

        int rawHandle = aCanHandle + CtreTalonSrxSpeedControllerSim.sCAN_SC_OFFSET;
        int followerRawHandle = sFOLLOWER_ID + CtreTalonSrxSpeedControllerSim.sCAN_SC_OFFSET;

        SpeedControllerWrapperAccessor accessor = DataAccessorFactory.getInstance().getSpeedControllerAccessor();
        Assertions.assertEquals(0, accessor.getPortList().size());

        CANSparkMax sparksMax = new CANSparkMax(aCanHandle, MotorType.kBrushless);
        CANSparkMax follower = new CANSparkMax(sFOLLOWER_ID, MotorType.kBrushless);
        IPwmWrapper leadWrapper = accessor.getWrapper(rawHandle);
        IPwmWrapper followerWrapper = accessor.getWrapper(followerRawHandle);
        Assertions.assertEquals(2, accessor.getPortList().size());

        follower.follow(sparksMax);

        sparksMax.set(-0.5);
        Assertions.assertEquals(-0.5, leadWrapper.getVoltagePercentage(), sDOUBLE_EPSILON);
        Assertions.assertEquals(-0.5, sparksMax.get(), sDOUBLE_EPSILON);
        Assertions.assertEquals(-0.5, sparksMax.getAppliedOutput(), sDOUBLE_EPSILON);

        Assertions.assertEquals(-0.5, followerWrapper.getVoltagePercentage(), sDOUBLE_EPSILON);
//        Assertions.assertEquals(0.5, follower.get(), sDOUBLE_EPSILON); // TODO: Doesn't work, vendor issue
        Assertions.assertEquals(-0.5, sparksMax.getAppliedOutput(), sDOUBLE_EPSILON);

        sparksMax.close();
        follower.close();

    }

    @Test
    public void testSwitchControlModes()
    {
        int canHandle = 8;
        int simHandle = canHandle + CtreTalonSrxSpeedControllerSim.sCAN_SC_OFFSET;

        SpeedControllerWrapperAccessor accessor = DataAccessorFactory.getInstance().getSpeedControllerAccessor();
        Assertions.assertEquals(0, accessor.getPortList().size());

        CANSparkMax sparksMax = new CANSparkMax(canHandle, CANSparkMaxLowLevel.MotorType.kBrushless);
        IPwmWrapper wrapper = accessor.getWrapper(simHandle);
        Assertions.assertEquals(1, accessor.getPortList().size());
        Assertions.assertEquals("Rev SC " + canHandle, wrapper.getName());

        // Simulate CIM drivetrain
        DcMotorModelConfig motorConfig = DataAccessorFactory.getInstance().getSimulatorDataAccessor().createMotor("CIM", 1, 10, 1);
        Assertions.assertTrue(DataAccessorFactory.getInstance().getSimulatorDataAccessor().setSpeedControllerModel_Static(simHandle, motorConfig,
            new StaticLoadMotorSimulationConfig(.2)));

        CANPIDController pid = sparksMax.getPIDController();
        CANEncoder encoder = sparksMax.getEncoder();
        pid.setFeedbackDevice(encoder);

        pid.setP(.04);
        pid.setFF(.019);
        simulateForTime(1, () ->
        {
            pid.setReference(40, ControlType.kVelocity);
        });

        Assertions.assertEquals(40, encoder.getVelocity(), 1);
        Assertions.assertEquals(40, wrapper.getVelocity(), 1);
        Assertions.assertEquals(0.7342491156153788, wrapper.getVoltagePercentage(), .0001);

        sparksMax.set(.5);
        simulateForTime(1, () ->
        {
        });
        Assertions.assertEquals(.5, wrapper.getVoltagePercentage(), .0001);
        Assertions.assertEquals(.5, sparksMax.get());
    }

}
