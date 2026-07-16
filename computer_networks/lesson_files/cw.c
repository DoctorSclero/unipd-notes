
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

char response[1000000];
int t,counter;
int main()
{
int s ;
unsigned char * p;
int x; 
const char * req = "GET /\n";
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
for(counter=0; t=write(s,req+counter,6-counter); counter+=t);

for(counter=0; t = read(s,response+counter,1000000-counter); counter+=t);

response[counter]=0;

printf("%s",response);



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

