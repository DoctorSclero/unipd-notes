#include <errno.h>
#include <stdlib.h>
#include <string.h>
#include <stdio.h>
#include <unistd.h>
#include <netinet/ip.h>
#include <arpa/inet.h>
#include <sys/socket.h>
#include <sys/types.h>

// Buffer size 1MB
#define BUFFER_SIZE 1000000
#define HEADER_COUNT 1000

struct header {
    char* name;
    char* value;
};

int main() {

    struct sockaddr_in server;

    //
    //  SOCKET CREATION
    //

    int sock = socket(AF_INET, SOCK_STREAM, 0);
    if (sock == -1) {
        perror("Socket fallita");
        return errno;
    }


    //
    //  SOCKET CONNECTION
    //

    // Target socket address
    server.sin_family = AF_INET;
    server.sin_port = htons(80);
    inet_pton(AF_INET, "142.251.141.238", &server.sin_addr.s_addr);

    if (connect(sock, (const struct sockaddr*)&server, sizeof(struct sockaddr)) == -1) {
        perror("Connection failed");
        return errno;
    }

    //  
    //  REQUEST GENERATION AND DELIVER
    //

    // Generating and sending request
    int packet_bytes;
    const char* req = "GET / HTTP/1.1\r\n\r\n";
    for (int cursor = 0; (packet_bytes = write(sock, req+cursor, strlen(req)-cursor)); cursor += packet_bytes);

    //  
    //  RESPONSE HEADER PARSING
    //
    
    // Reading and parsing headers
    struct header headers[HEADER_COUNT] = {0};
    // Initialization to 0 prevents bugs in parsing and
    // buffer overflows
    char h_buffer[BUFFER_SIZE] = {0};
    int h_index = 0;

    // Setting the first header
    headers[h_index].name = h_buffer;
    // Leaving space in the buf for the terminator char to prevent overflows
    for (int i = 0; i < BUFFER_SIZE-1 && read(sock, h_buffer+i, 1); i++) {
        if (h_buffer[i] == ':' && !headers[h_index].value) {
            // Replacing separator with string terminator
            // to split into two separate strings
            h_buffer[i] = 0;
            // Set the start of the value
            headers[h_index].value = h_buffer+i+2;
        }
        // Checking for end of header section
        if (i > 0 && h_buffer[i-1] == '\r' && h_buffer[i] == '\n' ) { 
            // Checking for too many headers
            if (h_index >= HEADER_COUNT) {
                errno = EMSGSIZE;
                perror("Header array overflow");
                return errno;
            }
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
    printf("\n");

    //  
    //  TRANSFER ENCODING DECODE
    //

    int i=0;
    while (strcmp(headers[i].name, "Transfer-Encoding") && headers[i].name[0]) {i++;}

    // Check for chunked data
    if (headers[i].name[0] && !strcmp(headers[i].value, "chunked")) {

        int chunk_bytes = 0;

        // Extract chuncked data
        char b_buffer_data[BUFFER_SIZE] = {0};
        char* chunk_pointer = b_buffer_data;

        do {

            // Gathering chunk size
            for (int cursor = 0; read(sock, chunk_pointer+cursor, 1); cursor++) {
                // Ignoring extensions
                if (chunk_pointer[cursor]==';') {
                    chunk_pointer[cursor] = 0;
                }
                // Capturing trailing CRLF
                if (cursor > 0 && chunk_pointer[cursor-1] == '\r' && chunk_pointer[cursor] == '\n') {
                    chunk_pointer[cursor-1] = 0;
                    break;
                }
            }

            // Translating chunk size 
            chunk_bytes = (int)strtol(chunk_pointer, 0, 16);

            // Reading chunk data
            int cursor = 0;
            for (int rbytes = 0; rbytes = read(sock, chunk_pointer+cursor, chunk_bytes-cursor+2); cursor+=rbytes);
            // Preparing for new write
            chunk_pointer+=cursor-2;


        } while (chunk_bytes);
        printf("%s\n", b_buffer_data); 
    }

    close(sock);
    return EXIT_SUCCESS;
}

