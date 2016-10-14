import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by sand94 on 21.09.2016.
 */
public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] items;
    private int n;


    public RandomizedQueue() {
        n = 0;
        items = (Item[]) new Object[1];
    }

    public boolean isEmpty() {
        return n == 0;
    }

    public int size() { return n; }

    public void enqueue(Item item) {
        if (item == null)
            throw new NullPointerException();
        if (n == items.length) resize(2*items.length);
        items[n++] = item;
    }

    public Item dequeue() {
        if (isEmpty())
            throw new NoSuchElementException();
        int x = StdRandom.uniform(n);
        Item item = items[x];
        items[x] = items[--n];
        items[n] = null;
        if (n > 0 && n == items.length/4) resize(items.length/2);
        return item;
    }

    public Item sample() {
        if (isEmpty())
            throw new NoSuchElementException();
        return items[StdRandom.uniform(n)];
    }

    @Override
    public Iterator<Item> iterator() {
        int[] randNumbers = new int[n];
        for (int i = 0; i < n; i++)
            randNumbers[i] = i;
        StdRandom.shuffle(randNumbers);

        return new Iterator<Item>() {
            private int i = 0;

            @Override
            public boolean hasNext() {
                return i < n;
            }

            @Override
            public Item next() {
                if (!hasNext())
                    throw new NoSuchElementException();
                return items[randNumbers[i++]];
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    private void resize(int capacity) {
        Item[] temp = (Item[]) new Object[capacity];
        for (int i = 0; i < n; i++)
            temp[i] = items[i];
        items = temp;
    }
}
