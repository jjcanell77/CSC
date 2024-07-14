#include <iostream>
#include <fstream>
#include <string>
#include <algorithm>

using namespace std;

void reverseContent(const string &initialFile, const string &reversedFile, const string &errorMessage) {
     // opens the inital file and checks for errors
     ifstream inFile(initialFile);
     if(!inFile.is_open()){
          // if not then presents error message and exits program.
          cerr << errorMessage << initialFile << endl;
          return;
     }

     // opens the inital file and checks for errors
     ofstream outFile(reversedFile);  // Open the output file for writing
     if(!outFile.is_open()){   
          // if not then presents error message and exits program.   
          cerr << errorMessage << reversedFile << endl;
          return;
     }

     // used to store each line from the inital file
     string line;
     // reads each line in file line by line
     while (getline(inFile, line)){
          // reverses the characters of the line
          reverse(line.begin(), line.end());
          // inserts the reversed line to the output file
          outFile << line << endl;
     }

     // closes files
     inFile.close();
     outFile.close();
}

int main() {
     // declares the file names
     string fileName = "CSC450_CT5_mod5.txt";
     string reversedFilename = "CSC450-mod5-reverse.txt";

     string userInput;
     string errorMessage = "Could not open the file ";
     // opens the inital file in append mode
     ofstream intialFile(fileName, ios::app);
     // checks if the file can be oppened
     if(!intialFile.is_open()){
          // if not then presents error message and exits program.
          cerr << errorMessage << fileName << endl;
          return 1;
     }

     cout << "Enter text to add/append to the file: " << fileName << endl;
     // gets user unput
     getline(cin, userInput);

     // appends the user input to the file
     intialFile << userInput << endl;
     // closes the file
     intialFile.close();  

     // handle reversing the content of the file and saving it to the output file
     reverseContent(fileName, reversedFilename, errorMessage);

     cout << "File saved and reversed to " << reversedFilename << endl;

     return 0;
}