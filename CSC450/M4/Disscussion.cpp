#include <iostream>
#include <cstring>
using namespace std;

void vulnerableFunction(char *input) {
    char buffer[10];
    strcpy(buffer, input); // <--
    cout << "Buffer contents: " << buffer << endl;
}

int main(int argc, char *argv[]) {
    if (argc < 2) {
        cerr << "Usage: " << argv[0] << " <input>" << endl;
        return 1;
    }
    vulnerableFunction(argv[1]);
    return 0;
}