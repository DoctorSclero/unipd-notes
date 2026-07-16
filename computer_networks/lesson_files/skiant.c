#include<stdio.h>

int main ()
{
int i;
int c[5];

printf("Sono prima del loop\n");
   for(i=0;i<1000000;i++)
       c[i]=i;
printf("Sono dopo il loop");
}
