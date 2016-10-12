import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;

/**
 * Created by sand94 on 22.09.2016.
 */
public class Subset {
    public static void main(String[] args) {
        RandomizedQueue<String> rQ = new RandomizedQueue<>();
        int k = Integer.parseInt(args[0]);
        for (int i = 0; i < k; i++)
            rQ.enqueue(StdIn.readString());
        int n = k;
        while (!StdIn.isEmpty()) {
            String next = StdIn.readString();
            n++;
            int i = StdRandom.uniform(n);
            if (i < k) {
                rQ.dequeue();
                rQ.enqueue(next);
            }
        }
        while (!rQ.isEmpty())
            System.out.println(rQ.dequeue());
    }
}
