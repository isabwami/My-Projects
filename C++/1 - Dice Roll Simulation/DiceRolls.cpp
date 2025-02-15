/*
Immanuel Sabwami
Dice Roll Simulation
Description: this program simulates the number of times each output of 2, 6-sided dice given a number of rolls specified by the user. 
*/

#include <iostream>
#include <random>
#include <cstdlib>

using namespace std;

/*
main: runs every step of the dice roll simulation
    Returns: 0 with the successful execution of the program (integer)
    Parameters: None
*/

int main() {
    
    // local variables
    int num1; // dice 1
    int num2; // dice 2
    int result; // sum of individual dice rolls

    srand(time(NULL)); // seed random number

    cout << "How many rolls? "; // prompt user for number of rolls

    int input; // create variable to hold user input for number of rolls
    cin >> input; // store entered value in variable "input"
    cout << "Simulating " << input << " rolls..." << endl; // let the user know what is being done   

    int array[11]; // create array of 11 elements to track rolled values

    // for loop for array element initialization
    for(int i = 0; i < 11; i++) {
        array[i] = 0; 
    }

    // for loop for dice rolls
    for(int i = 0; i < input; i++) {
        num1 = (rand() % 6) + 1; // roll the first dice

        num2 = (rand() % 6) + 1; // roll the second dice

        result = num1 + num2; // add the two rolled values

        // add 1 to an array index depending on the result
        if (result == 2) {
            array[0]++;
        } else if (result == 3) {
            array[1]++;
        } else if (result == 4) {
            array[2]++;
        } else if (result == 5) {
            array[3]++;
        } else if (result == 6) {
            array[4]++;
        } else if (result == 7) {
            array[5]++;
        } else if (result == 8) {
            array[6]++;
        } else if (result == 9) {
            array[7]++;
        } else if (result == 10) {
            array[8]++;
        } else if (result == 11) {
            array[9]++;
        } else {
            array[10]++;
        }
    }

    // print results
    cout << "Results: " << endl; 

    for(int i = 0; i < 11; i++) {
        if (array[i] == 1) {
            cout << i+2 << " was rolled " << array[i] << " time" << endl; // if this particular value was rolled once, print "[value] was rolled 1 time"   
        } else {
            cout << i+2 << " was rolled " << array[i] << " times" << endl; // if this particular value was not rolled once, print "[value] was rolled [i] times"
        }
    }

    return 0;
}
