
sudo sh -c 'echo "deb http://apt.llvm.org/trusty/ llvm-toolchain-trusty-5.0 main" > /etc/apt/sources.list.d/llvm.list'
wget -O - https://apt.llvm.org/llvm-snapshot.gpg.key|sudo apt-key add -
sudo apt-get update -q || true
sudo apt-get install clang-format-5.0 -y

printf "HELLO"
printf "\n\n\n\n"
which pip
which pip3

sudo sh -c "echo 'y' | apt install python3-pip"
sudo sh -c "apt-get install -y python-setuptools"

printf "HELLO2222"
printf "\n\n\n\n"
which pip
which pip3
pip3 install --user wpiformat
pip3 install --user yapf
