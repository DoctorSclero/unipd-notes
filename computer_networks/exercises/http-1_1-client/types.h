const int BUFFER_SIZE = 1000000; // Buffer size 1MB
const int HEADER_COUNT = 10000; // Max header count 10k

struct header {
    char* name;
    char* value;
};
