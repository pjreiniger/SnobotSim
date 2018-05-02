
import re
import collections


def search_files(header_file, source_file, header_pattern, source_pattern):


    header_functions = collections.OrderedDict()
    with open(header_file, 'r') as f:
        lines = f.readlines()
        lines = "".join(lines)
        for match in header_pattern.finditer(lines):
            header_functions[match.group(2)] = (match.group(1))

    source_functions = collections.OrderedDict()
    with open(source_file, 'r') as f:
        lines = f.readlines()
        lines = "".join(lines)
        for match in source_pattern.finditer(lines):
            source_functions[match.group(2)] = (match.group(1))

    # print "Headers..."
    # for func_name in header_functions:
       # print "  ", header_functions[func_name], func_name
    
    # print "Source..."
    # for func_name in source_functions:
       # print "  ", source_functions[func_name], func_name
        
    source_function_set = set(source_functions.keys())
    header_function_set = set(header_functions.keys())
    source_only = source_function_set.difference(header_function_set)
    header_only = header_function_set.difference(source_function_set)
    
    success = len(source_only) == 0 and len(header_only) == 0
    if not success:
        print "Error in ", header_file
    
    if len(source_only) != 0:
        print "  Source Only:"
        print "    " + "\n  ".join(source_only)
    
    if len(header_only) != 0:
        print "  Header Only:"
        print "    " + "\n  ".join(header_only)


def search_jni():
    jni_header_base = r'C:\Users\PJ\GitHub\SnobotSim\ctre_override\temp\driver\include\ctre\phoenix\jni'
    jni_source_base = r'C:\Users\PJ\GitHub\SnobotSim\ctre_override\src\main\native\cpp\ctre_jni_mocks'
    
    jni_header_pattern = re.compile(r'JNIEXPORT (.*) JNICALL (.*)\n +\((.*)\);', re.MULTILINE)
    jni_source_pattern = re.compile(r'JNIEXPORT (.*) JNICALL (.*)(\r?\n +)?\(', re.MULTILINE)

    search_files(jni_header_base + '/com_ctre_phoenix_CANifierJNI.h', jni_source_base + '/CANifierJNI.cpp', jni_header_pattern, jni_source_pattern)
    search_files(jni_header_base + '/com_ctre_phoenix_CTRLoggerJNI.h', jni_source_base + '/CTRLoggerJNI.cpp', jni_header_pattern, jni_source_pattern)
    search_files(jni_header_base + '/com_ctre_phoenix_MotorControl_CAN_MotControllerJNI.h', jni_source_base + '/MotControllerJNI.cpp', jni_header_pattern, jni_source_pattern)
    search_files(jni_header_base + '/com_ctre_phoenix_Sensors_PigeonImuJNI.h', jni_source_base + '/PigeonImuJNI.cpp', jni_header_pattern, jni_source_pattern)

    
def search_cci():
    cci_header_base = r'C:\Users\PJ\GitHub\SnobotSim\ctre_override\temp\driver\include\ctre\phoenix\CCI'
    cci_source_base = r'C:\Users\PJ\GitHub\SnobotSim\ctre_override\src\main\native\cpp\ctre_cci_mocks'
    
    search_files(cci_header_base + '/CANifier_CCI.h', 
                cci_source_base + '/CANifier_CCI.cpp', 
                re.compile(r'\t(.*)(c_CANifier_.*)\(', re.MULTILINE), 
                re.compile(r'(.*)(c_CANifier_.*)\(', re.MULTILINE))
    
    search_files(cci_header_base + '/Logger_CCI.h', 
                cci_source_base + '/Logger_CCI.cpp', 
                re.compile(r'\t(.*)(c_Logger_.*)\(', re.MULTILINE), 
                re.compile(r'(.*)(c_Logger_.*)\(', re.MULTILINE))
    
    search_files(cci_header_base + '/MotController_CCI.h', 
                cci_source_base + '/MotController_CCI.cpp', 
                re.compile(r'\t(.*)(c_MotController_.*)\(', re.MULTILINE), 
                re.compile(r'(.*)(c_MotController_.*)\(', re.MULTILINE))
    
    search_files(cci_header_base + '/PigeonIMU_CCI.h', 
                 cci_source_base + '/PigeonIMU_CCI.cpp', 
                 re.compile(r'\t(.*)(c_PigeonIMU_.*)\(', re.MULTILINE), 
                 re.compile(r'(.*)(c_PigeonIMU_.*)\(', re.MULTILINE))


def main():
    search_jni()
    search_cci()

main()