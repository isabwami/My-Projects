#pragma once

#include <iostream>
#include <exception>
#include <vector>

using namespace std;

typedef int keyType;
typedef string valueType;

class AVLTree {
    public:

    /// @brief  creates a new empty AVL tree
    AVLTree() : root(nullptr), numNodes(0) { }
    
    /// @brief creates a new AVL tree and copies the contents of another tree
    /// @param t the tree to be copied
    AVLTree(const AVLTree& t);

    /// @brief destroys all nodes in the tree and releases all memory associated with the tree
    ~AVLTree();

    /// @brief the current tree is released and replaced by a deep copy of another tree. A reference to the copied tree is returned
    /// @param t the tree to be copied
    /// @return this tree with elements from the other tree copied into 
    AVLTree& operator=(const AVLTree& t);

    /// @brief allows the AVL Tree to be printed using the << operator
    /// @param os the output stream to which the printable AVL Tree will be sent
    /// @param me the AVL Tree to be printed
    /// @return reference to the output stream
    friend ostream& operator<<(ostream& os, const AVLTree& me);

    /// @brief inserts a node with the given key and value as long as a node with the same key does not already exist in the tree
    /// @param key the key of the node to be inserted
    /// @param value the value of the node to be inserted
    /// @return boolean value stating whether a node has been inserted
    bool insert(keyType key, valueType value);

    /// @brief searches the tree for a key and replaces its value with the given value
    /// @param key the key of the node to be found 
    /// @param value the value that will replace the value of the found node
    /// @return boolean value stating whether the desired node was found
    bool find(keyType key, valueType& value);

    /// @brief finds all nodes in the tree with keys between lowkey and highkey and adds their value to a vector
    /// @param lowkey the lower limit of the keys being searched for
    /// @param highkey the upper limit of the keys being searched for
    /// @return the vector containing all found values
    vector<valueType> findRange(int lowkey, int highkey);

    /// @brief returns the size of the tree
    /// @return size of tree
    int getSize();

    /// @brief returns the height of the tree
    /// @return height of the tree
    int getHeight();

    /// @brief prints the AVL Tree using the printHelper function
    /// @param os the output stream to which the AVL Tree will be sent
    void print(ostream& os) const;

    private:
        ///@brief private inner class for the tree nodes
        class treeNode {
            public:

            treeNode* leftChild;
            treeNode* rightChild;
            keyType key;
            valueType value;
            int height;

            /// @brief empty constructor
            treeNode() : leftChild(nullptr), rightChild(nullptr) { }
            
            /// @brief parameterized constructor
            /// @param newKey node's key
            /// @param newValue node's value
            treeNode(keyType newKey, valueType newValue) : leftChild(nullptr), rightChild(nullptr), key (newKey), value(newValue), height(0) { }

            /// @brief node's destructor
            ~treeNode() { }

            /// @brief recalculate this node's height
            void fixHeight();

        };
    
    treeNode* root;
    int numNodes;

    /// @brief contains the logic behind the insert function
    /// @param key the key of the node to be inserted
    /// @param value the value of the node to be inserted
    /// @param current pointer reference to the current node being tested for insertion 
    /// @return boolean value indicating whether a node has been inserted
    bool insertHelper(keyType, valueType, treeNode*&);

    /// @brief contains the logic behind the find function
    /// @param key the key of the node to be found
    /// @param value the value that will replace the value of the found node
    /// @param current pointer reference to the current node being tested 
    /// @return boolean value indicating whether a node has been found and its value replaced
    bool findHelper(keyType, valueType, treeNode*&);

    /// @brief Contains the logic behind the findRange function
    /// @param lowkey the lower limit of the keys being searched for
    /// @param highkey the upper limit of the keys being searched for
    /// @param current pointer reference to the current node being tested
    /// @param valuesInRange the vector containing all found values
    /// @return the vector containing all found values
    vector<valueType> findRangeHelper(int lowkey, int highkey, treeNode*&, vector<valueType>&);

    /// @brief contains the logic behind printing the AVL tree in sideways via in-order traversal
    /// @param currrent the current node to be printed
    /// @param os the output stream to which the AVL Tree will be sent
    void printHelper(treeNode*, ostream& os) const;

    /// @brief makes this tree a deep copy of another tree
    /// @param current a pointer reference to the current node being tested/inserted
    void copyHelper(treeNode*&);

    /// @brief recalculate the current node's balance
    /// @param current pointer reference to the current node
    /// @return balance of the current node
    int setBalance(treeNode*& current);
    
    /// @brief checks the current node's balance and performs rotations if necessary
    /// @param current the current node being tested
    void rebalance(treeNode*& current);

    /// @brief performs a single left rotation at the specified problem node
    /// @param current the deepest unbalanced node
    void singleLeftRotate(treeNode*& current);

    /// @brief performs a single right rotation at the specified problem node
    /// @param problemNode the deepest unbalanced node
    void singleRightRotate(treeNode*& problemNode);

    /// @brief performs a double left rotation at the specified problem node
    /// @param problemNode the deepest unbalanced node
    void doubleLeftRotate(treeNode*& problemNode);

    /// @brief performs a double right rotation at the specified problem node
    /// @param problemNode the deepest unbalanced node
    void doubleRightRotate(treeNode*& problemNode);

    /// @brief traverses through the tree, releasing every node from the heap
    /// @param current a pointer reference to the current node being tested and/or deleted
    void deleteTree(treeNode*& current);
    
};
