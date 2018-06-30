package com.snobot.test.utilities;

import java.io.IOException;

import net.java.games.input.AbstractComponent;
import net.java.games.input.AbstractController;
import net.java.games.input.Component;
import net.java.games.input.Component.Identifier;
import net.java.games.input.Controller;
import net.java.games.input.Event;
import net.java.games.input.Rumbler;

public class MockController extends AbstractController
{
    private static Component[] createComponents()
    {
        Component[] output = new Component[100];

        int i = 0;
        output[i++] = new MockComponent(Identifier.Key.W);
        output[i++] = new MockComponent(Identifier.Key.A);
        output[i++] = new MockComponent(Identifier.Key.S);
        output[i++] = new MockComponent(Identifier.Key.D);

        output[i++] = new MockComponent(Identifier.Key.I);
        output[i++] = new MockComponent(Identifier.Key.J);
        output[i++] = new MockComponent(Identifier.Key.K);
        output[i++] = new MockComponent(Identifier.Key.L);

        output[i++] = new MockComponent(Identifier.Key.C);
        output[i++] = new MockComponent(Identifier.Key.N);

        output[i++] = new MockComponent(Identifier.Key.UP);
        output[i++] = new MockComponent(Identifier.Key.DOWN);
        output[i++] = new MockComponent(Identifier.Key.LEFT);
        output[i++] = new MockComponent(Identifier.Key.RIGHT);

        output[i++] = new MockComponent(Identifier.Axis.X);
        output[i++] = new MockComponent(Identifier.Axis.Y);
        output[i++] = new MockComponent(Identifier.Axis.Z);
        output[i++] = new MockComponent(Identifier.Axis.RX);
        output[i++] = new MockComponent(Identifier.Axis.RY);
        output[i++] = new MockComponent(Identifier.Axis.RZ);

        output[i++] = new MockComponent(Identifier.Button._0);
        output[i++] = new MockComponent(Identifier.Button._1);
        output[i++] = new MockComponent(Identifier.Button._2);
        output[i++] = new MockComponent(Identifier.Button._3);
        output[i++] = new MockComponent(Identifier.Button._4);
        output[i++] = new MockComponent(Identifier.Button._5);
        output[i++] = new MockComponent(Identifier.Button._6);
        output[i++] = new MockComponent(Identifier.Button._7);
        output[i++] = new MockComponent(Identifier.Button._8);
        output[i++] = new MockComponent(Identifier.Button._9);
        output[i++] = new MockComponent(Identifier.Button._10);
        output[i++] = new MockComponent(Identifier.Button._11);
        output[i++] = new MockComponent(Identifier.Button._12);
        output[i++] = new MockComponent(Identifier.Button._13);

        for (; i < output.length; ++i)
        {
            output[i] = new MockComponent(Identifier.Axis.POV);
        }


        return output;
    }

    public MockController()
    {
        super("Hello", createComponents(), new Controller[] {}, new Rumbler[] {});
    }

    @Override
    protected boolean getNextDeviceEvent(Event aEvent) throws IOException
    {
        return false;
    }

    private static class MockComponent extends AbstractComponent
    {
        private float mPollValue;

        protected MockComponent(Identifier aId)
        {
            super("Mock", aId);
        }

        @Override
        public boolean isRelative()
        {
            return false;
        }

        @Override
        protected float poll() throws IOException
        {
            return mPollValue;
        }

    }

    public void setValueForComponent(Identifier aIdentifier, double aValue)
    {
        MockComponent component = (MockComponent) getComponent(aIdentifier);
        component.mPollValue = (float) aValue;
    }

}
