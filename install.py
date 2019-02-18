#Add plugin to script
#Run simulator
#Modify robot path

import subprocess

def modifyScript(scriptFile):
    scriptRead = open(scriptFile,"r")
    scriptWrite = open(scriptFile+".tmp","w")
    scriptList = scriptRead.readlines()
    # print(scriptList)
    
    #Add to plugins block
    for i in scriptList:
        if i=="plugins {\n":
            scriptList.insert(scriptList.index(i)+1,"//SnobotSim:\n    id \"com.snobot.simulator.plugin.SnobotSimulatorPlugin\" version \"2019-0.2.0\" apply false\n")
            break

    #Apply plugin
    deployBlock=False
    bracketCount=0
    counter=0
    for i in scriptList:
        counter=counter+1
        if i=="deploy {\n":
            deployBlock=True
        if deployBlock and "{" in i:
             bracketCount=bracketCount+1
        if deployBlock and "}" in i:
             bracketCount=bracketCount-1
        if deployBlock and bracketCount==0:
            deployBlock=False
            scriptList.insert(counter+1,"\n//SnowbotSim:\napply plugin: com.snobot.simulator.plugin.SnobotSimulatorPlugin\n\nconfigurations {\n    snobotSimCompile\n}\n\n")

    #Update dependencies
    counter=0
    dependenciesBlock = False
    for i in scriptList:
        counter=counter+1
        if i=="dependencies {\n":
            dependenciesBlock=True
        if dependenciesBlock and "}" in i:
            scriptList.insert(counter-1,"\n    // SnobotSim\n    snobotSimCompile snobotSimJava()\n")
            break

    scriptWrite.write(''.join(scriptList))
    subprocess.call(["mv",scriptFile,scriptFile+".bak"])
    subprocess.call(["mv",scriptFile+".tmp",scriptFile])
    print("Script modified successfully")

def executeGradle():
    subprocess.call(["./gradlew","downloadAll"])
    subprocess.call(["./gradlew","runJavaSnobotSim"])

def robotPath():
    path = input("What is the name of your robot class? (e.g. org.usfirst.frc.project.Robot)")
    configFile = open("simulator_config/simulator_config.properties","r+")
    configList = configFile.readlines()
    for i in configList:
        if "robot_class" in i:
            configList[configList.index(i)]="robot_class="+path
    configFile.write(''.join(configList))
    print("Robot class updated successfully")

modifyScript("build.gradle")
executeGradle()
robotPath()
print("Success!")


