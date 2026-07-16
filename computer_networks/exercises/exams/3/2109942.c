#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/types.h>          /* See NOTES */
#include <sys/socket.h>
#include <errno.h>
#include <arpa/inet.h>
#include <stdint.h>
#include <unistd.h>
#include <netdb.h>
#include <netinet/in.h>
#include <sys/types.h>
#include <signal.h>

int pid;
struct sockaddr_in local, remote, server;
char request[10000];
char request2[10000];
char response[1000];
char response2[10000];

struct header {
	char * n;
	char * v;
} h[100];

struct host {
	char *name;
	unsigned int port;
} hosts[2] = {
	{"www.sito1.com", 8888},
	{"www.sito2.com", 8889}
};

struct hostent *he;

void writeBytes(int fd, char *message, int size) {
	int c, b = 0;
	for (c = 0; b = write(fd, message+c, size-c); c+=b);
}

int main()
{
	char hbuffer[10000];
	char buffer[2000];
	char * reqline;
	char * method, *url, *ver, *scheme, *hostname, *port;
	char * filename;
	FILE * fin;
	int c;
	int n;
	int i,j,t, s,s2,s3;
	int yes = 1;
	int len;

	// Creating socket
	if (( s = socket(AF_INET, SOCK_STREAM, 0 )) == -1) { 
		printf("errno = %d\n",errno);
		perror("Socket Fallita");
		return -1;
	}

	// Set binding address
	local.sin_family = AF_INET;
	local.sin_port = htons(20161);
	local.sin_addr.s_addr = 0;

	t = setsockopt(s,SOL_SOCKET,SO_REUSEADDR,&yes,sizeof(int));
	if ( t == -1) {
		perror("setsockopt fallita"); 
		return 1;
	}

	if ( -1 == bind(s, (struct sockaddr *)&local,sizeof(struct sockaddr_in))) { 
		perror("Bind Fallita");
		return -1;
	}

	if ( -1 == listen(s,10)) {
		perror("Listen Fallita");
		return -1;
	}

	// Get remote address
	remote.sin_family = AF_INET;
	remote.sin_port = htons(0);
	remote.sin_addr.s_addr = 0;

	len = sizeof(struct sockaddr_in);
	while ( 1 ){
		// Accepting Incoming connection
		s2=accept(s,(struct sockaddr *)&remote,&len);
		printf("Remote address: %.8X\n",remote.sin_addr.s_addr);
		// If parent process ignore following instructions
		if (fork()) {
            close(s2);
            continue;
        }
		if(s2 == -1){perror("Accept fallita"); exit(1);}
		// Header buffer initialization
		bzero(hbuffer,10000);
		bzero(h,100*sizeof(struct header));

		// Header parsing
		reqline = h[0].n = hbuffer;
		for (i=0,j=0; read(s2,hbuffer+i,1); i++) {
			printf("%c",hbuffer[i]);
			if(hbuffer[i]=='\n' && hbuffer[i-1]=='\r'){
				hbuffer[i-1]=0; // Termino il token attuale
				if (!h[j].n[0]) break;
				h[++j].n=hbuffer+i+1;
			}
			if (hbuffer[i]==':' && !h[j].v && j>0){
				hbuffer[i]=0;
				h[j].v = hbuffer + i + 1;
			}
		}

		printf("Request line: %s\n",reqline);
		method = reqline;
		for(i=0;i<100 && reqline[i]!=' ';i++); reqline[i++]=0; 
		url=reqline+i;
		for(;i<100 && reqline[i]!=' ';i++); reqline[i++]=0; 
		ver=reqline+i;
		for(;i<100 && reqline[i]!='\r';i++); reqline[i++]=0; 

		// Routing management
		if ( !strcmp(method,"GET") ){

			/**
			// URL parsing
			scheme=url;
			// GET http://www.aaa.com/file/file 
			printf("url=%s\n",url);
			// parsing schema
			for(i=0;url[i]!=':' && url[i] ;i++);
			if(url[i]==':') url[i++]=0;
			else {
				printf("Parse error, expected ':'");
				exit(1);
			}
			// parsing host skipping // to get to host
			if(url[i]!='/' || url[i+1] !='/') {
				printf("Parse error, expected '//'");
				exit(1);
			}
			i=i+2; hostname=url+i;
			// parsing filename
			for(;url[i]!='/'&& url[i];i++);	
			if(url[i]=='/') url[i++]=0;
			else {printf("Parse error, expected '/'"); exit(1);}
			filename = url+i;
			printf("Schema: %s, hostname: %s, filename: %s\n",scheme,hostname,filename); 
			*/

			// Reading Host Header
			char *host;
			for (i = 0; h[i].n[0] && strcmp(h[i].n, "Host"); i++);

			if (h[i].n[0]) {
				host = h[i].v+1;
                // Stripping port number
                for (j = 0; h[i].v[j]; j++) {
                    if (h[i].v[j]==':') {
                        h[i].v[j] = 0;
                        break;
                    }
                }
				printf("[i] Host header found: %s: %s\n", h[i].n, h[i].v+1);
			} else {
				char *err_502 = "HTTP/1.1 502 Bad Gateway\r\n\r\n<html><h1>Bad Gateway</h1></html>";
				writeBytes(s2, err_502, strlen(err_502));
				exit(0);
			}

			// Host Lookup
			int found = 0;
			for (i = 0; i < 2 && !found; i++) {
				found = strcmp(host, hosts[i].name)==0;
			}
            i--;

			if (!found) {
				printf("[i] Host not found\n");
				char *err_502 = "HTTP/1.1 502 Bad Gateway\r\n\r\n<html><h1>Bad Gateway</h1></html>";
				writeBytes(s2, err_502, strlen(err_502));
				exit(0);
			}

			printf("[i] Host found, connecting client to it...\n");
				
			// server socket creation
			if (( s3 = socket(AF_INET, SOCK_STREAM, 0 )) == -1)
			{ printf("errno = %d\n",errno); perror("Socket Fallita"); exit(-1); }

			// Remote server configuration
			server.sin_family = AF_INET;
			server.sin_port = htons(hosts[i].port);
			server.sin_addr.s_addr = *(unsigned int *)(gethostbyname("127.0.0.1")->h_addr);

			// Connection
			if(-1 == connect(s3,(struct sockaddr *) &server, sizeof(struct sockaddr_in))) {
                perror("Connect Fallita"); Connection refuse
				char *err_502 = "HTTP/1.1 502 Bad Gateway\r\n\r\n<html><h1>Bad Gateway</h1></html>";
				writeBytes(s2, err_502, strlen(err_502));
                exit(1);
            }	

			
			// setting up server http connection
			sprintf(request,"GET /%s HTTP/1.1\r\nHost:%s\r\nConnection:close\r\n\r\n",filename,hostname);
			printf("%s\n",request);
			writeBytes(s3,request,strlen(request));
			while ( t=read(s3,buffer,2000))
				writeBytes(s2,buffer,t);
			close(s3);

		} else if(!strcmp("CONNECT",method)) { // it is a connect  host:port 
			hostname=url;
			for(i=0;url[i]!=':';i++); url[i]=0;
			port=url+i+1;
			printf("hostname:%s, port:%s\n",hostname,port);
			he = gethostbyname(hostname);
			if (he == NULL) { printf("Gethostbyname Fallita\n"); return 1;}
			printf(
				"Connecting to address = %u.%u.%u.%u\n",
				(unsigned char ) he->h_addr[0],
				(unsigned char ) he->h_addr[1],
				(unsigned char ) he->h_addr[2],
				(unsigned char ) he->h_addr[3]
			); 			

			s3=socket(AF_INET,SOCK_STREAM,0);

			if(s3==-1){perror("Socket to server fallita"); return 1;}
			server.sin_family=AF_INET;
			server.sin_port=htons((unsigned short)atoi(port));
			server.sin_addr.s_addr=*(unsigned int*) he->h_addr;			
			t=connect(s3,(struct sockaddr *)&server,sizeof(struct sockaddr_in));		
			if(t==-1){perror("Connect to server fallita"); exit(0);}
			sprintf(response,"HTTP/1.1 200 Established\r\n\r\n");
			write(s2,response,strlen(response));
			// <==============
			if(!(pid=fork())){ //Child
				while(t=read(s2,request2,2000)){	
					write(s3,request2,t);
					//printf("CL >>>(%d)%s \n",t,hostname); //SOLO PER CHECK
				}	
				exit(0);
			}
			else { //Parent	
				while(t=read(s3,response2,2000)){	
					write(s2,response2,t);
					//printf("CL <<<(%d)%s \n",t,hostname);
				}	
				kill(pid,SIGTERM);
				close(s3);
			}	
		}	
		else {
			sprintf(response,"HTTP/1.1 501 Not Implemented\r\n\r\n");
			write(s2,response,strlen(response));
		}
		close(s2);
		exit(1);
	}
	close(s);
}
