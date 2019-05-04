package com.snobot.simulator.config;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.snobot.simulator.motor_sim.DcMotorModelConfig;
import com.snobot.simulator.motor_sim.GravityLoadMotorSimulationConfig;
import com.snobot.simulator.motor_sim.RotationalLoadMotorSimulationConfig;
import com.snobot.simulator.motor_sim.SimpleMotorSimulationConfig;
import com.snobot.simulator.motor_sim.StaticLoadMotorSimulationConfig;
import com.snobot.simulator.wrapper_accessors.DataAccessorFactory;
import com.snobot.test.utilities.BaseSimulatorJavaTest;
import com.snobot.test.utilities.MockRobot;

public class TestWriteConfig extends BaseSimulatorJavaTest
{
    @Test
    public void testWriteConfig()
    {

        DataAccessorFactory.getInstance().getSpiAccessor().createSpiSimulator(0, "ADXRS450");
        DataAccessorFactory.getInstance().getSpiAccessor().createSpiSimulator(1, "ADXL345");
        DataAccessorFactory.getInstance().getSpiAccessor().createSpiSimulator(2, "ADXL362");
        DataAccessorFactory.getInstance().getSpiAccessor().createSpiSimulator(3, "ADXRS450");
        DataAccessorFactory.getInstance().getI2CAccessor().createI2CSimulator(0, "ADXL345");

        // Used to create some components
        MockRobot robot = new MockRobot();

        DataAccessorFactory.getInstance().getEncoderAccessor().connectSpeedController(0, 1);
        DataAccessorFactory.getInstance().getSimulatorDataAccessor().setSpeedControllerModel_Simple(0, new SimpleMotorSimulationConfig(12));

        DataAccessorFactory.getInstance().getSimulatorDataAccessor().connectTankDriveSimulator(0, 1, 1, .5);
        DataAccessorFactory.getInstance().getSimulatorDataAccessor().connectTankDriveSimulator(0, 1, 100, 1);
        DataAccessorFactory.getInstance().getSimulatorDataAccessor().connectTankDriveSimulator(0, 1, 103, 2);

        DataAccessorFactory.getInstance().getSimulatorDataAccessor().setSpeedControllerModel_Simple(0, new SimpleMotorSimulationConfig(12));

        DcMotorModelConfig staticMotorConfig = DataAccessorFactory.getInstance().getSimulatorDataAccessor().createMotor("rs775", 1, 10, 1);
        DataAccessorFactory.getInstance().getSimulatorDataAccessor().setSpeedControllerModel_Static(1, staticMotorConfig,
                new StaticLoadMotorSimulationConfig(.5, 6.28));

        DcMotorModelConfig gravitationalMotorConfig = DataAccessorFactory.getInstance().getSimulatorDataAccessor().createMotor("rs775", 1, 10, 1);
        DataAccessorFactory.getInstance().getSimulatorDataAccessor().setSpeedControllerModel_Gravitational(2, gravitationalMotorConfig,
                new GravityLoadMotorSimulationConfig(.5));

        DcMotorModelConfig rotationalMotorConfig = DataAccessorFactory.getInstance().getSimulatorDataAccessor().createMotor("rs775", 1, 10, 1);
        DataAccessorFactory.getInstance().getSimulatorDataAccessor().setSpeedControllerModel_Rotational(3, rotationalMotorConfig,
                new RotationalLoadMotorSimulationConfig(1, 1));

        String dumpFile = "test_output/testWriteFile.yml";

        SimulatorConfigWriter writer = new SimulatorConfigWriter();
        Assertions.assertTrue(writer.writeConfig(dumpFile));

        robot.close();
    }

    @Test
    public void testWriteConfigToNonExistingDirectory()
    {
        String dumpFile = "directory_does_not_exist/testWriteFile.yml";

        SimulatorConfigWriter writer = new SimulatorConfigWriter();
        Assertions.assertFalse(writer.writeConfig(dumpFile));
    }
}
