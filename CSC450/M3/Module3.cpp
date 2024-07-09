#include <iostream>
using namespace std;

int main() {   
     int size = 5;
     int arr[size] = { 1, 2, 3, 4, 5 };
     int* ptr = arr; 

     cout << "the address of num: " << ptr << endl; // shows us the address of num
     for (int i = 0; i < size; ++i) { 
          cout << "the value of num at index " << i << ": " << *(ptr + i) << endl; // shows the value of num
     }
     return 0;
}