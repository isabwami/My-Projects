#include "MaxHeap.h"

int main() {

    /*
    int array[] = {12, 22, 5, 16, 30, 25, 34};

    int* arrayPtr = array;

    MaxHeap myHeap(arrayPtr, 7);
    */

     

    MaxHeap myHeap;

    myHeap.offer(12);
    myHeap.offer(22);
    myHeap.offer(5);
    myHeap.offer(16);
    myHeap.offer(30);
    myHeap.offer(25);
    myHeap.offer(34);

    myHeap.offer(45);
    myHeap.offer(23);
    myHeap.offer(35);
    myHeap.offer(64);
    myHeap.offer(74);
    myHeap.offer(84);
    myHeap.offer(39);
    myHeap.offer(94);
    myHeap.offer(54);
    myHeap.offer(14);
    myHeap.offer(32);
    myHeap.offer(36);
    myHeap.offer(33);
    myHeap.offer(79);
    

    cout << myHeap << endl;

    MaxHeap yourHeap;
 
    yourHeap = myHeap;

    cout << yourHeap << endl;


    vector<int> sorted = myHeap.sorted();

    cout << "{";

    for (string::size_type i = 0; i < sorted.size(); i++) {
        if (i == sorted.size() - 1) {
            cout << sorted[i];
        } else {
            cout << sorted[i] << ", ";
        }
    }

    cout << "}" << endl;



}
