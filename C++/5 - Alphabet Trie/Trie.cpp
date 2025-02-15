#include "Trie.h"

// CONSTRUCTORS

/*
Default Constructor: creates an empty Trie
    Returns: N/A
    Parameters: N/A
*/
Trie::Trie() {
    root = new trieNode; // create a root node
    numNodes = 1; // initialize the number of nodes to 1
}

/*
Copy Constructor: creates a deep copy of the trie
    Returns: N/A
    Parameters: 
        t (constant Trie ref) - trie this trie will be a copy of
*/
Trie::Trie(const Trie& t) {

    root = new trieNode; // create a root node for this trie
    numNodes = 1; // initialize the number of nodes to 1

    trieNode* current = t.root; // point a current pointer to the root of the other trie

    copyHelper(current); // copy the contents of the other trie into this one 

}

/*
Destructor: deletes trie and releases associated memory
    Returns: N/A
    Parameters: N/A
*/
Trie::~Trie() {
    deleteTrie(root);
}

/*
Trie Node Default Constructor: creates a trie node
    Returns: N/A
    Parameters: N/A
*/
Trie::trieNode::trieNode() {
    for (int i = 0; i < 26; i++) {
        charArray[i] = nullptr; // set all array elements in the node's array to null pointers
    }
}

// FUNCTIONS

/* 
operator=: deletes this trie and replaces it with a deep copy of another trie 
    Returns: 
        Trie ref - A reference to this trie
    Parameters: 
        t (constant Trie ref) - trie from which data is copied from
*/
Trie& Trie::operator=(const Trie& t) {

    deleteTrie(root); // first release this trie

    trieNode* current = t.root; // point a current pointer to the root of the other trie

    root = new trieNode; // create a root node for this trie
    numNodes = 1; // reset the number of nodes

    copyHelper(current); // copy the contents of the other trie into this trie

    return *this;

}

/*
insert: inserts a word into the trie
    Returns: 
        bool - returns true if the given word has been inserted
    Parameters:
        word (string) - word to be inserted
*/
bool Trie::insert(string word) {

    // if the word is already in the trie, do not insert
    if (find(word)) {
        return false;
    }

    // point a current pointer to the root node for tracking
    trieNode* current = root;

    // traverse through the tree adding pointers where necessary based on the string
    for (string::size_type i = 0; i < word.size(); i++) {
        
        // if there is already a pointer for the current letter being added
        if (current->charArray[word[i] - 'a'] != nullptr) {
            // point current to the existing node and proceed to the next loop iteration
            current = current->charArray[word[i] - 'a'];
            continue;
        } else {
            // otherwise, create a new node
            trieNode* newNode = new trieNode();
            // increment the number of nodes
            numNodes++; 
            // point the pointer for the corresponding letter to the new node
            current->charArray[word[i] - 'a'] = newNode;

            // check whether the current letter is the last letter of the string
            if (i == word.size() - 1) {
                // if so make this node a wordEnd node
                newNode->wordEnd = 1;
            }
            // increment the current pointer
            current = newNode; 
        }
    }
    return true;
}

/*
count: counts the number of words in the trie
    Returns: 
        int - number of words in the trie
    Parameters: N/A
*/
int Trie::count() {
    int numWords = 0;
    return countHelper(root, numWords);
}

/*
countHelper: contains the logic behind the count function
    Returns: 
        int - number of words in the trie
    Parameters: 
        current (trieNode*) - pointer to the current node being tested
        numWords (int ref) - a reference to the number of words in the trie 
*/
int Trie::countHelper(trieNode* current, int& numWords) {

    // if the current node marks the end of a string, increment the number of words
    if (current->wordEnd == true) {
        numWords++;
    }

    // traverse through each character location in this node's array 
    for (int i = 0; i < 26; i++) {
        // if there is a pointer at this array location, follow it
        if (current->charArray[i] != nullptr) {
            numWords = countHelper(current->charArray[i], numWords);
        }
    }

    return numWords;

}

/*
getSize: returns the number of nodes in the trie
    Returns: 
        int - number of nodes in the trie
    Parameters: N/A
*/
int Trie::getSize() {
    return numNodes;
}

