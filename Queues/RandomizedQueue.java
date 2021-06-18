import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Node front;
    private Node back;
    private int size;

    // construct an empty randomized queue
    public RandomizedQueue() {
        front = null;
        back = null;
        size = 0;

    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        if (size == 0) {
            return true;
        } else {
            return false;
        }
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    // add the item to the front
    public void enqueue(Item item) {
        // Case: 0 items (set both front and back)
        // Case: 1 item (overwrite front where 'next' is back. Set back's left to be front)
        // Case: 2+ items (right is old front, set new front)

        Node temp = new Node();
        temp.item = item;

        if (size == 0) {
            back = temp;
        }
        if (size == 1) {
            temp.right = back;
            back.left = temp;
        }
        if (size >= 2) {
            temp.right = front;
            front.left = temp;
        }
        front = temp;
        size++;
    }

    // remove and return a random item
    public Item dequeue() {
        if (size == 0) throw new NoSuchElementException();
        Node temp = front;
        int index = StdRandom.uniform(size) + 1;
        if (size == 1) {
            Item item = front.item;
            front = null;
            back = null;
            size--;
            return item;
        } else {
            if (index == 1) {
                Node temp2 = front;
                Item item = temp2.item;
                front = front.right;
                temp2 = null;
                front.left = null;
                return item;
            } else if (index == size) {
                Node temp2 = back;
                Item item = temp2.item;
                back = back.left;
                temp2 = null;
                back.right = null;
                return item;
            } else {
                for (int i = 1; i < index; i++) {
                    temp = temp.right;
                }
                Item item = temp.item;
                removenode(temp);
                size--;
                return item;
            }
        }
    }

    private void removenode(Node node) {
        Node rear = node.left;
        Node forward = node.right;
        rear.right = forward;
        forward.left = rear;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (size == 0) throw new NoSuchElementException();
        Node temp = front;
        int index = StdRandom.uniform(size) + 1;
        if (size == 1) {
            return temp.item;
        } else {
            if (index == 1) {
                return front.item;
            } else if (index == size) {
                return back.item;
            } else {
                for (int i = 1; i < index; i++) {
                    temp = temp.right;
                }
                return temp.item;
            }
        }

    }

    private class Node {
        Item item;
        Node right;
        Node left;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private Node current = front;

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.right;
            return item;
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> randomqueue = new RandomizedQueue<Integer>();
        System.out.println(randomqueue.isEmpty());
        for (int i = 0; i < 5; i++) {
            randomqueue.enqueue(i);
        }
        System.out.println("****************");
        System.out.println(randomqueue.size());
        System.out.println(randomqueue.isEmpty());
        System.out.println("****************");
        for (int i : randomqueue) {
            System.out.println(i);
        }
        int item = randomqueue.dequeue();
        System.out.println("About to remove " + item);
        System.out.println("****************");
        for (int i : randomqueue) {
            System.out.println(i);
        }
        System.out.println("****************");
        System.out.println(randomqueue.size());
        System.out.println(randomqueue.isEmpty());
        System.out.println("****************");
        System.out.println(randomqueue.sample());
    }
}
