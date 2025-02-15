/*
Immanuel Sabwami
AVL Tree
    Description: This is an implementation of an AVL tree that rebalances the tree after each new insertion to ensure that 
                 the AVL tree property is maintained. It also supports finding particular nodes as well as a range of nodes.
    Global Constants: None
*/
#include <iostream>
#include "AVLTree.h"

// CONSTRUCTORS 

/*
Copy Constructor: creates a deep copy of the tree
    Returns: N/A
    Parameters: 
        t (constant AVLTree ref) - tree this tree will be a copy of
*/
AVLTree::AVLTree(const AVLTree& t) {
    // initialize an empty tree
    root = nullptr;
    numNodes = 0;
    
    // create a tree node pointer to track position in tree t
    treeNode* current = t.root;

    // call copyHelper to copy the tree with current pointing to t's root
    copyHelper(current);
}

/*
Destructor: deletes tree and releases associated memory
    Returns: N/A
    Parameters: N/A
*/
AVLTree::~AVLTree() {
    deleteTree(root);
}

// FUNCTIONS

/* 
operator=: delets this tree and replaces it with a deep copy of another tree 
    Returns: 
        AVLTree ref - A reference to this tree
    Parameters: 
        t (constant AVLTree ref) - tree from which data is copied from
*/
AVLTree& AVLTree::operator=(const AVLTree& t) {
    // release this tree
    treeNode* current = root;
    deleteTree(current);

    // copy the contents of t into this tree using copyHelper
    current = t.root;
    copyHelper(current);

    return *this;
}

/*
operator<<: allows the AVL Tree to be printed using the << operator
    Returns:
        os (ostream ref) - reference to the output stream
    Parameters: 
        os (ostream ref) - the output stream to which the printable AVL Tree will be sent
        me (constant AVLTree ref) - the AVL Tree to be printed
*/
ostream& operator<<(ostream& os, const AVLTree& me) {
    // call print with the ostream

    me.print(os);
    return os;
}

/*
insert: inserts a node with the given key and value as long as a node with the same key does not already exist in the tree
    Returns: 
        bool - returns true if a node with the given key and value has been inserted
    Parameters:
        key (keyType) - the key of the node to be inserted
        value (valueType) - the value of the node to be inserted
*/
bool AVLTree::insert(keyType key, valueType value) { 
    // insert the given key and value
    return insertHelper(key, value, root);
}

/*
find: searches the tree for a key and replaces its value with the given value
    Returns:
        bool - returns true if a node with the specified key is found and its value replaced by the given value
    Parameters:
        key (keyType) - the key of the node to be found 
        value (valueType) - the value that will replace the value of the found node
*/
bool AVLTree::find(keyType key, valueType& value) {
    // find the specified node
    return findHelper(key, value, root);
}

/*
findRange: finds all nodes in the tree with keys between lowkey and highkey and adds their value to a vector
    Returns:
        vector<valueType> - the vector containing all found values
    Parameters: 
        lowkey (int) - the lower limit of the keys being searched for
        highkey (int) - the upper limit of the keys being searched for
*/
vector<valueType> AVLTree::findRange(int lowkey, int highkey) {
    // create a vector to store found values
    vector<valueType> valuesInRange;

    // call findRangeHelper to look for values
    valuesInRange = findRangeHelper(lowkey, highkey, root, valuesInRange);

    return valuesInRange;
}

/*
getSize: returns the size of the tree
    Returns:
        int - size of the tree
    Parameters: N/A
*/
int AVLTree::getSize() {
    // return the size value stored in the tree
    return numNodes;
}

/*
getHeight: returns the height of the tree
    Returns:
        int - height of the tree
    Parameters: N/A
*/
int AVLTree::getHeight() {
    // return the height of the root node
    return root->height;
}

/*
print: prints the AVL Tree using the printHelper function
    Returns: void 
    Parameters: 
        os (ostream ref) - the output stream to which the AVL Tree will be sent
*/
void AVLTree::print(ostream& os) const {
    // print the tree
    printHelper(root, os);
}

