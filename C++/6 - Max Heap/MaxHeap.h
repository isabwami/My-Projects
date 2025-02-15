#pragma once

#include <iostream>
#include <exception>
#include <vector>

using namespace std;

#define HEAP_MIN_SIZE 20

class MaxHeap {
    public:

    // CONSTRUCTORS
    
    /// @brief creates new heap with an empty array of size HEAP_MIN_SIZE
    MaxHeap() : heapArray(new int[HEAP_MIN_SIZE]), heapSize(0), heapCapacity(HEAP_MIN_SIZE) { }

    /// @brief creates a deep copy of the heap
    /// @param h heap this heap will be a copy of
    MaxHeap(const MaxHeap& h);

    /// @brief creates a max heap from a given array using heapify()
    /// @param values array to be heapified
    /// @param count size of array to be heapified
    MaxHeap(int* values, int count);

    /// @brief deletes heap and releases associated memory
    ~MaxHeap();

    /// @brief delets this heap and replaces it with a deep copy of another heap 
    /// @param h heap from which data is copied from
    /// @return A reference to this heap
    MaxHeap& operator=(const MaxHeap& h);

    /// @brief allows the heap to be printed to an output stream using the << operator
    /// @param os the output stream to which the printable AVL Tree will be sent
    /// @param me the heap to be printed
    /// @return reference to an output stream
    friend ostream& operator<<(ostream& os, const MaxHeap& me);

    /// @brief inserts the given value into the next empty index in the heap and sifts up until the max heap property is satisfied
    /// @param value the value to be inserted
    void offer(int value);

    /// @brief returns the greatest value in the heap, replaces it with the value at the last index and sifts this value down until the max heap property is satisfied
    /// @return the greatest value in the heap
    int poll();

    /// @brief checks if heap is empty
    /// @return whether the heap is empty
    bool isEmpty() const;

    /// @brief returns the greatest value in the heap without removing it
    /// @return greatest value in the heap
    int peek() const;

    /// @brief sorts the heap from least to greatest and returns a vector containing the sorted heap
    /// @return sorted heap
    vector<int> sorted() const;
    
    private: 

    int* heapArray;
    int heapSize;
    int heapCapacity;

    // ADDITIONAL PRIVATE FUNCTIONS

    /// @brief heapifies the given array ensuring it satisfies the max heap property
    /// @param array the array to be heapified
    /// @param size the size of the array to be heapified
    void heapify(int* array, int size) const;

    /// @brief returns the size of the heap
    /// @return size of the heap
    int getSize();

    /// @brief creates a deep copy of the given heap array
    /// @param array the array to be copied
    /// @param size the size of the array to be copied
    /// @return copied array
    int* copyHeap(int* array, int size) const;

    /// @brief creates a deep copy of the given heap array that is twice as large as the given array
    /// @param array the array to be copied
    /// @param size the size of the array to be copied
    /// @return copied array
    int* expandHeap(int* array, int size) const ;

    /// @brief returns the parent index of the current index
    /// @param index current index being queried
    /// @return parent index
    int getParent(int index) const;
    
    /// @brief returns the left child index of the current index
    /// @param index left child index
    /// @return current index being queried
    int getLeftChild(int index) const;

    /// @brief returns the right child index of the current index
    /// @param index right child index
    /// @return current index being queried
    int getRightChild(int index) const;

    /// @brief prints the heap to the given output stream
    /// @param os the output stream to which the heap will be sent
    void print(ostream& os) const;

};
