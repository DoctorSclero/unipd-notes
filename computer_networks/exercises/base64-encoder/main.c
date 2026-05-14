#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <stdint.h>

/**
 * Encodes a string into a base64 string
 * @param out Output string the location of the output.
 * @param in Input string assumes \0 terminator.
 */
char* base64(char* out, unsigned char* in) {
    
    char* mapping = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
    int c = 0;

    for (int i = 0; (c == 0 || out[c-1] != '=') && in[i]; i+=3) {
        out[c++] = mapping[(in[i+0] & 0xFC)>>2];
        out[c++] = mapping[((in[i+0] & 0x03)<<4) + ((in[i+1] & 0xF0)>>4)];
        out[c++] = in[i+1] ? mapping[((in[i+1] & 0x0F)<<2) + ((in[i+2] & 0xC0)>>6)] : '=';
        out[c++] = in[i+1] && in[i+2] ? mapping[in[i+2] & 0x3F] : '=';
    }
    out[c++] = 0;

    return out;
}

int main(int argc, char** argv) {
    if (argc < 2) {
        fprintf(stderr, "Too few arguments\n");
        return -1;
    }
    int arg_len = strlen(argv[1])*5/3;
    argv[arg_len] = 0;
    // Allocating memory for output
    char* out = malloc(arg_len);
    memset(out, 0, arg_len);
    // Printing base64
    printf("%s\n", base64(out, argv[1]));
    free(out);
    return 0;
}
