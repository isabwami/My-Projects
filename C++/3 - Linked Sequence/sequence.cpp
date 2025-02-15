/*
Immanuel Sabwami
Linked Sequence Data Structure
    Description: This is a data structure that supports random access and dynamic insertion and new element removal, implemented similar to a linked list. 
    Global Constants: None
*/

#include <iostream>
#include "sequence.h"

// CONSTRUCTORS

/*
Default Constructor: creates an empty sequence or a sequence with sz elements
    Returns: N/A
    Parameters: 
        sz (size_type) - size of new sequence
*/
Sequence::Sequence(size_type sz) {
    // default values for head and tail assume list is empty
    head = tail = nullptr;
    numElts = sz; // this is always true regardless of the sequence size

    if (sz > 0) {
        head = tail = new SequenceNode(); // point the head and tail of the sequence to the new first Sequence Node

        // create sz-1 Sequence Nodes since 1 has already been created above
        for (size_type i = 0; i < sz - 1; i++) {
            tail->next = new SequenceNode(); // point the tail's next field to a new Sequence Node
    
            tail->next->prev = tail; // initialize the new Sequence Node's prev pointer to the current tail of the sequence
            tail = tail->next; // move the tail down the list to the newly created Sequence Node
        }
    }
}

/*
Copy Constructor: creates a deep copy of sequence
    Returns: N/A
    Parameters: 
        s (constant Sequence ref) - sequence into which this will be to be copied
*/
Sequence::Sequence(const Sequence& s) {
    // initialize fields to null/0 because sequence is empty
    head = tail = nullptr; 
    numElts = 0;

    SequenceNode* current = s.head; // create a current Sequence Node and point it to the other Sequence's head
    
    // step through the other sequence adding each element to this sequence
    while(current != nullptr) {
        push_back(current->elt);
        current = current->next;
    }
}

/*
Destructor: Deletes sequence and releases associated memory
    Returns: N/A
    Parameters: N/A
*/
Sequence::~Sequence() {
    SequenceNode* current = head; // create a current Sequence Node pointer for tracking 
    while(current != nullptr) {
        SequenceNode* killMeNext = current->next; // create a new pointer that points to the next Sequence Node so that it is not lost when the current one is deleted
        delete current; // delete the current Sequcence Node
        current = killMeNext; // move the current forward to the next Sequence Node
    }
}

// FUNCTIONS

/* 
operator=: delets this sequence and replaces it with a deep copy of another sequence 
    Returns: 
        *this (Sequence) - A pointer to this sequence
    Parameters: 
        s (constant Sequence ref) - sequence from which data is copied from
*/
Sequence& Sequence::operator=(const Sequence& s) {
    // release this sequence
    SequenceNode* current = head; // create a current Sequence Node pointer for tracking 
    while(current != nullptr) {
        SequenceNode* killMeNext = current->next; // create a new pointer that points to the next Sequence Node so that it is not lost when the current one is deleted
        delete current; // delete the current Sequcence Node
        current = killMeNext; // move the current forward to the next Sequence Node
        numElts--;
    }
    
    head = tail = nullptr; // clear the head and tail of the Sequence
    
    current = s.head;

    while (current != nullptr) {
        push_back(current->elt);
        current = current->next;
    }

    return *this;
}

/*
operator[]: gives value at the specified Sequence index
    Returns: 
        elt (value_type ref) - the element at the specified index
    Parameters:
        position (size_type) - the position of the queried element 
*/
Sequence::value_type& Sequence::operator[](size_type position) {

    // throw an exception if the given position is not in the bounds of the sequence
    if (!position_in_bounds(position)) {
        throw exception(); 
    }

    SequenceNode* current = head; // otherwise set current to the head of the sequence

    // step through the list until the given position 
    for(size_type i = 0; i < position; i++) {
        current = current -> next;
    }

    return current->elt; // return the element at that position
}

/*
position_in_bounds: checks if given position is within the bounds of the sequence
    Returns:    
        boolean - true if the position is in within the bounds of the sequence
    Parameters: 
        position (size_type) - the position to be checked
*/
bool Sequence::position_in_bounds(size_type position) {
    size_type numElements = 0; // create an int to count how many elements are in the sequence

    SequenceNode* current = head; // create a current Sequence Node pointer for tracking
    
    // count number of elements in the sequence
    while(current != nullptr) {
        numElements++;
        current = current->next;
    }
    
    // check the position parameter to verify that it is between 0 and the end of the sequence 
    if (position < 0 || position > numElements-1) {
       return false;
    }
    return true;
}

