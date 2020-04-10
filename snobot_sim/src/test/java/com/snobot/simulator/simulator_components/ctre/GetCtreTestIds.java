
package com.snobot.simulator.simulator_components.ctre;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

public final class GetCtreTestIds
{
    private static final int SC_COUNT = 1;

    public static class GetCtreTestIdsFeedbackDevice implements ArgumentsProvider
    {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext aContext) throws Exception
        {
            List<Integer> output = new ArrayList<>();

            for (int i = 0; i < SC_COUNT; ++i)
            {
                output.add(i);
            }

            return output.stream().map(Arguments::of);
        }
    }

    public static class GetCtreTestIdsWithFeedbackDevice implements ArgumentsProvider
    {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext aContext) throws Exception
        {
            Collection<Object[]> output = new ArrayList<>();

            for (int i = 0; i < SC_COUNT; ++i)
            {

                output.add(new Object[]{ i, FeedbackDevice.Analog });
                output.add(new Object[]{ i, FeedbackDevice.QuadEncoder });
            }
            return output.stream().map(Arguments::of);
        }
    }
}
