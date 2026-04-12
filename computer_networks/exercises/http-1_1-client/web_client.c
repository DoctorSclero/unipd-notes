#include <errno.h>
#include <stdlib.h>
#include <string.h>
#include <stdint.h>
#include <stdio.h>
#include <unistd.h>
#include <netinet/ip.h>
#include <sys/socket.h>
#include <sys/types.h>

#include "types.h"

int main() {

    struct sockaddr_in server;

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
        if (i > 0 && h_buffer[i-1] == '\r' && h_buffer[i] == '\n' ) { 
            // Marking end of header value
            h_buffer[i-1] = 0;
            // If the last header has no name because the line started with
            // CRLF that means that we finished the header section
            if (!headers[h_index].name[0]) break;
            // Moving to next header
            headers[++h_index].name = h_buffer+i+1;
        }
    }
    /*
    // Print recorded headers
    for (int i = 0; headers[i].name[0]; i++) {
        printf("%s --> %s\n", headers[i].name, headers[i].value);
    }
    printf("\n");
    */

    //  
    //  TRANSFER ENCODING DECODE
    //

    int i=0;
    while (strcmp(headers[i].name, "Transfer-Encoding") && headers[i].name[0]) {i++;}

    // Check for chunked data
    if (headers[i].name[0] && !strcmp(headers[i].value, "chunked")) {
        int chunk_bytes = 0;

        do {
            // Extract chuncked data
            char b_buffer[BUFFER_SIZE];
            char* chunk_data;
            char* chunk_size = b_buffer;

            // Gathering chunk size
            for (int cursor = 0; read(sock, b_buffer+cursor, 1); cursor++) {
                // Ignoring extensions
                if (b_buffer[cursor]==';') {
                    b_buffer[cursor] = 0;
                }
                // Capturing trailing CRLF
                if (cursor > 0 && b_buffer[cursor-1] == '\r' && b_buffer[cursor] == '\n') {
                    b_buffer[cursor-1] = 0;
                    chunk_data = b_buffer+cursor+1;
                    break;
                }
            }

            // printf("Chunk data size: %s", chunk_size);

            // Translating chunk size 
            chunk_bytes = (int)strtol(chunk_size, 0, 16);

            // printf(" --> %d bytes\n\n", chunk_bytes);

            // Reading chunk-data bytes
            int cursor = 0;
            for (int rbytes = 0; rbytes = read(sock, chunk_data+cursor, chunk_bytes-cursor+2); cursor+=rbytes) {};
            chunk_data[cursor-1]=0;
            if (chunk_bytes) printf("%s\n", chunk_data);
        } while (chunk_bytes);
         
    }

    close(sock);
    return EXIT_SUCCESS;
}

