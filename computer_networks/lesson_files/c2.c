#include<stdio.h>
#include<stdlib.h>

char x;
char y;
char z;


char  fattoriale( char x )
{

printf("Fattoriale : indirizzo x: %lx\n", &x);
if ( x == 0 ) return 1;

return x*fattoriale(x-1);

}

int main()
{
unsigned char * p; 
x=y=z=0;
printf("Main: indirizzo x = %lx \n", &x);
printf("Main: indirizzo y = %lx \n", &y);
printf("Main: indirizzo z = %lx \n", &z);
printf("Fattoriale di 5 = %d\n", fattoriale (5)) ;

p = malloc(1024);
printf("Indirizzo della memoria allocata: %lx\n",p);
p = malloc(1024);
printf("Indirizzo della memoria allocata: %lx\n",p);
p = malloc(1024);
printf("Indirizzo della memoria allocata: %lx\n",p);

}



