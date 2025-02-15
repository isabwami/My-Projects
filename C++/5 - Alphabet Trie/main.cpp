/*
Immanuel Sabwami
Autocomplete
    Description: This is an implementation of an autocomplete program that prompts the user for a prefix and optionally prints all words in a given dictionary file beginning with
                 that prefix. This is done using my implementation of an alphabet trie that has functions that return the list of words as well as the number of words.
    Global Constants: None
*/
#include <fstream> // File syntax from: https://www.udacity.com/blog/2021/05/how-to-read-from-a-file-in-cpp.html 
#include "Trie.h"

/*
main: prompts user for a prefix, and optionally prints all words in a given dictionary file beginning with that prefix
    Returns: 
        int - 0 upon successfull completion
    Parameters: N/A
*/
int main() {

    ifstream wordList ("wordlist_mac.txt"); // open word list file
    string temp; // string to temporarily hold each word 
    Trie dictionary; // trie that will contain all words

    // verify that the file is open
    if (wordList.is_open()) {
        // for each line, insert the word in that line into the trie
        while (wordList.good()) {
            wordList >> temp;
            dictionary.insert(temp);
        }
    }

    string prefix;
    string confirm; // confirmation for whether completions shoudl be shown
    string newLine; // string to check if enter was pressed
    vector<string> vec; // vector that will store all found words

    while (true) {
        cout << "Please enter a word prefix (or press enter to exit): "; // prompt user
        getline(cin, prefix); // store the input in the prefix string

        // check if enter was pressed
        if (prefix.empty()) {
            break; // NOTE: There is a bug where this will always terminate the program on the second itteration
        }

        vec = dictionary.complete(prefix); // find all matching words

        cout << "There are " << dictionary.completeCount(prefix) << " completions for the prefix '" << prefix << "'. Show completions? ";
        cin >> confirm; // store the confirmation in the confirm string

        // if user confirms, print all found words
        if (confirm == "y" || confirm == "yes" || confirm == "Yes") {
            cout << "Completions" << endl;
            cout << "-----------" << endl;
            for (string::size_type i = 0; i < vec.size(); i++) {
                cout << vec[i] << endl;
            }
        }

        cout << endl;
    }

    wordList.close(); // close file
    
}
