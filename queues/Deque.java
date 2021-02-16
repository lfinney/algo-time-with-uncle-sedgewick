/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private Node first;
    private Node last;
    private int count;

    private class Node {
        Item item;
        Node front = null;
        Node back = null;
    }

    // construct an empty deque
    public Deque() {
        count = 0;
        first = null;
        last = null;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return this.count == 0;
    }

    // return the number of items on the deque
    public int size() {
        return this.count;
    }

    // add the item to the front
    public void addFirst(Item item) {
        this.nullItemCheck(item);

        Node oldFirst = this.first;
        Node newFirst = new Node();
        newFirst.item = item;

        if (this.count == 0) {
            this.first = newFirst;
            this.last = newFirst;
        }
        else {
            // The front of the old first, points to the new first
            oldFirst.front = newFirst;
            // The back of the new first, points to the old first
            newFirst.back = oldFirst;
            // Reset this.first
            this.first = newFirst;
        }

        this.count++;
    }

    // add the item to the back
    public void addLast(Item item) {
        this.nullItemCheck(item);

        Node oldLast = this.last;
        Node newLast = new Node();
        newLast.item = item;

        if (this.count == 0) {
            this.last = newLast;
            this.first = newLast;
        }
        else {
            // The back of the old last, points to the new the new last
            oldLast.back = newLast;
            // The front of the new last, points to the old last
            newLast.front = oldLast;
            // Reset this.last
            this.last = newLast;
        }

        this.count++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        this.checkForElement();

        Item item = first.item;

        if (this.first == this.last) {
            this.first = null;
            this.last = null;
        }
        else {
            this.first = this.first.back;
            this.first.front = null;
        }
        this.count--;
        return item;
    }

    // remove and return item from the back
    public Item removeLast() {
        this.checkForElement();

        Item item = last.item;

        if (this.last == this.first) {
            this.last = null;
            this.first = null;
        }
        else {
            this.last = this.last.front;
            this.last.back = null;
        }
        this.count--;
        return item;
    }

    private void nullItemCheck(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("'null' is not an acceptable argument");
        }
    }

    private void checkForElement() {
        if (this.count == 0) {
            throw new java.util.NoSuchElementException(
                    "The deque is empty and there are no elements to return");
        }
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();
        // Implement the following two exceptions when building iterator

        // Throw a java.util.NoSuchElementException if the client calls the next()
        // method in the iterator when there are no more items to return.

        // Throw an UnsupportedOperationException if the client calls the remove()
        // method in the iterator.
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public Item next() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            Item item = current.item;
            current = current.back;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // unit testing
    public static void main(String[] args) {

    }
}
