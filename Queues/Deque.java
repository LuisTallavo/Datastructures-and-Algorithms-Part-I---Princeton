import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Node front;
    private Node back;
    private int size;

    // construct an empty deque
    public Deque() {
        front = null;
        back = null;
        size = 0;

    }

    // is the deque empty?
    public boolean isEmpty() {
        if (size == 0) {
            return true;
        } else {
            return false;
        }
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
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

    // add the item to the back
    public void addLast(Item item) {
        // Case: 0 items (set both front and back)
        // Case: 1 item (Change the current nodes right to be the new node, the new nodes left to be the current node, assign the new back)
        // Case: 2+ items (Change the new nodes left to be the back, the backs right to be the new node, assign the new back)

        Node temp = new Node();
        temp.item = item;

        if (size == 0) {
            front = temp;
        }
        if (size == 1) {
            temp.left = front;
            front.right = temp;
        }
        if (size >= 2) {
            temp.left = back;
            back.right = temp;
        }
        back = temp;
        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        // Case: 0 items (throw an exception)
        // Case: 1 item (remove the only item)
        // Case 2+ items (remove the front item only)
        Item item = front.item;
        if (size == 0) {
            throw new NoSuchElementException();
        }
        if (size == 1) {
            front = null;
            back = null;
        }
        if (size >= 2) {
            front = front.right;
            front.left = null;
        }
        size--;
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        // Case: 0 items (throw an exception)
        // Case: 1 item (remove the only item)
        // Case 2+ items (remove the last item only)
        Item item = back.item;
        if (size == 0) {
            throw new NoSuchElementException();
        }
        if (size == 1) {
            front = null;
            back = null;
        }
        if (size >= 2) {
            back = back.left;
            back.right = null;
        }
        size--;
        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class Node {
        Item item;
        Node right;
        Node left;
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
        Deque<Integer> queue = new Deque<Integer>();
        System.out.println(queue.isEmpty());
        for (int i = 0; i < 5; i++) {
            queue.addLast(i);
        }
        System.out.println("****************");
        System.out.println(queue.size());
        System.out.println(queue.isEmpty());
        System.out.println("****************");
        for (int i = 0; i < 5; i++) {
            queue.addFirst(i);
        }
        System.out.println(queue.size());
        System.out.println(queue.isEmpty());
        System.out.println("****************");
        for (int i : queue) {
            System.out.println(i);
        }
        queue.removeFirst();
        queue.removeLast();
        queue.removeLast();
        System.out.println("****************");
        for (int i : queue) {
            System.out.println(i);
        }
        System.out.println("****************");
        for (int i : queue) {
            queue.removeFirst();
        }
        System.out.println(queue.size());
        System.out.println(queue.isEmpty());
    }
}
