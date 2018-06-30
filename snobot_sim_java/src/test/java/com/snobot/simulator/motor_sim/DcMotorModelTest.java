package com.snobot.simulator.motor_sim;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.snobot.simulator.motor_sim.motor_factory.MakeTransmission;
import com.snobot.simulator.motor_sim.motor_factory.PublishedMotorFactory;

// https://github.com/Team254/Sim-FRC-2015/blob/master/test/com/team254/frc2015/sim/TestDCMotor.java
public class DcMotorModelTest
{
    private static final double EPS = 1E-9;

    @Test
    public void testGettersSetters()
    {
        // Make a new motor.
        DcMotorModel motor = new DcMotorModel(new DcMotorModelConfig(null, null));

        // Check initial conditions.
        Assertions.assertEquals(motor.getPosition(), 0.0, EPS);
        Assertions.assertEquals(motor.getVelocity(), 0.0, EPS);
        Assertions.assertEquals(motor.getCurrent(), 0.0, EPS);

        // Reset initial conditions.
        motor.reset(1.0, 2.0, 3.0);
        Assertions.assertEquals(motor.getPosition(), 1.0, EPS);
        Assertions.assertEquals(motor.getVelocity(), 2.0, EPS);
        Assertions.assertEquals(motor.getCurrent(), 3.0, EPS);
    }

    @Test
    public void testRS775_6VSmallLoad()
    {
        DcMotorModel rs775 = new DcMotorModel(MakeTransmission.makeTransmission(PublishedMotorFactory.makeRS775(), 1, 10.0, 1.0));

        // Apply a positive voltage and small load.
        for (int i = 0; i < 1000; ++i)
        {
            rs775.step(6.0, 0.01, 0.0, 0.01);
        }
        // We expect negligible final current, and a final velocity of ~68.04
        // rad/sec.
        Assertions.assertEquals(rs775.getCurrent(), 0.0, 1E-3);
        Assertions.assertEquals(rs775.getVelocity(), 68.06, 1E-2);

    }

    @Test
    public void testRS775_12VSmallLoad()
    {
        DcMotorModel rs775 = new DcMotorModel(MakeTransmission.makeTransmission(PublishedMotorFactory.makeRS775(), 1, 10.0, 1.0));

        // Apply a larger voltage.
        for (int i = 0; i < 1000; ++i)
        {
            rs775.step(12.0, 0.01, 0.0, 0.01);
        }

        // We expect negligible final current, and a final velocity of ~2 *
        // 68.04 rad/sec.
        Assertions.assertEquals(rs775.getCurrent(), 0.0, 1E-3);
        Assertions.assertEquals(rs775.getVelocity(), 68.04 * 2, 1E-1);

    }

    @Test
    public void testRS775_12VLargeLoad()
    {
        DcMotorModel rs775 = new DcMotorModel(MakeTransmission.makeTransmission(PublishedMotorFactory.makeRS775(), 1, 10.0, 1.0));

        for (int i = 0; i < 1000; ++i)
        {
            rs775.step(12.0, 1.0, 0.0, 0.01);
        }

        // This is slower, so 1000 iterations isn't enough to get to steady
        // state
        Assertions.assertEquals(rs775.getCurrent(), 48.758, 1E-3);
        Assertions.assertEquals(rs775.getVelocity(), 59.59, 1E-1);
    }

    @Test
    public void testDoubleRS775_100Efficiency_12VLargeLoad()
    {
        DcMotorModel rs775 = new DcMotorModel(MakeTransmission.makeTransmission(PublishedMotorFactory.makeRS775(), 2, 10.0, 1.0));

        for (int i = 0; i < 1000; ++i)
        {
            rs775.step(12.0, 1.0, 0.0, 0.01);
        }

        // We expect the two motor version to move faster than the single motor
        // version.
        Assertions.assertEquals(rs775.getCurrent(), 17.378, 1E-3);
        Assertions.assertEquals(rs775.getVelocity(), 122.517, 1E-1);
    }

    @Test
    public void testDoubleRS775_80Efficiency_12VLargeLoad()
    {
        DcMotorModel rs775 = new DcMotorModel(MakeTransmission.makeTransmission(PublishedMotorFactory.makeRS775(), 2, 10.0, 0.8));

        for (int i = 0; i < 1000; ++i)
        {
            rs775.step(12.0, 1.0, 0.0, 0.01);
        }

        Assertions.assertEquals(rs775.getCurrent(), 27.540, 1E-3);
        Assertions.assertEquals(rs775.getVelocity(), 114.545, 1E-1);
        // We expect the less efficient version to be slower.
        // assert (rs775.getVelocity() + EPS < final_velocity);
        // assert (rs775.getPosition() + EPS < final_position);
    }

    @Test
    public void testRS775_Neg12VSmallLoad()
    {
        DcMotorModel rs775 = new DcMotorModel(MakeTransmission.makeTransmission(PublishedMotorFactory.makeRS775(), 1, 10.0, 1.0));

        // Go in reverse.
        for (int i = 0; i < 1000; ++i)
        {
            rs775.step(-12.0, 1.0, 0.0, 0.01);
        }
        Assertions.assertEquals(rs775.getCurrent(), 48.758, 1E-3);
        Assertions.assertEquals(rs775.getVelocity(), -59.590, 1E-1);

    }

    @Test
    public void testGravity()
    {
        DcMotorModel rs775 = new DcMotorModel(MakeTransmission.makeTransmission(PublishedMotorFactory.makeRS775(), 1, 10.0, 1.0));
        for (int i = 0; i < 1000; ++i)
        {
            // Assume pulling against gravity.
            // Load is a 1kg mass on .2m pulley.
            rs775.step(12.0, 0.04, -9.8 / .2, 0.01);
        }

        rs775.reset(0, 0, 0);
        for (int i = 0; i < 1000; ++i)
        {
            // Assume pulling with gravity.
            // Load is a 1kg mass on .2m pulley.
            rs775.step(-12.0, 0.04, -9.8 / .2, 0.01);
        }

        Assertions.assertNotNull(rs775); // supress findbugs
    }
}
