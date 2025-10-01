#include <unistd.h>
#include <iostream>

int main() {
    std::cout << "Spawning shell..." << std::endl;
    execve("/bin/sh", NULL, NULL);
    std::cout << "Shell spawned!" << std::endl;
    return 0;
}