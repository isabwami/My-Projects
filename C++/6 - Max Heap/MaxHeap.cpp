/*
Immanuel Sabwami
Max Heap
    Description: This is an implementation of a max heap supports heap insertion, heapify, and whos capacity grows and shrinks
                 depending on how full it is.
    Global Constants:
        HEAP_MIN_SIZE (int): the minimum capacity the heap can have
*/
#include "MaxHeap.h"

// CONSTRUCTORS

/*
Copy Constructor: creates a deep copy of the heap
    Returns: N/A
    Parameters: 
        h (constant MaxHeap ref) - heap this heap will be a copy of
*/
MaxHeap::MaxHeap(const MaxHeap& h) {

    // copy the other heap's array into this heap
    heapArray = copyHeap(h.heapArray, h.heapSize);

    // copy the other heap's size and capacity into this heap
    heapSize = h.heapSize;
    heapCapacity = h.heapCapacity;

}

/*
Seconday Copy Constructor: creates a max heap from a given array using heapify()
    Returns: N/A
    Parameters: 
        values (int*) - array to be heapified
        count (int) - size of array to be heapified
*/
MaxHeap::MaxHeap(int* values, int count) {

    // initialize heapArray depending on the size of the given array
    if (count < HEAP_MIN_SIZE) {
        
        // if the array size is less than HEAP_MIN_SIZE, set the heap capacity to HEAP_MIN_SIZE 
        heapArray = copyHeap(values, HEAP_MIN_SIZE);  
        heapCapacity = HEAP_MIN_SIZE;
    
    } else if (count == HEAP_MIN_SIZE) {
        
        // if the array size is HEAP_MIN_SIZE, set the heap capacity to twice HEAP_MIN_SIZE because the array will be "full"
        heapArray = copyHeap(values, HEAP_MIN_SIZE * 2);
        heapCapacity = HEAP_MIN_SIZE * 2;

    } else {
        
        // if the array size is greater that HEAP_MIN_SIZE, set the heap capacity to twice the array size since the array will
        // again be full
        heapArray = copyHeap(values, count*2);
    
    }

    // the size of this heap will be the size of the given array
    heapSize = count;

    // heapify the heapArray
    heapify(heapArray, count);
    
}

/*
Destructor: deletes heap and releases associated memory
    Returns: N/A
    Parameters: N/A
*/
MaxHeap::~MaxHeap() {

    // delete the heap array
    delete[] heapArray;

}

// FUNCTIONS

/* 
operator=: delets this heap and replaces it with a deep copy of another heap 
    Returns: 
        MaxHeap ref - A reference to this heap
    Parameters: 
        h (constant MaxHeap ref) - heap from which data is copied from
*/
MaxHeap& MaxHeap::operator=(const MaxHeap& h) {

    // delete the current heap array
    delete[] heapArray;

    // copy the other heap's array into this one including its size and capacity
    heapArray = copyHeap(h.heapArray, h.heapSize);

    heapSize = h.heapSize;
    heapCapacity = h.heapCapacity;

    return *this;

}

/*
operator<<: allows the heap to be printed to an output stream using the << operator
    Returns:
        os (ostream ref) - reference to an output stream
    Parameters: 
        os (ostream ref) - the output stream to which the printable AVL Tree will be sent
        me (constant MaxHeap ref) - the heap to be printed
*/
ostream& operator<<(ostream& os, const MaxHeap& me) {
    
    // call print on this heap;
    me.print(os);
    return os;

}

/*
offer: inserts the given value into the next empty index in the heap and sifts up until the max heap property is satisfied
    Returns: N/A
    Parameters:
        value (int) - the value to be inserted
*/
void MaxHeap::offer(int value) {

    int currentIndex = heapSize; // the next empty index in the array
    int temp; // temporary storage for swapping
    int parentIndex = getParent(currentIndex); // index of the current index's parent
    int currentValue = value; // value at the current index
    int parentValue; // value at the parent index

    // insert the new value into the next empty index and increment the size
    heapArray[currentIndex] = value;
    heapSize++;

    // set the value of the parent
    parentValue = heapArray[parentIndex];

    // check max heap property and sift up if necessary
    while (parentIndex >= 0) {
        
        // if the current value is greater than its parent, sift up
        if (currentValue > parentValue) {
        
            temp = parentValue;
            heapArray[parentIndex] = currentValue;
            heapArray[currentIndex] = temp;
        
        }

        // once the parent index is 0, we have reached the root of the tree and can break
        if (parentIndex == 0) {
            
            break;

        } else {
            
            // update the current index and value to the parent to continue verifying max heap property is met
            currentIndex = parentIndex;
            currentValue = heapArray[parentIndex];
            parentIndex = getParent(parentIndex);
            parentValue = heapArray[parentIndex];
        
        }
    }

    // check if the heap is full and double the capacity if so
    if (heapSize == heapCapacity) {
        
        int* newArray = expandHeap(heapArray, heapCapacity * 2); // copy contents of the current array into an array twice as large

        delete[] heapArray; // delete the current heap array

        heapArray = newArray; // point the heap array to the new larger array

        heapCapacity *= 2; // double the capcity

    }
}

