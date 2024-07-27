#include <iostream>
#include <thread>
#include <mutex>

using namespace std;

// global counter variable
int counter = 0;
// used to ensure that the counter variable is incremented safely by both threads
mutex mtx;

// function that counts up to 20
void incrementThread() {
     // while loop stops at 20
     while (counter < 20) {
          // ensures thread-safe access to the shared variable
          lock_guard<mutex> lock(mtx);
          ++counter;
          cout << "Counting up: " << counter << endl;
     }
}

// function that count down to 0
void decrementThread() {
     // while loop stops at 0
     while (counter > 0) {
          // ensures thread-safe access to the shared variable
          lock_guard<mutex> lock(mtx);
          --counter;
          cout << "Counting down: " << counter << endl;
     }
}

int main() {
     // creates first thread to count up
     thread thread1(incrementThread);
     // waits for the first thread to finish using join()
     thread1.join();

     // creates second thread to count down
     thread thread2(decrementThread);
     // waits for the second thread to finish
     thread2.join();

     return 0;
}