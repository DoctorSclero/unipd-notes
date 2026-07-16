#include<string.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <netinet/ip.h> /* superset of previous */
#include<stdio.h>
#include <sys/types.h>          /* See NOTES */
#include <sys/socket.h>
#include <unistd.h>

struct sockaddr_in  server;

short int girashort (short int a){
	short int b = 1;
	char *p = (char*) &b;
    //printf("girashort %x\n",a);
    // printf("girashort %x\n",a<<8 &0xffff);
    // printf("girashort %x\n",(a>>8 & 0x00ff));
	if(*p) return (a<<8&0xffff) + (a>>8 & 0x00ff);
	return a;
}
struct header{ 
        char * n; 
        char * v;
        } h[100];

char hbuf[10000];
char entitybody[1000000];
int t,counter;

int sonouguali ( char * s1, char * s2 )
{
    int i = 0; 
    do if (s1[i] != s2[i]) return 0; while ((s1[i++]!=0)) ;
    return 1; 
}

int stringaintero ( char * s){
int tot=0;
for(int i =0; (s[i]>='0') && (s[i]<='9'); i++)
    tot = tot * 10 + s[i]-'0' ; 
return tot;
}

int main()
{

char primiDuePunti;
int i,j,lunghezza;
int s ;
unsigned char * p;
int x; 
const char * req = "GET / HTTP/1.1\r\n\r\n";
if ((s = socket(AF_INET, SOCK_STREAM, 0)) == -1) {
    perror("Socket Fallita");
    return 0;
   }
server.sin_family=AF_INET;
server.sin_port=girashort(80);
p = (char*) & server.sin_addr.s_addr ;
p[0]=142;p[1]=250;p[2]=151;p[3]=147; // 142.250.151.147
if( -1 == connect(s, (struct sockaddr *) &server, sizeof(struct sockaddr_in))){
    perror("Connect Fallita");
    return 0;
}



for(counter=0; t=write(s,req+counter,strlen(req)-counter); counter+=t);

h[0].n = hbuf+1;
hbuf[0]=0;
primiDuePunti=0;
for (j=0,i=1; read(s,hbuf+i,1);i++){
   if (hbuf[i]==':' && !primiDuePunti){
      hbuf[i]='\0';
      h[j].v=hbuf+i+1;
      primiDuePunti=1;
   }
   if (hbuf[i]=='\n' && hbuf[i-1]=='\r') {
       hbuf[i-1]=0;
       if(!h[j].n[0]) break;
       h[++j].n=hbuf+i+1;
       primiDuePunti=0;
    }
}

lunghezza = 10000;
for(i=0;h[i].n[0];i++){
    if(sonouguali(h[i].n,"Content-Length")){
        lunghezza = stringaintero(h[i].v+1);
        printf("lunghezza = %d\n", lunghezza);
    }
    printf("%s ---> %s\n",h[i].n,h[i].v);
}

for(counter=0; t = read(s,entitybody+counter,lunghezza-counter); counter+=t);

entitybody[counter]=0;

printf("Entity body : %d\n",counter );

printf("%s\n",entitybody);
//printf("%x\n",girashort(0x1234));

/* man 7 ip
 * struct sockaddr_in {
               sa_family_t    sin_family; // address family: AF_INET 
               in_port_t      sin_port;   // port in network byte order
               struct in_addr sin_addr;   // internet address 
           };

           // Internet address.
           struct in_addr {
               uint32_t       s_addr;    //  address in network byte order
           };
*/



}

