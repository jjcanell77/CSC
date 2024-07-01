#include <iostream>
#include <string>
using namespace std;

int main() {
     cout << "Please Enter different strings of varying length below: " << endl;
     // created an array of strings to hold strings
     string concatenatedStrings[3];
     // used a for loop to handle the three strings of varing length
     for (int i = 0; i < 3; ++i) {
          // declares the  two strings
          string stringOne;
          string stringTwo;

          // prompts user for first string
          cout << "Enter the first string: ";
          getline(cin, stringOne);

          // prompts user for second string
          cout << "Enter the second string: ";
          getline(cin, stringTwo);

          // concatenate the two strings 
          string concatenatedString = stringOne + " " + stringTwo;
          
          // adds new string to array
          concatenatedStrings[i] = concatenatedString;
     }

     // prints the results
     cout << "\nConcatenated strings:\n";
     for (int i = 0; i < 3; ++i) {
          cout << concatenatedStrings[i] << endl;
     }
     return 0;
}
