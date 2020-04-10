
package com.snobot.simulator.simulator_components.rev;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

class GetRevTestIds implements ArgumentsProvider 
{

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext aContext) throws Exception 
    {
        List<Integer> output = new ArrayList<>();

        output.add(7);

        return output.stream().map(Arguments::of);
    }
}
