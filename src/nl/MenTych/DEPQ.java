package nl.MenTych;

import java.util.Arrays;

import static java.lang.Math.log;

/**
 * The type Depq.
 * <p>
 * This is the class that is the DEPQ.
 * This also has all the functions.
 */
class DEPQ {

    /**
     * The Heap.
     *
     * this contains all the numbers in the heap.
     */
    public int[] heap;

    /**
     * Get the level of the current index.
     *
     * @param i the index to check.
     * @return int the level of the index
     */
    private int getLevel(int i) {
        if (i == 0) {
            return 0;
        }

        return (int) (log(i + 1) / log(2));
    }

    /**
     * Swaps the 2 index in the heap.
     *
     * @param heap the where the elements are in.
     * @param m the 1st index to swap.
     * @param i the 2nd index to swap.
     */
    private void swap(int[] heap, int m, int i) {
        int temp = heap[m];
        heap[m] = heap[i];
        heap[i] = temp;
    }

    /**
     * returns the grandparent of the current element.
     * @param i the index to check
     * @return int the index of the grandparent.
     */
    private int getGrandParent(int i) {
        return getParentFromCurrentElement(getParentFromCurrentElement(i));
    }

    /**
     * returns the parent fo
     * @param i
     * @return
     */
    private int getParentFromCurrentElement(int i) {
        if (i == 0) {
            return -1;
        }
        return (i - 1) / 2;
    }


    /**
     * Gets left from root.
     *
     * @param i the
     * @return the left from root
     */
    int getLeftFromRoot(int i) {
        return (2 * i + 1);
    }


    /**
     * Gets right from root.
     *
     * @param i the
     * @return the right from root
     */
    int getRightFromRoot(int i) {
        return (2 * i + 2);
    }


    /**
     * Has left child boolean.
     *
     * @param i the
     * @return the boolean
     */
    boolean hasLeftChild(int i) {
        return getLeftFromRoot(i) < size();
    }


    /**
     * Has right child boolean.
     *
     * @param i the
     * @return the boolean
     */
    boolean hasRightChild(int i) {
        return getRightFromRoot(i) < size();
    }


    /**
     * Check if the given child m is a grand child of i
     *
     * @param m the child index to check
     * @param i the parent index to check
     * @return boolean if is a grandchild of i
     */
    private boolean isGrandChild(int m, int i) {
        if (m == 0 || m == 1 || m == 2) return false;
        return getParentFromCurrentElement(getParentFromCurrentElement(m)) == i;
    }

    /**
     * Check if the given index has an grandparent.
     *
     * @param i the index of element to check
     * @return boolean if it has grandparent
     */
    private boolean hasGrandParent(int i) {
        int parent = getParentFromCurrentElement(i);
        return getParentFromCurrentElement(parent) != -1;
    }

    /**
     * Check if the heap is empty.
     *
     * @return boolean if emtphy
     */
    private boolean isEmpty() {
        if (this.heap == null) {
            throw new RuntimeException("heap is null");
        }
        return size() == 0;
    }

    /**
     * Get the size of the heap
     *
     * @return the size of the heap.
     */
    private int size() {
        return this.heap.length;
    }

    /**
     * Find minimum int in the heap and return it
     *
     * @param heap the heap
     * @return int the minimum of the heap
     */
    int findMinimum(int[] heap) {
        if (isEmpty()) {
            throw new RuntimeException("Heap is empty");
        }
        return heap[0];
    }


    /**
     * Find maximum int.
     *
     * @param heap the heap
     * @return the int
     */
    int findMaximum(int[] heap) {
        if (isEmpty()) {
            throw new RuntimeException("Heap is empty");
        }

        if (heap.length > 1) {
            if (heap[1] > heap[2]) {
                return heap[1];
            } else {
                return heap[2];
            }
        } else {
            return heap[0];
        }
    }


    /**
     * Find maximum index int.
     *
     * @param heap the heap
     * @return the int
     */
    int findMaximumIndex(int[] heap) {
        if (isEmpty()) {
            throw new RuntimeException("Heap is empty");
        }

        if (heap.length > 1) {
            if (heap[1] > heap[2]) {
                return 1;
            } else {
                return 2;
            }
        } else {
            return 0;
        }
    }


    /**
     * Remove smallest int.
     *
     * @param heap the heap
     * @return the int
     */
    int removeSmallest(int[] heap) {
        if (isEmpty()) {
            throw new RuntimeException("Heap is empty");
        }

        int smallestval = heap[0];
        int[] newheap;
        swap(heap, 0, heap.length - 1);
        newheap = Arrays.copyOf(heap, heap.length - 1);

        this.heap = newheap;
        pushDown(this.heap, 0);

        return smallestval;
    }

    /**
     * Remove maximum int.
     *
     * @param heap the heap
     * @return the int
     */
//WORKS
    int removeMaximum(int[] heap) {
        if (isEmpty()) {
            throw new RuntimeException("Heap is empty");
        }
        int[] newheap;
        int largestindex;

        if (heap.length == 1) {
            largestindex = 0;
        } else if (heap.length == 2) {
            largestindex = 1;
        } else {
            largestindex = findMaximumIndex(heap);
        }
        int biggestval = heap[largestindex];
        swap(heap, largestindex, heap.length - 1);
        newheap = Arrays.copyOf(heap, heap.length - 1);
        this.heap = newheap;
        pushDown(newheap, largestindex);
        return biggestval;
    }


