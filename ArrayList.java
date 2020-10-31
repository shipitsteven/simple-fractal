import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Makes a generic ArrayList that stores primitives or objects
 *
 * @param <E> Generic type to be replaced by desired type checking parameter
 */
public class ArrayList<E> implements Serializable, Iterable<E> {
    /**
     * list of values
     */
    private E[] elementData;
    /**
     * current number of elements in the list
     */
    private int size;
    /**
     * default capacity of the starting list
     */
    public static final int DEFAULT_CAPACITY = 50;
    /**
     * serializable ID
     */
    private static final long serialVersionUID = 1;

    /**
     * Constructor, initialize ArrayList to default capacity
     */
    public ArrayList() {
        this(DEFAULT_CAPACITY);
    }

    /**
     * pre : capacity &gt;= 0 (throws IllegalArgumentException if not)
     * post: constructs an empty list with the given capacity
     *
     * @param capacity starting capacity of the ArrayList
     */
    @SuppressWarnings("unchecked")
    public ArrayList(int capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException("capacity: " + capacity);
        }
        elementData = (E[]) new Object[capacity];
        size = 0;
    }

    /**
     * Removed all null from the ArrayList and resize the array.
     */
    public void compressNull() {
        int marker = 0;
        for (int idx = 0; idx < elementData.length; idx++) {
            if (elementData[idx] != null) {
                E temp = elementData[marker];
                elementData[marker] = elementData[idx];
                elementData[idx] = temp;
                marker++;
            }
        }
        size = marker;
        elementData = Arrays.copyOf(elementData, marker);

    }

    /**
     * post: returns the current number of elements in the list
     *
     * @return the current number of elements in the list
     */
    public int size() {
        return size;
    }

    /**
     * pre : 0 &lt;= index &lt; size() (throws IndexOutOfBoundsException if not)
     * post: returns the value at the given index in the list
     *
     * @param index index of the element desired
     * @return the requested element
     */
    public E get(int index) {
        checkIndex(index);
        return elementData[index];
    }


    /**
     * creates a comma-separated, bracketed version of the list
     *
     * @return a string representation of the ArrayList
     */
    public String toString() {
        if (size == 0) {
            return "[]";
        } else {
            String result = "[" + elementData[0];
            for (int i = 1; i < size; i++) {
                result += ", " + elementData[i];
            }
            result += "]";
            return result;
        }
    }

    /**
     * post : returns the position of the first occurrence of the given
     * value (-1 if not found)
     *
     * @param value the value to be searched
     * @return index of the value requested, -1 if not found
     */
    public int indexOf(E value) {
        for (int i = 0; i < size; i++) {
            if (elementData[i].equals(value)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * returns true if list is empty, false otherwise
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * post: returns true if the given value is contained in the list,
     * false otherwise
     *
     * @param value value to be searched
     * @return true if found, false otherwise
     */
    public boolean contains(E value) {
        return indexOf(value) >= 0;
    }

    /**
     * post: appends the given value to the end of the list
     *
     * @param value element to be added
     */
    public void add(E value) {
        ensureCapacity(size + 1);
        elementData[size] = value;
        size++;
    }

    /**
     * pre : 0 &lt;= index &lt;= size() (throws IndexOutOfBoundsException if not)
     * post: inserts the given value at the given index, shifting subsequent
     * values right
     *
     * @param index position in the ArrayList to be added
     * @param value element to be added
     */
    public void add(int index, E value) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("index: " + index);
        }
        ensureCapacity(size + 1);
        for (int i = size; i >= index + 1; i--) {
            elementData[i] = elementData[i - 1];
        }
        elementData[index] = value;
        size++;
    }

    /**
     * pre : 0 &lt;= index &lt; size() (throws IndexOutOfBoundsException if not)
     * post: removes value at the given index, shifting subsequent values left
     *
     * @param index index of the item to be removed
     */
    public void remove(int index) {
        checkIndex(index);
        for (int i = index; i < size - 1; i++) {
            elementData[i] = elementData[i + 1];
        }
        elementData[size - 1] = null;
        size--;
    }

    /**
     * Added method to remove an object
     *
     * @param value object to be removed
     */
    public void remove(E value) {
        if (contains(value)) {
            remove(indexOf(value));
        }
    }

    /**
     * pre : 0 &lt;= index &lt; size() (throws IndexOutOfBoundsException if not)
     * post: replaces the value at the given index with the given value
     *
     * @param index index of the element to be updated
     * @param value new value
     */
    public void set(int index, E value) {
        checkIndex(index);
        elementData[index] = value;
    }

    /**
     * Clears the list and reset size
     * post: list is empty
     */
    public void clear() {
        for (int i = 0; i < size; i++) {
            elementData[i] = null;
        }
        size = 0;
    }

    /**
     * post: appends all values in the given list to the end of this list
     *
     * @param other list to be appended to current list
     */
    public void addAll(ArrayList<E> other) {
        ensureCapacity(size + other.size);
        for (int i = 0; i < other.size; i++) {
            add(other.elementData[i]);
        }
    }

    /**
     * returns an iterator for this list
     *
     * @return an iterator for this list
     */
    public Iterator<E> iterator() {
        return new ArrayListIterator();
    }

    /**
     * post: ensures that the underlying array has the given capacity; if not,
     * the size is doubled (or more if given capacity is even larger)
     *
     * @param capacity current capacity of the List
     */
    public void ensureCapacity(int capacity) {
        if (capacity > elementData.length) {
            int newCapacity = elementData.length * 2 + 1;
            if (capacity > newCapacity) {
                newCapacity = capacity;
            }
            elementData = Arrays.copyOf(elementData, newCapacity);
        }
    }

    /**
     * post: throws an IndexOutOfBoundsException if the given index is
     * not a legal index of the current list
     *
     * @param index index to be checked
     */
    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("index: " + index);
        }
    }

    /**
     * Private class responsible for the functionality of an Iterator
     */
    private class ArrayListIterator implements Iterator<E> {
        /**
         * current position within the list
         */
        private int position;
        /**
         * whether it's okay to remove now
         */
        private boolean removeOK;

        /**
         * post: constructs an iterator for the given list
         */
        public ArrayListIterator() {
            position = 0;
            removeOK = false;
        }

        /**
         * post: returns true if there are more elements left, false otherwise
         *
         * @return true if there are more elements left, false otherwise
         */
        public boolean hasNext() {
            return position < size();
        }

        /**
         * pre : hasNext() (throws NoSuchElementException if not)
         * post: returns the next element in the iteration
         *
         * @return the next element in iteration
         */
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            E result = elementData[position];
            position++;
            removeOK = true;
            return result;
        }

        /**
         * pre : next() has been called without a call on remove (throw IllegalStateException if not)
         * post: removes the last element returned by the iterator
         */
        public void remove() {
            if (!removeOK) {
                throw new IllegalStateException();
            }
            ArrayList.this.remove(position - 1);
            position--;
            removeOK = false;
        }
    }
}
