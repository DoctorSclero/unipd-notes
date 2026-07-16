#include<stdio.h>
#include<string.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <arpa/inet.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/stat.h>
#include <fcntl.h>

int main(){
    struct header {
        char *n;
        char *v;
    };

    struct header h[100];
    char *statusLine;
    char *body;

    int sockfd = socket(AF_INET, SOCK_STREAM, 0);
    
    struct sockaddr_in address;

    address.sin_family = AF_INET;
    address.sin_port = htons(80); //big endian di 8080 (network byte order)

    char* ip = (char *)&address.sin_addr; 
    ip[0] = 217, ip[1] = 61, ip[2] = 15, ip[3] = 222;
    
    int c = connect(sockfd,(struct sockaddr*) &address, sizeof(address));

    if(c == 0){
        printf("connessione stabilita\n");
    } else {
        perror("connessione fallita");
        exit(1);
    }

    char buffer[] = "GET / HTTP/1.1\r\nHost:www.example.it\r\nConnection:keep-alive\r\n\r\n";

    int byteScritti = 0;
    int m =0;
    while(byteScritti < strlen(buffer)){
       m = write(sockfd, buffer + byteScritti, strlen(buffer)- byteScritti);
       byteScritti += m;
    }
  
    printf("ho scritto %d bytes\n", byteScritti);

    char response[1000000];

    int n = 0;
        int byteBody = 0;
      int headerIndex = 0;
      int lettoNomeHeader = 0;
    int byteLetti = 0;
    while( (n = read(sockfd, response + byteLetti, 1)) > 0){
          if(response[byteLetti] == '\n' && response[byteLetti -1] == '\r'){
              if(response[byteLetti - 3] == 0){
                  body = response + byteLetti + 1;
                  break;
              }
              lettoNomeHeader = 0;
              response[byteLetti - 1] = 0;
              h[headerIndex].n = response + byteLetti + 1 ;
          } else if(!lettoNomeHeader && response[byteLetti] == ':'){
              lettoNomeHeader = 1;
              response[byteLetti] = 0;
              h[headerIndex++].v = response + byteLetti + 1;
          }

        byteLetti += n;
    }

       printf("ho letto tuti gli header");
        statusLine = response;

    

    int statusCode;
    char httpVersion[10];
    char statusPhrase[20];
    sscanf(statusLine, "%s %d %s", httpVersion, &statusCode, statusPhrase);
    printf("lo status code = %d\n", statusCode);

    printf("numero di header letti = %d\n", headerIndex - 1);

    for(int i = 0; i < headerIndex -1 ; i++){
        printf("header %s = %s\n", h[i].n, h[i].v);
        if(strcmp("Content-Length", h[i].n) == 0){
            sscanf(h[i].v, "%d", &byteBody);
        }
    }
    
    printf("il numero di caratteri del body = %d\n",byteBody);
    //printf("valore del body:\n%s", body);
    n = 0;
    while( (n += read(sockfd, body + n, 1)) > 0 && n < byteBody){}

    close(sockfd);
    printf("numero di byte letti = %d\n", n);
    printf("la responseLine=%s", statusLine);
    
    int f = open("response.html", O_CREAT | O_WRONLY);
    
    write(f, body, byteBody);
    //printf("il server dice:%s\n", response);
}
