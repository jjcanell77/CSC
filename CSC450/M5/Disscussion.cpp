#include <iostream>
#include <thread>
#include <mutex>
using namespace std;

int counter = 0;
// used to ensure that the counter variable is incremented safely by both threads
mutex mtx;

void increment() {
    for (int i = 0; i < 1000; ++i) {
        // ensures thread-safe access to the shared variable
        lock_guard<mutex> lock(mtx);
        ++counter;
    }
}

int main() {
    thread t1(increment);
    thread t2(increment);

    // waiting for threads to finish
    t1.join();
    t2.join();

    cout << "Final counter value: " << counter << endl;
    return 0;
}