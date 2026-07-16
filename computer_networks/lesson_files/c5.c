#include<stdio.h>


struct pacchetto 
{
int campo1;
short  campo2;
char campo3;
char campo4;
};

struct pacchetto2 
{
char campo3;
int campo1;
char campo4;
short  campo2;
};

struct pacchetto pkt;
struct pacchetto2 pkt2;

int main()

{

pkt.campo1=4;
pkt.campo2=8;
pkt.campo3=14;
pkt.campo4=24;

printf("Pkt:\n");
printf("Indirizzo Campo 1 %lx \n",&pkt.campo1);
printf("Indirizzo Campo 2 %lx \n",&pkt.campo2);
printf("Indirizzo Campo 3 %lx \n",&pkt.campo3);
printf("Indirizzo Campo 4 %lx \n",&pkt.campo4);

printf("Pkt2:\n");
printf("Indirizzo Campo 3 [char]  %lx \n",&pkt2.campo3);
printf("Indirizzo Campo 1 [int]   %lx \n",&pkt2.campo1);
printf("Indirizzo Campo 4 [char]  %lx \n",&pkt2.campo4);
printf("Indirizzo Campo 2 [short] %lx \n",&pkt2.campo2);

}