/*
find: checks if the given word is in the trie
    Returns: 
        bool - returns true if the given word has been found
    Parameters:
        word (string) - word to be searched for
*/
bool Trie::find(string word) {

    // point a current pointer to the root node for tracking
    trieNode* current = root;

    // traverse through the tree locating each character of the given string
    for (string::size_type i = 0; i < word.size(); i++) {

        // if the pointer in the position of the current character is null
        if (current->charArray[word[i] - 'a'] == nullptr) {
            return false; // the string is not in the trie
        }

        // check whether the current letter is the last letter of the string  
        if (i != word.size() - 1) { 
            // if not, point current to the next character
            current = current->charArray[word[i]-'a']; 
        } else {
            // if so, check whether the next node is a word end node
            if (current->charArray[word[i] - 'a']->wordEnd == false) {
                // note: the last letter will always point to an empty node 
                // that is the word end node
                return false;
            }
        }
    }
    return true;
}

/*
completeCount: searches through the tree for all words beginning with the given string and returns the number of words found
    Returns: 
        int - number of words beginning with the given string
    Parameters:
        word (string) - prefix to be searched for 
*/
int Trie::completeCount(string word) {

    // point a current pointer to the root for tracking
    trieNode* current = root;
    // word count tracker
    int numWords = 0;

    // step through each character of the string and re-assign current if that pointer exists
    for (string::size_type i = 0; i < word.size(); i++) {
        if (current->charArray[word[i] - 'a'] != nullptr) {
            current = current->charArray[word[i] - 'a'];
        } else {
            // if one of the character pointers does not exist, return 0
            return numWords; 
        }
    }

    // return the number of completed words after this character in the trie
    return countHelper(current, numWords);
}

/*
complete: searches through the trie for all words beginning with the given string and returns a vector containing them
    Returns: 
        vector<string> - vector containing all words found
    Parameters:
        word (string) - prefix to be searched for
*/
vector<string> Trie::complete(string word) {

    // initialize vector
    vector<string> wordsFound;

    // point a current pointer to the root for tracking
    trieNode* current = root;

    // check the given string and traverse to the cooresponding trie node
    for (string::size_type i = 0; i < word.size(); i++) {
        if (current->charArray[word[i] - 'a'] != nullptr) {
            current = current->charArray[word[i] - 'a'];      
        } else {
            return wordsFound; // if the string is not in the trie, return an empty vector
        }
    }

    return completeHelper(current, word, wordsFound);

}

/*
completeHelper: contains the logic behind the complete function
    Returns: 
        vector<string> - vector containing all words found
    Parameters:
        current (trieNode*) - pointer to the current node being tested
        word (string) - prefix to be searched for
        wordsFound (vector<string> ref) - vector containing all words found
*/
vector<string> Trie::completeHelper(trieNode* current, string word, vector<string>& wordsFound) {
    
    // check if the current node is a word end 
    if (current->wordEnd) {
        // if so, add the word to the vector
        wordsFound.push_back(word); 
    }

    // check each element in the node
    for (int i = 0; i < 26; i++) {
        if (current->charArray[i] != nullptr) {
            // if the current element is not null, add its corresponding letter to the word that will be added to the vector
            word.push_back((char) i + 'a'); 
            // recursively call completeHelper with the new word and next node
            wordsFound = completeHelper(current->charArray[i], word, wordsFound);
            // When going back up through recursive calls, there is one extra character that is not yet removed
            word.pop_back(); 
        }
    }

    return wordsFound;

}

/*
copyHelper: makes this trie a deep copy of another trie
    Returns: void
    Parameters: 
        current (trieNode* ref) - a pointer reference to the current node being tested
*/
void Trie::copyHelper(trieNode*& current) {

    vector<string> words; // vector containing words to be inserted
    words = completeHelper(current, "", words); // insert all words in t into the vector
    
    // insert all words in the vector into this trie
    for (string::size_type i = 0; i < words.size(); i++) {
        insert(words[i]);
    }

}

/*
deleteTrie: traverses through the trie, releasing every node from the heap
    Returns: void 
    Parameters: 
        current (trieNode* ref) - a pointer reference to the current node being tested and/or deleted
*/
void Trie::deleteTrie(trieNode*& current) {

    // if the current node is null, it has already been deleted
    if (current == nullptr) {
        return;
    }

    // call deleteTrie for each node pointer in the current node's array
    for (int i = 0; i < 26; i++) {
        deleteTrie(current->charArray[i]);
    }

    delete current; // delete this node once all of it's elements are cleared

}

