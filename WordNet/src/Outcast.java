import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

/**
 * Created by sand94 on 01.03.2017.
 */
public class Outcast {
    private WordNet wordNet;

    public Outcast(WordNet wordNet) {
        this.wordNet = wordNet;
    }

    public String outcast(String[] nouns) {
        int mx = 0;
        String res = "";
        for (String s0 : nouns) {
            int cur = 0;
            for (String s1 : nouns)
                cur += wordNet.distance(s0, s1);
            if (cur > mx) {
                res = s0;
                mx = cur;
            }
        }
        return res;
    }

    public static void main(String[] args) {
        WordNet wordNet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordNet);
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }
    }
}
