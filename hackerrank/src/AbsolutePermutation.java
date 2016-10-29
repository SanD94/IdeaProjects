import java.util.Scanner;

/**
 * Created by sand94 on 29.10.2016.
 */
public class AbsolutePermutation {

    private static void swap(int[] perm, int a, int b) {
        int temp = perm[a];
        perm[a] = perm[b];
        perm[b] = temp;
    }

    private static void eval(int n, int k) {
        boolean[] mark = new boolean[n];
        int[] perm = new int[n];
        for (int i = 0; i < n; i++)
            perm[i] = i + 1;
        int count = 0;
        for (int i = 0; i < n; i++)  {
            if (mark[i]) {
                count++;
                continue;
            }
            else if (i + k < n) {
                mark[i] = mark[i+k] = true;
                swap(perm, i, i+k);
                count++;
            }
        }
        if (count < n)
            System.out.print(-1);
        else
            for (int i = 0; i < n; i++)
                System.out.print(perm[i] + " ");
        System.out.println();
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int t = in.nextInt();
        while (t-- != 0) {
            int n = in.nextInt();
            int k = in.nextInt();
            eval(n,k);
        }
    }
}
