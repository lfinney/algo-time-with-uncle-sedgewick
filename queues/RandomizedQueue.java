/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private int count;
    private Item[] queue;

    // construct an empty randomized queue
    public RandomizedQueue() {
        count = 0;
        queue = (Item[]) new Object()[1];
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return this.count == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return this.count;
    }

    // add the item
    public void enqueue(Item item) {
        this.nullItemCheck(item);
        if (this.count == this.queue.length) {
            this.doubleQueueSize();
        }
        this.queue[this.count] = item;
        this.count++;
    }

    // remove and return random item
    public Item dequeue() {
        this.checkForElement();
        if (this.count > 1 && this.count == this.queue.length / 4) {
            this.halveQueueSize();
        }
        Item item = this.queue[this.count];
        this.count--;
        this.queue[this.count] = null;
        return item;
    }

    private void doubleQueueSize() {
        int newCapacity = this.count * 2;
        this.createNewQueue(newCapacity);
    }

    private void halveQueueSize() {
        int newCapacity = this.count / 2;
        this.createNewQueue(newCapacity);
    }

    private void createNewQueue(int newSize) {
        Item[] newQueue = (Item[]) new Object[newSize];
        for (int i = 0; i < newSize; i++)
            newQueue[i] = this.queue[i];
        this.queue = newQueue;
    }

    // return a random item, but do not remove it
    public Item sample() {
        this.checkForElement();
        int randomIndex = StdRandom.uniform(this.count);
        return this.queue[randomIndex];
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

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new QueueIterator();
    }

    private class QueueIterator implements Iterator<Item> {
        // Iterator.  Each iterator must return the items in uniformly random
        // order. The order of two or more iterators to the same randomized
        // queue must be mutually independent; each iterator must maintain its
        // own random order.

        // create a new array with the count;

        // this needs to be random index
        private int queueIndex;


        public boolean hasNext() {
            return queue[queueIndex] != null;
        }

        public Item next() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            Item item = queue[queueIndex];
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public static void main(String[] args) {

    }
}