    /**
     * Build int [ ].
     *
     * @param heap the heap
     * @return the int [ ]
     */
    int[] build(int[] heap) {
        for (int i = 0; i < (size() / 2); i++) {
            pushDown(heap, i);

        }
        return heap;
    }


    private int getSmallestChildOrGrandchild(int[] heap, int i) {
        //All the possible positions of smaller elements
        int[] positisions = new int[]{i * 2 + 2, i * 4 + 3, i * 4 + 4, i * 4 + 5, i * 4 + 6};
        if (hasLeftChild(i)) {
            int smallestindex = i * 2 + 1;
            for (int positision : positisions) {
                if (positision < size() - 1) {
                    if (heap[positision] < heap[smallestindex]) {
                        smallestindex = positision;
                    }
                }
            }
            return smallestindex;
        }
        return -1;
    }


    private int getLargestChildOrGrandchild(int[] heap, int i) {
        //All the possible positions of bigger elements
        int[] positisions = new int[]{i * 2 + 2, i * 4 + 3, i * 4 + 4, i * 4 + 5, i * 4 + 6};
        if (hasLeftChild(i)) {
            int biggest = i * 2 + 1;
            for (int positision : positisions) {
                if (positision < size() - 1) {
                    if (heap[positision] > heap[biggest]) {
                        biggest = positision;
                    }
                }
            }
            return biggest;
        }
        return -1;
    }


    private boolean hasChildren(int i) {
        return hasLeftChild(i) || hasRightChild(i);
    }


    private void pushDown(int[] heap, int index) {

        if (getLevel(index) == 0) {
            //Min
            pushDownMin(heap, index);
        } else if ((getLevel(index) % 2) == 0) {
            //Min
            pushDownMin(heap, index);
        } else {
            //Max
            pushDownMax(heap, index);
        }
    }


    private void pushDownMin(int[] heap, int index) {
        if (hasChildren(index)) {
            int m = getSmallestChildOrGrandchild(heap, index);
            if (isGrandChild(m, index)) {
                if (heap[m] < heap[index]) {
                    swap(heap, m, index);
                    if (heap[m] > heap[getParentFromCurrentElement(m)]) {
                        swap(heap, m, getParentFromCurrentElement(m));
                    }
                    pushDownMin(heap, m);
                }
            } else if (heap[m] < heap[index]) {
                swap(heap, m, index);
            }
        }
    }


    private void pushDownMax(int[] heap, int index) {
        if (hasChildren(index)) {
            int m = getLargestChildOrGrandchild(heap, index);
            if (isGrandChild(m, index)) {
                if (heap[m] > heap[index]) {
                    swap(heap, m, index);
                    if (heap[m] < heap[getParentFromCurrentElement(m)]) {
                        swap(heap, m, getParentFromCurrentElement(m));
                    }
                    pushDownMax(heap, m);
                }
            } else if (heap[m] > heap[index]) {
                swap(heap, m, index);
            }
        }
    }


    /**
     * Insert.
     *
     * @param heap  the heap
     * @param value the value
     */
    void insert(int[] heap, int value) {
        int[] newheap = new int[heap.length + 1];
        System.arraycopy(heap, 0, newheap, 0, heap.length);
        newheap[heap.length] = value;
        this.heap = newheap;
        pushUp(this.heap, heap.length);
    }


    private void pushUp(int[] heap, int index) {

        if (index > 0) {
            if (getLevel(index) % 2 == 0) {
                //Min
                if (heap[index] > heap[getParentFromCurrentElement(index)]) {
                    swap(heap, index, getParentFromCurrentElement(index));
                    pushUpMax(heap, index);
                } else {
                    pushUpMin(heap, index);
                }
            } else {
                //Max
                if (heap[index] < heap[getParentFromCurrentElement(index)]) {
                    swap(heap, index, getParentFromCurrentElement(index));
                    pushUpMin(heap, index);
                } else {
                    pushUpMax(heap, index);
                }
            }
        }
    }


    private void pushUpMin(int[] heap, int index) {
        if (hasGrandParent(index) && heap[index] < heap[getGrandParent(index)]) {
            swap(heap, index, getGrandParent(index));
            pushUpMin(heap, getGrandParent(index));
        }
    }


    private void pushUpMax(int[] heap, int index) {
        if (hasGrandParent(index) && heap[index] > heap[getGrandParent(index)]) {
            swap(heap, index, getGrandParent(index));
            pushUpMax(heap, getGrandParent(index));
        }
    }


    /**
     * Print heap.
     *
     * @param heap the heap
     */
    void printHeap(int[] heap) {
        for (int i = 0; i < heap.length; i++) {
            System.out.print("index: " + i + " with value: " + heap[i] + " ");
            if (hasLeftChild(i)) {
                System.out.print(" LEFT CHILD " + getLeftFromRoot(i) + " ");
            }
            if (hasRightChild(i)) {
                System.out.print(" RIGHT CHILD " + getRightFromRoot(i) + " ");
            }
            System.out.println();
        }
    }


}
