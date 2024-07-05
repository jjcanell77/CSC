#include <iostream>
#include <stdexcept>
using namespace std;

void safeInput() {
     char buffer[10];
     cout << "please enter a string max 9 characters: ";
     cin >> buffer;
     if (cin.fail() || cin.gcount() > 10) {
          throw runtime_error("input is too long or invalid");
     }
     cout << "You entered: " << buffer << endl;
}

int main() {
     try {
          safeInput();
     } 
     catch (const runtime_error &e) {
          cerr << "error: " << e.what() << endl;
     }
     return 0;
}