/*
insertHelper: contains the logic behind the insert function
    Returns:
        bool - returns true if a node has been inserted
    Parameters:
        key (keyType) - the key of the node to be inserted
        value (valueType) - the value of the node to be inserted
        current (treeNode* ref) - pointer reference to the current node being tested for insertion 
*/
bool AVLTree::insertHelper(keyType key, valueType value, treeNode*& current) {
    
    bool returnVal;

    // add the node to the tree if the current pointer lands on a nullptr (empty tree, or child of a leaf node)
    if (current == nullptr) {
        current = new treeNode(key, value);
        returnVal = true;
        numNodes++;
    } else if (key == current->key) {
        // if the key is already in the tree, do not insert
        returnVal = false;
        // recursively call insertHelper with the current node's children until current is null
    } else if (key < current->key) {
        returnVal = insertHelper(key, value, current->leftChild);
    } else {
        returnVal = insertHelper(key, value, current->rightChild);
    }
    
    // recalculate the current node's height and balacne
    current->fixHeight();
    
    // ensure the current node is balanced
    rebalance(current);

    return returnVal;
}

/*
findHelper: contains the logic behind the find function
    Returns:
        bool - returns true if a node has been found and its value replaced
    Parameters:
        key (keyType) - the key of the node to be found
        value (valueType) - the value that will replace the value of the found node
        current (treeNode* ref) - pointer reference to the current node being tested 
*/
bool AVLTree::findHelper(keyType key, valueType value, treeNode*& current) {
    // traverse through the tree either until the desired key is found or until it is determined that the key is not in the tree
    if (current == nullptr) {
        return false;
    } else if (key == current->key) {
        // if the key is found, place the value in the node
        current->value = value;
        return true;
    } else if (key > current->key) {
        // if the desired key is greater than the key, recursively call find with the right subtree
        return findHelper(key, value, current->rightChild);
    } else {
        // if the desired key is less than the key, recursively call find with the left subtree
        return findHelper(key, value, current->leftChild);
    }

    return false;
}

/*
findRangeHelper: Contains the logic behind the findRange function
    Returns:
        vector<valueType> - the vector containing all found values
    Parameters:
        lowkey (int) - the lower limit of the keys being searched for
        highkey (int) - the upper limit of the keys being searched for
        current (treeNode* ref) - pointer reference to the current node being tested
        valuesInRange (vector<valueType) - the vector containing all found values
*/
vector<valueType> AVLTree::findRangeHelper(int lowkey, int highkey, treeNode*& current, vector<valueType>& valuesInRange) {

    if (current == nullptr) {
        // if the current node is null, return the vector
        return valuesInRange;
    } else if (current->key >= lowkey && current->key <= highkey) {
        // if the current node's value is in the range, add the value to the vector and recursively call findRangeHelper with the node's children 
        valuesInRange.push_back(current->value);
        valuesInRange = findRangeHelper(lowkey, highkey, current->leftChild, valuesInRange);
        valuesInRange = findRangeHelper(lowkey, highkey, current->rightChild, valuesInRange);
        return valuesInRange;
    } else {
        if (current->key < lowkey) {
            // if the current node's key is lower than the lower limit, recursively call findRangeHelper with the node's right subtree
            return findRangeHelper(lowkey, highkey, current->rightChild, valuesInRange);
        } else {
            // if the current node's key is higher than the upper limit, recursively call findRangeHelper with the node's left subtree
            return findRangeHelper(lowkey, highkey, current->leftChild, valuesInRange);
        }
    }
}

/*
printHelper: contains the logic behind printing the AVL tree in sideways via in-order traversal
    Returns: void
    Parameters: 
        current (treeNode*) - the current node to be printed
        os (ostream ref) - the output stream to which the AVL Tree will be sent
*/
void AVLTree::printHelper(treeNode* current, ostream& os) const {
    // as long as the current node is not null, print its key and value to the ostream
    if (current == nullptr) return;
    // use in-order traversal from right to left to recursively call printHelper with the current node's children
    printHelper(current->rightChild, os);

    // print the appropriate number of space strings depending on the current node's height to get the sideways AVL tree format
    for (int i = 0; i < root->height - current->height; i++) {
        os << "        ";
    }

    os << current->key << ", " << current->value << endl;
    printHelper(current->leftChild, os);
}