/*
push_back: appends an element to the end of the sequence
    Returns: void
    Parameters:
        value (constant value_type ref) - value to be appended
*/
void Sequence::push_back(const value_type& value) {

    // if the list is empty
    if (head == nullptr && tail == nullptr) {
        head = new SequenceNode();
        
        head->next = nullptr;
        head->prev = nullptr;
        head->elt = value;
        tail = head;
        numElts++;
    } else {
        // If sequence is not empty
        SequenceNode* newNode = tail->next = new SequenceNode(); // create a new Sequence Node pointer and point it as well as the tail's next pointer to the new node that is being added 
        
        // if there is only 1 element in the sequence, set the head's next pointer to the new Sequence Node
        if (numElts == 1) {
            head->next = newNode; 
        }

        newNode->next = nullptr; // initilize the new Sequence Node's next pointer to null 
        newNode->prev = tail; // initialize the new Sequence Node's prev pointer to the current tail
        newNode->elt = value; // initialize the new Sequence Node's value to the given value
        tail = newNode; // move the tail pointer to the new tail of the sequence
        numElts++; // increment the number of elements in the sequence
    }
}

/*
pop_back: removed an element from the end of the sequence
    Returns: void
    Parameters: None
*/
void Sequence::pop_back() {
    // check if sequence is empty
    SequenceNode* current = head;

    if (current == nullptr) {
        throw exception(); // if the sequence is empty, the head will be a null pointer therefore making current a null pointer
    } else {
        // otherwise, delete the last element
        while (current->next != nullptr) {
            current = current->next; // continue walking through the sequence until the last element. The last element will be the element whose next pointer is a null pointer
        }
        SequenceNode* deleteNode = current; // create a new Sequence Node that will point to the element that is to be deleted

        if (current == head) {
            delete current;
            head = tail = nullptr;
        } else {
            current = current->prev; // move current back by 1 element
            
            delete deleteNode; // delete the last element

            current->next = nullptr; // set current's next pointer to be null pointer
            tail = current; // set the tail to be the current element since the last one was deleted
        }
        numElts--; // decrement the number of elements in the sequence
    }
}

/*
insert: insert an element into a specific position in the Sequence
    Returns: void
    Parameters: 
        position (size_type) - the position into which the element will be inserted
        value (value_type) - the value that will be inserted
*/
void Sequence::insert(size_type position, value_type value) {
    
    // if position is out of the bounds of the sequence, throw an exception 
    if (!position_in_bounds(position)) {
        throw exception();
    }

    SequenceNode* current = head; // point a new current node to the head of the sequence for tracking

    // if adding to the beginning of the sequence
    if (position == 0) {
        SequenceNode* newNode = new SequenceNode(); // create the new sequence node that is to be added
        newNode->elt = value; // set the value to the given value
        newNode->next = current; // point its next pointer to the current head of the sequence since it will be placed at the beginning of the sequence

        head = newNode; // set the head of the sequence to the newly created node
        current->prev = newNode; // point the old head of the list's prev pointer to the new head
        return; // done
    }
    
    // if adding anywhere else 
    for (size_type i = 0; i < position; i++) {
        current = current->next; // step through the sequence until the given position is reached
    }

    SequenceNode* newNode = new SequenceNode(); // create the new sequence node that is to be added
    newNode->elt = value; // se the value to the given value
    newNode->next = current; // point its next pointer to the current node since it will be placed before the current node
    newNode->prev = current->prev; // point its prev pointer to the current node's prev pointer, again since it will be placed before the current node

    current->prev->next = newNode; // set the next pointer of the node that was originally before current to the new node
    current->prev = newNode; // set the current node's prev pointer to the new node

    numElts++; // increment the number of elements in the sequence
}

/*
front: returns a reference to the first item in the Sequence
    Returns: 
        elt (value_type ref) - reference to the first item in the sequence
    Parameters: None
*/
const Sequence::value_type& Sequence::front() const {
    
    // if the head is null, the list is empty and an expetion should be thrown
    if (head == nullptr) {
        throw exception();
    }
    
    return head->elt;
}

/*
back: returns a reference to the last item in the Sequence
    Returns:
        elt (value_type ref) - reference to the last item in the sequence
*/
const Sequence::value_type& Sequence::back() const {
    
    // if the head is null, the list is empty and an exception should be thrown
    if (head == nullptr) {
        throw exception();
    }

    return tail->elt;
}

