#include<stdio.h>

int main(int argc, char * argv[], char * env[])
{
int i;
for(i=0;i<argc;i++)
    printf("argomento: %s\n", argv[i]);
for(i=0;env[i];i++)
    printf("environment: %s\n", env[i]);
       
} 
