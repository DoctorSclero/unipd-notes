#include <iostream>
#include <unistd.h> // Unix system library
#include <sys/types.h> // Unix system types library ?
#include <sys/wait.h> // Unix library to wait sub processes

int main() {
    std::cout << "Spawning a new instance of this executable" << std::endl;
    
    // Creating a variable to store the reference
    // to the child process
    pid_t pid0 = fork();
    pid_t pid1 = fork();
    pid_t pid2 = fork();

    if (pid0 > 0 || pid1 > 0) {
        std::cout << "Process 0: " << pid0 << std::endl;
        std::cout << "Process 1: " << pid1 << std::endl;
        std::cout << "Process 2: " << pid1 << std::endl;
        std::cout << "Waiting for subprocesses to finish..." << std::endl;
        wait(NULL);
        wait(NULL);
        wait(NULL);
        std::cout << "Child processes ended" << std::endl;
    }
    

    if (pid0 == 0) {
        std::cout << "Child process 1: spawned" << std::endl;
    }

    if (pid1 == 0) {
        std::cout << "Child process 2: spawned" << std::endl;
    }
    
    if (pid2 == 0) {
        std::cout << "Child process 3: spawned" << std::endl;
    }
}
