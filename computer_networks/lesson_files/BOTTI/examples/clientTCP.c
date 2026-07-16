#include<stdio.h>
#include<string.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <arpa/inet.h>
#include <stdlib.h>
#include <unistd.h>

int main(){
    int sockfd = socket(AF_INET, SOCK_STREAM, 0);

    struct sockaddr_in address;

    address.sin_family = AF_INET;
    address.sin_port = htons(8081); //big endian di 8080 (network byte order)

    char* ip = (char *)&address.sin_addr.s_addr;

    ip[0] = 127, ip[1] = 0, ip[2] = 0, ip[3] = 1;

    int c = connect(sockfd,(struct sockaddr*) &address, sizeof(address));

    if(c == 0){
        printf("connessione stabilita\n");
    } else {
        perror("connessione fallita");
        exit(1);
    }

    char buffer[] = "ciao server!!";
    write(sockfd, buffer, strlen(buffer));

    char response[1000];

    read(sockfd, response, sizeof(response));

    printf("il server dice:%s\n", response);
}
