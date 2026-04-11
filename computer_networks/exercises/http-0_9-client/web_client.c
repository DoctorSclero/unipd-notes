#include "stdio.h"
#include "errno.h"

#include "unistd.h"
#include "netinet/ip.h"
#include "sys/socket.h"
#include "sys/types.h"

struct sockaddr_in server;

int main() {
    //
    // ================= SIGNATURE ================
    //
    // int socket(int domain, int type, int protocol)
    //
    // ================= PARAMETERS ===============
    //
    // domain: the network level protocol
    // type: type of the transport layer protocol
    // protocol: the transport layer protocol
    //
    int sock = socket(AF_INET, SOCK_STREAM, 0);

    if (sock == -1) {
        perror("Socket fallita");
        return errno;
    }

    // Connect to google.com
    // We write write the address byte then we
    // convert it to a int32 casting it to a in_addr_t
    char addr[4] = {142, 250, 151, 147};

    server.sin_family = AF_INET;
    server.sin_port = htons(80);
    server.sin_addr.s_addr = *( in_addr_t* ) addr;

    if (connect(sock, (const struct sockaddr*)&server, sizeof(struct sockaddr)) == -1) {
        perror("Connection failed");
        return errno;
    }

    // The count parameter does not take into account the
    // terminator char '/0'
    int packet_bytes;
    const char* req = "GET /\r\n";

    // Finchè la write non ha finito di scrivere aka finchè non ritorna 0
    // aka ho finito di inviare i dati.
    for (int cursor = 0; packet_bytes = write(sock, req+cursor, 8-cursor); cursor += packet_bytes);

    const int buffer_size = 1000000; // Buffer size 1MB
    char res[buffer_size];
    int res_size = 0;

    // Finchè la read non restituisce 0 AKA: finchè la trasmissione non è terminata
    // faccio un append di buffer_size-cursor elementi. Cursor tiene traccia della
    // posizione dell'ultimo carattere inserito.
    for (int cursor = 0; packet_bytes = read(sock, res+cursor, buffer_size-cursor); cursor += packet_bytes) {
        res_size += packet_bytes;
    }
    res[res_size] = 0;
    printf("%.2f MB from SERVER: \n%s\n", (double)res_size/1000000, res);
}
