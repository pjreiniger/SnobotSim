
#include "SnobotSim/Logging/SnobotCoutLogger.h"
#include "SnobotSim/Logging/SnobotLogger.h"
#include "gtest/gtest.h"

int main(int argc, char** argv)
{
    SnobotLogging::SnobotCoutLogger logger;
    logger.SetLogLevel(SnobotLogging::LOG_LEVEL_INFO);
    SnobotLogging::SetLogger(&logger);

    ::testing::InitGoogleTest(&argc, argv);
    int ret = RUN_ALL_TESTS();

    SnobotLogging::SetLogger(NULL);

    return ret;
}
