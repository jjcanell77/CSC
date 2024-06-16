#include <iostream>

// Standard namespace declaration 
using std::cout;// added this
using std::endl;// added this
using std::string;// added this

int main() {
     // person's information
     string firstName = "Luke";
     string lastName("Skywalker");
     string streetAddress = "123 Death Star Road";
     string city = "Tattoine";
     string zipCode = "98765";
    
     // Standard Ouput Statement
     cout << "First Name: " << firstName << endl;
     cout << "Last Name: " << lastName << endl;
     cout << "Street Address: " << streetAddress << endl;
     cout << "City: " << city << endl;
     cout << "Zip Code: " << zipCode << endl;

     // Main Function return Statement
     return 0;
}