/*
empty: checks whether the sequence is empty
    Returns: 
        boolean - true if the sequence is empty
    Parameters: None
*/
bool Sequence::empty() const {
    
    // check for the head being null
    if (head == nullptr) {
        return true; // if the head is null, the list is empty
    }

    return false;
}

/*
size: returns the size of the sequence 
    Returns: 
        numElements (size_type) - the number of elements in the sequence
    Parameters: None
*/
Sequence::size_type Sequence::size() const {
    
    size_type numElements = 0; // variable for keeping count of elements

    SequenceNode* current = head; // create a current pointer for tracking and point it to the head of the sequence

    // step through the sequence, and increment the number of elements for every step in which current is not a null pointer
    while (current != nullptr) {
        current = current->next;
        numElements++;
    }

    return numElements; 
}

/*
clear: deletes sequence elements and releases associated memory
    Returns: void
    Parameters: None
*/
void Sequence::clear() {
    
    SequenceNode* current = head; // create a current Sequence Node pointer for tracking and point it to the head of the sequence
    
    // step through the sequence deleting each node
    while(current != nullptr) {
        SequenceNode* killMeNext = current->next; // create a new pointer that points to the next Sequence Node so that it is not lost when the current one is deleted
        delete current; // delete the current Sequcence Node
        current = killMeNext; // move the current forward to the next Sequence Node
    }

    head = tail = nullptr; // set head and tail to null pointer since the nodes have been deleted and they would be pointing at a random piece of memory otherwise
}

/*
erase: removes a specified chunch of the sequence (from position to (position + count-1))
    Returns: void
    Parameters: 
        position (size_type) - the position from which to start removing elements
        count (size_type) - the number of elements to remove
*/
void Sequence::erase(size_type position, size_type count) {
    
    size_type numElements = 0; // create an int to count how many elements are in the sequence

    SequenceNode* current = head; // create a current Sequence Node pointer for tracking
    
    // count number of elements in the sequence
    while(current != nullptr) {
        numElements++;
        current = current->next;
    }
    
    // check the position parameter to verify that it is between 0 and the end of the sequence 
    if (position < 0 || position > numElements-1) {
        throw exception();
    }

    // check that the entered range of items to delete does not extend past the list
    if (position + (count - 1) > numElements) {
        throw exception();
    }

    current = head; // point the current pointer to the head of the sequence for tracking

    // step through the sequence until current is pointing at the node at the specified position 
    for (int i = 0; i < position; i++) {
        current = current->next;
    }

    // step through and delete the node at the specified position as well as then number of following elements specified
    for (int i = 0; i < count; i++) {

        SequenceNode* nextNode = current->next; // create a pointer that points to the node right after the current node

        if (current == head) { // if the current node to be deleted is the head, 
            head = current->next; // point the new head to the node right after current
            current->next->prev = nullptr; // and make the new head's prev pointer a null pointer
            delete  current; // delete the current node
        } else if (current == tail) { // if the current node to be deleted is the tail,
            tail = current->prev; // point the new tail to the node right before current
            current->prev->next = nullptr; // and make the new tail's next pointer a null pointer
            delete current; // delete the current node
        } else { // othewise, the current node is in the middle of the sequence and 
            current->prev->next = current->next; // point the next pointer of the node right before current to the node right after current  
            current->next->prev = current->prev; // point the prev pointer of the node right after current to the node right before current
            delete current; // delete the current node
        }
        current = nextNode; // point current to the node that was after the deleted node 
    }
}

/*
print: specifies how the Sequence should be printed
    Returns: void
    Parameters: 
        os (ostream ref) - the output stream to which to send the printable sequence
*/
void Sequence::print(ostream& os) const {

    cout<< '<'; // beginning of printed content

    SequenceNode* current = head; // create a current Sequence Node pointer and point it to the head of the sequence

    // step through each node and print the element 
    while (current != nullptr) {
        if (current->next != nullptr) {
            cout << current->elt << ", "; // if the current node is not the last node in the list, print a comma after it
        }
        else {
            cout << current->elt; // if the current node is the last node in the list, do not print the comma
        }
        current = current->next;
    }

    cout<< '>' << endl; // end of printed content
}

/*
operator<<: allows the Sequence to be printed using the << operator
    Returns:
        os (ostream ref) - reference to the output stream
    Parameters: 
        os (ostream ref) - the output stream to which the printable sequence will be sent
        s (constant Sequence ref) - the Sequence to be printed
*/
ostream& operator<<(ostream& os, const Sequence& s)
{
    s.print(os);
    return os;
}