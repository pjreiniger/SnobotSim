package com.snobot.simulator.simulator_components.rev;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.ControlType;
import com.snobot.simulator.motor_sim.DcMotorModelConfig;
import com.snobot.simulator.motor_sim.StaticLoadMotorSimulationConfig;
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
        int rawHandle = aCanHandle + CtreTalonSrxSpeedControllerSim.sCAN_SC_OFFSET;

        Assertions.assertEquals(0, DataAccessorFactory.getInstance().getSpeedControllerAccessor().getPortList().size());

        CANSparkMax sparksMax = new CANSparkMax(aCanHandle, MotorType.kBrushless);
        Assertions.assertEquals(1, DataAccessorFactory.getInstance().getSpeedControllerAccessor().getPortList().size());
        Assertions.assertEquals("Rev SC " + aCanHandle,
                DataAccessorFactory.getInstance().getSpeedControllerAccessor().getName(rawHandle));
        
        sparksMax.set(-1.0);
        Assertions.assertEquals(-1.0, DataAccessorFactory.getInstance().getSpeedControllerAccessor().getVoltagePercentage(rawHandle), sDOUBLE_EPSILON);
        Assertions.assertEquals(-1.0, sparksMax.get(), sDOUBLE_EPSILON);
        Assertions.assertEquals(-1.0, sparksMax.getAppliedOutput(), sDOUBLE_EPSILON);

        sparksMax.set(0.5);
        Assertions.assertEquals(0.5, DataAccessorFactory.getInstance().getSpeedControllerAccessor().getVoltagePercentage(rawHandle), sDOUBLE_EPSILON);
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

        Assertions.assertEquals(0, DataAccessorFactory.getInstance().getSpeedControllerAccessor().getPortList().size());

        CANSparkMax sparksMax = new CANSparkMax(aCanHandle, MotorType.kBrushless);
        CANSparkMax follower = new CANSparkMax(sFOLLOWER_ID, MotorType.kBrushless);
        Assertions.assertEquals(2, DataAccessorFactory.getInstance().getSpeedControllerAccessor().getPortList().size());

        follower.follow(sparksMax);

        sparksMax.set(-0.5);
        Assertions.assertEquals(-0.5, DataAccessorFactory.getInstance().getSpeedControllerAccessor().getVoltagePercentage(rawHandle), sDOUBLE_EPSILON);
        Assertions.assertEquals(-0.5, sparksMax.get(), sDOUBLE_EPSILON);
        Assertions.assertEquals(-0.5, sparksMax.getAppliedOutput(), sDOUBLE_EPSILON);

        Assertions.assertEquals(-0.5, DataAccessorFactory.getInstance().getSpeedControllerAccessor().getVoltagePercentage(followerRawHandle), sDOUBLE_EPSILON);
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

        Assertions.assertEquals(0, DataAccessorFactory.getInstance().getSpeedControllerAccessor().getPortList().size());

        CANSparkMax sparksMax = new CANSparkMax(canHandle, CANSparkMaxLowLevel.MotorType.kBrushless);
        Assertions.assertEquals(1, DataAccessorFactory.getInstance().getSpeedControllerAccessor().getPortList().size());
        Assertions.assertEquals("Rev SC " + canHandle,
            DataAccessorFactory.getInstance().getSpeedControllerAccessor().getName(simHandle));

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
        Assertions.assertEquals(40, DataAccessorFactory.getInstance().getSpeedControllerAccessor().getVelocity(simHandle), 1);
        Assertions.assertEquals(0.7342491156153788, DataAccessorFactory.getInstance().getSpeedControllerAccessor().getVoltagePercentage(simHandle), .0001);

        sparksMax.set(.5);
        simulateForTime(1, () ->
        {
        });
        Assertions.assertEquals(.5, DataAccessorFactory.getInstance().getSpeedControllerAccessor().getVoltagePercentage(simHandle), .0001);
        Assertions.assertEquals(.5, sparksMax.get());
    }

}
