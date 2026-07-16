#include<stdio.h>
#include<string.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <arpa/inet.h>
#include <stdlib.h>
#include <unistd.h>
#include <fcntl.h>

int inviaByte(int fd, char *buffer, int numeroByte){
     int byteScritti = 0;
     int m =0;
     while(byteScritti < numeroByte){
         m = write(fd, buffer + byteScritti, numeroByte - byteScritti);
         byteScritti += m;
      }
}

int main(){
    struct header {
          char *n;
          char *v;
      };
 
      struct header h[100];

    int sockfd = socket(AF_INET, SOCK_STREAM, 0);

    int opt = 1;
    setsockopt(sockfd, SOL_SOCKET, SO_REUSEADDR, &opt, sizeof(opt));

    struct sockaddr_in address;
    address.sin_family = AF_INET;
    address.sin_port = htons(9012);
    address.sin_addr.s_addr = INADDR_ANY;

    int s = bind(sockfd,(struct sockaddr *) &address, sizeof(address));

    if(s != 0){
        perror("bind fallita");
        exit(1);
     } else {
        printf("bind avvento con successo");
        fflush(stdout);
    }

    int l = listen(sockfd, 5);


    printf("il server è pronto per l'accept");
    fflush(stdout);
    while(1) {


       
        int clientSockId = accept(sockfd, NULL, NULL);

        int forkId = fork();
        if(forkId == 0){
        printf("accept effettuata");

        char buffer[10000];

        printf("mi preparo a leggere\n");
        fflush(stdout);
        int n = 0;
        int lettoNomeHeader = 0;
        int byteLetti = 0;
        int headerIndex = 0;
        while((n += read(clientSockId, buffer + n, 1))> 0 ){
            //printf("%c", buffer[n - 1]);
            if(buffer[n -1] == '\n' && buffer[n - 2] == '\r'){
                  if(buffer[n - 4] == 0){
                      //body = buffer + byteLetti + 1;
                      break;
                  }
                  lettoNomeHeader = 0;
                  buffer[n - 2] = 0;
                  h[headerIndex].n = buffer + n ;
              } else if(!lettoNomeHeader && buffer[n - 1] == ':'){
                  lettoNomeHeader = 1;
                  buffer[n - 1] = 0;
                  h[headerIndex++].v = buffer + n;
              }
                   
        }
        
        char *contentLengthValue;
        int contentLength = 0;
        printf("stampo gli headers");
        for(int i = 0; i < headerIndex; i++){
            if(strcmp(h[i].n, "Content-Length") == 0){
                sscanf(h[i].v, "%d", &contentLength);
                contentLengthValue = h[i].v + 1;
            }
            printf("%s:%s\n", h[i].n, h[i].v);
        }

        printf("content length = %d\n", contentLength);
        char *requestLine = buffer;
        printf("request line:%s", requestLine);

        char method[10], uri[100], version[10];

        sscanf(requestLine, "%s %s %s", method, uri, version);

        printf("method:%s\n", method);
        printf("uri:%s\n", uri);
        printf("version:%s\n", version);

     

        char response[1000] = "HTTP/1.1 200 OK\r\nTransfer-Encoding:chunked\r\n\r\n";
        
        if(strcmp(method, "GET") == 0){
            if(strcmp(uri,"/") == 0){
                sprintf(uri, "/index.html");         
            } 


            if(memcmp(uri, "/cgi-bin",8)==0){
               int pid = fork();
                if(pid == 0){
                    char *queryString = NULL;

                    int i;
                    for( i = 0; uri[i] != 0 && uri[i] != '?'; i++){}

                    if(uri[i] == '?'){
                        uri[i] = 0;
                        printf("il valore della uri = %s\n\n", uri);
                        queryString = uri + i + 1;
                        printf("il valore della queryString = %s\n\n", queryString);
                        setenv("QUERY_STRING", queryString, 1);
                    
                    }
                    dup2(clientSockId, 0); //stdin
                    dup2(clientSockId, 1); //stdout;
                    setenv("METHOD", "GET", 1);
                    printf("HTTP/1.1 200 OK\r\n\r\n");
                    execv(uri + 1, NULL);

                } else {
                    waitpid(pid);
                }          
               //printf(response, "HTTP/1.1 404 Not Found\r\n\r\n<html>PAGINA NON TROVATA!</html");
               //inviaByte(clientSockId, response, strlen(response));

            } else {
            int fd = open(uri + 1, O_RDONLY);
            if(fd < 0){
                inviaByte(clientSockId, response, strlen(response));
                sprintf(response, "HTTP/1.1 404 Not Found\r\n\r\n<html>PAGINA NON TROVATA!</html>");
            } else {


                inviaByte(clientSockId, response, strlen(response));
                int m = 0;
                char bufferFile[1024];

                while((m = read(fd, bufferFile, sizeof(bufferFile))) > 0){
                    char chunkSize[100];
                    sprintf(chunkSize, "%x\r\n", m);
                    printf("sto inviando m = %d, 0x%s byte ",m , chunkSize);
                    inviaByte(clientSockId, chunkSize, strlen(chunkSize));
                    inviaByte(clientSockId, bufferFile, m);
                    inviaByte(clientSockId, "\r\n", 2);
                } 
                inviaByte(clientSockId, "0\r\n\r\n", 5);
            }
            }
        } else if(strcmp(method, "POST") == 0) {
            if(memcmp(uri, "/cgi-bin",8)==0){
               int pid = fork();
                if(pid == 0){
                    char *queryString = NULL;

                    int i;
                    for( i = 0; uri[i] != 0 && uri[i] != '?'; i++){}

                    if(uri[i] == '?'){
                        uri[i] = 0;
                        printf("il valore della uri = %s\n\n", uri);
                        queryString = uri + i + 1;
                        printf("il valore della queryString = %s\n\n", queryString);
                        setenv("QUERY_STRING", queryString, 1);

                    }
                    dup2(clientSockId, 0); //stdin
                    dup2(clientSockId, 1); //stdout;
                    setenv("METHOD", "POST", 1);
                    setenv("ContentLength", contentLengthValue, 1);
                    printf("HTTP/1.1 200 OK\r\n\r\n");
                    execv(uri + 1, NULL);

                } else {
                    waitpid(pid);
                }
               //printf(response, "HTTP/1.1 404 Not Found\r\n\r\n<html>PAGINA NON TROVATA!</html");
               //inviaByte(clientSockId, response, strlen(response));

            } else {    
            
            int m = 0;
                char bufferFile[1024];

                while((m += read(clientSockId, bufferFile + m, sizeof(bufferFile))) < contentLength){
                
                }         
                printf("buffer Body:%s\n", bufferFile);
                inviaByte(clientSockId, response, strlen(response));            
            }
        } else {
            sprintf(response, "HTTP/1.1 405 Method Not Allowed\r\n\r\n");
            inviaByte(clientSockId, response, strlen(response));
        }
    
        close(clientSockId);
            return 0;
        } else {
            close(clientSockId);
        }
    }
}