/*
copyHelper: makes this tree a deep copy of another tree
    Returns: void
    Parameters: 
        current (treeNode* ref) - a pointer reference to the current node being tested/inserted
*/
void AVLTree::copyHelper(treeNode*& current) {
    // do not copy the current node if it is null    
    if (current == nullptr) {
        return;
    }

    // insert the current node from tree t and recursively call copyHelper with the node's children
    insert(current->key, current->value);
    copyHelper(current->leftChild);
    copyHelper(current->rightChild);
}

/*
setBalance: recalculate the current node's balance
    Returns: 
        int - balance of the current node
    Parameters: 
        current (treeNode*&) - pointer reference to the current node
*/
int AVLTree::setBalance(treeNode*& current) {
    
    int balance = 0;

    if (current->leftChild == nullptr && current->rightChild == nullptr) {
        // if this is a leaf node, the balance is 0
        return balance;
    } else if (current->leftChild == nullptr && current->rightChild != nullptr) {
        // if the node only has a right child, subtract the right child's height from -1
        balance = (-1) - current->rightChild->height;
    } else if (current->rightChild == nullptr && current->leftChild != nullptr) {
        // if the node only has a left child, subtract -1 from the left child's height
        balance = current->leftChild->height - (-1);
    } else {
        // subtract right subtree's height from the left subtree
        balance = current->leftChild->height - current->rightChild->height;
    }
    return balance;
}

/*
rebalance: checks the current node's balance and performs rotations if necessary
    Returns: void
    Parameters:
        current (treeNode* ref) - the current node being tested
*/
void AVLTree::rebalance(treeNode*& current) {
    
    // find the balance of the current node
    int balance = setBalance(current);

    // if the current node is balanced, do nothing
    if (balance >= -1 && balance <= 1) {
        return;
    } else if (balance < 0 && setBalance(current->rightChild) > 0) {
        // if the current node's balance is negative and its right child's balance is positive, perform a double left rotation
        doubleLeftRotate(current);
    } else if (balance > 0 && setBalance(current->leftChild) < 0) {
        // if the current node's balance is positive and its left child's balance is negative, perform a double right rotation
        doubleRightRotate(current);
    } else {
        // otherwise perform a single rotation based on the sign of the current node's balance
        if (balance > 0) {
            singleRightRotate(current);
        } else {
            singleLeftRotate(current);
        }
    }

    // recalculate the current node's height and balance
    current->fixHeight();
}

/*
singleLeftRotate: performs a single left rotation at the specified problem node
    Returns: void
    Parameters:
        problemNode (treeNode* ref) - the deepest unbalanced node
*/
void AVLTree::singleLeftRotate(treeNode*& problemNode) {
    // make hook node the problem node's right child and create a temp pointer in case the hook node has a left child
    treeNode* hookNode = problemNode->rightChild;
    treeNode* temp = nullptr;

    if (hookNode->leftChild != nullptr) {
        // if the hook node has a left child, point temp to it 
        temp = hookNode->leftChild;
        // make the problem node the hook node's left child
        hookNode->leftChild = problemNode;
        // point the pointer currently pointing to the problem node, at the hook node
        if (problemNode->key == root->key) {
            root = hookNode;
        } else {
            problemNode = hookNode;
        }
        // make the hook node's original left child, the original problem node's right child 
        hookNode->leftChild->rightChild = temp;
    } else {
        // if the hook node does not have a left child, make the problem node the hook node's left child
        hookNode->leftChild = problemNode;
        // point the pointer currently pointing to the problem node, at the hook node
        if (problemNode->key == root->key) {
            root = hookNode;
        } else {
            problemNode = hookNode;
        }
        // make the original problem node's right child a null pointer
        hookNode->leftChild->rightChild = nullptr;
    }

    // recalculate the original hook node's and its left child's height and balance
    hookNode->leftChild->fixHeight();
    hookNode->fixHeight();
}

