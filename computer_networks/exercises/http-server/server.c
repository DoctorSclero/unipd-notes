#include <netinet/in.h>
#include <sys/socket.h>
#include <unistd.h>
#include <fcntl.h>
#include <string.h>
#include <stdlib.h>
#include <stdio.h>
#include <errno.h>

// A megabit buffer
#define BUFFER_SIZE 1000000
#define HEADER_COUNT 1000

struct header {
    char* name;
    char* value;
};

int main() {

    static struct sockaddr_in server;

    server.sin_addr.s_addr = 0; // ip 0.0.0.0
    server.sin_family = AF_INET;
    server.sin_port = htons(8080);

    int sockfd = socket(AF_INET, SOCK_STREAM, 0);

    if (bind(sockfd, (const struct sockaddr*)(&server), sizeof(struct sockaddr_in))) {
        perror(0);
        return errno;
    }
    if (listen(sockfd, 10)) {
        perror(0);
        return errno;
    }

    int client;
    struct sockaddr_in addr;
    unsigned int addr_len = sizeof(struct sockaddr);
    while ((client = accept(sockfd, (struct sockaddr*)(&addr), &addr_len))) {
        // If address is invalid close connection early
        if ((addr_len != sizeof(struct sockaddr_in))) {
            printf("Socket connection refusal: bad protocol sockaddr\n");
            close(client);
            continue;
        }
        // Reading and parsing headers
        struct header headers[HEADER_COUNT] = {0};
        char h_buffer[BUFFER_SIZE]={0};
        int h_index = 0;

        // Setting the first header
        headers[h_index].name = h_buffer;
        for (int i = 0; read(client, h_buffer+i, 1); i++) {
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
                headers[++h_index].name = h_buffer + i + 1;
            }
        }

        // Print headers
        for (int i = 0; headers[i].name[0]; i++) {
            printf("%s --> %s\n", headers[i].name, headers[i].value);
        }

        printf("\n");

        // Extract method
        
        int i = 0;

        char* method = headers[0].name;
        while (headers[0].name[i] != ' ') {i++;}
        headers[0].name[i] = 0;
        printf("Request method: %s\n", method);

        // Extract url
        char* url = method + i + 1;
        while (headers[0].name[i] != ' ') {i++;}
        headers[0].name[i] = 0;
        printf("Request url: %s\n", url);

        // Calculating relative path
        char* resource_path = malloc(strlen(url)+1);
        sprintf(resource_path,"./%s", url+1);

        // Send status ok
        char* res = "HTTP/1.1 200 OK\r\n\r\n";
        for (int cursor = 0, t = 0; (t=write(client,res+cursor,strlen(res)-cursor)); cursor+=t);

        // Retrieve url file
        int resource_fd = open(resource_path, O_RDONLY);
        char resource_data[BUFFER_SIZE] = {0};

        int cursor = 0;
        for (int bytes = 0; (bytes = read(resource_fd, resource_data, BUFFER_SIZE-1-cursor)); cursor+=bytes);
        resource_data[cursor+1] = 0;
        free(resource_path);

        // Send file
        int res_size = cursor;
        for (int cursor = 0, t = 0; (t = write(client, resource_data+cursor, res_size-cursor)); cursor+=t);

        close(client);
    }
}
