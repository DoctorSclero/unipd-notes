#include<stdio.h>
#include<unistd.h>
#include<string.h>
#include <stdlib.h>

int main(int argc, char *argv[], char* env[]){
    for(int i = 0; env[i]; i++){
        printf("env: %s\n", env[i]);
    }

    char *method = getenv("METHOD");

    if(strcmp(method, "GET") == 0){
    
    
   
     printf("metodo letto: %s\n", method);
        char buffer[10];
        //read(0, buffer, sizeof(buffer));
        printf("letto:%s", buffer);
        write(1, "ciao", 4); 
     // printf("<html>codice generare dinamicamente!</html>");
    } else if(strcmp(method, "POST") == 0){
        //todo 
        //leggere il body del messaggio
        char *contentLength = getenv("ContentLength");
        int contentLengthValue;
        sscanf(contentLength, "%d", &contentLengthValue);

        char buffer[100];
        read(0, buffer, contentLengthValue);
        printf("letto body della post:%s", buffer);

    }


}