/*
poll: returns the greatest value in the heap, replaces it with the value at the last index and sifts this value down until 
      the max heap property is satisfied
    Returns:
        int - the greatest value in the heap
    Parameters: N/A
*/
int MaxHeap::poll() {

    // throw an exception if heap is empty
    if (isEmpty()) {

        throw exception();
    
    }

    // store the max value in the heap
    int max = heapArray[0];

    // swap the max value and the last value and remove the max value
    heapArray[0] = heapArray[heapSize - 1];
    heapArray[heapSize - 1] = 0;
    heapSize--;

    // sift down
    int temp; // temporary storage for swaps
    int currentIndex = 0; // sift down from index 0
    // determine the current value and children index/value
    int currentValue = heapArray[currentIndex]; 
    int leftChildIndex = getLeftChild(currentIndex);
    int leftChildValue = heapArray[leftChildIndex];
    int rightChildIndex = getRightChild(currentIndex);
    int rightChildValue = heapArray[rightChildIndex];

    // while the current index is not the last one in the heap,
    while (currentIndex < heapSize - 1) {
        
        // check if this value is less than either of its children
        if (leftChildValue > currentValue || rightChildValue > currentValue) {
            
            // if the left child is the greater of the two children, swap current with its left child 
            if (leftChildValue > rightChildValue) {
                temp = leftChildValue;
                heapArray[leftChildIndex] = currentValue;
                heapArray[currentIndex] = temp;

                currentIndex = leftChildIndex; // set the new current index to the left child index since the values swapped

            } else {
                // if the right child is the greater of the two, swap the current with its right child
                temp = rightChildValue;
                heapArray[rightChildIndex] = currentValue;
                heapArray[currentIndex] = temp;

                currentIndex = rightChildIndex; // set the new current index to the right child index since the values swapped

            }

            // update current value and children index/value
            currentValue = heapArray[currentIndex];
            leftChildIndex = getLeftChild(currentIndex);
            leftChildValue = heapArray[leftChildIndex];
            rightChildIndex = getRightChild(currentIndex);
            rightChildValue = heapArray[rightChildIndex];
    
        } else {
            
            // if max heap property is met, break out of the loop
            break;
        
        }
    
    }

    // check if heap is less than half full but with a capacity greater than the minimum heap size
    if (heapSize < heapCapacity/2 && heapCapacity > HEAP_MIN_SIZE) {
        
        // if so, half the capacity by creating a new array with half the capacity and copying 
        // the current heap array's contents into the new array
        int* newArray = copyHeap(heapArray, heapCapacity/2);

        delete[] heapArray; // delete the current heap array

        heapArray = newArray; // point the heap array to the new smaller array

        heapCapacity /= 2; // half the capacity

    }

    return max; // return the maximum value

}

/*
isEmpty: checks if heap is empty
    Returns:
        bool - whether the heap is empty
    Parameters: N/A
*/
bool MaxHeap::isEmpty() const {
    
    // if the heap size is 0, the heap is empty
    if (heapSize == 0) {
    
        return true;
    
    }

    return false;

}

/*
peek: returns the greatest value in the heap without removing it
    Returns:
        int - greatest value in the heap
    Parameters: N/A
*/
int MaxHeap::peek() const {
    
    // if the heap is not empty return the max value
    if (!isEmpty()) {

        return heapArray[0];
    
    } else {

        throw exception();
    
    }

}

/*
sorted: sorts the heap from least to greatest and returns a vector containing the sorted heap
    Returns:
        vector<int> - sorted heap
    Parameters: N/A
*/
vector<int> MaxHeap::sorted() const {

    vector<int> sorted; // vector to store sorted heap
    int* tempArray = copyHeap(heapArray, heapSize); // temporary array to sort items

    int temp; // temporary storage for swaps

    // starting with the last element in the array,
    for (int i = heapSize - 1; i > 1; i--) {
        
        // swap the first and last elements
        temp = tempArray[0];
        tempArray[0] = tempArray[i];
        tempArray[i] = temp;

        // heapify the array, but exclude the last element as that is the maximum
        heapify(tempArray, i - 1);

    }

    for (int i = 0; i < heapSize; i++) {

        sorted.push_back(tempArray[i]); // add sorted array to vector
    
    }

    delete[] tempArray; // delete the temporary array

    return sorted; 

}

