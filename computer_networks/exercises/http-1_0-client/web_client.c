#include <errno.h>
#include <string.h>
#include <stdio.h>
#include <unistd.h>
#include <netinet/ip.h>
#include <sys/socket.h>
#include <sys/types.h>

const int BUFFER_SIZE = 1000000; // Buffer size 1MB
const int HEADER_COUNT = 10000; // Max header count 10k

struct sockaddr_in server;
struct header {
    char* name;
    char* value;
};

int main() {

    int sock = socket(AF_INET, SOCK_STREAM, 0);
    if (sock == -1) {
        perror("Socket fallita");
        return errno;
    }

    // Connect to google.com
    // We write write the address byte then we
    // convert it to a int32 casting it to a in_addr_t
    char addr[4] = {142, 250, 151, 147};

    server.sin_family = AF_INET;
    server.sin_port = htons(80);
    server.sin_addr.s_addr = *( in_addr_t* ) addr;

    if (connect(sock, (const struct sockaddr*)&server, sizeof(struct sockaddr)) == -1) {
        perror("Connection failed");
        return errno;
    }

    // Generating and sending request
    int packet_bytes;
    const char* req = "GET / HTTP/1.0\r\n\r\n";
    for (int cursor = 0; (packet_bytes = write(sock, req+cursor, strlen(req)-cursor)); cursor += packet_bytes);

    // Reading and parsing headers
    struct header headers[HEADER_COUNT];
    char h_buffer[BUFFER_SIZE];
    int h_index = 0;

    // Setting the first header
    headers[h_index].name = h_buffer;
    for (int i = 0; read(sock, h_buffer+i, 1); i++) {
        if (h_buffer[i] == ':' && !headers[h_index].value) {
            // Replacing separator with string terminator
            // to split into two separate strings
            h_buffer[i] = 0;
            // Set the start of the value
            headers[h_index].value = h_buffer+i+2;
        }
        // Checking for end of header section
        if (h_buffer[i-1] == '\r' && h_buffer[i] == '\n' ) { 
            // Marking end of header value
            h_buffer[i-1] = 0;
            // If the last header has no name because the line started with
            // CRLF that means that we finished the header section
            if (!headers[h_index].name[0]) break;
            // Moving to next header
            headers[++h_index].name = h_buffer+i+1;
        }
    }

    // Print recorded headers
    for (int i = 0; headers[i].name[0]; i++) {
        printf("%s --> %s\n", headers[i].name, headers[i].value);
    }

    // Print response body
    char b_buffer[BUFFER_SIZE];
    int res_size = 0;

    for (int cursor = 0; (packet_bytes = read(sock, b_buffer+cursor, BUFFER_SIZE-cursor)); cursor += packet_bytes) {
        res_size += packet_bytes;
    }

    b_buffer[res_size] = 0;
    printf("%.2f MB from SERVER: \n%s\n", (double)res_size/1000000, b_buffer);
}
