#include "stdio.h"
#include "stdint.h"

/*
    Scrivere una funzione che determina se
    l'architettura corrente utilizza il BIG ENDIAN (network order)
    o LITTLE ENDIAN.
*/

/**
 * Checks endianness of the architecture
 * @returns [true] If little endian [false] if big endian
 */
int8_t endianness() {
    int16_t num = 1;
    return *(int8_t*)&num;
}

/*
    Scrivere una funzinoe che riorganizza uno short int
    in NETWORK ORDER
*/

int16_t hton(int16_t s) {
    // Usiamo una maschera per selezionare i bit interessati
    // potremmo fare una somma ma siccome sappiamo che stiamo sommando
    // le cifre a 0 basta usare un bitwise OR per aumentarne l'efficienza
    if (endianness()) return (s & 0x00ff) << 8 | (s & 0xff00) >> 8;
    return s;
}

int main() {
    if (endianness()) printf("LITTLE ENDIAN\n");
    else printf("BIG ENDIAN\n");

    printf("Original: %04X\n", 0x3245);
    printf("Network Order: %04X\n", hton(0x3245));
    return 0;
} 