// ADDITIONAL PRIVATE FUNCTIONS
/*
heapify: heapifies the given array ensuring it satisfies the max heap property
    Returns: N/A
    Parameters:
        array (int*) - the array to be heapified
        size (int) - the size of the array to be heapified
*/
void MaxHeap::heapify(int* array, int size) const {

    // i starts with the first non leaf integer
    for (int i = (int)floor((size/2) - 1); i >= 0; i--) {
        
        // set current and children indices and values
        int currentIndex = i;
        int currentValue = array[currentIndex];
        int leftChildIndex = getLeftChild(currentIndex);
        int leftChildValue = array[leftChildIndex];
        int rightChildIndex = getRightChild(currentIndex);
        int rightChildValue = array[rightChildIndex];
        int temp; // temporary storage for swaps
        
        // check if this value is less than either of its children and keep sifting down until this is no longer true
        while (currentValue < leftChildValue || currentValue < rightChildValue) {
            
            // if the left child is the greater of the two children, swap current with its right child 
            if (leftChildValue > rightChildValue) {
                
                temp = leftChildValue;
                array[leftChildIndex] = currentValue;
                array[currentIndex] = temp;

                currentIndex = leftChildIndex; // set the new current index to the left child index since the values swapped

            } else if (rightChildValue > leftChildValue) {

                // if the right child is the greater of the two children, swap current with its right child 
                temp = rightChildValue;
                array[rightChildIndex] = currentValue;
                array[currentIndex] = temp; 

                currentIndex = rightChildIndex; // set the new current right to the left child index since the values swapped

            }

            // update current value and children values/indices only if current is not a leaf 
            if (currentIndex <= floor((size/2)-1)) {
                
                currentValue = array[currentIndex];
                leftChildIndex = getLeftChild(currentIndex);
                leftChildValue = array[leftChildIndex];
                rightChildIndex = getRightChild(currentIndex);
                rightChildValue = array[rightChildIndex];

            } else {
                
                // if current is a leaf, break and move to the next index 
                break;
            
            }
                        
        }

    }

}

/*
getSize: returns the size of the heap
    Returns:
        int - size of the heap
    Parameters: N/A
*/
int MaxHeap::getSize() {

    return heapSize;

}

/*
copyHeap: creates a deep copy of the given heap array
    Returns:
        int* - copied array
    Parameters:
        array (int*) - the array to be copied
        size (int) - the size of the array to be copied
*/
int* MaxHeap:: copyHeap(int* array, int size) const {

    int* newArray = new int[size]; // create a new array that will hold the contents of the given array

    // copy over each element
    for (int i = 0; i < size; i++) {
        newArray[i] = array[i];
    }

    return newArray;

}

/*
expandHeap: creates a deep copy of the given heap array that is twice as large as the given array
    Returns:
        int* - copied array
    Parameters:
        array (int*) - the array to be copied
        size (int) - the size of the array to be copied
*/
int* MaxHeap:: expandHeap(int* array, int size) const {

    int* newArray = new int[size]; // create a new array that will hold the contents of the given array

    // copy over each element
    for (int i = 0; i < size/2; i++) {
        newArray[i] = array[i]; 
        // note: since this array is twice as large as the given array, this loop should only iterate 
        // until it reaches the end of the given array
    }

    return newArray;

}

/*
getParent: returns the parent index of the current index
    Returns:
        int - parent index
    Parameters:
        index (int) - current index being queried
*/
int MaxHeap::getParent(int index) const{
    
    return (int)floor((index - 1) / 2); // the parent of an index is always (n-1) / 2

}

/*
getLeftChild: returns the left child index of the current index
    Returns:
        int - left child index
    Parameters:
        index (int) - current index being queried
*/
int MaxHeap::getLeftChild(int index) const {

    return (2 * index) + 1; // the left child of an idex is always (2n) + 1

}

/*
getRightChild: returns the right child index of the current index
    Returns:
        int - right child index
    Parameters:
        index (int) - current index being queried
*/
int MaxHeap::getRightChild(int index) const{

    return (2 * index) + 2; // the right child of an index is always (2n) + 2

}

/*
print: prints the heap to the given output stream
    Returns: N/A
    Parameters:
        os (ostream ref) - the output stream to which the heap will be sent
*/
void MaxHeap::print(ostream& os) const {
    os << "[";
    
    // iterate through the array, printing each element
    for (int i = 0; i < heapSize; i++) {
        
        if (i == heapSize - 1) {
        
            os << heapArray[i];
        
        } else {
        
            os << heapArray[i] << ", ";
        
        }
    
    }

    os << "]";

}
