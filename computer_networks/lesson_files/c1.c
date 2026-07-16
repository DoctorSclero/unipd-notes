#include<stdio.h>
#include<stdlib.h>

int main(){

char a;
char b;
char c[5] = {'C','I','A','O','!'};
char * p;

a = 'B';

printf("La variabile a ha il valore %c\n", a );
printf("La variabile a ha il valore %d\n", a );
printf("La variabile a ha il valore %x\n", a );

printf("La variabile a è nella cella di memoria #%lx \n", &a);

p = &a;

printf("P legge a casa di a il valore %d\n", *p);

*p = 'C';

printf("La variabile a non capisce... ora vale %d\n", a);

b = 'F';

printf("La variabile b ha il valore %d\n", b );

*(p+1) = 'Z';

printf("La variabile b ha il valore %d\n", b );

printf("I primi due elementi di c valgono %c e %c\n",c[0],c[1]);

p = &c[0]; 

printf("I primi due elementi di c valgono %c e %c\n",*p, *(p+1) );

p = c;

printf("I primi due elementi di c valgono %c e %c\n", p[0],p[1]);

// c = &a; NON SI PUO' FARE

}
