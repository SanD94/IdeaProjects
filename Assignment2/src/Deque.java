import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by sand94 on 21.09.2016.
 */
public class Deque<Item> implements Iterable<Item> {

    private int n;
    private Node front;
    private Node back;

    private class Node {
        private Item item;
        private Node next;
        private Node prev;
    }

    public Deque() {
        front = null;
        back = null;
        n = 0;
    }


    public boolean isEmpty() {
        return n == 0;
    }

    public int size() {
        return n;
    }

    public void addFirst(Item item) {
        if (item == null) throw new NullPointerException();
        Node oldFront = front;
        front = new Node();
        front.item = item;
        front.next = oldFront;
        if (isEmpty()) back = front;
        else oldFront.prev = front;
        n++;
    }

    public void addLast(Item item) {
        if (item == null) throw new NullPointerException();
        Node oldBack = back;
        back = new Node();
        back.item = item;
        back.prev = oldBack;
        if (isEmpty()) front = back;
        else oldBack.next = back;
        n++;
    }
    
    public Item removeFirst() {
        Item item = remove(front);
        front = front.next;
        n--;
        if (isEmpty()) back = null;
        else front.prev = null;

        return item;
    }

    public Item removeLast() {
        Item item = remove(back);
        back = back.prev;
        n--;
        if (isEmpty()) front = null;
        else back.next = null;
        return item;
    }

    @Override
    public Iterator<Item> iterator() {
        return new Iterator<Item>() {
            private Node current = front;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public Item next() {
                if (!hasNext()) throw new NoSuchElementException();
                Item item = current.item;
                current = current.next;
                return item;
            }
        };
    }

    private Item remove(Node x) {
        if (isEmpty()) throw new NoSuchElementException();
        return x.item;
    }

    public static void main(String[] args) {
        Deque<Integer> d = new Deque<>();
        d.addFirst(0);
        d.removeFirst();
    }
}
