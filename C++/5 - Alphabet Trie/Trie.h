#pragma once

#include <iostream>
#include <exception>
#include <vector>

using namespace std;

class Trie {
    public:

    /// @brief creates a new empty trie
    Trie();

    /// @brief creates a new trie and copies the contents of another trie
    /// @param t the trie to be copied
    Trie(const Trie& t);

    /// @brief destroys all nodes in the trie and releases all memory associated with the trie
    ~Trie();

    /// @brief the current trie is released and replaced by a deep copy of another trie. A reference to the copied trie is returned
    /// @param t the trie to be copied
    /// @return this trie with elements from the other trie copied into 
    Trie& operator=(const Trie& t);

    /// @brief inserts a word into the trie
    /// @param word word to be inserted
    /// @return boolean value stating whether the given word has been inserted
    bool insert(string word);

    /// @brief counts the number of words in a the tire
    /// @return number of words in the trie
    int count();

    /// @brief counts the number of nodes in the trie
    /// @return number of nodes in the trie
    int getSize();

    /// @brief searches for the given word in the trie
    /// @param word word to be serached for
    /// @return boolean value stating whether the word has been found
    bool find(string word);

    /// @brief searches through the trie for all words beginning with the given string and returns the number of words found
    /// @param word prefix to be searched for
    /// @return number of words beginning with the given prefix
    int completeCount(string word);

    /// @brief searches through the trie for all words beginning with the given string and returns a vector containing them
    /// @param word prefix to be searched for
    /// @return vector containing all words found
    vector<string> complete(string word);

    private:

        class trieNode {
            public:
            
            /// @brief creates a new trie node
            trieNode* charArray[26];
            bool wordEnd;

            trieNode();

        };
    
    trieNode* root;
    int numNodes;

    /// @brief contains the logic behind the count function
    /// @param current pointer to the current node being tested
    /// @param numWords a reference to the number of words in the trie 
    /// @return number of words in the trie
    int countHelper(trieNode* current, int& numWords);

    /// @brief contains the logic behind the complete function
    /// @param current pointer to the current node being tested
    /// @param word prefix to be searched for
    /// @param wordsFound vector containing all words found
    /// @return vector containing all words found
    vector<string> completeHelper(trieNode* current, string word, vector<string>& wordsFound);

    /// @brief makes this trie a deep copy of another trie
    /// @param current a pointer reference to the current node being tested
    void copyHelper(trieNode*& current);

    /// @brief traverses through the trie, releasing every node from the heap
    /// @param current a pointer reference to the current node being tested and/or deleted
    void deleteTrie(trieNode*& current);

};
