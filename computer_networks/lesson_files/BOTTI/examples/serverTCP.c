#include<stdio.h>
#include<string.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <arpa/inet.h>
#include <stdlib.h>
#include <unistd.h>


int main(){

    int sockfd = socket(AF_INET, SOCK_STREAM, 0);

    int opt = 1;
    setsockopt(sockfd, SOL_SOCKET, SO_REUSEADDR, &opt, sizeof(opt));

    struct sockaddr_in address;
    address.sin_family = AF_INET;
    address.sin_port = htons(8081);
    address.sin_addr.s_addr = INADDR_ANY;

    int s = bind(sockfd,(struct sockaddr *) &address, sizeof(address));

    if(s != 0){
        perror("bind fallita");
        exit(1);
     } else {
        printf("bind avvento con successo");
    }

    int l = listen(sockfd, 5);


    int clientSockId = accept(sockfd, NULL, NULL);


    char buffer[100];

    printf("mi preparo a leggere\n");
    read(clientSockId, buffer, sizeof(buffer) -1);

    printf("contenuto inviato dal client:%s\n", buffer);

    char response[] = "contenuto di risposta del server.";

    int caratteriDaScrivere = strlen(response);
    int caratteriScritti = 0;
    int writeCount = 0;
    while(caratteriScritti < caratteriDaScrivere){
        
        writeCount = write(clientSockId, response + caratteriScritti, 1);
        caratteriScritti += writeCount;
    }
    printf("caratteri scritti %d\n", caratteriScritti); 


}
