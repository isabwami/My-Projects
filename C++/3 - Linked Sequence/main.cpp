#include <iostream>
#include "sequence.h"

using namespace std;

int main() {

    
    Sequence mySequence(0);
    mySequence.push_back(20);
    mySequence.push_back(21);
    mySequence.push_back(22);
    mySequence.push_back(23);

    cout << mySequence;

    Sequence mySequence2(mySequence);

    cout << mySequence2;


    //mySequence.pop_back();
}
