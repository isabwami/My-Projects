#include "AVLTree.h"

using namespace std;

int main() {

    AVLTree myTree;

    /*
    // insert test
    bool insert = myTree.insert(40, "forty");
    
    insert = myTree.insert(20, "twenty");
    
    insert = myTree.insert(50, "fifty");

    insert = myTree.insert(45, "forty-five");
    
    insert = myTree.insert(60, "sixty");
    
    insert = myTree.insert(10, "ten");
    
    insert = myTree.insert(9, "nine");
    */

   bool insert = myTree.insert(1, "one");
   insert = myTree.insert(2, "two");
   cout << myTree << endl << endl;
   insert = myTree.insert(3, "three");
   cout << myTree << endl << endl;
   insert = myTree.insert(4, "four");
   cout << myTree << endl << endl;
   insert = myTree.insert(5, "five");
   cout << myTree << endl << endl;
   insert = myTree.insert(6, "six");
   cout << myTree << endl << endl;
   insert = myTree.insert(7, "seven");
   cout << myTree << endl << endl;
   insert = myTree.insert(8, "eight");
   cout << myTree << endl << endl;
   insert = myTree.insert(9, "nine");
   cout << myTree << endl << endl;
   insert = myTree.insert(10, "ten");
   cout << myTree << endl << endl;
   insert = myTree.insert(11, "eleven");
   cout << myTree << endl << endl;
   insert = myTree.insert(12, "twelve");
   cout << myTree << endl << endl;
   insert = myTree.insert(13, "thirteen");
   cout << myTree << endl << endl;
   insert = myTree.insert(14, "fourteen");
   cout << myTree << endl << endl;
   insert = myTree.insert(15, "fifteen");
   cout << myTree << endl << endl;
   insert = myTree.insert(16, "sixteen");
   cout << myTree << endl << endl;
   insert = myTree.insert(17, "seventeen");
   cout << myTree << endl << endl;
   insert = myTree.insert(18, "eighteen");
   cout << myTree << endl << endl;
   insert = myTree.insert(19, "nineteen");
   cout << myTree << endl << endl;
   insert = myTree.insert(20, "twenty");
   cout << myTree << endl << endl;

   cout << myTree << endl;
}
