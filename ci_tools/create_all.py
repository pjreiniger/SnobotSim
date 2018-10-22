
import os
import zipfile


def createAll(start_dir, root_path, library_name, version):
    temp_build_dir = start_dir + library_name
    library_path = os.path.join(start_dir, root_path, library_name, version)
    
    natives = []
    # natives.append(os.path.join(library_path, library_name + "-" + version + "-linux-x86-64.jar"))
    natives.append(os.path.join(library_path, library_name + "-" + version + "-os x-x86-64.jar"))
    natives.append(os.path.join(library_path, library_name + "-" + version + "-windows-x86.jar"))
    natives.append(os.path.join(library_path, library_name + "-" + version + "-windows-x86-64.jar"))
        
    for native in natives:
        zip_ref = zipfile.ZipFile(native, 'r')
        zip_ref.extractall(temp_build_dir)
        zip_ref.close()
        
    
    # ziph is zipfile handle
    ziph = zipfile.ZipFile(library_path + "/" + library_name + "-" + version + "-all.jar", 'w', zipfile.ZIP_DEFLATED)
    for root, dirs, files in os.walk(temp_build_dir):
        for file in files:
            full_path = os.path.join(root, file)
            zip_path = full_path[len(temp_build_dir)+len(os.sep):] #XXX: relative path
            print (full_path + ", " + zip_path)
            ziph.write(full_path, zip_path)

    pass
    
    
def detect_version(start_dir):
    path = os.path.abspath(start_dir + "com/snobot/simulator/adx_family")
    
    output = None
    
    for d in os.listdir(path):
        full_path = os.path.join(path, d)
        if os.path.isdir(full_path):
            output = d
            
    return output


def main():
    start_dir = os.path.abspath("../output/") + "/"
    version = detect_version(start_dir)
    
    createAll(start_dir, "com/snobot/simulator", "navx_simulator", version)
    createAll(start_dir, "com/snobot/simulator", "adx_family", version)
    createAll(start_dir, "com/snobot/simulator", "snobot_sim_jni", version)


if __name__ == "__main__":
    main()