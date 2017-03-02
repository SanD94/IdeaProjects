import edu.princeton.cs.algs4.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sand94 on 22.02.2017.
 */
public class WordNet {

    private SAP sap;
    private Map<String, Bag<Integer>> nouns;
    private ArrayList<String> synsets;
    private int count;

    //constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        if (synsets == null || hypernyms == null)
            throw new NullPointerException();


        In synsetFile = new In(synsets);
        this.nouns = new HashMap<>();
        this.synsets = new ArrayList<>();

        while (synsetFile.hasNextLine()) {
            String line = synsetFile.readLine();
            String[] parser = line.split(",");
            int id = Integer.parseInt(parser[0]);
            this.synsets.add(parser[1]);
            String[] currentNouns = parser[1].split(" ");
            count++;
            for (String s : currentNouns) {
                Bag<Integer> cur = new Bag<>();
                if (!isNoun(s))  nouns.put(s, cur);
                cur = nouns.get(s);
                cur.add(id);
            }
        }
        synsetFile.close();

        In hypernymFile = new In(hypernyms);
        Digraph digraph = new Digraph(count);

        while (hypernymFile.hasNextLine()) {
            String line = hypernymFile.readLine();
            String[] parser = line.split(",");
            int cur = Integer.parseInt(parser[0]);
            for (int i = 1; i < parser.length; i++)
                digraph.addEdge(cur, Integer.parseInt(parser[i]));
        }
        hypernymFile.close();

        if (!isRootedDAG(digraph))
            throw new IllegalArgumentException();
        this.sap = new SAP(digraph);
    }

    private boolean isRootedDAG(Digraph digraph) {
        boolean[] mark = new boolean[digraph.V()];
        boolean[] done = new boolean[digraph.V()];
        int count = 0;
        boolean haveCycle = false;
        for (int i = 0; i < digraph.V(); i++) {
            if (!done[i]) haveCycle |= dfs(mark, done, i, digraph);
            if (digraph.outdegree(i) == 0) count ++;
        }
        return !haveCycle && count == 1;
    }

    private boolean dfs(boolean[] mark, boolean[] done, int cur, Digraph digraph) {
        boolean res = false;
        mark[cur] = true;
        done[cur] = true;
        for (int adj : digraph.adj(cur)) {
            if (!done[adj]) res |=  dfs(mark, done, adj, digraph);
            if (mark[adj]) return true;
        }
        mark[cur] = false;
        return res;
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return nouns.keySet();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String noun) {
        nullExcept(noun);
        return nouns.containsKey(noun);
    }


    // distance between nounA and nounB
    public int distance(String nounA, String nounB) {
        nullExcept(nounA);
        nullExcept(nounB);
        if (!isNoun(nounA) || !isNoun(nounB))
            throw new IllegalArgumentException();
        return sap.length(nouns.get(nounA), nouns.get(nounB));
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path
    public String sap(String nounA, String nounB) {
        nullExcept(nounA);
        nullExcept(nounB);
        if (!isNoun(nounA) || !isNoun(nounB))
            throw new IllegalArgumentException();
        return synsets.get(sap.ancestor(nouns.get(nounA), nouns.get(nounB)));
    }

    private void nullExcept(String s) {
        if ( s == null )
            throw new NullPointerException();
    }

    // do unit testing of this class
    public static void main(String[] args) {
        WordNet w = new WordNet("wordnet/synsets3.txt", "wordnet/hypernyms3InvalidTwoRoots.txt");
        for (String s : w.nouns())
            System.out.println(s);
    }


}