/*
singleRightRotate: performs a single right rotation at the specified problem node
    Returns: void
    Parameters:
        problemNode (treeNode* ref) - the deepest unbalanced node
*/
void AVLTree::singleRightRotate(treeNode*& problemNode) {
     // make hook node the problem node's left child and create a temp pointer in case the hook node has a right child
    treeNode* hookNode = problemNode->leftChild;
    treeNode* temp = nullptr;

    if (hookNode->rightChild != nullptr) {
        // if the hook node has a right child, point temp to it 
        temp = hookNode->rightChild;
        // make the problem node the hook node's right child
        hookNode->rightChild = problemNode;
        // point the pointer currently pointing to the problem node, at the hook node
        if (problemNode->key == root->key) {
            root = hookNode;
        } else {
            problemNode = hookNode;
        }
        // make the hook node's original right child, the original problem node's left child 
        hookNode->rightChild->leftChild = temp;
    } else {
        // if the hook node does not have a right child, make the problem node the hook node's right child
        hookNode->rightChild = problemNode;
        // point the pointer currently pointing to the problem node, at the hook node
        if (problemNode->key == root->key) {
            root = hookNode;
        } else {
            problemNode = hookNode;
        }
        // make the original problem node's left child a null pointer
        hookNode->rightChild->leftChild = nullptr;
    }
    
    // recalculate the original hook node's and its right child's height and balance
    hookNode->rightChild->fixHeight();
    hookNode->fixHeight();
}

/*
doubleLeftRotate: performs a double left rotation at the specified problem node
    Returns: void
    Parameters:
        problemNode (treeNode* ref) - the deepest unbalanced node
*/
void AVLTree::doubleLeftRotate(treeNode*& problemNode) {
    // perform a signle right rotation with the problem node's right child
    singleRightRotate(problemNode->rightChild);
    // perform a signle left rotation with the problem node
    singleLeftRotate(problemNode);
}

/*
doubleRightRotate: performs a double right rotation at the specified problem node
    Returns: void
    Parameters: 
        problemNode (treeNode* ref) - the deepest unbalanced node
*/
void AVLTree::doubleRightRotate(treeNode*& problemNode) {
    // perform a signle left rotation with the problem node's left child
    singleLeftRotate(problemNode->leftChild);
    // perform a signle right rotation wit the problem node
    singleRightRotate(problemNode);
}

/*
deleteTree: traverses through the tree, releasing every node from the heap
    Returns: void 
    Parameters: 
        current (treeNode* ref) - a pointer reference to the current node being tested and/or deleted
*/
void AVLTree::deleteTree(treeNode*& current) {
    // step through each node in the tree and delete nodes with no children
    if (current == nullptr) {
        return;
    } else if (current->leftChild == nullptr && current->rightChild == nullptr) {
        delete current;
    // if the node has children, recursively call deleteTree with the node's children until the node is deleted
    } else if (current->leftChild == nullptr && current->rightChild != nullptr) {
        deleteTree(current->rightChild);
    } else if (current->rightChild == nullptr && current->leftChild != nullptr) {
        deleteTree(current->leftChild);
    } else {
        deleteTree(current->leftChild);
        deleteTree(current->rightChild);
    }
}

/*
fixHeight: recalculate this node's height
    Returns: void
    Parameters: N/A
*/
void AVLTree::treeNode::fixHeight() {
    if (leftChild == nullptr && rightChild == nullptr) {
        // if this is a leaf node, the height is 0
        height = 0;
        return;
    } else if (leftChild == nullptr && rightChild != nullptr) {
        // if the node only has 1 child, add 1 to the height of the child
        height = rightChild->height + 1;
    } else if (rightChild == nullptr && leftChild != nullptr) {
        height = leftChild->height + 1;
    } else if (leftChild->height > rightChild->height) {
        height = leftChild->height + 1;
    } else {
        // get height of right child and left child and add 1 to the bigger of the two
        height = rightChild->height + 1;
    }
}