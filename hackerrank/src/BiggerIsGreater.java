import java.util.Scanner;

/**
 * Created by sand94 on 22.10.2016.
 */
public class BiggerIsGreater {

    private static String nextW;

    private static void swap(char[] w, int a, int b){
        char c = w[a];
        w[a] = w[b];
        w[b] = c;
    }

    private static void reverse(char[] w, int start, int end) {
        char[] rev = new char[end-start];
        for (int i = end-1, j=0; i>=start; i--, j++)
            rev[j] = w[i];
        for (int i = 0, j = start; i < rev.length; i++, j++)
            w[j] = rev[i];
    }


    private static boolean nextPermutation(char[] w){
        int k = w.length - 2;
        while (k >= 0 && w[k] >= w[k+1]) k--;
        if (k < 0) return false;
        int l = w.length - 1;
        while (w[k] >= w[l]) l--;
        swap(w, k, l);
        reverse(w, k+1, w.length);
        nextW = new String(w);
        return true;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int t = in.nextInt();
        while (t-- != 0) {
            String w = in.next();
            String res = "no answer";
            if (nextPermutation(w.toCharArray()))
                res = nextW;
            System.out.println(res);
        }
    }


}
