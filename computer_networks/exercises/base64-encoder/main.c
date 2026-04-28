#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <stdint.h>

/**
 * TODO: implementare base64
 */
char* base64(char* out, char* in, int bytes) {
    int current = 0;
    int32_t block = (int32_t)*in;
    for (int i = 0; i < 3; i++) {
        if (0 <= block & (0xF2000000 >> i*6) <= 25) {
            out[current++] = block & (0xF2000000 >> i*6) + 65;
        } else if (26 <= block & (0xF2000000 >> i*6) <= 51) {
            out[current++] = block & (0xF2000000 >> i*6) + 65 + 7;
        } else {
            out[current++] = block & (0xF2000000 >> i*6) + 48;
        }
    }
    return out;
}

int main() {
    char* s = "ciao";
    char* out = malloc(strlen(s)*2);
    base64(out, s, strlen(s)*2);
    printf("%s\n", out);
    return 0;
}