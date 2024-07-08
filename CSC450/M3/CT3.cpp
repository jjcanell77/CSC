#include <iostream>
using namespace std;

int main() {
     // decalres three in vars
     int value1, value2, value3;

     // prompts uses to enter those vars values
     cout << "Please enter three numbers:" << endl;
     // gives values to those vars
     cin >> value1 >> value2 >> value3;

     // creates the integer vars pointers
     int *ptr1 = new int;
     int *ptr2 = new int;
     int *ptr3 = new int;

     // stores the values in the dynamically allocated memory
     *ptr1 = value1;
     *ptr2 = value2;
     *ptr3 = value3;

     // prints the contents of the variables and then deallocates the memory
     cout << "Values entered:\t Pointers value:" << endl;
     cout << "value1: " << value1 <<  "\t " <<  *ptr1 << endl;
     delete ptr1;
     cout << "value2: " << value2 << "\t " << *ptr2 << endl;
     delete ptr2;
     cout << "value3: " << value3 << "\t " << *ptr3 << endl;
     delete ptr3;

     return 0;
}
