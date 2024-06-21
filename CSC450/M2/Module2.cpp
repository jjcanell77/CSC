#include <iostream>
#include <cstring>

int main() {
     char src[] = "Hello, World!";
     char dest[6];

     // Using strncpy to prevent buffer overflow
     strncpy(dest, src, sizeof(dest) - 1);
     dest[sizeof(dest) - 1] = '\0'; // Ensuring null-termination

     std::cout << "Destination string: " << dest << std::endl;

     return 0;
}