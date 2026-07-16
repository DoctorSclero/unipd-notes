#include<stdio.h>

int main()
{
unsigned int i;

char c[5]={'C','I','A','O','!'};

char * p ;

p = &c[0];

for ( i = 0; i < 4 ; i++)
    printf("%d: %c\n", i,  *(p+i));

p = c;

for ( i = 0; i < 4 ; i++)
    printf("%d: %c\n", i,  p[i]);


for ( i = 0; i < 4 ; i++)
    printf("%d: %c\n", i,  *(c+i));

// c = &i; ERRORE!

for(i=0; i>=0; i++)
    c[i]=20;


}
