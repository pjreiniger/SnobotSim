
#include "SnobotSim/Config/SimulatorConfigReaderV1.h"
#include "gtest/gtest.h"

TEST(LoadConfigFile, TestGoodFile)
{
    SimulatorConfigReaderV1 configReader;
    EXPECT_TRUE(configReader.LoadConfig("F:/git/FIRST/SnobotSim/examples/CppWithGuiExample/simulator_config/simulator_config.yml"));
}

TEST(LoadConfigFile, TestMissingFile)
{
    SimulatorConfigReaderV1 configReader;
    EXPECT_FALSE(configReader.LoadConfig("File/Does/Not/Exist.yml"));
}
