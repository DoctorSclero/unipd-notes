#include<stdio.h>

int main()
{
int i;
short int c[5]={0,1,2,3,4}; 
short int * p;

for ( i = 0; i < 5; i++ )
    printf("%d: %lx\n",i,&c[i]);

p = c;

for ( i = 0; i < 5; i++ )
    printf("%d: %d\n", i, *(p+i));
}

