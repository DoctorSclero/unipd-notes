#include <stdio.h>
#include <string.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <sys/wait.h>
#include <arpa/inet.h>
#include <stdlib.h>
#include <unistd.h>
#include <fcntl.h>

int inviaByte(int fd, char *buffer, int numeroByte){
	int byteScritti = 0;
	int m =0;
	while(byteScritti < numeroByte){
		m = write(fd, buffer + byteScritti, numeroByte - byteScritti);
		byteScritti += m;
	}
}

int main(){
	struct header {
		char *n;
		char *v;
	};

	struct header h[100];

	int sockfd = socket(AF_INET, SOCK_STREAM, 0);

	// Update sockopt to enable address reuse
	int opt = 1;
	setsockopt(sockfd, SOL_SOCKET, SO_REUSEADDR, &opt, sizeof(opt));

	// Setting socket bind address
	struct sockaddr_in address;
	address.sin_family = AF_INET;
	address.sin_port = htons(9012);
	address.sin_addr.s_addr = INADDR_ANY;

	// Binding the socket
	int s = bind(sockfd,(struct sockaddr *) &address, sizeof(address));

	// Checking for errors
	if(s != 0){
		perror("bind fallita");
		exit(1);
	} else {
		printf("bind avvento con successo");
		fflush(stdout);
	}

	// Listening for connections with a queue of 5
	int l = listen(sockfd, 5);

	// Forcing output of successful listen information
	printf("il server è pronto per l'accept");
	fflush(stdout);

	while(1) {

		// Waiting for new client
		int clientSockId = accept(sockfd, NULL, NULL);

		// Creating a new istance to manage the connection
		int forkId = fork();
		if(forkId == 0) {
			printf("accept effettuata");

			char buffer[10000];

			printf("mi preparo a leggere\n");
			fflush(stdout);

			int n = 0;
			int lettoNomeHeader = 0; // guard was header
			int byteLetti = 0; // request size
			int headerIndex = 0; // number of headers

			// Reading and parsing request headers
			while((n += read(clientSockId, buffer + n, 1))> 0 ){
				//printf("%c", buffer[n - 1]);
				if(buffer[n -1] == '\n' && buffer[n - 2] == '\r'){
					if(buffer[n - 4] == 0){
						//body = buffer + byteLetti + 1;
						break;
					}
					lettoNomeHeader = 0;
					buffer[n - 2] = 0;
					h[headerIndex].n = buffer + n ;
				} else if(!lettoNomeHeader && buffer[n - 1] == ':'){
					lettoNomeHeader = 1;
					buffer[n - 1] = 0;
					h[headerIndex++].v = buffer + n;
				}

			}

			// Preparing for Content-Length management

			// Content-Length
			char *contentLengthValue; // For environment setting
			int contentLength = 0;

			// User-Agent
			char *userAgent;
			
			printf("stampo gli headers");
			for(int i = 0; i < headerIndex; i++){
				// Extract Content-Length if present
				if(strcmp(h[i].n, "Content-Length") == 0){
					// Convert to int the lenght
					sscanf(h[i].v, "%d", &contentLength);
					contentLengthValue = h[i].v + 1;
				}
				if (strcmp(h[i].n, "User-Agent") == 0) {
					userAgent = h[i].v;
				}
				printf("%s:%s\n", h[i].n, h[i].v);
			}

			// Printing header informations

			printf("content length = %d\n", contentLength);
			char *requestLine = buffer;
			printf("request line:%s", requestLine);

			char method[10], uri[100], version[10];

			sscanf(requestLine, "%s %s %s", method, uri, version);

			printf("method:%s\n", method);
			printf("uri:%s\n", uri);
			printf("version:%s\n", version);

			// Adding user agent for debugging purposes
			printf("user agent:%s\n", userAgent);

			// Defaulted to 0: since there is no indication on other user agents
			// I'm treating them like Chrome
			int allowed = 0;
			int stripped = 0;

			// Setting handling flags based on user agent
			if (strstr(userAgent, "Firefox")) {
				// respond and send file
				allowed = 1;
			} else if (strstr(userAgent, "curl")) {
				// strip html tags and send
				allowed = 1;
				stripped = 1;
			}

			char response[1000] = "HTTP/1.1 200 OK\r\nTransfer-Encoding:chunked\r\n\r\n";
			if (allowed) {

				if(strcmp(method, "GET") == 0){
					if(strcmp(uri,"/") == 0){
						sprintf(uri, "/index.html");         
					} 
					
					// CGI route
					if(memcmp(uri, "/cgi-bin",8)==0){
						int pid = fork();
                        // CGI sub process
						if(pid == 0){
							char *queryString = NULL;

							int i;
							for( i = 0; uri[i] != 0 && uri[i] != '?'; i++){}

							if(uri[i] == '?'){
								uri[i] = 0;
								printf("il valore della uri = %s\n\n", uri);
								queryString = uri + i + 1;
								printf("il valore della queryString = %s\n\n", queryString);
								setenv("QUERY_STRING", queryString, 1);

							}
							dup2(clientSockId, 0); //stdin
							dup2(clientSockId, 1); //stdout;
							setenv("METHOD", "GET", 1);
							printf("HTTP/1.1 200 OK\r\n\r\n");
							execv(uri + 1, NULL);

						} else {
							waitpid(pid, NULL, 0);
						}          

					} else {

						// STATIC route

						int fd = open(uri + 1, O_RDONLY); // Open uri file

						if (fd < 0) {
							sprintf(response, "HTTP/1.1 404 Not Found\r\n\r\n<html>PAGINA NON TROVATA!</html>");
                            if (stripped) sprintf(response, "HTTP/1.1 404 Not Found\r\n\r\nPAGINA NON TROVATA!");
							inviaByte(clientSockId, response, strlen(response));

						} else {

							// Send default response (success message)
							inviaByte(clientSockId, response, strlen(response));
							int m = 0;
							char bufferFile[1024];
                            int isTag = 0;

							while((m = read(fd, bufferFile, sizeof(bufferFile))) > 0){

                                printf("New segment: ");
                                for (int i = 0; i < m; i++) {
                                    printf("%c", bufferFile[i]);
                                }
                                printf("\n");

								// Stripped behaviour for curl

								if (stripped) {
									// Additional buffer for ease of implementation
									int cursor = 0;
									for (int i = 0; i < m; i++) {
										// Checking for and stripping tags
										while (isTag || bufferFile[i] == '<') {
                                            printf("Stripping %c\n", bufferFile[i]);
											isTag = bufferFile[i++] != '>';
										}
										// Overwriting file buffer and buffer size
										bufferFile[cursor++] = bufferFile[i];
									}
                                    // Updating chunk size
                                    m = cursor;
								}

								char chunkSize[100];

								sprintf(chunkSize, "%x\r\n", m);
								printf("sto inviando m = %d, 0x%x byte\n", m, m);
								inviaByte(clientSockId, chunkSize, strlen(chunkSize));
								inviaByte(clientSockId, bufferFile, m);
								inviaByte(clientSockId, "\r\n", 2);

							} 

							inviaByte(clientSockId, "0\r\n\r\n", 5);

						}

					}
				} else if(strcmp(method, "POST") == 0) {
					if(memcmp(uri, "/cgi-bin",8)==0){
						int pid = fork();
						if(pid == 0){
							char *queryString = NULL;

							int i;
							for( i = 0; uri[i] != 0 && uri[i] != '?'; i++){}

							if(uri[i] == '?'){
								uri[i] = 0;
								printf("il valore della uri = %s\n\n", uri);
								queryString = uri + i + 1;
								printf("il valore della queryString = %s\n\n", queryString);
								setenv("QUERY_STRING", queryString, 1);
							}

							dup2(clientSockId, 0); //stdin
							dup2(clientSockId, 1); //stdout;
							setenv("METHOD", "POST", 1);
							setenv("ContentLength", contentLengthValue, 1);
							printf("HTTP/1.1 200 OK\r\n\r\n");
							execv(uri + 1, NULL);

						} else {
							waitpid(pid, NULL, 0);
						}
						//printf(response, "HTTP/1.1 404 Not Found\r\n\r\n<html>PAGINA NON TROVATA!</html");
						//inviaByte(clientSockId, response, strlen(response));

					} else {    

						int m = 0;
						char bufferFile[1024];

						while((m += read(clientSockId, bufferFile + m, sizeof(bufferFile))) < contentLength){

						}         

						printf("buffer Body:%s\n", bufferFile);
						inviaByte(clientSockId, response, strlen(response));            
					}
				} else {
					sprintf(response, "HTTP/1.1 405 Method Not Allowed\r\n\r\n");
					inviaByte(clientSockId, response, strlen(response));
				}

				close(clientSockId);
				return 0;
			} else {
				sprintf(response, "HTTP/1.1 406 Not Acceptable\r\n\r\n");
				inviaByte(clientSockId, response, strlen(response));
			}
		} else {
			close(clientSockId);
		}
	}
}
